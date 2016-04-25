package com.djl.pxmatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.djl.doc.FormatDoc;

/**
 * 
 * 适配方案二，全像素适配<br>
 * 第三步，抽取(把dimens.xml中抽取好的px以1280x720为基准适配到其它屏幕尺寸)
 * 
 * @author DJL E-mail:
 * @date 2015-11-30 下午3:36:14
 * @version 1.0
 * @parameter
 * @since
 */
public class FullPXMacher3 {
	public static String[] XAttrs = { "horizontal", "width", "top", "bottom", "side" };
	public static String[] YAttrs = { "vertical", "height", "left", "right", "margin", "padding",
			"textsize" };
	public static Pattern patternDP = Pattern.compile("[0-9]*dp");
	public static Pattern patternSP = Pattern.compile("[0-9]*sp");
	public static Pattern patternDIP = Pattern.compile("[0-9]*dip");
	public static Pattern patternPX = Pattern.compile("[0-9]*px");
	public static String projectPath = "E:\\AndroidStudioWorkSpace\\shangshihuihai\\ShangShiHuiHai\\app\\src\\main";
	private static String Template = "<dimen name=\"{0}\">{1}px</dimen>\n";
	public static String rootPath = projectPath + "\\res\\values-{0}x{1}\\";
	public static String rootPath2 = projectPath + "\\res\\values-{0}\\";
	private static String Template2 = "<dimen name=\"{0}\">{1}dp</dimen>\n";

	public static void main(String[] args) {
		// File dimens = new File(projectPath + "\\res\\values\\dimens.xml");
		File dimens = new File(projectPath + "\\res\\values-1280x720\\dimens.xml");
		// 获取dimensPXInfos
		HashMap<String, String> dimensPXInfos = getPXFromDimens(dimens);
		// annotation(0.5f, null, dimensPXInfos);
		annotation(320, 480, dimensPXInfos);
		annotation(360, 640, dimensPXInfos);
		annotation(480, 800, dimensPXInfos);
		annotation(540, 960, dimensPXInfos);
		annotation(600, 1024, dimensPXInfos);
		annotation(720, 1184, dimensPXInfos);
		annotation(720, 1196, dimensPXInfos);
		annotation(720, 1280, dimensPXInfos);
		annotation(768, 1024, dimensPXInfos);
		annotation(800, 1280, dimensPXInfos);
		annotation(1080, 1776, dimensPXInfos);
		annotation(1080, 1812, dimensPXInfos);
		annotation(1080, 1920, dimensPXInfos);
		annotation(1200, 1920, dimensPXInfos);
		annotation(1440, 2560, dimensPXInfos);
		annotation(1600, 2560, dimensPXInfos);
		// 模糊适配
		// annotationRough(dimensPXInfos);
		ArrayList<ScreenSize> list = parser();
		for (ScreenSize screenSize : list) {
			if (screenSize.width * screenSize.hight > 0) {
				annotation(screenSize.width, screenSize.hight, dimensPXInfos);
			}
		}
	}

	private static void annotationRough(HashMap<String, String> dimensPXInfos) {
		// TODO 426 small 470 normal 640 large 960 XLarge
		float small = (426 + 470) / 2;
		float normal = (470 + 640) / 2;
		float large = (640 + 960) / 2;
		float XLarge = 960;
		float defaultSize = 640;
		float smallScall = (small / defaultSize) / 2;
		float normalScall = (normal / defaultSize) / 2;
		float largeScall = (large / defaultSize) / 2;
		float XLargeScall = (XLarge / defaultSize) / 2;
		// small, normal, large, xlarge
		annotation(smallScall, "small", dimensPXInfos);
		annotation(normalScall, "normal", dimensPXInfos);
		annotation(largeScall, "large", dimensPXInfos);
		annotation(XLargeScall, "xlarge", dimensPXInfos);
	}

	private static HashMap<String, String> getPXFromDimens(File f) {
		HashMap<String, String> map = new HashMap<>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			// 解析
			org.w3c.dom.Document doc = builder.parse(f);
			Element root = doc.getDocumentElement();
			NodeList childNodes = root.getChildNodes();
			if (childNodes != null && childNodes.getLength() > 0) {
				for (int i = 0; i < childNodes.getLength(); i++) {
					Node item = childNodes.item(i);
					if (item.getNodeType() == Node.ELEMENT_NODE) {
						String nodeValue = item.getAttributes().getNamedItem("name").getNodeValue();
						System.out.println(nodeValue);
						String nodeValue2 = item.getTextContent();
						System.out.println(nodeValue2);
						map.put(nodeValue, nodeValue2);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * f 将要修改其中特定内容的文件 src 将被替换的内容 dst 将被替换层的内容
	 */
	public static void annotation(int x, int y, HashMap<String, String> map) {
		File f = new File(rootPath.replace("{0}", y + "").replace("{1}", x + "") + "dimens.xml");
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdir();
		}
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		float celly = y / 1280f;
		float cellx = x / 720f;
		Set<Entry<String, String>> entrySet = map.entrySet();
		StringBuilder sb = new StringBuilder();
		sb.append("<resources>");
		for (Entry<String, String> e : entrySet) {
			int i = Integer.parseInt(e.getValue().replace("px", ""));
			if (e.getKey().contains("x")) {
				sb.append(Template.replace("{0}", e.getKey()).replace("{1}",
						"" + Math.round(i * cellx)));
			} else {
				sb.append(Template.replace("{0}", e.getKey()).replace("{1}",
						"" + Math.round(i * celly)));
			}
		}
		sb.append("</resources>");
		FormatDoc.write(sb.toString(), f);
	}

	/**
	 * f 将要修改其中特定内容的文件 src 将被替换的内容 dst 将被替换层的内容
	 */
	public static void annotation(float cell, String name, HashMap<String, String> map) {
		File f = new File(name == null ? projectPath + "\\res\\values\\" : rootPath2.replace("{0}",
				name) + "dimens.xml");
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdir();
		}
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		float celly = cell;
		float cellx = cell;
		Set<Entry<String, String>> entrySet = map.entrySet();
		StringBuilder sb = new StringBuilder();
		sb.append("<resources>");
		for (Entry<String, String> e : entrySet) {
			int i = Integer.parseInt(e.getValue().replace("px", ""));
			if (e.getKey().contains("x")) {
				sb.append(Template2.replace("{0}", e.getKey()).replace("{1}",
						"" + Math.round(i * cellx)));
			} else {
				sb.append(Template2.replace("{0}", e.getKey()).replace("{1}",
						"" + Math.round(i * celly)));
			}
		}
		sb.append("</resources>");
		FormatDoc.write(sb.toString(), f);
	}

	public static class ScreenSize {
		public int width, hight;

		/**
		 * 
		 * @param size
		 *            1280x720
		 */
		public ScreenSize(String size) {
			String[] split = size.split("x");
			try {
				hight = Integer.parseInt(split[0].trim());
				width = Integer.parseInt(split[1].trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public ScreenSize(int width, int hight) {
			this.width = width;
			this.hight = hight;
		}

	}

	public static ArrayList<ScreenSize> parser() {
		ArrayList<ScreenSize> list = new ArrayList<>();
		String line = null;
		InputStreamReader isr;
		try {
			isr = new InputStreamReader(new FileInputStream("screenSizes.txt"));
			System.out.println("fileReader.getEncoding()=" + isr.getEncoding());
			BufferedReader reader = new BufferedReader(isr);
			int i = 0;
			while ((line = reader.readLine()) != null) {
				int indexOf = line.indexOf("\t");
				if (indexOf < 0)
					indexOf = line.indexOf(" ");
				if (indexOf > 0) {
					char[] dst = new char[indexOf];
					line.getChars(0, indexOf, dst, 0);
					String size = new String(dst).trim();
					System.out.println(size);
					list.add(new ScreenSize(size));
					i++;
				}
			}
			System.out.println(i);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}

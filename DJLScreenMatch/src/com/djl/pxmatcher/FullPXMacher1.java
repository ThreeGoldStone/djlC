package com.djl.pxmatcher;

import java.io.File;
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
 * 第一步，抽取(把dimens.xml中的dp/sp/px/dip抽取出来，还原到layout.xml中)
 * 
 * @author DJL E-mail:
 * @date 2015-11-30 下午3:36:14
 * @version 1.0
 * @parameter
 * @since
 */
public class FullPXMacher1 {
	public static String[] XAttrs = { "horizontal", "width", "top", "bottom", "side" };
	public static String[] YAttrs = { "vertical", "height", "left", "right", "margin", "padding",
			"textsize" };
	public static Pattern patternDP = Pattern.compile("[0-9]*dp");
	public static Pattern patternSP = Pattern.compile("[0-9]*sp");
	public static Pattern patternDIP = Pattern.compile("[0-9]*dip");
	public static Pattern patternPX = Pattern.compile("[0-9]*px");

	public static void main(String[] args) {
		String projectPath = "E:\\Android_WorkSpace_1\\HHY2";
		// dimens获取的位置
		// File dimens = new File(projectPath + "\\res\\values\\dimens.xml");
		File dimens = new File(projectPath + "\\res\\values-1280x720\\dimens.xml");
		// 获取dimensPXInfos
		HashMap<String, String> dimensPXInfos = getPXFromDimens(dimens);
		// 获取dimensPXInfos,
		HashMap<String, String> dimensPXInfos2 = new HashMap<>();
		Set<Entry<String, String>> entrySet = dimensPXInfos.entrySet();
		for (Entry<String, String> e : entrySet) {
			dimensPXInfos2.put("\"" + e.getKey() + "\"", "\"" + e.getValue() + "\"");
		}
		// /layout文件夹下
		File layouts = new File(projectPath + "\\res\\layout");
		File[] ls = layouts.listFiles();
		for (File layout : ls) {
			// 遍历解析抽取
			if (layout.getAbsolutePath().endsWith(".xml")) {
				annotation(layout, dimensPXInfos2);
			}
		}
		// /drawable文件夹下
		File drawable = new File(projectPath + "\\res\\drawable");
		File[] lsDrawable = layouts.listFiles();
		for (File drawble : lsDrawable) {
			// 遍历解析抽取
			if (drawble.getAbsolutePath().endsWith(".xml")) {
				annotation(drawble, dimensPXInfos2);
			}
		}
		// /styles文件下
		File style = new File(projectPath + "\\res\\values\\styles.xml");
		annotation(style, dimensPXInfos);

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
						String nodeValue = "@dimen/"
								+ item.getAttributes().getNamedItem("name").getNodeValue();
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
	public static void annotation(File f, HashMap<String, String> map) {
		String content = FormatDoc.read(f);
		Set<Entry<String, String>> entrySet = map.entrySet();
		for (Entry<String, String> entry : entrySet) {
			content = content.replace(entry.getKey(), entry.getValue());
		}
		FormatDoc.write(content, f);
	}
}

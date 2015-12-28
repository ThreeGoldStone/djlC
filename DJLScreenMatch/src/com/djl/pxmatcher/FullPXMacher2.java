package com.djl.pxmatcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.djl.doc.FormatDoc;

/**
 * 
 * 适配方案二，全像素适配<br>
 * 第二步，抽取(把layout.xml中的dp/sp/px/dip抽取出来，转化为以1280x720为标准的px值，写入dimens.xml中)
 * 
 * @author DJL E-mail:
 * @date 2015-11-30 下午3:36:14
 * @version 1.0
 * @parameter
 * @since
 */
public class FullPXMacher2 {
	private static boolean isStyle = false;
	public static ArrayList<DimensPXInfo> dimensPXInfos;
	public static String[] XAttrs = { "horizontal", "width", "top", "bottom", "side" };
	public static String[] YAttrs = { "vertical", "height", "left", "right", "margin", "padding",
			"textsize" };
	public static Pattern patternDP = Pattern.compile("[0-9]*dp");
	public static Pattern patternSP = Pattern.compile("[0-9]*sp");
	public static Pattern patternDIP = Pattern.compile("[0-9]*dip");
	public static Pattern patternPX = Pattern.compile("[0-9]*px");

	public static void main(String[] args) {
		dimensPXInfos = new ArrayList<>();
		// 项目路径
		String projectPath = "E:\\Android_WorkSpace_1\\HHY2";
		File layouts = new File(projectPath + "\\res\\layout");
		File[] ls = layouts.listFiles();
		for (File layout : ls) {
			// 遍历解析抽取
			exchange(layout);
		}
		isStyle = true;
		File style = new File(projectPath + "\\res\\values\\styles.xml");
		exchange(style);
		isStyle = false;
		// File dimens = new File(projectPath + "\\res\\values\\dimens.xml");
		File dimens = new File(projectPath + "\\res\\values-1280x720\\dimens.xml");
		if (!dimens.getParentFile().exists()) {
			dimens.getParentFile().mkdir();
		}
		if (!dimens.exists()) {
			try {
				dimens.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		StringBuilder sb = new StringBuilder();
		for (DimensPXInfo d : dimensPXInfos) {
			sb.append(d.getNameL());
		}
		FormatDoc.annotation(dimens, "</resources>", sb.append("</resources>").toString());
	}

	/**
	 * 修改一个文件
	 * 
	 * @param f
	 */
	public static void exchange(File f) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			// 解析
			org.w3c.dom.Document doc = builder.parse(f);
			Element root = doc.getDocumentElement();
			// 替换
			change(root);
			// 写入
			writeToXML(doc, f.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 遍历及替换对应的attr的值
	 * 
	 * @param element
	 */
	public static void change(Element element) {
		NamedNodeMap attributes = element.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Node a = attributes.item(i);
			String nodeName = a.getNodeName();
			String nodeValue = a.getNodeValue();
			if (nodeName.equals("name")) {
				nodeName = nodeValue;
				nodeValue = element.getTextContent();
			}
			System.out.println("nodeName=" + nodeName);
			System.out.println("nodeValue=" + nodeValue);
			DimensPXInfo d = getNodeChangeValue(nodeName, nodeValue);
			if (d != null) {
				if (!dimensPXInfos.contains(d)) {
					dimensPXInfos.add(d);
				}
				String changevalue = d.getName();
				if (isStyle) {
					element.setTextContent(changevalue);
				} else {
					element.setAttribute(nodeName, changevalue);
				}
			}
		}
		NodeList childNodes = element.getChildNodes();
		if (childNodes != null && childNodes.getLength() > 0) {
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node item = childNodes.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					change((Element) item);
				}
			}
		}
	}

	/***
	 * 
	 * 
	 * @param nodeName
	 * @param nodeValue
	 * @return 替换attr的值
	 */
	private static DimensPXInfo getNodeChangeValue(String nodeName, String nodeValue) {
		if (patternDIP.matcher(nodeValue).matches() || patternDP.matcher(nodeValue).matches()
				|| patternSP.matcher(nodeValue).matches() || patternPX.matcher(nodeValue).matches()) {
			// 长度数值
			int lenth = 0;
			if (patternDIP.matcher(nodeValue).matches())
				lenth = Integer.parseInt(nodeValue.replace("dip", "").trim());
			;
			if (patternDP.matcher(nodeValue).matches())
				lenth = Integer.parseInt(nodeValue.replace("dp", "").trim());
			;
			if (patternSP.matcher(nodeValue).matches())
				lenth = Integer.parseInt(nodeValue.replace("sp", "").trim());
			;
			if (patternPX.matcher(nodeValue).matches())
				lenth = Math.round(Integer.parseInt(nodeValue.replace("px", "").trim()) / 2f);
			;
			// 如果<=2px跳过
			if (lenth <= 1) {
				return null;
			}
			// 是宽还是高
			int type = getNodeNameType(nodeName);
			switch (type) {
			case 1:
			case 2:
				return new DimensPXInfo(lenth, type);
			default:
				break;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param nodeName
	 * @return 0表示没有，1表示与Y有关，2表示与X有关
	 */
	private static int getNodeNameType(String nodeName) {
		int result = 0;
		for (int i = 0; i < YAttrs.length; i++) {
			if (nodeName.toLowerCase().contains(YAttrs[i])) {
				result = 1;
			}
		}
		for (int i = 0; i < XAttrs.length; i++) {
			if (nodeName.toLowerCase().contains(XAttrs[i])) {
				result = 2;
			}
		}
		return result;
	}

	/**
	 * 把doc写入文件
	 * 
	 * @param doc
	 * @param fileName
	 */
	private static void writeToXML(Document doc, String fileName) {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(doc);
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			// PrintWriter pw = new PrintWriter(new FileOutputStream());
			StreamResult result = new StreamResult(new OutputStreamWriter(new FileOutputStream(
					fileName), "utf-8"));
			// StreamResult result = new StreamResult(pw);
			// trans.transform(xmlSource, );
			transformer.transform(source, result); // 关键转换
			// System.out.println("生成XML文件成功!");
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	public static class DimensPXInfo {
		private final static String WTemplate = "@dimen/x{0}";
		private final static String HTemplate = "@dimen/y{0}";
		private final static String WTemplateL = "<dimen name=\"x{0}\">{1}px</dimen>\n";
		private final static String HTemplateL = "<dimen name=\"y{0}\">{1}px</dimen>\n";
		// 目标大小如18
		public int lenth;
		// 发现次数
		public int findTimes;
		// 是1高2宽
		public int type;

		public DimensPXInfo(int lenth, int type) {
			this.lenth = lenth;
			this.type = type;
		}

		public String getName() {
			switch (type) {
			case 1:
				return HTemplate.replace("{0}", lenth * 2 + "");
			case 2:
				return WTemplate.replace("{0}", lenth * 2 + "");
			default:
				break;
			}
			return null;
		}

		public String getNameL() {
			switch (type) {
			case 1:
				return HTemplateL.replace("{0}", lenth * 2 + "").replace("{1}", lenth * 2 + "");
			case 2:
				return WTemplateL.replace("{0}", lenth * 2 + "").replace("{1}", lenth * 2 + "");
			default:
				break;
			}
			return null;
		}

		@Override
		public boolean equals(Object obj) {
			DimensPXInfo obj2 = (DimensPXInfo) obj;
			boolean b = obj2 != null && obj2.lenth == lenth && obj2.type == type;
			System.out.println("equals" + b);
			return b;
		}
	}
}

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
 * ���䷽������ȫ��������<br>
 * �ڶ�������ȡ(��layout.xml�е�dp/sp/px/dip��ȡ������ת��Ϊ��1280x720Ϊ��׼��pxֵ��д��dimens.xml��)
 * 
 * @author DJL E-mail:
 * @date 2015-11-30 ����3:36:14
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
		// ��Ŀ·��
		String projectPath = "E:\\Android_WorkSpace_1\\HHY2";
		File layouts = new File(projectPath + "\\res\\layout");
		File[] ls = layouts.listFiles();
		for (File layout : ls) {
			// ����������ȡ
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
	 * �޸�һ���ļ�
	 * 
	 * @param f
	 */
	public static void exchange(File f) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			// ����
			org.w3c.dom.Document doc = builder.parse(f);
			Element root = doc.getDocumentElement();
			// �滻
			change(root);
			// д��
			writeToXML(doc, f.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �������滻��Ӧ��attr��ֵ
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
	 * @return �滻attr��ֵ
	 */
	private static DimensPXInfo getNodeChangeValue(String nodeName, String nodeValue) {
		if (patternDIP.matcher(nodeValue).matches() || patternDP.matcher(nodeValue).matches()
				|| patternSP.matcher(nodeValue).matches() || patternPX.matcher(nodeValue).matches()) {
			// ������ֵ
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
			// ���<=2px����
			if (lenth <= 1) {
				return null;
			}
			// �ǿ��Ǹ�
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
	 * @return 0��ʾû�У�1��ʾ��Y�йأ�2��ʾ��X�й�
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
	 * ��docд���ļ�
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
			transformer.transform(source, result); // �ؼ�ת��
			// System.out.println("����XML�ļ��ɹ�!");
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
		// Ŀ���С��18
		public int lenth;
		// ���ִ���
		public int findTimes;
		// ��1��2��
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

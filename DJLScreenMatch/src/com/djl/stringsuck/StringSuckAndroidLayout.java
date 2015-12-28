package com.djl.stringsuck;

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
public class StringSuckAndroidLayout {
	private static boolean isStyle = false;
	public static ArrayList<StringInfo> stringInfos;
	public static String[] StringAttrs = { "android:text", "android:hint" };
	public static Pattern patternDP = Pattern.compile("[0-9]*dp");
	public static Pattern patternSP = Pattern.compile("[0-9]*sp");
	public static Pattern patternDIP = Pattern.compile("[0-9]*dip");
	public static Pattern patternPX = Pattern.compile("[0-9]*px");

	public static void main(String[] args) {
		stringInfos = new ArrayList<>();
		// ��Ŀ·��
		String projectPath = "E:\\Android_WorkSpace_1\\HHY2";
		File layouts = new File(projectPath + "\\res\\layout");
		File[] ls = layouts.listFiles();
		for (File layout : ls) {
			// ����������ȡ
			exchange(layout);
		}
		// isStyle = true;
		// File style = new File(projectPath + "\\res\\values\\styles.xml");
		// exchange(style);
		// isStyle = false;
		File strings = new File(projectPath + "\\res\\values\\strings.xml");
		if (!strings.getParentFile().exists()) {
			strings.getParentFile().mkdir();
		}
		if (!strings.exists()) {
			try {
				strings.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		StringBuilder sb = new StringBuilder();
		for (StringInfo d : stringInfos) {
			sb.append(d.getNameL());
		}
		FormatDoc.annotation(strings, "</resources>", sb.append("</resources>").toString());
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
			StringInfo e = new StringInfo(f.getName(), 1);
			stringInfos.add(e);
			int stringsSize = stringInfos.size();
			change(root);
			if (stringsSize == stringInfos.size()) {
				stringInfos.remove(e);
			} else {
				stringInfos.add(new StringInfo(f.getName(), 2));
			}
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
			StringInfo d = getNodeChangeValue(nodeName, nodeValue);
			if (d != null) {
				if (!stringInfos.contains(d)) {
					stringInfos.add(d);
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
	private static StringInfo getNodeChangeValue(String nodeName, String nodeValue) {
		// �ǿ��Ǹ�
		int type = getNodeNameType(nodeName);
		if (nodeValue.contains("@string/")) {
			return null;
		}
		switch (type) {
		case 1:
		case 2:
			return new StringInfo(nodeValue);
		default:
			break;
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
		for (int i = 0; i < StringAttrs.length; i++) {
			if (nodeName.equals(StringAttrs[i])) {
				result = 1;
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

	public static class StringInfo {
		private static int index;
		private final static String WTemplate = "@string/s{0}";
		// <string name="percent_tag">%</string>
		private final static String WTemplateL = "<string name=\"s{0}\">\"{1}\"</string>\n";
		private final static String attr = "<!--{0}          {1}-->\n";
		private String content;
		private int isStart;
		private int indexCurrent = -1;

		public StringInfo(String s) {
			this.content = s;
		}

		/**
		 * 
		 * @param absolutePath
		 * @param isStart
		 *            1��ʼһ���ļ���2����һ���ļ�
		 */
		public StringInfo(String absolutePath, int isStart) {
			this.isStart = isStart;
			content = absolutePath;
		}

		public String getName() {
			if (indexCurrent < 0) {
				indexCurrent = index++;
			}
			return WTemplate.replace("{0}", indexCurrent + "");
		}

		public String getNameL() {
			switch (isStart) {
			case 1:
				return attr.replace("{0}", content).replace("{1}", "start");
			case 2:
				return attr.replace("{0}", content).replace("{1}", "end");
			default:
				if (indexCurrent < 0) {
					indexCurrent = index++;
				}
				return WTemplateL.replace("{0}", indexCurrent + "").replace("{1}", content + "");
			}

		}

	}
}

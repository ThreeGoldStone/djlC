package com.djl.doc;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 作为结构图的BaseBean
 * 
 * @author DJL E-mail:
 * @date 2015-11-30 下午2:50:55
 * @version 1.0
 * @parameter
 * @since
 */
public class LayoutXml {
	private ArrayList<LayoutXml> sons;
	public String title;
	public String content;
	public double progress;
	public double weight;

	public void addSon(LayoutXml son) {
		if (sons == null) {
			sons = new ArrayList<>();
		}
		sons.add(son);
	}

	public ArrayList<LayoutXml> getSons() {
		return sons;
	}

	public LayoutXml() {
	}

	public LayoutXml(Element element) {
		NamedNodeMap attributes = element.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Node a = attributes.item(i);
			String nodeName = a.getNodeName();
			System.out.println("nodeName=" + nodeName);
			String nodeValue = a.getNodeValue();
			System.out.println("nodeValue=" + nodeValue);
		}
		NodeList childNodes = element.getChildNodes();
		if (childNodes != null && childNodes.getLength() > 0) {
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node item = childNodes.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					addSon(new LayoutXml((Element) item));
				}
			}
		}
	}

	public Element ToElement(Document doc) {
		Class<? extends LayoutXml> clazz = this.getClass();
		String tag = clazz.getSimpleName();
		org.w3c.dom.Element root = doc.createElement(tag);
		Field[] fields = clazz.getFields();
		for (Field field : fields) {
			try {
				root.setAttribute(field.getName(), field.get(this) + "");

			} catch (DOMException | IllegalAccessException | IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		if (getSons() != null && getSons().size() > 0) {
			for (LayoutXml son : getSons()) {
				root.appendChild(son.ToElement(doc));
			}
		}

		return root;

	}
}

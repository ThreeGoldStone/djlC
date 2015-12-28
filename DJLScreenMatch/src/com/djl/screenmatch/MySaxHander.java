package com.djl.screenmatch;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import dimens.Dimes;

class MySaxHander extends DefaultHandler {
	public ArrayList<Dimes> dimes = new ArrayList<>();
	private Dimes temp;

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// System.out.println("uri \t" + uri);
		// System.out.println("localName \t" + localName);
		// int i = 0;
		// String value = null;
		// while ((value = attributes.getValue(i)) != null) {
		// System.out.println("attributes \t" + i + value);
		// i++;
		// }
		if (qName.equals("dimen")) {
			// System.out.println("qName \t" + qName);
			temp = new Dimes();
		}
		if (temp != null) {
			temp.setName(attributes.getValue(attributes.getIndex("name")));
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String value = new String(ch, start, length);
		// System.out.println("characters \t" + value);
		if (temp != null) {
			temp.setValue(value);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// System.out.println("End uri \t" + uri);
		// System.out.println("End localName \t" + localName);
		// System.out.println("End qName \t" + qName + "\n");
		if (qName.equals("dimen")) {
			if (temp != null) {
				dimes.add(temp);
				temp = null;
			}
		}
	}

}
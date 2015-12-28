package com.djl.doc;

import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * @author DJL E-mail:
 * @date 2015-12-8 ионГ9:16:36
 * @version 1.0
 * @parameter
 * @since
 */
public class PullParser {
	public static void main(String[] args) {
		SAXBuilder saxBuilder = new SAXBuilder();
		try {
			Document doc = saxBuilder.build(new File(""));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

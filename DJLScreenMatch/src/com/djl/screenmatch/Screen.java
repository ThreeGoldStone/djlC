package com.djl.screenmatch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import dimens.Dimes;

public class Screen {
	// /** ���ظ� */
	// public int hpx;
	// /** ���ؿ� */
	// public int wpx;
	// /** px/dp */
	// public float px_dp;
	/** dp�� */
	public int hdp;
	/** dp�� */
	public int wdp;
	/** ��Ÿķֱ���dp����xml��·�� */
	private String dpXmlPath;
	private ArrayList<Dimes> dimes;

	public Screen(int hpx, int wpx, float px_dp) {
		this.hdp = Math.round(hpx / px_dp);
		this.wdp = Math.round(wpx / px_dp);
	}

	public Screen(String dpXmlPath) {
		this.dpXmlPath = dpXmlPath;
		String[] pathArray = dpXmlPath.split("/");
		String forderName = pathArray[pathArray.length - 2];
		String[] values = forderName.split("-");
		switch (values.length) {
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;

		default:
			break;
		}
	}

	/**
	 * ��ȡdimes xml
	 * 
	 * @param path
	 * @return name value pear
	 */
	public ArrayList<Dimes> parse() {
		if (dimes != null && dimes.size() > 0) {
			return dimes;
		}
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = null;
		try {
			sp = spf.newSAXParser();
			MySaxHander dh = new MySaxHander();
			if (getDpXmlPath() == null) {
				return null;
			}
			sp.parse(new File(getDpXmlPath()), dh);
			dimes = dh.dimes;
			for (Dimes d : dimes) {
				System.out.println(d.toString());
			}
			return dimes;
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param input
	 *            ����ĳ���
	 * @param rat
	 *            ����ת����
	 * @return
	 */
	public static String change(String input, float rat) {
		if (input == null) {
			return null;
		}
		// ��õ�λ
		char[] p = new char[2];
		input = input.trim();
		input.getChars(input.length() - 2, input.length(), p, 0);
		// ����
		char[] l = new char[2];
		input.getChars(0, input.length() - 2, l, 0);
		String pstring = new String(p);
		String lstring = new String(l);
		System.out.println(pstring);
		System.out.println(lstring);
		int output = Math.round(Integer.parseInt(lstring) * rat);
		return output + pstring;

	}

	public void writeToFile(Screen input) {
		ArrayList<Dimes> inputDimes = input.parse();
		int inputhdp = input.hdp;
		int inputwdp = input.wdp;
		float hRat = Math.abs(hdp / inputhdp);
		float wRat = Math.abs(wdp / inputwdp);
		ArrayList<Dimes> outputDimes = new ArrayList<>();
		for (Dimes inputDime : inputDimes) {
			switch (inputDime.tag) {
			case 1:
				if (wRat > 1.01 || wRat < 0.99) {
					outputDimes.add(new Dimes(inputDime.getName(), change(inputDime.getValue(), wRat)));
				}
				break;
			case 2:
				if (hRat > 1.01 || hRat < 0.99) {
					outputDimes.add(new Dimes(inputDime.getName(), change(inputDime.getValue(), wRat)));
				}
				break;

			default:
				break;
			}

		}

	}

	public String getDpXmlPath() {
		return dpXmlPath;
	}

	public void setDpXmlPath(String dpXmlPath) {
		this.dpXmlPath = dpXmlPath;
	}

	public boolean equals(Object obj) {
		Screen s = (Screen) obj;
		if (s == null) {
			return false;
		}
		/** ֻҪ��Ļdp��Сһ������Ϊ��һ���� */
		return hdp == s.hdp && wdp == s.wdp;
	}

}

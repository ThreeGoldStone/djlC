package com.djl.lottery;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author DJL E-mail:
 * @date 2015-12-22 ÉÏÎç9:46:58
 * @version 1.0
 * @parameter
 * @since
 */
public class Test {
	public static void main(String[] args) {
		ArrayList<LotteryDoubleColor> ldcs = new ArrayList<>();
		loadTxt(ldcs, "2003.txt");
		loadTxt(ldcs, "2004.txt");
		loadTxt(ldcs, "2005.txt");
		loadTxt(ldcs, "2006.txt");
		loadTxt(ldcs, "2007.txt");
		loadTxt(ldcs, "2008.txt");
		loadTxt(ldcs, "2009.txt");
		loadTxt(ldcs, "2010.txt");
		loadTxt(ldcs, "2011.txt");
		loadTxt(ldcs, "2012.txt");
		loadTxt(ldcs, "2013.txt");
		loadTxt(ldcs, "2014.txt");
		loadTxt(ldcs, "2015.txt");
		int[] red = new int[34], blue = new int[17];
		for (LotteryDoubleColor ldc : ldcs) {
			System.out.println(ldc.toString());
			for (int i = 0; i < ldc.redBall.length; i++) {
				red[ldc.redBall[i]]++;
			}
			blue[ldc.blueBall]++;
		}
		for (int i = 0; i < blue.length; i++) {
			System.out.println("blue>>>---" + "\tnumb\t" + i + "\t>>--time\t" + blue[i]);
		}
		for (int i = 0; i < red.length; i++) {
			System.out.println("red>>>---" + "\tnumb\t" + i + "\t>>--time\t" + red[i]);
		}
	}

	private static void loadTxt(ArrayList<LotteryDoubleColor> ldcs, String path) {
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(path));
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.replace("|", "@");
				if (line.contains("@")) {
					String[] s1 = line.split(" ");
					ldcs.add(new LotteryDoubleColor(s1[4], s1[0]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class LotteryDoubleColor {
		public int redBall[] = new int[6], blueBall;
		public int index;

		/**
		 * 01,04,07,15,28,32@16
		 * 2003089    18,19,21,26,27,33|16    2003-12-28
		 * 
		 * @param code
		 */
		public LotteryDoubleColor(String code, String indexs) {
			try {
				index = Integer.parseInt(indexs.trim());
				String[] s2 = code.split("@");
				blueBall = Integer.parseInt(s2[1].trim());
				String[] s3 = s2[0].split(",");
				for (int i = 0; i < s3.length; i++) {
					redBall[i] = Integer.parseInt(s3[i].trim());
				}
			} catch (Exception e) {
				throw e;
			}

		}

		@Override
		public String toString() {
			return "LotteryDoubleColor [redBall=" + Arrays.toString(redBall) + ", blueBall="
					+ blueBall + ", index=" + index + "]";
		}

	}
}

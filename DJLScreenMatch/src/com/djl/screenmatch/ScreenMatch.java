package com.djl.screenmatch;

/**
 * @author DJL E-mail:
 * @date 2015-8-28 ÉÏÎç9:39:56
 * @version 1.0
 * @parameter
 * @since
 */
public class ScreenMatch {
	public final static Screen[] targetScreens = {//
	new Screen(1920, 1080, 3.0f), //
	};

	public static void main(String[] args) {
		// parse();
		// System.out.println(change(" 33dp ", 0.833333f));
		// Screen.parse();
	}

	public static void fit(Screen input, Screen... output) {
		for (int i = 0; i < output.length; i++) {
			output[i].writeToFile(input);
		}
	}

}

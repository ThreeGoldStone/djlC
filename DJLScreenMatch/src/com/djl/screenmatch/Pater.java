package com.djl.screenmatch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author DJL E-mail:
 * @date 2015-9-10 ÉÏÎç9:52:27
 * @version 1.0
 * @parameter
 * @since
 */
public class Pater {
	public void find() {
		// Pattern pattern =
		// Pattern.compile("<dimen name=\"(activity_vertical_margin|)\">16dp</dimen>");
		// Pattern pattern =
		// Pattern.compile("<dimen name=\"(activity_vertical_margin|)\">16dp</dimen>");
		// Pattern pattern =
		// Pattern.compile("<dimen name=\"(activity_vertical_margin|)\">16dp</dimen>");
		// Pattern pattern =
		// Pattern.compile("<dimen name=\"(activity_vertical_margin|)\">16dp</dimen>");
		// Pattern pattern =
		// Pattern.compile("<dimen name=\"(activity_vertical_margin|)\">16dp</dimen>");
		Pattern patternVerticalInDimen = Pattern.compile("<dimen \\sname=[a-z]*(left|right|vertical)[a-z]*>[0-9](dp|sp|dip|px)</dimen>");
		Matcher matcher = patternVerticalInDimen.matcher("");
		while (matcher.matches()) {

		}

	}
}

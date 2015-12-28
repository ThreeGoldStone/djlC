package com.djl.doc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.djl.utils.CipherUtils;

/**
 * @author DJL E-mail:
 * @date 2015-9-11 下午1:22:33
 * @version 1.0
 * @parameter
 * @since
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ScreenMatcher matcher = new
		// ScreenMatcher("E:\\Android_WorkSpace_1\\HHY2Test2\\",
		// new ScreenSize(720, 1280, 4.65f));
		// // 输入需要适配的类型1920×10805.5英寸
		// // 屏幕尺寸：5.5英寸
		// // 分辨率：1280×720(HD,720P)
		// matcher.start(
		// new ScreenSize(480, 800, 4.0f),
		// new ScreenSize(720, 1280, 5.5f),
		// new ScreenSize(1080, 1920, 5.0f),
		// new ScreenSize(1080, 1920, 5.5f),
		// new ScreenSize(1080, 1920, 5.7f),
		// new ScreenSize(1440, 2560, 5.7f));
		// StringBuilder sb = new StringBuilder();
		// for (int i = 0; i < 100000; i++) {
		// sb.append("abcdadfadsfasdfasdfadsadsfsadfadsfasdfadfadsfasdfwerwqersfdghwetr4234234eqrgq cdfas d");
		// }
		// System.out.println(CipherUtils.md5(sb.toString()));
		// System.out.println(CipherUtils.md5(sb.append("a").toString()));
		// System.out
		// .println(CipherUtils
		// .md5("fasdfadsfdafsssssssssssssssssssssssssssssssss4wqr5q14ryqhewj45chqydhadsfwrt vqwuq wrsdf aewrb qfhdsajlfhjpcnqoiwrcoquewqcw e"));
		// FileReader fileReader = new FileReader(src);
		StringBuffer res = new StringBuffer();
		String line = null;
		InputStreamReader isr;
		try {
			isr = new InputStreamReader(new FileInputStream("screenSizes.txt"), "UTF-8");
			System.out.println("fileReader.getEncoding()=" + isr.getEncoding());
			BufferedReader reader = new BufferedReader(isr);
			int i = 0;
			while ((line = reader.readLine()) != null) {
				int indexOf = line.indexOf("\t");
				if (indexOf < 0)
					indexOf = line.indexOf(" ");
				if (indexOf > 0) {
					char[] dst = new char[indexOf];
					line.getChars(0, indexOf, dst, 0);
					System.out.println(new String(dst));
					i++;
				}
			}
			System.out.println(i);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

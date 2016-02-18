package com.djl.stringsuck;

import java.io.File;

import com.djl.doc.FormatDoc;

/**
 * @author DJL E-mail:
 * @date 2016-1-8 ÏÂÎç2:46:35
 * @version 1.0
 * @parameter
 * @since
 */
public class JavaCounter {
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) {
		String path = "C:\\Users\\Administrator\\Desktop\\a\\com\\huhuiying\\ssgj";
		String outPut = "C:\\Users\\Administrator\\Desktop\\a\\output2.java";
		extraClasss(new File(path));
		FormatDoc.write(sb.toString(), new File(outPut));
	}

	public static void extraClasss(File f) {
		if (!f.isDirectory()) {
			String fileName = f.getName();
			if (fileName.matches(".*.java")) {
				String content = FormatDoc.read(f);
				sb.append(content);
			}
		} else {
			File[] fs = f.listFiles();
			for (int i = 0; i < fs.length; ++i) {
				File file = fs[i];
				extraClasss(file);
			}
		}
	}

}

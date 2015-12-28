package com.djl.doc;

/* 
 * ȥ��Android�ĵ�����Ҫ������javascript���� 
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FormatDoc {
	public static int j = 1;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		File file = new File("D:/android/android-sdk-windows/docs/");
		searchDirectory(file, 0);
		System.out.println("OVER");
	}

	public static void searchDirectory(File f, int depth) {
		if (!f.isDirectory()) {
			String fileName = f.getName();
			if (fileName.matches(".*.{1}html")) {
				String src = "<(link rel)[=]\"(stylesheet)\"\n(href)[=]\"(http)://(fonts.googleapis.com/css)[?](family)[=](Roboto)[:](regular,medium,thin,italic,mediumitalic,bold)\"( title)[=]\"roboto\">";
				String src1 = "<script src=\"http://www.google.com/jsapi\" type=\"text/javascript\"></script>";
				String dst = "";
				// �����html�ļ���ע�͵����е��ض�javascript����
				annotation(f, src, dst);
				annotation(f, src1, dst);
			}
		} else {
			File[] fs = f.listFiles();
			depth++;
			for (int i = 0; i < fs.length; ++i) {
				File file = fs[i];
				searchDirectory(file, depth);
			}
		}
	}

	/**
	 * f ��Ҫ�޸������ض����ݵ��ļ� src �����滻������ dst �����滻�������
	 */
	public static void annotation(File f, String src, String dst) {
		String content = FormatDoc.read(f);
		content = content.replaceFirst(src, dst);
		int ll = content.lastIndexOf(src);
		System.out.println(ll);
		FormatDoc.write(content, f);
		System.out.println(j++);
		return;

	}

	public static String read(File src) {
		StringBuffer res = new StringBuffer();
		String line = null;
		try {
			// FileReader fileReader = new FileReader(src);
			InputStreamReader isr = new InputStreamReader(new FileInputStream(src), "UTF-8");
			System.out.println("fileReader.getEncoding()=" + isr.getEncoding());
			BufferedReader reader = new BufferedReader(isr);
			int i = 0;
			while ((line = reader.readLine()) != null) {
				if (i != 0) {
					res.append('\n');
				}
				res.append(line);
				i++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res.toString();
	}

	public static boolean write(String cont, File dist) {
		try {
			// FileWriter out = new FileWriter(dist);
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(dist), "utf-8");
			System.out.println("out.getEncoding()=" + osw.getEncoding());
			BufferedWriter writer = new BufferedWriter(osw);
			writer.write(cont);
			writer.flush();
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
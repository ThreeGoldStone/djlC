package com.djl.stringsuck;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.djl.doc.FormatDoc;
import com.djl.utils.CipherUtils;

/**
 * @author DJL E-mail:
 * @date 2015-12-10 下午5:33:35
 * @version 1.0
 * @parameter
 * @since
 */
public class StringSuckJava {
	public static String ClassFieldF = "public static final String {0}=\"{1}\";\n";

	public static void main(String[] args) {

		// 输出文件
		File fOutPut = new File("E:\\Android_WorkSpace_1\\HHY2Test2\\src\\com\\I.java");

		ArrayList<StringSuckBean> ssbs = new ArrayList<>();
		File fProject = new File("E:\\Android_WorkSpace_1\\HHY2Test2\\src\\com\\huhuiying\\ssgj\\fragment");
		extraClasss(fProject, ssbs);
		// File f = new File(
		// "E:\\Android_WorkSpace_1\\HHY2Test2\\src\\com\\djl\\javaUtils\\EscapedCharacter.java");
		// extraAClass(f, ssbs);
		if (!fOutPut.exists()) {
			try {
				fOutPut.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		StringBuilder sbOut = new StringBuilder();
		sbOut.append("package com;\n\npublic class I {\n");
		for (StringSuckBean c : ssbs) {
			System.out.println(c.toString());
			sbOut.append(ClassFieldF.replace("{0}", c.md5).replace("{1}", c.content));
		}
		sbOut.append("}");
		String result = sbOut.toString();
		result = result.replace("@FUCK", "\\\"");
		FormatDoc.write(result, fOutPut);
	}

	public static void extraClasss(File f, ArrayList<StringSuckBean> ssbs) {
		if (!f.isDirectory()) {
			String fileName = f.getName();
			if (fileName.matches(".*.java")) {
				extraAClass(f, ssbs);
			}
		} else {
			File[] fs = f.listFiles();
			for (int i = 0; i < fs.length; ++i) {
				File file = fs[i];
				extraClasss(file, ssbs);
			}
		}
	}

	/**
	 * 抽取一个java文件f的String部分放入ssbs数组中，并且替换f中的String中，写入f
	 * 
	 * @param f
	 * @param ssbs
	 * @return
	 */
	private static void extraAClass(File f, ArrayList<StringSuckBean> ssbs) {
		String content = FormatDoc.read(f);
		content = content.replace("\\\"", "@FUCK");
		char[] arr = content.toCharArray();
		StringBuilder sb = null;
		int j = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == '\"') {
				j++;
				if (j % 2 == 1) {
					sb = new StringBuilder();
				} else {
					StringSuckBean e = new StringSuckBean(sb.deleteCharAt(0).toString());
					if (!ssbs.contains(e)) {
						ssbs.add(e);
					}
					sb = null;
				}
			}
			if (sb != null) {
				sb.append(arr[i]);
			}
		}
		for (StringSuckBean c : ssbs) {
			String target = "\"" + c.content + "\"";
			content = content.replace(target, c.name);
		}
		content = content.replace("@FUCK", "\\\"");
		FormatDoc.write(content, f);
	}

	static class StringSuckBean {
		public String name, content;
		public String md5;

		public StringSuckBean(String content) {
			this.content = content
			// .replace("@FUCK", "\\\"")
			;
			md5 = "_" + CipherUtils.md5(this.content);
			this.name = "I." + md5;
		}

		@Override
		public boolean equals(Object obj) {
			StringSuckBean ssb = (StringSuckBean) obj;
			return ssb != null && ssb.content.equals(this.content);
		}

		@Override
		public String toString() {
			return "StringSuckBean [name=" + name + ", content=" + content + "]";
		}

	}

	/**
	 * f 将要修改其中特定内容的文件 src 将被替换的内容 dst 将被替换层的内容
	 */
	public static void annotation(File f, String src, String dst) {
		String content = FormatDoc.read(f);
		content = content.replaceFirst(src, dst);
		int ll = content.lastIndexOf(src);
		return;

	}
}

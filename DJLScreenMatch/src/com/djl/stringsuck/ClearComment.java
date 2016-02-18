package com.djl.stringsuck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * title: ���ע��
 * 
 * @author Administrator
 * @ʱ�� 2009-7-30:����09:28:10
 */
public class ClearComment {

	/** ��Ŀ¼ */
	public static String rootDir = "C:\\Users\\Administrator\\Desktop\\a";

	// "D:\\testdir
	// D:\\workspace\\proj_map\\src\\com
	public static void main(String args[]) throws FileNotFoundException,
			UnsupportedEncodingException {
		deepDir(rootDir);

	}

	public static void deepDir(String rootDir) throws FileNotFoundException,
			UnsupportedEncodingException {
		File folder = new File(rootDir);
		if (folder.isDirectory()) {
			String[] files = folder.list();
			for (int i = 0; i < files.length; i++) {
				File file = new File(folder, files[i]);
				if (file.isDirectory() && file.isHidden() == false) {
					System.out.println(file.getPath());
					deepDir(file.getPath());
				} else if (file.isFile()) {
					clearComment(file.getPath());
				}
			}
		} else if (folder.isFile()) {
			clearComment(folder.getPath());
		}
	}

	/**
	 * @param currentDir
	 *            ��ǰĿ¼
	 * @param currentFileName
	 *            ��ǰ�ļ���
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	/**
	 * @param filePathAndName
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void clearComment(String filePathAndName) throws FileNotFoundException,
			UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		String line = null; // ��������ÿ�ж�ȡ������
		InputStream is = new FileInputStream(filePathAndName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		try {
			line = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ��ȡ��һ��
		while (line != null) { // ��� line Ϊ��˵��������
			buffer.append(line); // ��������������ӵ� buffer ��
			buffer.append("\r\n"); // ��ӻ��з�
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			} // ��ȡ��һ��
		}
		// 1��������е�ע�ͣ��磺 //ĳĳ������Ϊ ��\/\/.*
		// 2��������е�ע�ͣ��磺/** ĳĳ */������Ϊ��\/\*\*.*\*\/
		// 3��������е�ע�ͣ��磺/* ĳĳ */������Ϊ��\/\*.*\*\/
		// 4��������е�ע�ͣ���:
		// /* ĳĳ1
		// ĳĳ2
		// */
		// ����Ϊ��.*/\*(.*)\*/.*
		// 5��������е�ע�ͣ��磺
		// /** ĳĳ1
		// ĳĳ2
		// */
		// ����Ϊ��/\*\*(\s*\*\s*.*\s*?)*
		String filecontent = buffer.toString();

		Map<String, String> patterns = new HashMap<String, String>();
		patterns.put("([^:])\\/\\/.*", "$1");// ƥ���ڷ�ð�ź����ע�ͣ���ʱ�Ͳ���������http://
		patterns.put("\\s+\\/\\/.*", "");// ƥ�䡰//��ǰ�ǿհ׷���ע��
		patterns.put("^\\/\\/.*", "");
		patterns.put("^\\/\\*\\*.*\\*\\/$", "");
		patterns.put("\\/\\*.*\\*\\/", "");
		patterns.put("/\\*(\\s*\\*\\s*.*\\s*?)*\\*\\/", "");
		// patterns.put("/\\*(\\s*\\*?\\s*.*\\s*?)*", "");
		Iterator<String> keys = patterns.keySet().iterator();
		String key = null, value = "";
		while (keys.hasNext()) {
			// ��������滻
			key = keys.next();
			value = patterns.get(key);
			filecontent = replaceAll(filecontent, key, value);
		}
		System.out.println(filecontent);
		// �������ԭ�ļ�
		try {
			File f = new File(filePathAndName);
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}
			FileOutputStream out = new FileOutputStream(filePathAndName);
			byte[] bytes = filecontent.getBytes("UTF-8");
			out.write(bytes);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param fileContent
	 *            ����
	 * @param patternString
	 *            ƥ���������ʽ
	 * @param replace
	 *            �滻������
	 * @return
	 */
	public static String replaceAll(String fileContent, String patternString, String replace) {
		String str = "";
		Matcher m = null;
		Pattern p = null;
		try {
			p = Pattern.compile(patternString);
			m = p.matcher(fileContent);
			str = m.replaceAll(replace);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			m = null;
			p = null;
		}
		// ���ƥ��������
		return str;

	}

}
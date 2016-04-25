package com.djl.fileupload;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author DJL E-mail:
 * @date 2016-2-19 上午10:23:22
 * @version 1.0
 * @parameter
 * @since
 */
public class FileUpLoad {
	/**
	 * post表单可以是文件或文本
	 * 
	 * @param actionUrl
	 * @param boundary
	 * @param forms
	 * @return
	 */
	public static String postForm(String actionUrl, String boundary, ArrayList<FormBean> forms) {
		String end = "\r\n";
		String Hyphens = "--";
		URL url;
		HttpURLConnection con = null;
		try {
			url = new URL(actionUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			// Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
			// Content-Type: multipart/form-data;
			// boundary=----moxieboundary1456821623670
			// User-Agent: Dalvik/2.1.0 (Linux; U; Android 6.0; Android SDK
			// built for x86_64 Build/MASTER)
			// Host: 114.241.205.122:8086
			// Accept-Encoding: gzip
			// Content-Length: 23659
			con.setRequestMethod("POST");
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Referer", "http://hehe");
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			for (int j = 0; j < forms.size(); j++) {
				FormBean fb = forms.get(j);
				// 拼接表单条目的前缀
				StringBuilder sbFormHead = new StringBuilder().append(Hyphens + boundary + end)
						.append("Content-Disposition: form-data; ");
				String[][] parameterPairs = fb.parameterPairs;
				// 遍历拼接参数
				for (int i = 0; i < parameterPairs.length; i++) {
					String[] pair = parameterPairs[i];
					sbFormHead.append(" ").append(pair[0]).append("=").append("\"").append(pair[1])
							.append("\"");
					if (i < parameterPairs.length - 1) {
						sbFormHead.append("; ");
					}
				}
				sbFormHead.append(end).append(end);
				// 写入表单条目的前缀
				ds.writeBytes(sbFormHead.toString());
				// 写入表单条目的content
				switch (fb.contentType) {
				case text:
					ds.writeBytes(fb.content);
					break;
				case file:
					writeFile(ds, fb.content);
					break;

				default:
					break;
				}
				ds.writeBytes(j < forms.size() - 1 ? end : new StringBuilder().append(end)
						.append(Hyphens + boundary).append((Hyphens + end)).toString());
			}
			InputStream is = con.getInputStream();
			int length = -1;
			byte[] cache = new byte[1024];
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			while ((length = is.read(cache)) != -1) {
				byteArray.write(cache, 0, length);
			}
			byte[] data = byteArray.toByteArray();
			return new String(data, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}

	}

	private static void writeFile(DataOutputStream ds, String fileName)
			throws FileNotFoundException, IOException {
		FileInputStream fStream = new FileInputStream(new File(fileName));
		/* 设定每次写入1024bytes */
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int length = -1;
		/* 从文件读取数据到缓冲区 */
		while ((length = fStream.read(buffer)) != -1) {
			/* 将数据写入DataOutputStream中 */
			ds.write(buffer, 0, length);
		}
	}

	/**
	 * 
	 * @author DJL E-mail:
	 * @date 2016-2-23 下午2:03:06
	 * @version 1.0
	 * @parameter
	 * @since
	 */
	public static class FormBean {
		public static enum ContentType {
			text, file
		};

		public ContentType contentType;
		public String content;
		public String[][] parameterPairs;

		/**
		 * 
		 * @param boundary
		 *            表单的分割
		 * @param contentType
		 *            表单元素的类型
		 * @param content
		 *            可以是文件路径，或文本
		 * @param parameterPairs
		 *            表单条目的键值对 new String[] {key,value}
		 */
		public FormBean(ContentType contentType, String content, String[]... parameterPairs) {
			this.contentType = contentType;
			this.content = content;
			this.parameterPairs = parameterPairs;
		}

	}

}

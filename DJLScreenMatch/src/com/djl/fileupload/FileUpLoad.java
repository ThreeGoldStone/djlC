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
 * @date 2016-2-19 ����10:23:22
 * @version 1.0
 * @parameter
 * @since
 */
public class FileUpLoad {
	/**
	 * post���������ļ����ı�
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
				// ƴ�ӱ���Ŀ��ǰ׺
				StringBuilder sbFormHead = new StringBuilder().append(Hyphens + boundary + end)
						.append("Content-Disposition: form-data; ");
				String[][] parameterPairs = fb.parameterPairs;
				// ����ƴ�Ӳ���
				for (int i = 0; i < parameterPairs.length; i++) {
					String[] pair = parameterPairs[i];
					sbFormHead.append(" ").append(pair[0]).append("=").append("\"").append(pair[1])
							.append("\"");
					if (i < parameterPairs.length - 1) {
						sbFormHead.append("; ");
					}
				}
				sbFormHead.append(end).append(end);
				// д�����Ŀ��ǰ׺
				ds.writeBytes(sbFormHead.toString());
				// д�����Ŀ��content
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
		/* �趨ÿ��д��1024bytes */
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int length = -1;
		/* ���ļ���ȡ���ݵ������� */
		while ((length = fStream.read(buffer)) != -1) {
			/* ������д��DataOutputStream�� */
			ds.write(buffer, 0, length);
		}
	}

	/**
	 * 
	 * @author DJL E-mail:
	 * @date 2016-2-23 ����2:03:06
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
		 *            ���ķָ�
		 * @param contentType
		 *            ��Ԫ�ص�����
		 * @param content
		 *            �������ļ�·�������ı�
		 * @param parameterPairs
		 *            ����Ŀ�ļ�ֵ�� new String[] {key,value}
		 */
		public FormBean(ContentType contentType, String content, String[]... parameterPairs) {
			this.contentType = contentType;
			this.content = content;
			this.parameterPairs = parameterPairs;
		}

	}

}

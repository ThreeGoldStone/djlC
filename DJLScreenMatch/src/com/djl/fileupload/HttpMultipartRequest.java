package com.djl.fileupload;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HttpMultipartRequest {
	// ÿ��post����֮��ķָ�
	static  String BOUNDARY = "----MyFormBoundarySMFEtUYQG6r5B920"; // �������ݷָ���
	private String urlStr; // ���ӵĵ�ַ
	private List<String[]> strParams; // ����post� strParams 1:key 2:value
	private List<String[]> fileParams; // �ļ���post� fileParams 1:fileField,
										// 2.fileName, 3.fileType, 4.filePath

	public HttpMultipartRequest(String urlStr, List<String[]> strParams, List<String[]> fileParams,String bOUNDARY) {
		this.urlStr = urlStr;
		this.strParams = strParams;
		this.fileParams = fileParams;
		BOUNDARY=bOUNDARY;

	}

	public byte[] sendPost() throws Exception {
		HttpURLConnection hc = null; // http������
		ByteArrayOutputStream bos = null;// byte�������������ȡ���������ص���Ϣ
		InputStream is = null;// ��������������ȡ���������ص���Ϣ
		byte[] res = null;// ������������ص���Ϣ��byte����
		try {
			URL url = new URL(urlStr);
			hc = (HttpURLConnection) url.openConnection();

			hc.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			hc.setRequestProperty("Charsert", "UTF-8");
			// ����POST�������������������
			hc.setDoOutput(true);
			hc.setDoInput(true);
			hc.setUseCaches(false);
			hc.setRequestMethod("POST");

			OutputStream dout = hc.getOutputStream();
			// //1.��д������ʽ��post��
			// ͷ
			String boundary = BOUNDARY;
			// ��
			StringBuffer resSB = new StringBuffer("\r\n");
			// β
			String endBoundary = "\r\n--" + boundary + "--\r\n";
			// strParams 1:key 2:value
			if (strParams != null) {
				for (String[] parsm : strParams) {
					String key = parsm[0];
					String value = parsm[1];
					resSB.append("Content-Disposition: form-data; name=\"").append(key)
							.append("\"\r\n").append("\r\n").append(value).append("\r\n")
							.append("--").append(boundary).append("\r\n");
				}
			}
			String boundaryMessage = resSB.toString();

			// д����
			dout.write(("--" + boundary + boundaryMessage).getBytes("utf-8"));

			// 2.��д�ļ���ʽ��post��
			// fileParams 1:fileField, 2.fileName, 3.fileType, 4.filePath
			resSB = new StringBuffer();
			if (fileParams != null) {
				for (int i = 0, num = fileParams.size(); i < num; i++) {
					String[] parsm = fileParams.get(i);
					String fileField = parsm[0];
					String fileName = parsm[1];
					String fileType = parsm[2];
					String filePath = parsm[3];

					resSB.append("Content-Disposition: form-data; name=\"").append(fileField)
							.append("\"; filename=\"").append(fileName).append("\"\r\n")
							.append("Content-Type: ").append(fileType).append("\r\n\r\n");

					dout.write(resSB.toString().getBytes("utf-8"));

					// ��ʼд�ļ�
					File file = new File(filePath);
					DataInputStream in = new DataInputStream(new FileInputStream(file));
					int bytes = 0;
					byte[] bufferOut = new byte[1024 * 5];
					while ((bytes = in.read(bufferOut)) != -1) {
						dout.write(bufferOut, 0, bytes);
					}

					if (i < num - 1) {
						dout.write(endBoundary.getBytes("utf-8"));
					}

					in.close();
				}

			}

			// 3.���д��β
			dout.write(endBoundary.getBytes("utf-8"));
			dout.close();
			int ch;
			is = hc.getInputStream();
			bos = new ByteArrayOutputStream();
			while ((ch = is.read()) != -1) {
				bos.write(ch);
			}
			res = bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null)
					bos.close();
				if (is != null)
					is.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return res;
	}

}
package com.djl.copyfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Test {
	public static void main(String[] args) {
		copyFile("2003.txt", "a/0.png");
	}

	public static void copyFile(final String sourceFilePath, final String targetFilePath) {
		new Thread() {
			public void run() {
				File tf = new File(targetFilePath);
				if (!tf.getParentFile().exists()) {
					tf.getParentFile().mkdirs();
				}
				if (!tf.exists()) {
					try {
						tf.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				FileOutputStream fs = null;
				InputStream inStream = null;
				try {
					int bytesum = 0;
					int byteread = -1;
					File oldfile = new File(sourceFilePath);
					if (oldfile.exists()) { // �ļ�����ʱ
						int totalsize = (int) oldfile.length();
						inStream = new FileInputStream(sourceFilePath); // ����ԭ�ļ�
						fs = new FileOutputStream(tf);
						byte[] buffer = new byte[1024 * 10];
						System.out.println("��ʼ����");
						while ((byteread = inStream.read(buffer)) != -1) {
							bytesum += byteread; // �ֽ��� �ļ���С
							System.out.println(bytesum);
							fs.write(buffer, 0, byteread);
							System.out.println(totalsize + "/" + bytesum);
						}
						inStream.close();
						fs.flush();
						fs.close();
						System.out.println("finish");
					}
				} catch (Exception e) {
					System.out.println("���Ƶ����ļ���������");
					e.printStackTrace();

				} finally {
					try {
						inStream.close();
						fs.flush();
						fs.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			};
		}.start();

	}
}

/**
 * @author DJL E-mail:
 * @date 2016-2-25 ����3:22:36
 * @version 1.0
 * @parameter
 * @since
 */

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
					if (oldfile.exists()) { // 文件存在时
						int totalsize = (int) oldfile.length();
						inStream = new FileInputStream(sourceFilePath); // 读入原文件
						fs = new FileOutputStream(tf);
						byte[] buffer = new byte[1024 * 10];
						System.out.println("开始复制");
						while ((byteread = inStream.read(buffer)) != -1) {
							bytesum += byteread; // 字节数 文件大小
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
					System.out.println("复制单个文件操作出错");
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
 * @date 2016-2-25 下午3:22:36
 * @version 1.0
 * @parameter
 * @since
 */

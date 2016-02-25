package com.djl.fileupload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.djl.fileupload.FileUpLoad.FormBean;
import com.djl.fileupload.FileUpLoad.FormBean.ContentType;

/**
 * @author DJL E-mail:
 * @date 2016-2-19 ÏÂÎç5:37:35
 * @version 1.0
 * @parameter
 * @since
 */
public class Test {
	// static String url =
	// "http://110.172.238.85:8086/FileServer/MutiFileUpServlet?"
	// + "sysid=licaicrm" + "&userid=" + "&otblname=ordr" + "&opkid=856" +
	// "&attachtype=1"
	// + "&thumb=false" + "&transtoweb=" + "&singlefile=false";
	static String url = "http://chenlequn:8080/FileServer/FileProServlet?" + "sysid=sshhcar"
			+ "&userid=20160218" + "&otblname=ofdr" + "&opkid=2" + "&attchtype=1"
			+ "&transtoweb=true" + "&timestamp={timestamp}" + "&nonce={nonce}"
			+ "&signature={signature}";

	// ds.writeBytes(formLine("name", name, gape) );
	// ds.writeBytes(formLine("filename", name, gape));
	// ds.writeBytes(formLine("chunk", "0", gape));
	// ds.writeBytes(formLine("chunks", "1", gape));
	// ds.writeBytes(formLine("filesize", uploadFile.length() + "", gape));
	public static void main(String[] args) {
		String token = "acdfbac3789183FE";
		String timestamp = System.currentTimeMillis() + "";
		String nonce = "122";
		String[] ss = { token, timestamp, nonce };
		Arrays.sort(ss);
		String signature = CipherUtils.SHA1(ss[0] + ss[1] + ss[2]);
		String u = url.replace("{timestamp}", timestamp).replace("{nonce}", nonce)
				.replace("{signature}", signature);
		// String u = "http://192.168.6.145:8888/FileLoadUp/xx.action";
//		String u = "http://192.168.6.150:8080/Demo2/servlet/Server2";
		// FileUpLoad.uploadPost(u, new File("2003.txt"), timestamp);

		// ///***********************************************************//
		// List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// nameValuePairs.add(new BasicNameValuePair("name", "2003.txt"));
		// nameValuePairs.add(new BasicNameValuePair("filename", "2003.txt"));
		// nameValuePairs.add(new BasicNameValuePair("chunk", "0"));
		// nameValuePairs.add(new BasicNameValuePair("chunks", "1"));
		// nameValuePairs.add(new BasicNameValuePair("image", "2003.txt"));
		// post(u, nameValuePairs);
		String boundary = "----moxieboundary"+timestamp;
		ArrayList<FormBean> forms = new ArrayList<>();
		forms.add(new FormBean("boundary", ContentType.text, "0.png", new String[] { "name", "filename" }));
		forms.add(new FormBean("boundary", ContentType.text, "0.png", new String[] { "name", "name" }));
		forms.add(new FormBean("boundary", ContentType.text, "0", new String[] { "name", "chunk" }));
		forms.add(new FormBean("boundary", ContentType.text, "1", new String[] { "name", "chunks" }));
		forms.add(new FormBean("boundary", ContentType.file, "0.png", new String[] { "name", "upfile" },
				new String[] { "filename", "0.png" }));
		String postForm = FileUpLoad.postForm(u, boundary, forms);
		System.out.println(postForm);
	}

	// public static void post(String url, List<NameValuePair> nameValuePairs) {
	// HttpClient httpClient = new DefaultHttpClient();
	// HttpContext localContext = new BasicHttpContext();
	// HttpPost httpPost = new HttpPost(url);
	// // httpPost.setHeader("Content-Type",
	// // "multipart/form-data; boundary=djl");
	// try {
	// MultipartEntity entity = new
	// MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	// for (int index = 0; index < nameValuePairs.size(); index++) {
	// if (nameValuePairs.get(index).getName().equalsIgnoreCase("image")) {
	// // If the key equals to "image", we use FileBody to transfer
	// // the data
	// entity.addPart(nameValuePairs.get(index).getName(), new FileBody(new
	// File(
	// nameValuePairs.get(index).getValue())));
	// } else {
	// // Normal string data
	// entity.addPart(nameValuePairs.get(index).getName(), new StringBody(
	// nameValuePairs.get(index).getValue()));
	// }
	// }
	// httpPost.setEntity(entity);
	// HttpResponse response = httpClient.execute(httpPost, localContext);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
}

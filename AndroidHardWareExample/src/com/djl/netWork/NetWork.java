package com.djl.netWork;

import java.util.Map;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.djlpatch.SimpleStringRequst;
import com.android.volley.djlpatch.SimpleVolleyListener;
import com.android.volley.toolbox.Volley;

/**
 * 用于添加网络请求的静态类<br>
 * 每个网络请求都对应着里的一个静态方法
 * 
 * @author DJL E-mail:
 * @date 2015-6-19 下午3:12:57
 * @version 1.0
 * @parameter
 * @since
 */
public class NetWork {
	public final static String url = "";
	private static RequestQueue queue;

	public static void getXXX(Context context,
			SimpleVolleyListener<String> listener, String... xxx) {
		//初始化请求队列
		initQueue(context);
		// TODO xxxxx
		queue.add(new SimpleStringRequst(Method.GET, url, listener, listener));
	}

	public static void postXXX(Context context,
			SimpleVolleyListener<String> listener, String... xxx) {
		//初始化请求队列
		initQueue(context);
		SimpleStringRequst request = new SimpleStringRequst(Method.POST, url,
				listener, listener);
		Map<String, String> params = null;// TODO xxxxx
		request.setPostParams(params);
		// queue 在初始化时就自动启动了,requst只要添加进去就自动请求了
		queue.add(request);
	}

	/**
	 * 初始化请求队列
	 * 
	 * @param context
	 */
	private static void initQueue(Context context) {
		if (queue == null) {
			queue = Volley.newRequestQueue(context);
		}
	}
}

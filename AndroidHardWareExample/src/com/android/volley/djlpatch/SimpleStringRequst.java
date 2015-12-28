package com.android.volley.djlpatch;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

/**
 * @author DJL E-mail:
 * @date 2015-6-12 下午12:11:47
 * @version 1.0
 * @parameter
 * @since
 */
public class SimpleStringRequst extends StringRequest {
	private Map<String, String> params;

	public SimpleStringRequst(int method, String url,
			Listener<String> listener, ErrorListener errorListener) {
		super(method, url, listener, errorListener);
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params;
	}

	/**
	 * 
	 * @param 发送post请求的body
	 * 
	 */
	public void setPostParams(Map<String, String> params) {
		switch (getMethod()) {
		case Method.POST:
			this.params = params;
			break;
		default:
			// TODO
			break;
		}
	}

	public void addPostParams(String key, String value) {
		if (params == null) {
			params = new HashMap<String, String>();
		}
		params.put(key, value);
	}
}

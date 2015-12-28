package com.android.volley.djlpatch;

import android.os.Handler;
import android.os.Message;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.djl.util.androidUtil.DJLUtils;

/**
 * @author DJL E-mail:
 * @date 2015-6-16 上午11:49:13
 * @version 1.0
 * @parameter
 * @since
 */
public class SimpleVolleyListener<T> implements Listener<T>, ErrorListener {
	private int tag;
	private Handler mHandler;

	/**
	 * 
	 * @param tag
	 *            requst的标记号，在互惠赢的应用中这个tag就是opcode
	 * @param mHandler
	 *            传出的mhandler
	 *            的what=tag(Opcode)*100+00成功，what=tag(Opcode)*100+1x表示失败
	 */
	public SimpleVolleyListener(int tag, Handler mHandler) {
		this.tag = tag;
		this.mHandler = mHandler;
	}

	public SimpleVolleyListener(Handler mHandler) {
		this.mHandler = mHandler;
	}

	@Override
	public void onResponse(T response) {
		onResponseWithTag(response, getTag());
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		onErrorResponseWithTag(error, getTag());

	}

	public void onResponseWithTag(T response, int tag) {

		Message msg = Message.obtain();
		msg.obj = response;
		msg.what = tag * 100 + 00;
		DJLUtils.log("response is " + response);
		mHandler.sendMessage(msg);

	}

	public void onErrorResponseWithTag(VolleyError error, int tag) {
		DJLUtils.log("response is error" + error.getMessage());
		error.printStackTrace();
		Message msg = Message.obtain();
		String message = error.getMessage();
		msg.obj = message;
		msg.what = tag * 100 + 10;
		mHandler.sendMessage(msg);
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}
}

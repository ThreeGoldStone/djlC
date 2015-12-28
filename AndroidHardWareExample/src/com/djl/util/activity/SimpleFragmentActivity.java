package com.djl.util.activity;

import com.djl.util.view.AnnotationInit;
import com.djl.util.view.ViewFinder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View.OnClickListener;

/**
 * 使用注释的方式初始化ContentView ,View ,及view的点击事件
 * 
 * @author DJL E-mail:
 * @date 2015-6-25 上午9:34:44
 * @version 1.0
 * @parameter
 * @since
 */
public abstract class SimpleFragmentActivity extends SFragmentActivity
		implements OnClickListener {
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			handleMessage_(msg);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AnnotationInit.inject(this, new ViewFinder(this));
		initView();
		initData();
	}

	/**
	 * 初始化数据
	 * 
	 */
	public abstract void initData();

	/**
	 * 初始化控件
	 * 
	 */
	public abstract void initView();

	/**
	 * 处理handler事件
	 * 
	 * @param msg
	 */
	public abstract void handleMessage_(Message msg);

	/**
	 * 获取handler
	 * 
	 * @return
	 */
	public Handler getHandler() {
		return mHandler;
	}
}

package com.djl.util.activity;

import com.djl.app.SimpleApplication;
import com.djl.util.view.AnnotationInit;
import com.djl.util.view.ViewFinder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * @author DJL E-mail:
 * @date 2015-6-25 下午5:26:12
 * @version 1.0
 * @parameter
 * @since
 */
@SuppressLint("HandlerLeak")
public abstract class SimpleFragment extends Fragment implements OnClickListener {
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			handleMessage_(msg);
		};
	};
	private View layout;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		layout = initLayout(inflater, container, savedInstanceState);
		AnnotationInit.inject(this, new ViewFinder(this));
		initView();
		initData();
		return layout;
	}

	public abstract View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState);

	public View findViewById(int id) {
		if (layout == null) {
			return null;
		}
		return layout.findViewById(id);

	};

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

	public SimpleApplication getApp() {
		return (SimpleApplication) getActivity().getApplication();
	}

	/**
	 * 获取handler
	 * 
	 * @return
	 */
	public Handler getHandler() {
		return mHandler;
	}
}

package com.example.djlandroidexample;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.djl.util.activity.SimpleFragment;
import com.djl.util.androidUtil.DJLUtils;
import com.djl.util.view.annotation.Click;
import com.djl.util.view.annotation.VID;

/**
 * @author DJL E-mail:
 * @date 2015-6-25 下午5:38:52
 * @version 1.0
 * @parameter
 * @since
 */
public class MyFragment extends SimpleFragment {
//	@Click
//	@VID(R.id.tv)
	TextView tv;

	@Override
	public void onClick(View v) {
		DJLUtils.toastL(getActivity(), "fragment 注射点击");

	}

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.activity_main, null);
		layout.setBackgroundColor(0xffff0000);
		return layout;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initView() {
		tv.setText("fuck fragment !");

	}

	@Override
	public void handleMessage_(Message msg) {
		// TODO Auto-generated method stub

	}

}

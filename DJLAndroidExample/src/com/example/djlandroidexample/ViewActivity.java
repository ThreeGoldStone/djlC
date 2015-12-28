package com.example.djlandroidexample;

import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.djl.util.activity.SimpleFragmentActivity;
import com.djl.util.androidUtil.DJLUtils;
import com.djl.util.view.annotation.Click;
import com.djl.util.view.annotation.ContentView;
import com.djl.util.view.annotation.VID;

/**
 * 调用SimpleActivity 的例子
 * 
 * @author DJL E-mail:
 * @date 2015-6-25 下午3:14:30
 * @version 1.0
 * @parameter
 * @since
 */
//@ContentView(R.layout.activity_main)
public class ViewActivity extends SimpleFragmentActivity {
//	@Click
//	@VID(R.id.tv)
	TextView mtv;

	@Override
	public void onClick(View v) {
		DJLUtils.toastS(this, "text click");
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initView() {
		mtv.setText("Hello Annotation !");
		FragmentTransaction bt = getSupportFragmentManager().beginTransaction();
		bt.replace(R.id.fl, new MyFragment());
		bt.commit();
	}

	@Override
	public void handleMessage_(Message msg) {
		// TODO Auto-generated method stub

	}

}

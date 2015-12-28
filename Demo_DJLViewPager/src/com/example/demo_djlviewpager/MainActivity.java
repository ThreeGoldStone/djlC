package com.example.demo_djlviewpager;

import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.djl.util.activity.SimpleFragmentActivity;
import com.djl.util.androidUtil.DJLUtils;
import com.djl.widget.LabelBean;
import com.djl.widget.LabelBean.OnPageShowListener;
import com.djl.widget.LabelPage;

public class MainActivity extends SimpleFragmentActivity implements OnPageShowListener {

	public int lbls[] = { R.id.cb0, R.id.cb1, R.id.cb2, R.id.cb3, R.id.cb4 };
	private String contents[] = { "0", "1", "2", "3", "4" };

	@Override
	public void onClick(View v) {

	}

	@Override
	public int initContent() {
		return R.layout.activity_main;
	}

	@Override
	public void initData() {
	}

	@Override
	public void initView() {
		LabelPage lp = new LabelPage(getViewPager(R.id.vp));
		for (int i = 0; i < lbls.length; i++) {
			TextView pageView = new TextView(this);
			pageView.setText(contents[i]);
			// lp.add(new LabelBean((LableView) getView(lbls[i]), pageView, i,
			// this));
		}
		lp.init(this);
	}

	@Override
	public void handleMessage_(Message msg) {

	}

	@Override
	public void onPageShow(LabelBean labelBean) {
		DJLUtils.log("onPageShow>>>---" + labelBean.index);
	}

}

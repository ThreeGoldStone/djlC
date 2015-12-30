package com.example.demo_djlviewpager;

import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.djl.util.activity.SimpleFragmentActivity;
import com.djl.util.androidUtil.DJLUtils;
import com.example.demo_djlviewpager.LabelBean.OnPageShowListener;

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
			TextView pageView = (TextView) View.inflate(this, R.layout.layout_item, null);
			pageView.setText("ÎÒÊÇµÚ" + i);
			lp.add(new LabelBean(getView(lbls[i]), pageView, this));
		}
		lp.init(this);
		lp.setCurrentShow(0);
	}

	@Override
	public void handleMessage_(Message msg) {

	}

	@Override
	public void onPageShow(LabelBean labelBean) {
		labelBean.mBarView.setBackgroundResource(R.drawable.ic_launcher);
		for (int i = 0; i < lbls.length; i++) {
			if (lbls[i] == labelBean.mBarView.getId()) {
				DJLUtils.log("onPageShow>>>---" + i);
			}
		}
	}

	@Override
	public void onPageNotShow(LabelBean labelBean) {
		labelBean.mBarView.setBackgroundColor(0xff00ff00);
		for (int i = 0; i < lbls.length; i++) {
			if (lbls[i] == labelBean.mBarView.getId()) {
				DJLUtils.log("onPageNotShow>>>---" + i);
			}
		}
	}

}

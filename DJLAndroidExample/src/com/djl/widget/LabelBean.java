package com.djl.widget;

import android.view.View;
import android.widget.CompoundButton;

public class LabelBean {
	public CompoundButton LabelView;
	public View PageView;
	public int index;
	private OnPageShowListener mOPSLintener;

	public LabelBean(CompoundButton labelView, View pageView, int index,
			OnPageShowListener mOPSLintener) {
		LabelView = labelView;
		PageView = pageView;
		this.index = index;
		labelView.setTag(index);
		this.mOPSLintener = mOPSLintener;
	}

	public OnPageShowListener getmOPSLintener() {
		return mOPSLintener;
	}

	public void setmOPSLintener(OnPageShowListener mOPSLintener) {
		this.mOPSLintener = mOPSLintener;
	}

	public static interface OnPageShowListener {
		/** 页面显示出来 */
		void onPageShow(LabelBean labelBean);

//		/** 页面初始化 */
//		void onPageInit(LabelBean labelBean);
	}
}

package com.djl.widget;

import android.view.View;
import android.widget.Checkable;

public class LabelBean implements Checkable {
	public View LabelView;
	public View PageView;
	public int index;
	private OnPageShowListener mOPSLintener;
	private boolean isChecked;

	public LabelBean(View labelView, View pageView, int index, OnPageShowListener mOPSLintener) {
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

	}

	@Override
	public void setChecked(boolean checked) {
		this.isChecked = checked;
	}

	@Override
	public boolean isChecked() {
		return isChecked;
	}

	@Override
	public void toggle() {
	}
}

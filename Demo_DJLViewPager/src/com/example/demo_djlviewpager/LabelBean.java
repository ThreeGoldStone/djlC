package com.example.demo_djlviewpager;

import android.support.annotation.NonNull;
import android.view.View;
//private int mShowTextColor = -1;
// private int mNormalTextColor = -1;
// private int mShowBgID = -1;
// private int mNormalBgID = -1;
///**
//* 设置text的颜色
//* 
//* @param showTextColor
//* @param normalTextColor
//* @return
//*/
//public LabelBean setBarTextColor(int showTextColor, int normalTextColor) {
//	this.mShowTextColor = showTextColor;
//	this.mNormalTextColor = normalTextColor;
//	return this;
//}

///**
//* 设置bar的背景id
//* 
//* @param showBgID
//* @param normalBgID
//* @return
//*/
//public LabelBean setBarBackgroundResID(int showBgID, int normalBgID) {
//	this.mShowBgID = showBgID;
//	this.mNormalBgID = normalBgID;
//	return this;
//}
public class LabelBean {
	public View mBarView;
	public View mPageView;
	private OnPageShowListener mOPSLintener;
	private boolean mIsShow;

	public LabelBean(@NonNull View labelView, @NonNull View pageView,
			@NonNull OnPageShowListener mOPSLintener) {
		mBarView = labelView;
		mPageView = pageView;
		this.mOPSLintener = mOPSLintener;
	}

	public OnPageShowListener getmOPSLintener() {
		return mOPSLintener;
	}

	public LabelBean setmOPSLintener(OnPageShowListener mOPSLintener) {
		this.mOPSLintener = mOPSLintener;
		return this;
	}

	/** 设置该Lable是否显示 */
	public void setShow(boolean isShow) {
		// 显示状态不同时再进入
		if (mIsShow != isShow) {
			if (isShow) {
				mOPSLintener.onPageShow(this);
			} else {
				mOPSLintener.onPageNotShow(this);
			}
			mIsShow = isShow;
		}
	}

	/** 返回该Lable是否正在显示 */
	public boolean isShow() {
		return mIsShow;
	}

	/** 页面显示出来的回调 */
	public static interface OnPageShowListener {
		/** 页面显示出来 */
		void onPageShow(LabelBean labelBean);

		/** 页面从显示变不显示 */
		void onPageNotShow(LabelBean labelBean);

	}

}

package com.example.demo_djlviewpager;

import android.support.annotation.NonNull;
import android.view.View;
//private int mShowTextColor = -1;
// private int mNormalTextColor = -1;
// private int mShowBgID = -1;
// private int mNormalBgID = -1;
///**
//* ����text����ɫ
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
//* ����bar�ı���id
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

	/** ���ø�Lable�Ƿ���ʾ */
	public void setShow(boolean isShow) {
		// ��ʾ״̬��ͬʱ�ٽ���
		if (mIsShow != isShow) {
			if (isShow) {
				mOPSLintener.onPageShow(this);
			} else {
				mOPSLintener.onPageNotShow(this);
			}
			mIsShow = isShow;
		}
	}

	/** ���ظ�Lable�Ƿ�������ʾ */
	public boolean isShow() {
		return mIsShow;
	}

	/** ҳ����ʾ�����Ļص� */
	public static interface OnPageShowListener {
		/** ҳ����ʾ���� */
		void onPageShow(LabelBean labelBean);

		/** ҳ�����ʾ�䲻��ʾ */
		void onPageNotShow(LabelBean labelBean);

	}

}

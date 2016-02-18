package com.example.demo_djlviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.djl.util.androidUtil.DJLUtils;

public class WrapContentHeightViewPager extends ViewPager {

	public WrapContentHeightViewPager(Context context) {
		super(context);
	}

	public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (heightMeasureSpec < 0) {
			DJLUtils.log("widthMeasureSpec=" + widthMeasureSpec + "  heightMeasureSpec="
					+ heightMeasureSpec);
			int height = 0;
			// �����������child�ĸ߶�
			for (int i = 0; i < getChildCount(); i++) {
				View child = getChildAt(i);
				child.measure(widthMeasureSpec,
						MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
				int h = child.getMeasuredHeight();
				if (h > height) // ��������view�ĸ߶ȡ�
					height = h;
			}
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		DJLUtils.log("widthMeasureSpec=" + widthMeasureSpec + "  heightMeasureSpec="
				+ heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	}
}
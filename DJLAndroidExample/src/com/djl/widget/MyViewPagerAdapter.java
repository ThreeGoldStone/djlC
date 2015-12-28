package com.djl.widget;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * 
 */
public class MyViewPagerAdapter extends PagerAdapter {

	// 界面列表
	public List<LabelBean> lableBeans;

	public MyViewPagerAdapter(List<LabelBean> views) {
		this.lableBeans = views;
	}

	// 获得当前界面数
	@Override
	public int getCount() {
		if (lableBeans != null) {
			return lableBeans.size();
		}
		return 0;
	}

	// 初始化arg1位置的界面
	@Override
	public Object instantiateItem(View container, int index) {
		((ViewPager) container).addView(lableBeans.get(index).PageView, 0);
		// lableBeans.get(index).getmOPSLintener().onPageInit(lableBeans.get(index));
		return lableBeans.get(index);
	}

	// 销毁arg1位置的界面
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView(lableBeans.get(position).PageView);
	}

	// 判断是否由对象生成界面
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

}

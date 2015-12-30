package com.example.demo_djlviewpager;

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

	// �����б�
	public List<LabelBean> lableBeans;

	public MyViewPagerAdapter(List<LabelBean> views) {
		this.lableBeans = views;
	}

	// ��õ�ǰ������
	@Override
	public int getCount() {
		if (lableBeans != null) {
			return lableBeans.size();
		}
		return 0;
	}

	// ��ʼ��arg1λ�õĽ���
	@Override
	public Object instantiateItem(View container, int index) {
		((ViewPager) container).addView(lableBeans.get(index).mPageView, 0);
		// lableBeans.get(index).getmOPSLintener().onPageInit(lableBeans.get(index));
		return lableBeans.get(index).mPageView;
	}

	// ����arg1λ�õĽ���
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView(lableBeans.get(position).mPageView);
	}

	// �ж��Ƿ��ɶ������ɽ���
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

}

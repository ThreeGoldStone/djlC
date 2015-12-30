package com.example.demo_djlviewpager;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * @author DJL E-mail:
 * @date 2015-12-24 下午1:31:56
 * @version 1.0
 * @parameter
 * @since
 */
public class LabelPage implements OnClickListener {

	private ArrayList<LabelBean> labelBeans;
	private int mCurrentShowIndex;
	private ViewPager mPager;
	private ViewGroup mLayout;

	/**
	 * initWithViewPager
	 * 
	 * @param pager
	 */
	public LabelPage(ViewPager pager) {
		this.mPager = pager;
	}

	public LabelPage(FrameLayout fl) {
		this.mLayout = fl;
	}

	public LabelPage(RelativeLayout rl) {
		this.mLayout = rl;
	}

	public void init(Activity activity) {
		initViewPager();
		// TODO
		for (LabelBean lb : getLabelBeans()) {
			lb.mBarView.setClickable(true);
			lb.mBarView.setOnClickListener(this);
		}
	}

	private void initViewPager() {
		if (mPager != null) {
			mPager.setAdapter(new MyViewPagerAdapter(getLabelBeans()));
			mPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int position) {
					setCurrentShow(position);
				}

				@Override
				public void onPageScrolled(int position, float positionOffset,
						int positionOffsetPixels) {
				}

				@Override
				public void onPageScrollStateChanged(int state) {
				}
			});
		}
	}

	public void add(LabelBean labelBean) {
		getLabelBeans().add(labelBean);
	}

	private ArrayList<LabelBean> getLabelBeans() {
		if (labelBeans == null) {
			labelBeans = new ArrayList<>();
		}
		return labelBeans;
	}

	/**
	 * 获取当前显示的位置
	 * 
	 * @return
	 */
	public int getCurrentShow() {
		return mCurrentShowIndex;
	}

	/**
	 * 设置当前显示的位置
	 * 
	 * @param index
	 */
	public void setCurrentShow(int index) {
		for (int i = 0; i < getLabelBeans().size(); i++) {
			mPager.setCurrentItem(index);
			getLabelBeans().get(i).setShow(index == i);
		}
		this.mCurrentShowIndex = index;
	}

	@Override
	public void onClick(View v) {
		int index = -1;
		for (int i = 0; i < getLabelBeans().size(); i++) {
			if (getLabelBeans().get(i).mBarView.getId() == v.getId()) {
				index = i;
			}
		}
		if (index >= 0) {
			setCurrentShow(index);
		}
	}
}

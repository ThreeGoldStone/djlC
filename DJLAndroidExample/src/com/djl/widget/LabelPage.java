package com.djl.widget;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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
	private int currentCheck;
	private ViewPager mPager;
	private ViewGroup mLayout;
/**
 * initWithViewPager
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
		for (LabelBean lb : labelBeans) {
			lb.LabelView.setClickable(true);
			lb.LabelView.setOnClickListener(this);
		}
	}

	private void initViewPager() {
		if (mPager != null) {
			mPager.setAdapter(new MyViewPagerAdapter(labelBeans));
			mPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int position) {
					CompoundButton cb = labelBeans.get(position).LabelView;
					if (!cb.isChecked()) {
						cb.setChecked(true);
						labelBeans.get(position).LabelView.setChecked(false);
						labelBeans.get(position).getmOPSLintener()
								.onPageShow(labelBeans.get(position));
						currentCheck = position;
					}
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
		if (labelBeans == null) {
			labelBeans = new ArrayList<>();
		}
		labelBeans.add(labelBean.index, labelBean);
	}

	public int getCurrentCheck() {
		return currentCheck;
	}

	public void setCurrentCheck(int CheckId) {
		// TODO
		if (!labelBeans.get(CheckId).LabelView.isChecked()) {
			labelBeans.get(CheckId).LabelView.setChecked(true);
			labelBeans.get(currentCheck).LabelView.setChecked(false);
		}
		if (mPager != null) {
			mPager.setCurrentItem(currentCheck);
			labelBeans.get(currentCheck).getmOPSLintener().onPageShow(labelBeans.get(currentCheck));
		}
		this.currentCheck = CheckId;
	}

	@Override
	public void onClick(View v) {
		CompoundButton cb = (CompoundButton) v;
		int checkIndex = (int) cb.getTag();
		if (!cb.isChecked()) {
			cb.setChecked(true);
			labelBeans.get(currentCheck).LabelView.setChecked(false);
			setCurrentCheck(checkIndex);
		}
	}
}

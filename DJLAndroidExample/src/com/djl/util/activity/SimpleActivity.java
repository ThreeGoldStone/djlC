package com.djl.util.activity;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * 简化findviewbyid
 * 
 * @author DJL E-mail:
 * @date 2015-6-25 下午12:01:57
 * @version 1.0
 * @parameter
 * @since
 */
@SuppressLint("HandlerLeak")
public abstract class SimpleActivity extends SActivity implements OnClickListener {
	private HashMap<Integer, View> mViews;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			handleMessage_(msg);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (initContent() > 0)
			setContentView(initContent());
		initView();
		initData();
	}

	/***************************************************** Quick *************************************/

	public <T extends Activity> T getActivity(Class<T> clazz) {
		return getApp().getActivityManager().findActivity(clazz);
	}

	/***************************************************** Quick *************************************/
	/**
	 * 批量设置点击事件
	 * 
	 * @param ids
	 */
	public void SetOnClick(int... ids) {
		for (int i = 0; i < ids.length; i++) {
			View view = getView(ids[i]);
			if (view != null) {
				view.setClickable(true);
				view.setOnClickListener(this);
			}
		}
	}

	/**
	 * 初始化contentView
	 * 
	 * @return
	 */
	public abstract int initContent();

	/**
	 * 初始化数据
	 * 
	 */
	public abstract void initData();

	/**
	 * 初始化控件
	 * 
	 */
	public abstract void initView();

	/**
	 * 处理handler事件
	 * 
	 * @param msg
	 */
	public abstract void handleMessage_(Message msg);

	/**
	 * 获取handler
	 * 
	 * @return
	 */
	public Handler getHandler() {
		return mHandler;
	}

	public TextView getTextView(int id) {
		return (TextView) getView(id);
	}

	public Button getButton(int id) {
		return (Button) getView(id);
	}

	public ViewPager getViewPager(int id) {
		return (ViewPager) getView(id);
	}

	public EditText getEditText(int id) {
		return (EditText) getView(id);
	}

	public ImageView getImageView(int id) {
		return (ImageView) getView(id);
	}

	public CheckBox getCheckBox(int id) {
		return (CheckBox) getView(id);
	}

	public GridView getGridView(int id) {
		return (GridView) getView(id);
	}

	public ListView getListView(int id) {
		return (ListView) getView(id);
	}

	public LinearLayout getLinearLayout(int id) {
		return (LinearLayout) getView(id);
	}

	public RelativeLayout getRelativeLayout(int id) {
		return (RelativeLayout) getView(id);
	}

	public TableLayout getTableLayout(int id) {
		return (TableLayout) getView(id);
	}

	public RadioButton getRadioButton(int id) {
		return (RadioButton) getView(id);
	}

	public RadioGroup getRadioGroup(int id) {
		return (RadioGroup) getView(id);
	}

	public View getView(int id) {
		if (mViews == null) {
			mViews = new HashMap<>();
		}
		if ((!mViews.containsKey(id)) || mViews.get(id) == null) {
			View view = findViewById(id);
			mViews.put(id, view);
		}
		return mViews.get(id);
	}
}

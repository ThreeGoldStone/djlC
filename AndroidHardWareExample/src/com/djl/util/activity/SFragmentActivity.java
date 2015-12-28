package com.djl.util.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.djl.app.SimpleApplication;

/**
 * 可以用SimpleApplication里的ActivityManager来管理的activity<br>
 * 关闭时调用finish_();
 * @author DJL E-mail:
 * @date 2015-6-25 上午9:34:44
 * @version 1.0
 * @parameter
 * @since
 */
public class SFragmentActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SimpleApplication.getInstance().getActivityManager().addActivity(this);
	}

	public void finish_() {
		SimpleApplication.getInstance().getActivityManager()
				.finishActivity(this);
	}

	@Override
	public void onBackPressed() {
		finish_();
	}

}

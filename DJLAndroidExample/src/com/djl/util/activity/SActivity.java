package com.djl.util.activity;

import android.app.Activity;
import android.os.Bundle;

import com.djl.app.SimpleApplication;

/**
 * 可以用SimpleApplication里的ActivityManager来管理的activity<br>
 * 关闭时调用finish_();
 * 
 * @author DJL E-mail:
 * @date 2015-6-25 下午12:01:57
 * @version 1.0
 * @parameter
 * @since
 */
public abstract class SActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getApp() != null) {
			getApp().getActivityManager().addActivity(this);
		}
	}

	public SimpleApplication getApp() {
		SimpleApplication app = null;
		try {
			app = (SimpleApplication) getApplication();
		} catch (Exception e) {
		}
		return app;
	}

	public void finish_() {
		if (getApp() != null) {
			getApp().getActivityManager().finishActivity(this);
		} else {
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		finish_();
	}

}

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
		SimpleApplication instance = SimpleApplication.getInstance();
		if (instance != null) {
			instance.getActivityManager().addActivity(this);
		}
	}

	public void finish_() {
		SimpleApplication instance = SimpleApplication.getInstance();
		if (instance != null) {
			instance.getActivityManager().finishActivity(this);
		} else {
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		finish_();
	}

}

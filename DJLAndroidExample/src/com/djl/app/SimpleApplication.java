package com.djl.app;

import android.app.Application;

import com.djl.util.activity.KJActivityManager;

/**
 * @author DJL E-mail:
 * @date 2015-6-25 下午12:03:08
 * @version 1.0
 * @parameter
 * @since
 */
public class SimpleApplication extends Application {

	private KJActivityManager mActivityManager;

	@Override
	public void onCreate() {
		super.onCreate();
		mActivityManager = KJActivityManager.create();
	}

	public KJActivityManager getActivityManager() {
		return mActivityManager;
	}

}

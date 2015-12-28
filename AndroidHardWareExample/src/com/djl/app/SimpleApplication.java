package com.djl.app;

import java.io.Closeable;
import java.io.IOException;

import com.djl.util.activity.KJActivityManager;

import android.app.Application;

/**
 * @author DJL E-mail:
 * @date 2015-6-25 下午12:03:08
 * @version 1.0
 * @parameter
 * @since
 */
public class SimpleApplication extends Application implements Closeable {

	private KJActivityManager mActivityManager;
	private static SimpleApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		mActivityManager = KJActivityManager.create();
	}

	public static SimpleApplication getInstance() {
		return instance;
	}

	public KJActivityManager getActivityManager() {
		return mActivityManager;
	}

	@Override
	public void close() throws IOException {
		instance = null;
		mActivityManager.AppExit(this);

	}
}

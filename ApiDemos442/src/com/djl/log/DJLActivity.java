package com.djl.log;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * @author DJL E-mail:
 * @date 2015-12-17 ÉÏÎç9:27:25
 * @version 1.0
 * @parameter
 * @since
 */
public class DJLActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("djl", getClass().getName() + ">>>---onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		Log.i("djl", getClass().getName() + ">>>---onDestroy");
		super.onDestroy();
	}
}

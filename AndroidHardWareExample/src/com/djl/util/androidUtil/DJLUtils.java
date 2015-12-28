package com.djl.util.androidUtil;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author DJL Since 2015.5.13
 * 
 */
public class DJLUtils {
	public static boolean DEBUG = true;
	public static String TAG = "djl";

	public static void toastS(Context c, String message) {
		Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
	}

	public static void toastL(Context c, String message) {
		Toast.makeText(c, message, Toast.LENGTH_LONG).show();
	}

	public static void log(String message) {
		if (DEBUG) {
			Log.i(TAG, message);
		}
	}
}

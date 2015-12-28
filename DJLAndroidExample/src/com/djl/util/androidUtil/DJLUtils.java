package com.djl.util.androidUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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

	/**
	 * 显示dialog
	 * 
	 * @param context
	 *            环境
	 * @param strTitle
	 *            标题
	 * @param strText
	 *            内容
	 * @param icon
	 *            图标
	 */
	public static void showDialog(Activity context, String strTitle,
			String strText, int icon) {
		try {
			AlertDialog.Builder tDialog = new AlertDialog.Builder(context);
			tDialog.setIcon(icon);
			tDialog.setTitle(strTitle);
			tDialog.setMessage(strText);
			tDialog.setPositiveButton("确定", null);
			tDialog.show();
		} catch (Exception e) {

		}
	}

	//
	// show the progress bar.
	/**
	 * 显示进度条
	 * 
	 * @param context
	 *            环境
	 * @param title
	 *            标题
	 * @param message
	 *            信息
	 * @param indeterminate
	 *            确定性
	 * @param cancelable
	 *            可撤销
	 * @return
	 */
	public static ProgressDialog showProgress(Context context,
			CharSequence title, CharSequence message, boolean indeterminate,
			boolean cancelable) {
		ProgressDialog dialog = new ProgressDialog(context);
		if (title != null) {
			dialog.setTitle(title);
		}
		if (message != null) {
			dialog.setMessage(message);
		}
		dialog.setIndeterminate(indeterminate);
		dialog.setCancelable(cancelable);
		dialog.show();
		return dialog;
	}
}

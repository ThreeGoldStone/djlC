package com.djl.wifiadb;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	String[] orders = new String[] { "su", "", "stop adbd", "start adbd" };
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Toast.makeText(MainActivity.this, "order " + msg.what, 0).show();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	int i = 0;

	public void onClick(View v) {
		Log.i("djl", " click");
		final ArrayList<String> list = new ArrayList<String>();
		list.add("setprop service.adb.tcp.port 5555");
		list.add("stop adbd");
		list.add("start adbd");
		Toast.makeText(this, "start wifi adb", 0).show();
		new Thread() {	
			public void run() {
				boolean isRoot = ShellUtils.checkRootPermission();
				if (!isRoot) {
					Log.i("djl", " no root");
					mHandler.sendEmptyMessage(0);
				}
				ShellUtils.execCommand(list, true);
			};
		}.start();
	}

	public void onClick1(View v) {
		Log.i("djl", " click1");
		final ArrayList<String> list = new ArrayList<String>();
		list.add("stop adbd");
		list.add("start adbd");
		Toast.makeText(this, "restart adbd", 0).show();
		new Thread() {
			public void run() {
				boolean isRoot = ShellUtils.checkRootPermission();
				if (!isRoot) {
					Log.i("djl", " no root");
					mHandler.sendEmptyMessage(0);
				}
				ShellUtils.execCommand(list, true);
			};
		}.start();
	}
}

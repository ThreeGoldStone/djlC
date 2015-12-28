package com.example.gametest1;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

	private GameView msf;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		// 窗体状态设置-设置为无标题栏状态【全屏】
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 强制横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		msf = new GameView(this);
		setContentView(msf);
		//  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);这个是竖屏  
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)

		/* 弹出窗口的最上头文字 */

		.setTitle("退出提示")

		/* 设置弹出窗口的图式 */

		.setIcon(R.drawable.ic_launcher)

		/* 设置弹出窗口的信息 */

		.setMessage("确定退出结束游戏？？？")
		.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					// 关闭
					public void onClick(
							DialogInterface dialoginterface, int i) {
						android.os.Process
								.killProcess(android.os.Process
										.myPid());

						// SoundManager.Instance.stopBackgroundSound();
					}
				})
		.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					/* 设置跳出窗口的返回事件 */
					public void onClick(
							DialogInterface dialoginterface, int i) {
						// 不进行操作
					}
				}).show();

	}
	
	public void onPause() {
	    super.onPause();
	}
}

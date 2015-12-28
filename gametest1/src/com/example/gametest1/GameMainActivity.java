package com.example.gametest1;

import com.androidquery.AQuery;
import com.game.base.BaseInfo;
import com.game.commen.ActionToDo;
import com.game.commen.GetImgCommen;
import com.game.data.RoleData;
import com.game.renwu.SpiritMain;

import dalvik.system.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GameMainActivity extends Activity {

	int lastnpc = 0;
	private final static int CWJ_HEAP_SIZE = 6 * 1024 * 1024;
	private static AQuery aq;
	private boolean showflag = false;
	private boolean showflagliao = false;
	private boolean showflagjineng = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 强制横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_main);
		// 窗体状态设置-设置为无标题栏状态【全屏】
		aq = new AQuery(this);

		aq.id(R.id.tool_open).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (showflag == false) {
					jy_open();
					showflag = true;
				} else {
					jy_close();
					showflag = false;
				}
			}
		});

		// 默认模式
		aq.id(R.id.bt_moshi1).clicked(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GameView.paodongflag = 0;
				tip("已切换到默认模式!");
			}
		});

		// 默认模式
		aq.id(R.id.bt_moshi2).clicked(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GameView.paodongflag = 1;
				tip("已切换到手势模式!");
			}
		});

		// 默认模式
		aq.id(R.id.bt_moshi3).clicked(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GameView.paodongflag = 2;
				tip("已切换到点击模式!");
			}
		});

		aq.id(R.id.jineng_feijian).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stuba
				if (BaseInfo.getAttack_X() > 0 && BaseInfo.getAttack_X() > 0) {
					GameView.jineng1.flag = 0;
					GameView.paodong = ActionToDo.攻击;
					GameView.sprirt_xiaohua.flaggongji = 0;
					GameView.feijian.flag = 0;
				} else {
					tip("请选择释放技能的目标！");
				}
			}
		});

		// VMRuntime.getRuntime().setMinimumHeapSize(CWJ_HEAP_SIZE);// TODO

		Button bt_gamestart = (Button) findViewById(R.id.bt_gamestart);

		bt_gamestart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (SpiritMain.showwap == false) {
					SpiritMain.showwap = true;
				} else {
					SpiritMain.showwap = false;
				}
			}
		});

		aq.id(R.id.tool_liao).clicked(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (showflagliao == false) {
					jy_open2();
					aq.id(R.id.tool_liao).image(R.drawable.js_yingbutton);
					showflagliao = true;
				} else {
					jy_close2();
					aq.id(R.id.tool_liao).image(R.drawable.js_liaobutton);
					showflagliao = false;
				}
			}
		});

		aq.id(R.id.tool_jineng).clicked(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (showflagjineng == false) {
					jy_open3();
					showflagjineng = true;
				} else {
					jy_close3();
					showflagjineng = false;
				}
			}
		});

		aq.id(R.id.bt_fasong).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str = aq.id(R.id.et_input).getText().toString().trim();
				GameView.role_xiaohua.setTalkAbout(str);
				GameView.sprirt_xiaohua.talkflag = 0;
				aq.id(R.id.et_input).text("");
			}
		});

		Button bt_addnpc = (Button) findViewById(R.id.addnpc);
		bt_addnpc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				lastnpc++;
				RoleData role_meiniang = new RoleData();
				role_meiniang.setPlayerImg(GetImgCommen.readBitMap(GameMainActivity.this,
						R.drawable.rw_ziniang));
				role_meiniang.setYinziImg(GetImgCommen.readBitMap(GameMainActivity.this,
						R.drawable.yinzi));
				role_meiniang.setHorizontalcutnum(9);
				role_meiniang.setVerticalcutnum(8);
				role_meiniang.setPlaynum(3);
				GameView.add_NPC(role_meiniang);
				// Toast.makeText(GameMainActivity.this, "目前有："+lastnpc+"个NPC",
				// Toast.LENGTH_SHORT).show();
			}
		});

		Button bt_allnpc = (Button) findViewById(R.id.allnpc);
		bt_allnpc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(GameMainActivity.this, "目前有：" + lastnpc + "个NPC", Toast.LENGTH_SHORT)
						.show();
			}
		});

		LinearLayout mt = (LinearLayout) findViewById(R.id.mt);
		GameView gv = new GameView(this);
		// CustomView1 cv = new CustomView1(GameMainActivity.this);
		mt.addView(gv);
	}

	public void jy_open() {
		Animation an5 = AnimationUtils.loadAnimation(GameMainActivity.this, R.anim.weimu_right);
		aq.id(R.id.ll_tool).animate(an5);
		aq.id(R.id.ll_tool).visible();
	}

	public void jy_close() {
		Animation an5 = AnimationUtils.loadAnimation(GameMainActivity.this, R.anim.weimu_right2);
		aq.id(R.id.ll_tool).animate(an5);
		aq.id(R.id.ll_tool).gone();
	}

	public void jy_open2() {
		Animation an5 = AnimationUtils.loadAnimation(GameMainActivity.this, R.anim.weimu_right);
		aq.id(R.id.ll_liaotian).animate(an5);
		aq.id(R.id.ll_liaotian).visible();
	}

	public void jy_close2() {
		Animation an5 = AnimationUtils.loadAnimation(GameMainActivity.this, R.anim.weimu_right2);
		aq.id(R.id.ll_liaotian).animate(an5);
		aq.id(R.id.ll_liaotian).gone();
	}

	public void jy_open3() {
		Animation an5 = AnimationUtils.loadAnimation(GameMainActivity.this, R.anim.weimu_right);
		aq.id(R.id.ll_jineng).animate(an5);
		aq.id(R.id.ll_jineng).visible();
	}

	public void jy_close3() {
		Animation an5 = AnimationUtils.loadAnimation(GameMainActivity.this, R.anim.weimu_right2);
		aq.id(R.id.ll_jineng).animate(an5);
		aq.id(R.id.ll_jineng).gone();
	}

	public void tip(String str) {
		Toast.makeText(GameMainActivity.this, str, Toast.LENGTH_SHORT).show();
	}

}

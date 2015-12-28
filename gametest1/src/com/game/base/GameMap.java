package com.game.base;

import java.util.List;
import java.util.Random;

import com.example.gametest1.GameMainActivity;
import com.game.commen.ActionToDo;
import com.game.commen.Direction;
import com.game.commen.MapName;
import com.game.commen.ToDo;
import com.game.data.RoleData;
import com.game.data.RoleData_Main;
import com.game.renwu.Objs;
import com.game.renwu.Spirit_Main;
import com.game.renwu.Spirit_NPC;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class GameMap {
	private Bitmap bmpGBG;// 背景
	// Npc列表
	List<Spirit_NPC> npclist;
	// 时间物体列表
	List<Objs> objslist;
	// 初始地图坐标
	public static int F_x = 0;
	public static int F_y = 0;
	private int movespeed = 10;// 地图移动速度

	Random rd = new Random();
	Random rd2 = new Random();
	MapName mapname=MapName.方寸山;
	
	Direction dc = Direction.左;
	private boolean paodong = false;

	int guaiqun1_x = 378;
	int guaiqun1_y = 363;
	int zuobiao_x_new = 0;
	int zuobiao_y_new = 0;
	
	public GameMap(Bitmap bmpGBG, int F_x, int F_y, List<Spirit_NPC> npclist,
			List<Objs> objslist,MapName mapname) {
		super();
		this.mapname=mapname;
		this.bmpGBG = bmpGBG;
		this.npclist = npclist;
		this.objslist = objslist;
		this.F_x = F_x;
		this.F_y = F_y;
	}

	// 绘制背景
	public void myDraw_Map(Canvas canvas, Paint paint, int spirit_x,
			int spirit_y) {

		// 地图总长宽
		int mapwidth = bmpGBG.getWidth();
		int mapheight = bmpGBG.getHeight();

		// 屏幕长宽
		int screenWidth = PubSet.screenWidth;
		int screenHeight = PubSet.screenHeight;

		// 截取会动的地图
		// 根据人物脚下的坐标设定位移量，随人物脚下坐标移动地图
		Rect map_rect = null;
		if (spirit_x > (screenWidth * 0.95) && spirit_y > (screenHeight * 0.95)) {
			// 右下移动
			F_x += movespeed;
			F_y += movespeed;
		} else if (spirit_x < (screenWidth * 0.05)
				&& spirit_y < (screenHeight * 0.05)) {
			// 左上移动
			F_x -= movespeed;
			F_y -= movespeed;
		} else if (spirit_x > (screenWidth * 0.95)
				&& spirit_y < (screenHeight * 0.05)) {
			// 右上
			F_x += movespeed;
			F_y -= movespeed;
		} else if (spirit_x < (screenWidth * 0.05)
				&& spirit_y > (screenHeight * 0.95)) {
			// 左下
			F_x -= movespeed;
			F_y += movespeed;
		} else if (spirit_x > (screenWidth * 0.05)
				&& spirit_x < (screenWidth * 0.95)
				&& spirit_y > (screenHeight * 0.95)) {
			// 下
			F_y += movespeed;
		} else if (spirit_x > (screenWidth * 0.05)
				&& spirit_x < (screenWidth * 0.95)
				&& spirit_y < (screenHeight * 0.05)) {
			// 上
			F_y -= movespeed;
		} else if (spirit_x > (screenWidth * 0.95)
				&& spirit_y > (screenHeight * 0.05)
				&& spirit_y < (screenHeight * 0.95)) {
			// 左
			F_x += movespeed;
		} else if (spirit_x < (screenWidth * 0.05)
				&& spirit_y > (screenHeight * 0.05)
				&& spirit_y < (screenHeight * 0.95)) {
			// 右
			F_x -= movespeed;
		}

		// 防止地图超出屏幕
		if (F_x >= (mapwidth - screenWidth)) {
			F_x = mapwidth - screenWidth;
		}
		if (F_y >= (mapheight - screenHeight)) {
			F_y = (mapheight - screenHeight);
		}
		if (F_x <= 0) {
			F_x = 0;

		}
		if (F_y <= 0) {
			F_y = 0;
		}

		map_rect = new Rect();
		map_rect.left = F_x;
		map_rect.top = F_y;
		map_rect.right = F_x + screenWidth;
		map_rect.bottom = F_y + screenHeight;

		// 屏幕保持固定尺寸
		Rect screen_rect = new Rect();
		screen_rect.left = 0;
		screen_rect.top = 0;
		screen_rect.right = screenWidth;
		screen_rect.bottom = screenHeight;

		Paint p_zuobiao = new Paint();
		p_zuobiao.setTextSize(16);
		p_zuobiao.setColor(Color.GREEN);

		Paint renwu_zuobiao = new Paint();
		renwu_zuobiao.setTextSize(16);
		renwu_zuobiao.setColor(Color.RED);

		String map_zuobiao = F_x + "/" + F_y;
		String renwu_zuobiaostr = (Integer.valueOf(spirit_x)+Integer.valueOf(F_x)) + "/" + (Integer.valueOf(spirit_y)+Integer.valueOf(F_y));
		String renwu_zuobiaostr2 = spirit_x + "/" + spirit_y;
		canvas.drawBitmap(bmpGBG, map_rect, screen_rect, paint);
		canvas.drawText(map_zuobiao, screenWidth - 120, screenHeight - (36+18),
				p_zuobiao);
		canvas.drawText(renwu_zuobiaostr, screenWidth - 120, screenHeight - 36,
				renwu_zuobiao);
		canvas.drawText(renwu_zuobiaostr2, screenWidth - 120, screenHeight - 18,
				renwu_zuobiao);

		

		// 加载物体事件
		if (objslist != null&& mapname== MapName.方寸山) {
			//
			objslist.get(0).myDraw_obj(canvas, paint, 2657-F_x, 1024-F_y, 7, 1, 6, 0.3f,spirit_x,spirit_y,ToDo.切换场景,MapName.女妖怪洞府,259,366,0,720);
			objslist.get(0).myDraw_obj(canvas, paint, 932-F_x, 1879-F_y, 7, 1, 6, 0.3f,spirit_x,spirit_y,ToDo.切换场景,MapName.牛魔王洞府,204,360,0,116);
			objslist.get(3).myDraw_obj(canvas, paint, 1575-F_x, 567-F_y, 8, 1, 7, 0.5f,0,0,ToDo.弹对话框,null,0,0,0,0);
		}
		if (objslist != null&& mapname== MapName.女妖怪洞府) {
			//
			objslist.get(1).myDraw_obj(canvas, paint, 143-F_x, 1060-F_y, 8, 1, 7, 1f,spirit_x,spirit_y,ToDo.切换场景,MapName.方寸山,543,339,2004,728);
			objslist.get(3).myDraw_obj(canvas, paint, 1575-F_x, 567-F_y, 8, 1, 7, 0.5f,0,0,ToDo.弹对话框,null,0,0,0,0);
		}
		if (objslist != null&& mapname== MapName.牛魔王洞府) {
			// 加载NPC
			if (npclist!=null) {
				
				getMoveRandom(Direction.右下);
				guaiqun1_x+=zuobiao_x_new;
				guaiqun1_y+=zuobiao_y_new;
				npclist.get(0).myDraw_Spirit(PubSet.context, canvas, paint, guaiqun1_x-F_x, guaiqun1_y-F_y,dc, paodong);
				//大王
//				npclist.get(2).myDraw_Spirit(PubSet.context, canvas, paint, 498-F_x, 305-F_y,Direction.左下, false);
				//仙人
//				npclist.get(1).myDraw_Spirit(PubSet.context, canvas, paint, 381-F_x, 313-F_y,Direction.右下, false);
				//土地
//				npclist.get(3).myDraw_Spirit(PubSet.context, canvas, paint, 379-F_x, 316-F_y,Direction.右上, false);
				
			}
			objslist.get(1).myDraw_obj(canvas, paint, 125-F_x, 522-F_y, 8, 1, 7, 1f,spirit_x,spirit_y,ToDo.切换场景,MapName.方寸山,185,379,604,1488);
			objslist.get(3).myDraw_obj(canvas, paint, 1575-F_x, 567-F_y, 8, 1, 7, 0.5f,0,0,ToDo.弹对话框,null,0,0,0,0);
		}

	};
	int go = 0;
	String rdstr1 = "4,4,0,0,0,0,0,0,0,0,-4,-4,-4,-4,-4,4,4,4,4,4,-4,-4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
	String rdstr2 = "4,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-4,-4,-4,-4,-4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
	public void getMoveRandom(Direction lastdc)
	{
		
		String[] rdstrlist = rdstr1.split(",");
		String[] rdstrlist2 = rdstr2.split(",");
		String lastrd = rdstrlist[go];
		String lastrd2 = rdstrlist2[go];
		zuobiao_x_new = Integer.valueOf(lastrd);
		zuobiao_y_new = Integer.valueOf(lastrd2);
		
		if(zuobiao_x_new>0&&zuobiao_y_new>0)
		{
			dc = Direction.右下;
			paodong = true;
		}
		if(zuobiao_x_new<0&&zuobiao_y_new<0)
		{
			dc = Direction.左上;
			paodong = true;
		}
		if(zuobiao_x_new>0&&zuobiao_y_new<0)
		{
			dc = Direction.右上;
			paodong = true;
		}
		if(zuobiao_x_new<0&&zuobiao_y_new>0)
		{
			dc = Direction.左下;
			paodong = true;
		}
		if(zuobiao_x_new==0&&zuobiao_y_new>0)
		{
			dc = Direction.下;
			paodong = true;
		}
		if(zuobiao_x_new==0&&zuobiao_y_new<0)
		{
			dc = Direction.上;
			paodong = true;
		}
		if(zuobiao_x_new>0&&zuobiao_y_new==0)
		{
			dc = Direction.左;
			paodong = true;
		}
		if(zuobiao_x_new<0&&zuobiao_y_new==0)
		{
			dc = Direction.右;
			paodong = true;
		}
		if(zuobiao_x_new==0&&zuobiao_y_new==0)
		{
			dc = lastdc;
			paodong = false;
		}

		if(go>=39)
		{
			go = 0;
		}
		
		go++;
	}
	
	public void getMoveRandom2(Direction lastdc)
	{
		
		String rdstr1 = "4,0,0,0,0,0,0,0,0,0,0,-4";
		String[] rdstrlist = rdstr1.split(",");
		String lastrd = rdstrlist[rd.nextInt(12)];
		String lastrd2 = rdstrlist[rd.nextInt(12)];
		zuobiao_x_new = Integer.valueOf(lastrd);
		zuobiao_y_new = Integer.valueOf(lastrd2);
		
		if(zuobiao_x_new>0&&zuobiao_y_new>0)
		{
			dc = Direction.右下;
			paodong = true;
		}
		if(zuobiao_x_new<0&&zuobiao_y_new<0)
		{
			dc = Direction.左上;
			paodong = true;
		}
		if(zuobiao_x_new>0&&zuobiao_y_new<0)
		{
			dc = Direction.右上;
			paodong = true;
		}
		if(zuobiao_x_new<0&&zuobiao_y_new>0)
		{
			dc = Direction.左下;
			paodong = true;
		}
		if(zuobiao_x_new==0&&zuobiao_y_new>0)
		{
			dc = Direction.下;
			paodong = true;
		}
		if(zuobiao_x_new==0&&zuobiao_y_new<0)
		{
			dc = Direction.上;
			paodong = true;
		}
		if(zuobiao_x_new>0&&zuobiao_y_new==0)
		{
			dc = Direction.左;
			paodong = true;
		}
		if(zuobiao_x_new<0&&zuobiao_y_new==0)
		{
			dc = Direction.右;
			paodong = true;
		}
		if(zuobiao_x_new==0&&zuobiao_y_new==0)
		{
			dc = lastdc;
			paodong = false;
		}
		
	}

}

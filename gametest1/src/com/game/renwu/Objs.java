package com.game.renwu;

import java.util.ArrayList;
import java.util.List;

import com.example.gametest1.GameView;
import com.game.base.GameMap;
import com.game.commen.MapName;
import com.game.commen.ToDo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.widget.Toast;

public class Objs {

	private Bitmap ObjsImg;
	List<Rect> rectlist;

	public Objs(Bitmap ObjsImg) {
		super();
		this.ObjsImg = ObjsImg;
		rectlist = new ArrayList<Rect>();
	}

	public int playflag = 0;

	int oneimgwidth=0;
	int oneimgHeight=0;
	Rect dst;
	
	public boolean show_shuohua =  false;
	ToDo todo = ToDo.弹对话框;
	/**
	 * 参数：画布，画笔，物件显示屏幕上X,Y坐标，横，竖向切图数 ，总显示数，物体事件参数，传送阵相关：传送地图名，精灵坐标XY，地图坐标XY
	 * **/
	public void myDraw_obj(Canvas canvas, Paint paint, float x, float y,
			int horizontalcutnum, int verticalcutnum, int cutnum, float zoom,int spirit_x,
			int spirit_y,ToDo todo,MapName mapname,int SF_X,int SF_Y,int MF_X,int MF_Y) {

		this.todo=todo;
		rectlist = getRectList(ObjsImg, horizontalcutnum, verticalcutnum);// 8帧状态人物图
		oneimgwidth = ObjsImg.getWidth() / horizontalcutnum;
		oneimgHeight = ObjsImg.getHeight() / verticalcutnum;
		dst = new Rect();// 屏幕位置及尺寸
		dst.left = (int) x - (oneimgwidth / 2);
		dst.top = (int) y - (oneimgHeight / 2);
		dst.right = (int) x + (int) ((oneimgwidth / 2) * zoom);
		dst.bottom = (int) y + (int) ((oneimgHeight / 2) * zoom);

		if (playflag >= cutnum) {
			playflag = 0;
		}

		canvas.drawBitmap(ObjsImg, rectlist.get(playflag), dst, paint);
		
		
		playflag++;
		
		
		if(show_shuohua==true)
		{
		
			String familyName = "宋体";
			Paint p_talk = new Paint();
			p_talk.setTextSize(16);
			Typeface font3 = Typeface.create(familyName, Typeface.NORMAL);
			p_talk.setColor(Color.WHITE);
			p_talk.setTypeface(font3);
			// 炫上黑色背景
			Paint p_talk2 = new Paint();
			p_talk2.setTextSize(16);
			Typeface font4 = Typeface.create(familyName, Typeface.BOLD);
			p_talk2.setColor(Color.BLACK);
			p_talk2.setTypeface(font4);
			canvas.drawText("我是乖孩子!", x - (oneimgwidth / 3) + 2, y
					- (int) (oneimgHeight * 0.85) + 2, p_talk2);
			canvas.drawText("我是乖孩子!", x - (oneimgwidth / 3), y
					- (int) (oneimgHeight * 0.85), p_talk);
		}
		
		//碰撞检测,触摸到之后，进行操作//
		//人物移动过来触摸到，触发场景切换//
		if (spirit_x > dst.left && spirit_x < dst.right
				&& spirit_y > (dst.top+(oneimgHeight *0.5)) && spirit_y < dst.bottom) {
			//切换场景
			if(todo==ToDo.切换场景)
			{
				GameMap.F_x=MF_X;
				GameMap.F_y=MF_Y;
				GameView.zhujiao_x = SF_X;
				GameView.zhujiao_y = SF_Y;
				GameView.zhujiao_x_new = SF_X;
				GameView.zhujiao_y_new = SF_Y;
				GameView.mapflag = mapname;
			}
		}
		
	}

	//激活点击事件
	public void onTouch(MotionEvent e) {
		int x = (int) e.getX();
		int y = (int) e.getY();

		if (e.getAction() == MotionEvent.ACTION_DOWN
				|| e.getAction() == MotionEvent.ACTION_MOVE) {
			if (x > dst.left && x < dst.right
					&& y > dst.top && y < dst.bottom) {
				//切换场景
				if(todo==ToDo.弹对话框)
				{
					show_shuohua=true;
				}
			}
		}
	}

	/*
	 * 参数描述
	 * 
	 * lastleft lasttop 左上角起始坐标 horizontalcutnum 横向截图数 verticalcutnum 竖向截图数
	 */
	public List<Rect> getRectList(Bitmap bmp, int horizontalcutnum,
			int verticalcutnum) {
		List<Rect> rectlist = new ArrayList<Rect>();
		// 计算一帧的图像素大小
		int oneimgwidth = bmp.getWidth() / horizontalcutnum;
		int oneimgHeight = bmp.getHeight() / verticalcutnum;

		for (int i = 0; i < verticalcutnum; i++) {
			for (int j = 0; j < horizontalcutnum; j++) {
				Rect bmpRect = new Rect();
				int left = oneimgwidth * j;
				int top = oneimgHeight * i;
				int right = oneimgwidth * (j + 1);
				int bottom = oneimgHeight * (i + 1);
				bmpRect.set(left, top, right, bottom);
				rectlist.add(bmpRect);
			}
		}

		return rectlist;
	}

}

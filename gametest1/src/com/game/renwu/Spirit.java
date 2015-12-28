package com.game.renwu;

import java.util.ArrayList;
import java.util.List;

import com.game.data.RoleData;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Spirit {

	List<Rect> rectlist;

	private RoleData roloinfo;
	
	public Spirit(RoleData roloinfo) {
		super();
		this.roloinfo = roloinfo;
		rectlist = new ArrayList<Rect>();
	}

	public int playflag = 0;

	public void myDraw_Spirit(Canvas canvas, Paint paint,int x,int y) {

		//设置动画帧数
				int oneimgwidth = roloinfo.getPlayerImg().getWidth() / roloinfo.getHorizontalcutnum();
				int oneimgHeight = roloinfo.getPlayerImg().getHeight() / roloinfo.getVerticalcutnum();
				
				rectlist = getRectList(roloinfo.getPlayerImg(), roloinfo.getHorizontalcutnum(), roloinfo.getVerticalcutnum());
				
				// 处理设定显示坐标
				Rect dst = new Rect();
				// 让人物显示居中点
				dst.left = (int) x - (oneimgwidth / 2);
				dst.top = (int) y - (oneimgHeight / 2);
				dst.right = (int) x + oneimgwidth / 2;
				dst.bottom = (int) y + oneimgHeight / 2;

				if (playflag >= roloinfo.getPlaynum() == true) {
					playflag = 0;
				}
				
				// 影子的帧
				Rect rect_yinzi = new Rect();
				rect_yinzi.left = 0;
				rect_yinzi.top = 0;
				rect_yinzi.right = roloinfo.getYinziImg().getWidth();
				rect_yinzi.bottom = roloinfo.getYinziImg().getHeight();
				
				// 影是在人的脚下踩着的
				Rect rect_yinzi1 = new Rect();
				// 显示在人物脚下
				// 显示在人物脚下
				rect_yinzi1.left = (int) x -roloinfo.getYinziImg().getHeight();
				rect_yinzi1.top = (int) y + roloinfo.getYinziImg().getHeight()/2;
				rect_yinzi1.right = (int) x + roloinfo.getYinziImg().getHeight();
				rect_yinzi1.bottom = (int) y + (int)(roloinfo.getYinziImg().getHeight()*1.5);
				
				//按照顺序依次显示,影子，人，武器
				canvas.drawBitmap(roloinfo.getYinziImg(), rect_yinzi , rect_yinzi1, paint);
				canvas.drawBitmap(roloinfo.getPlayerImg(), rectlist.get(playflag), dst, paint);
				
				
				playflag++;

				dst = null;
	}

	/*
	 * 参数描述
	 * 
	 * horizontalcutnum 横向截图数 verticalcutnum 竖向截图数
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

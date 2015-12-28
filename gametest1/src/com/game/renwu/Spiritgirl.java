package com.game.renwu;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Spiritgirl {

	private Bitmap SpiritImg;
	List<Rect> rectlist ;
	public Spiritgirl() {
		super();
		
		rectlist = new ArrayList<Rect>();
	}
	
	public int playflag = 0;
	
	public void myDraw_renwu(Canvas canvas, Paint paint ,float x, float y) {

		rectlist = getRectList(SpiritImg,9,8);//9帧状态人物图
		int oneimgwidth = SpiritImg.getWidth()/9;
		int oneimgHeight = SpiritImg.getHeight()/8;
		Rect dst = new Rect();// 屏幕位置及尺寸
		// 下面的 dst 是表示 绘画这个图片的位置
		dst.left =(int) x-(oneimgwidth/2); 
		dst.top =(int) y-(oneimgHeight/2); 
		dst.right = (int)x+oneimgwidth/2; 
		dst.bottom =(int) y+oneimgHeight/2; 
		
		if(playflag>=3==true)
		{
			playflag=0;	
		}
		
		canvas.drawBitmap(SpiritImg, rectlist.get(playflag), dst, paint);
		playflag++;
		
		
		
//		Rect src1 = new Rect();// 图片
////		src1 = getRectList(src1);
//		
//		Rect src = new Rect();// 图片
//		Rect dst = new Rect();// 屏幕位置及尺寸
//		// src 这个是表示绘画图片的大小
//		src.left = 0; // 0,0
//		src.top = 0;
//		src.right = renwu1.getWidth()/8 ;// mBitDestTop.getWidth();,这个是桌面图的宽度，
//		src.bottom = renwu1.getHeight();// mBitDestTop.getHeight()/2;// 这个是桌面图的高度的一半
//		
//		int oneimgwidth = renwu1.getWidth()/8;
//		int oneimgHeight = renwu1.getHeight()/1;
//		
//		// 下面的 dst 是表示 绘画这个图片的位置
//		dst.left =(int) x-(oneimgwidth/2); 
//		dst.top =(int) y-(oneimgHeight/2); 
//		dst.right = (int)x+oneimgwidth/2; 
//		dst.bottom =(int) y+oneimgHeight/2; 
//		canvas.drawBitmap(renwu1, src, dst, paint);
		
		dst = null;

	}

	/*参数描述 
	*
	*  lastleft lasttop 左上角起始坐标
	*  horizontalcutnum 横向截图数
	*  verticalcutnum 竖向截图数
	*/
	public List<Rect> getRectList(Bitmap bmp,int horizontalcutnum,int verticalcutnum) {
		List<Rect> rectlist = new ArrayList<Rect>();
		//计算一帧的图像素大小
		int oneimgwidth = bmp.getWidth()/horizontalcutnum;
		int oneimgHeight = bmp.getHeight()/verticalcutnum;
		
		for (int i = 0; i < verticalcutnum; i++) {
			for (int j = 0; j < horizontalcutnum; j++) {
				Rect bmpRect = new Rect();
				int left = oneimgwidth*j;
				int top = oneimgHeight*i;
				int right = oneimgwidth*(j+1);
				int bottom = oneimgHeight*(i+1);
				bmpRect.set(left, top, right, bottom);
				rectlist.add(bmpRect);
			}
		}
		
		
		return rectlist;
	}

}

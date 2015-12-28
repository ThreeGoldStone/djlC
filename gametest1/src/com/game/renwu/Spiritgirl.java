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

		rectlist = getRectList(SpiritImg,9,8);//9֡״̬����ͼ
		int oneimgwidth = SpiritImg.getWidth()/9;
		int oneimgHeight = SpiritImg.getHeight()/8;
		Rect dst = new Rect();// ��Ļλ�ü��ߴ�
		// ����� dst �Ǳ�ʾ �滭���ͼƬ��λ��
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
		
		
		
//		Rect src1 = new Rect();// ͼƬ
////		src1 = getRectList(src1);
//		
//		Rect src = new Rect();// ͼƬ
//		Rect dst = new Rect();// ��Ļλ�ü��ߴ�
//		// src ����Ǳ�ʾ�滭ͼƬ�Ĵ�С
//		src.left = 0; // 0,0
//		src.top = 0;
//		src.right = renwu1.getWidth()/8 ;// mBitDestTop.getWidth();,���������ͼ�Ŀ�ȣ�
//		src.bottom = renwu1.getHeight();// mBitDestTop.getHeight()/2;// ���������ͼ�ĸ߶ȵ�һ��
//		
//		int oneimgwidth = renwu1.getWidth()/8;
//		int oneimgHeight = renwu1.getHeight()/1;
//		
//		// ����� dst �Ǳ�ʾ �滭���ͼƬ��λ��
//		dst.left =(int) x-(oneimgwidth/2); 
//		dst.top =(int) y-(oneimgHeight/2); 
//		dst.right = (int)x+oneimgwidth/2; 
//		dst.bottom =(int) y+oneimgHeight/2; 
//		canvas.drawBitmap(renwu1, src, dst, paint);
		
		dst = null;

	}

	/*�������� 
	*
	*  lastleft lasttop ���Ͻ���ʼ����
	*  horizontalcutnum �����ͼ��
	*  verticalcutnum �����ͼ��
	*/
	public List<Rect> getRectList(Bitmap bmp,int horizontalcutnum,int verticalcutnum) {
		List<Rect> rectlist = new ArrayList<Rect>();
		//����һ֡��ͼ���ش�С
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

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

		//���ö���֡��
				int oneimgwidth = roloinfo.getPlayerImg().getWidth() / roloinfo.getHorizontalcutnum();
				int oneimgHeight = roloinfo.getPlayerImg().getHeight() / roloinfo.getVerticalcutnum();
				
				rectlist = getRectList(roloinfo.getPlayerImg(), roloinfo.getHorizontalcutnum(), roloinfo.getVerticalcutnum());
				
				// �����趨��ʾ����
				Rect dst = new Rect();
				// ��������ʾ���е�
				dst.left = (int) x - (oneimgwidth / 2);
				dst.top = (int) y - (oneimgHeight / 2);
				dst.right = (int) x + oneimgwidth / 2;
				dst.bottom = (int) y + oneimgHeight / 2;

				if (playflag >= roloinfo.getPlaynum() == true) {
					playflag = 0;
				}
				
				// Ӱ�ӵ�֡
				Rect rect_yinzi = new Rect();
				rect_yinzi.left = 0;
				rect_yinzi.top = 0;
				rect_yinzi.right = roloinfo.getYinziImg().getWidth();
				rect_yinzi.bottom = roloinfo.getYinziImg().getHeight();
				
				// Ӱ�����˵Ľ��²��ŵ�
				Rect rect_yinzi1 = new Rect();
				// ��ʾ���������
				// ��ʾ���������
				rect_yinzi1.left = (int) x -roloinfo.getYinziImg().getHeight();
				rect_yinzi1.top = (int) y + roloinfo.getYinziImg().getHeight()/2;
				rect_yinzi1.right = (int) x + roloinfo.getYinziImg().getHeight();
				rect_yinzi1.bottom = (int) y + (int)(roloinfo.getYinziImg().getHeight()*1.5);
				
				//����˳��������ʾ,Ӱ�ӣ��ˣ�����
				canvas.drawBitmap(roloinfo.getYinziImg(), rect_yinzi , rect_yinzi1, paint);
				canvas.drawBitmap(roloinfo.getPlayerImg(), rectlist.get(playflag), dst, paint);
				
				
				playflag++;

				dst = null;
	}

	/*
	 * ��������
	 * 
	 * horizontalcutnum �����ͼ�� verticalcutnum �����ͼ��
	 */
	public List<Rect> getRectList(Bitmap bmp, int horizontalcutnum,
			int verticalcutnum) {
		List<Rect> rectlist = new ArrayList<Rect>();
		// ����һ֡��ͼ���ش�С
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

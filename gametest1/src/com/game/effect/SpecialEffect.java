package com.game.effect;

import java.util.ArrayList;
import java.util.List;

import com.game.base.BaseInfo;
import com.game.base.PubSet;
import com.game.commen.BitmapUtil;
import com.game.commen.Direction;
import com.game.commen.EffectName;
import com.game.data.RoleData;
import com.game.data.RoleData_Main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class SpecialEffect {

	private String effectUrl = "";

	private int maxnum = 0;

	private boolean repeat;
	String lastpath = "";
	public int flag = 0;
	int flagpao = 0;

	public EffectName effectName ;
	public SpecialEffect(EffectName effectName ,String effectUrl, int maxnum,boolean repeat) {
		super();
		this.effectUrl = effectUrl;
		this.maxnum = maxnum;
		this.repeat=repeat;
		this.effectName=effectName;
	}

	public void myDraw_Effect(Context context, Canvas canvas, Paint paint,
			int x, int y) {

		
		// ���÷��������ͼ
		SetImgUrl(effectUrl, maxnum ,repeat);

		if (flag < maxnum) {
			Bitmap bmp = BitmapUtil.getBitmapFromAssets(context, lastpath);
			if (bmp != null) {

				// ���ö���֡��
				int oneimgwidth = bmp.getWidth();
				int oneimgHeight = bmp.getHeight();

				Rect bmpRect = new Rect();
				int left = 0;
				int top = 0;
				int right = oneimgwidth;
				int bottom = oneimgHeight;
				bmpRect.set(left, top, right, bottom);

				// �����趨��ʾ����
				Rect dst = new Rect();
				// ��������ʾ���е�
				dst.left = (int) x - oneimgwidth / 2;
				dst.top = (int) y - oneimgHeight / 2 - oneimgHeight / 4;
				dst.right = (int) x + oneimgwidth / 2;
				dst.bottom = (int) y + oneimgHeight / 2 - oneimgHeight / 4;

				canvas.drawBitmap(bmp, bmpRect, dst, paint);

				bmp.recycle();
				bmp = null;
				dst = null;
			}
		}
		
	}

	// ƴ�ն�������
	public void SetImgUrl(String ActionUrl, int MaxNum, boolean repeat) {
		if (flag < 10) {
			lastpath = ActionUrl + "0000" + flag + ".png";
		} else {
			lastpath = ActionUrl + "000" + flag + ".png";
		}
		flag++;
		if (repeat == true) {
			if (flag >= MaxNum) {
				flag = 0;
			}
		}
	}

}

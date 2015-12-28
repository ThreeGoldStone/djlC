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
	ToDo todo = ToDo.���Ի���;
	/**
	 * ���������������ʣ������ʾ��Ļ��X,Y���꣬�ᣬ������ͼ�� ������ʾ���������¼���������������أ����͵�ͼ������������XY����ͼ����XY
	 * **/
	public void myDraw_obj(Canvas canvas, Paint paint, float x, float y,
			int horizontalcutnum, int verticalcutnum, int cutnum, float zoom,int spirit_x,
			int spirit_y,ToDo todo,MapName mapname,int SF_X,int SF_Y,int MF_X,int MF_Y) {

		this.todo=todo;
		rectlist = getRectList(ObjsImg, horizontalcutnum, verticalcutnum);// 8֡״̬����ͼ
		oneimgwidth = ObjsImg.getWidth() / horizontalcutnum;
		oneimgHeight = ObjsImg.getHeight() / verticalcutnum;
		dst = new Rect();// ��Ļλ�ü��ߴ�
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
		
			String familyName = "����";
			Paint p_talk = new Paint();
			p_talk.setTextSize(16);
			Typeface font3 = Typeface.create(familyName, Typeface.NORMAL);
			p_talk.setColor(Color.WHITE);
			p_talk.setTypeface(font3);
			// ���Ϻ�ɫ����
			Paint p_talk2 = new Paint();
			p_talk2.setTextSize(16);
			Typeface font4 = Typeface.create(familyName, Typeface.BOLD);
			p_talk2.setColor(Color.BLACK);
			p_talk2.setTypeface(font4);
			canvas.drawText("���ǹԺ���!", x - (oneimgwidth / 3) + 2, y
					- (int) (oneimgHeight * 0.85) + 2, p_talk2);
			canvas.drawText("���ǹԺ���!", x - (oneimgwidth / 3), y
					- (int) (oneimgHeight * 0.85), p_talk);
		}
		
		//��ײ���,������֮�󣬽��в���//
		//�����ƶ����������������������л�//
		if (spirit_x > dst.left && spirit_x < dst.right
				&& spirit_y > (dst.top+(oneimgHeight *0.5)) && spirit_y < dst.bottom) {
			//�л�����
			if(todo==ToDo.�л�����)
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

	//�������¼�
	public void onTouch(MotionEvent e) {
		int x = (int) e.getX();
		int y = (int) e.getY();

		if (e.getAction() == MotionEvent.ACTION_DOWN
				|| e.getAction() == MotionEvent.ACTION_MOVE) {
			if (x > dst.left && x < dst.right
					&& y > dst.top && y < dst.bottom) {
				//�л�����
				if(todo==ToDo.���Ի���)
				{
					show_shuohua=true;
				}
			}
		}
	}

	/*
	 * ��������
	 * 
	 * lastleft lasttop ���Ͻ���ʼ���� horizontalcutnum �����ͼ�� verticalcutnum �����ͼ��
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

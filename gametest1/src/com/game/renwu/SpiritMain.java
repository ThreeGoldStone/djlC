package com.game.renwu;

import java.util.ArrayList;
import java.util.List;

import com.game.commen.Direction;
import com.game.data.RoleData;

import android.R;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class SpiritMain {

	/*
	 * ////////////////////////////////////////////////////////////////////////
	 * 猪脚的代码这篇就是传说中的纸娃娃系统了，我们专业点也可以叫精灵系统Spirit它RPG游戏中常见的游戏系统部件之一，角色扮演，这就是我们的猪脚！
	 * 那么猪脚应该有哪些属性和部件呢？就跟我们写代码的同学一样，所了解的面相对象；
	 * 车的类型是一个类，但是车的组成部件（属性）有：车轱辘，发动机，方向盘，车厢，座椅等若干；
	 * 我们这个游戏里面猪脚有：武器，身体，和坐骑，影子（人都应该有影子）；忽然发现我们男猪脚的功能还是很强大的！
	 */
	// 角色帧动画八个方向
	private List<Rect> rectlist;
	// 武器帧动画
	private List<Rect> waplist;
	// 是否显示武器
	public static boolean showwap = false;
	private static RoleData roloinfo;

	public SpiritMain(RoleData roloinfo) {
		super();

		this.roloinfo = roloinfo;
		rectlist = new ArrayList<Rect>();
		waplist = new ArrayList<Rect>();
	}

	int lastflag = 0;
	int flag = 0;
	int flagpao = 3;
	public int[] playflag = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	public List<String> strlist;

	// 装载武器
	public static void SetWaepenImg(Bitmap wapimg) {
		roloinfo.setWeaponsImg(wapimg);
	}

	int talkline = 0;//有几行对话的字
	int talktime = 20;//每条信息显示多久
	public static int talkflag = 0;//循环参数
	
	public void myDraw_Spirit(Canvas canvas, Paint paint, int x, int y,
			Direction fangxiang, boolean pao) {

		// 设置动画帧数
		int oneimgwidth = roloinfo.getPlayerImg().getWidth()
				/ roloinfo.getHorizontalcutnum();
		int oneimgHeight = roloinfo.getPlayerImg().getHeight()
				/ roloinfo.getVerticalcutnum();

		rectlist = getRectList(roloinfo.getPlayerImg(),
				roloinfo.getHorizontalcutnum(), roloinfo.getVerticalcutnum());
		// 是否有武器
		if (roloinfo.getWeaponsImg() != null) {
			waplist = getRectList(roloinfo.getWeaponsImg(),
					roloinfo.getHorizontalcutnum(),
					roloinfo.getVerticalcutnum());
		}
		// 设置八方向
		SetFangXiang(fangxiang, pao);

		// 处理设定显示坐标
		Rect dst = new Rect();
		// 让人物显示居中点
		dst.left = (int) x - oneimgwidth / 2;
		dst.top = (int) y - oneimgHeight / 2 - oneimgHeight / 4;
		dst.right = (int) x + oneimgwidth / 2;
		dst.bottom = (int) y + oneimgHeight / 2 - oneimgHeight / 4;

		// 影子的帧
		Rect rect_yinzi = new Rect();
		rect_yinzi.left = 0;
		rect_yinzi.top = 0;
		rect_yinzi.right = roloinfo.getYinziImg().getWidth();
		rect_yinzi.bottom = (int) (roloinfo.getYinziImg().getHeight() * 1);

		String str = "我想跟你说一个故事，这个故事很好笑，我准备说了哈，好：故事！。完了";
		strlist = new ArrayList<String>();

		if (str.length() > 0) {
			if ((int) (str.length() % 8) != 0) {
				talkline = (int) (str.length() / 8) + 1;
			} else {
				talkline = (int) (str.length() / 8);
			}
			for (int i = 0; i < talkline; i++) {
				if (i == talkline - 1) {
					strlist.add(str.substring(i * 8, str.length()));
				} else {
					strlist.add(str.substring(i * 8, (i * 8) + 8));
				}

			}
		}

		// 影是在人的脚下踩着的
		Rect rect_yinzi1 = new Rect();
		// 显示在人物脚下
		rect_yinzi1.left = (int) x - roloinfo.getYinziImg().getWidth() / 2;
		rect_yinzi1.top = (int) y - roloinfo.getYinziImg().getHeight();
		rect_yinzi1.right = (int) x + roloinfo.getYinziImg().getWidth() / 2;
		rect_yinzi1.bottom = (int) y
				+ (int) (roloinfo.getYinziImg().getHeight() * 0.4);

		// 按照顺序依次显示,影子，人，武器，名字
		canvas.drawBitmap(roloinfo.getYinziImg(), rect_yinzi, rect_yinzi1,
				paint);
		canvas.drawBitmap(roloinfo.getPlayerImg(), rectlist.get(lastflag), dst,
				paint);
		if (showwap == true) {
			canvas.drawBitmap(roloinfo.getWeaponsImg(), waplist.get(lastflag),
					dst, paint);
		}

		// 游戏字体渲染也是很重要的元素
		paint.setTextSize(20);
		String familyName = "宋体";
		Typeface font = Typeface.create(familyName, Typeface.NORMAL);
		paint.setColor(Color.WHITE);
		paint.setTypeface(font);
		// 炫上黑色背景
		Paint p2 = new Paint();
		p2.setTextSize(21);
		Typeface font2 = Typeface.create(familyName, Typeface.BOLD);
		p2.setColor(Color.BLACK);
		p2.setTypeface(font2);
		canvas.drawText(roloinfo.getPlayerName(), x - (oneimgwidth / 8) + 1, y
				- (oneimgHeight / 2) + 1, p2);
		canvas.drawText(roloinfo.getPlayerName(), x - (oneimgwidth / 8), y
				- (oneimgHeight / 2), paint);

		// 对话框气泡
		Rect rect_talkpaopao = new Rect();
		rect_talkpaopao.left = 0;
		rect_talkpaopao.top = 0;
		rect_talkpaopao.right = roloinfo.getTalkbdImg().getWidth();
		rect_talkpaopao.bottom = roloinfo.getTalkbdImg().getHeight();

		Rect rect_talkpaopao1 = new Rect();
		// 气泡显示在人物头上
		rect_talkpaopao1.left = (int) x - roloinfo.getTalkbdImg().getWidth()
				/ 2;
		rect_talkpaopao1.top = (int) y
				- (int) (roloinfo.getTalkbdImg().getHeight() * 6)
				- (int) (roloinfo.getTalkbdImg().getHeight() * talkline);
		rect_talkpaopao1.right = (int) x + roloinfo.getTalkbdImg().getWidth()
				/ 2;
		rect_talkpaopao1.bottom = (int) y
				- (int) (roloinfo.getTalkbdImg().getHeight() * 5);

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

		canvas.drawBitmap(roloinfo.getTalkbdImg(), rect_talkpaopao,
				rect_talkpaopao1, paint);

		int j = 0;
		for (int i = talkline - 1; i >= 0; i--) {

			canvas.drawText(strlist.get(j), x - (oneimgwidth / 3) + 2,
					y - (int) (roloinfo.getTalkbdImg().getHeight() * (6 + i))
							+ 2, p_talk2);
			canvas.drawText(strlist.get(j), x - (oneimgwidth / 3), y
					- (int) (roloinfo.getTalkbdImg().getHeight() * (6 + i)),
					p_talk);
			j++;
		}

		// paint.setColor(R.color.transparent);
//		dst = null;
//		p2.reset();
	}

	/**
	 * 参数描述 horizontalcutnum 横向截图数 verticalcutnum 竖向截图数
	 */
	public List<Rect> getRectList(Bitmap bmp, int horizontalcutnum,
			int verticalcutnum) {
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

	public void SetFangXiang(Direction fangxiang, boolean pao) {
		if (fangxiang == Direction.左) {
			if (pao != true) {

				lastflag = playflag[flag] + (63);
				flag++;
				if (flag >= 3) {
					flag = 0;
				}
			} else {

				lastflag = playflag[flagpao] + (63);
				flagpao++;
				if (flagpao >= 8) {
					flagpao = 3;
				}
			}
		} else if (fangxiang == Direction.右) {
			if (pao != true) {
				lastflag = playflag[flag] + (45);
				flag++;
				if (flag >= 3) {
					flag = 0;
				}
			} else {
				lastflag = playflag[flagpao] + (45);
				flagpao++;
				if (flagpao >= 8) {
					flagpao = 3;
				}
			}
		} else if (fangxiang == Direction.上) {
			if (pao != true) {
				lastflag = playflag[flag] + (54);
				flag++;
				if (flag >= 3) {
					flag = 0;
				}
			} else {
				lastflag = playflag[flagpao] + (54);
				flagpao++;
				if (flagpao >= 8) {
					flagpao = 3;
				}
			}
		} else if (fangxiang == Direction.下) {
			if (pao != true) {
				lastflag = playflag[flag] + (36);
				flag++;
				if (flag >= 3) {
					flag = 0;
				}
			} else {
				lastflag = playflag[flagpao] + (36);
				flagpao++;
				if (flagpao >= 8) {
					flagpao = 3;
				}
			}
		} else if (fangxiang == Direction.右上) {
			if (pao != true) {
				lastflag = playflag[flag] + (27);
				flag++;
				if (flag >= 3) {
					flag = 0;
				}
			} else {
				lastflag = playflag[flagpao] + (27);
				flagpao++;
				if (flagpao >= 8) {
					flagpao = 3;
				}
			}
		} else if (fangxiang == Direction.右下) {
			if (pao != true) {
				lastflag = playflag[flag] + (0);
				flag++;
				if (flag >= 3) {
					flag = 0;
				}
			} else {
				lastflag = playflag[flagpao] + (0);
				flagpao++;
				if (flagpao >= 8) {
					flagpao = 3;
				}
			}
		} else if (fangxiang == Direction.左下) {
			if (pao != true) {
				lastflag = playflag[flag] + (9);
				flag++;
				if (flag >= 3) {
					flag = 0;
				}
			} else {
				lastflag = playflag[flagpao] + (9);
				flagpao++;
				if (flagpao >= 8) {
					flagpao = 3;
				}
			}
		} else if (fangxiang == Direction.左上) {
			if (pao != true) {
				lastflag = playflag[flag] + (17);
				flag++;
				if (flag >= 3) {
					flag = 0;
				}
			} else {
				lastflag = playflag[flagpao] + (17);
				flagpao++;
				if (flagpao >= 8) {
					flagpao = 3;
				}
			}
		}
	}
}

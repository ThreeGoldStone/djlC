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
	 * ��ŵĴ�����ƪ���Ǵ�˵�е�ֽ����ϵͳ�ˣ�����רҵ��Ҳ���Խо���ϵͳSpirit��RPG��Ϸ�г�������Ϸϵͳ����֮һ����ɫ���ݣ���������ǵ���ţ�
	 * ��ô���Ӧ������Щ���ԺͲ����أ��͸�����д�����ͬѧһ�������˽���������
	 * ����������һ���࣬���ǳ�����ɲ��������ԣ��У�����ꤣ��������������̣����ᣬ���ε����ɣ�
	 * ���������Ϸ��������У����������壬�����Ӱ�ӣ��˶�Ӧ����Ӱ�ӣ�����Ȼ������������ŵĹ��ܻ��Ǻ�ǿ��ģ�
	 */
	// ��ɫ֡�����˸�����
	private List<Rect> rectlist;
	// ����֡����
	private List<Rect> waplist;
	// �Ƿ���ʾ����
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

	// װ������
	public static void SetWaepenImg(Bitmap wapimg) {
		roloinfo.setWeaponsImg(wapimg);
	}

	int talkline = 0;//�м��жԻ�����
	int talktime = 20;//ÿ����Ϣ��ʾ���
	public static int talkflag = 0;//ѭ������
	
	public void myDraw_Spirit(Canvas canvas, Paint paint, int x, int y,
			Direction fangxiang, boolean pao) {

		// ���ö���֡��
		int oneimgwidth = roloinfo.getPlayerImg().getWidth()
				/ roloinfo.getHorizontalcutnum();
		int oneimgHeight = roloinfo.getPlayerImg().getHeight()
				/ roloinfo.getVerticalcutnum();

		rectlist = getRectList(roloinfo.getPlayerImg(),
				roloinfo.getHorizontalcutnum(), roloinfo.getVerticalcutnum());
		// �Ƿ�������
		if (roloinfo.getWeaponsImg() != null) {
			waplist = getRectList(roloinfo.getWeaponsImg(),
					roloinfo.getHorizontalcutnum(),
					roloinfo.getVerticalcutnum());
		}
		// ���ð˷���
		SetFangXiang(fangxiang, pao);

		// �����趨��ʾ����
		Rect dst = new Rect();
		// ��������ʾ���е�
		dst.left = (int) x - oneimgwidth / 2;
		dst.top = (int) y - oneimgHeight / 2 - oneimgHeight / 4;
		dst.right = (int) x + oneimgwidth / 2;
		dst.bottom = (int) y + oneimgHeight / 2 - oneimgHeight / 4;

		// Ӱ�ӵ�֡
		Rect rect_yinzi = new Rect();
		rect_yinzi.left = 0;
		rect_yinzi.top = 0;
		rect_yinzi.right = roloinfo.getYinziImg().getWidth();
		rect_yinzi.bottom = (int) (roloinfo.getYinziImg().getHeight() * 1);

		String str = "�������˵һ�����£�������ºܺ�Ц����׼��˵�˹����ã����£�������";
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

		// Ӱ�����˵Ľ��²��ŵ�
		Rect rect_yinzi1 = new Rect();
		// ��ʾ���������
		rect_yinzi1.left = (int) x - roloinfo.getYinziImg().getWidth() / 2;
		rect_yinzi1.top = (int) y - roloinfo.getYinziImg().getHeight();
		rect_yinzi1.right = (int) x + roloinfo.getYinziImg().getWidth() / 2;
		rect_yinzi1.bottom = (int) y
				+ (int) (roloinfo.getYinziImg().getHeight() * 0.4);

		// ����˳��������ʾ,Ӱ�ӣ��ˣ�����������
		canvas.drawBitmap(roloinfo.getYinziImg(), rect_yinzi, rect_yinzi1,
				paint);
		canvas.drawBitmap(roloinfo.getPlayerImg(), rectlist.get(lastflag), dst,
				paint);
		if (showwap == true) {
			canvas.drawBitmap(roloinfo.getWeaponsImg(), waplist.get(lastflag),
					dst, paint);
		}

		// ��Ϸ������ȾҲ�Ǻ���Ҫ��Ԫ��
		paint.setTextSize(20);
		String familyName = "����";
		Typeface font = Typeface.create(familyName, Typeface.NORMAL);
		paint.setColor(Color.WHITE);
		paint.setTypeface(font);
		// ���Ϻ�ɫ����
		Paint p2 = new Paint();
		p2.setTextSize(21);
		Typeface font2 = Typeface.create(familyName, Typeface.BOLD);
		p2.setColor(Color.BLACK);
		p2.setTypeface(font2);
		canvas.drawText(roloinfo.getPlayerName(), x - (oneimgwidth / 8) + 1, y
				- (oneimgHeight / 2) + 1, p2);
		canvas.drawText(roloinfo.getPlayerName(), x - (oneimgwidth / 8), y
				- (oneimgHeight / 2), paint);

		// �Ի�������
		Rect rect_talkpaopao = new Rect();
		rect_talkpaopao.left = 0;
		rect_talkpaopao.top = 0;
		rect_talkpaopao.right = roloinfo.getTalkbdImg().getWidth();
		rect_talkpaopao.bottom = roloinfo.getTalkbdImg().getHeight();

		Rect rect_talkpaopao1 = new Rect();
		// ������ʾ������ͷ��
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
		// ���Ϻ�ɫ����
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
	 * �������� horizontalcutnum �����ͼ�� verticalcutnum �����ͼ��
	 */
	public List<Rect> getRectList(Bitmap bmp, int horizontalcutnum,
			int verticalcutnum) {
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

	public void SetFangXiang(Direction fangxiang, boolean pao) {
		if (fangxiang == Direction.��) {
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
		} else if (fangxiang == Direction.��) {
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
		} else if (fangxiang == Direction.��) {
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
		} else if (fangxiang == Direction.��) {
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
		} else if (fangxiang == Direction.����) {
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
		} else if (fangxiang == Direction.����) {
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
		} else if (fangxiang == Direction.����) {
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
		} else if (fangxiang == Direction.����) {
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

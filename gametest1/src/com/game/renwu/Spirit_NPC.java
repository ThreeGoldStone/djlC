package com.game.renwu;

import java.util.ArrayList;
import java.util.List;

import com.game.base.BaseInfo;
import com.game.base.PubSet;
import com.game.commen.BitmapUtil;
import com.game.commen.Direction;
import com.game.commen.ToDo;
import com.game.data.RoleData;
import com.game.data.RoleData_Main;
import com.game.effect.SpecialEffect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class Spirit_NPC {

	private RoleData_Main roloinfo;

	String lastpath = "";
	String select_lastpath = "";
	int flag_select = 0;
	int flag = 0;
	int flagpao = 0;

	public int F_x = 0;
	public int F_y = 0;
	public String[] playflag_select = { "00", "01", "02", "03", "04", "05","06","07","08","09","10","11","12","13","14","15","16","17" };
	public int[] playflag = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	public int[] playflag2 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	public List<String> strlist;
	int talkline = 0;// �м��жԻ�����
	int talktime = 25;// ÿ����Ϣ��ʾ���
	public static int talkflag = 0;// ѭ������

	public Spirit_NPC(RoleData_Main roloinfo) {
		super();
		this.roloinfo = roloinfo;
	}

	private Rect dst;

	public Rect lastdd;

	private boolean selectflag = false;

	public void myDraw_Spirit(Context context, Canvas canvas, Paint paint,
			int x, int y, Direction fangxiang, boolean pao) {

		// ���÷��������ͼ
		SetFangXiang(roloinfo.getPlayerImgUrl_pao(),
				roloinfo.getPlayerImgUrl_zhan(), fangxiang, pao);
		SetSelectPlay(roloinfo.getPlayerImgUrl_selectflag());
		Bitmap bmp = BitmapUtil.getBitmapFromAssets(context, lastpath);
		Bitmap bmp_select = BitmapUtil.getBitmapFromAssets(context,
				select_lastpath);
		if (bmp != null && bmp_select != null) {

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
			dst = new Rect();
			// ��������ʾ���е�
			dst.left = (int) x - oneimgwidth / 2;
			dst.top = (int) y - oneimgHeight / 2 - oneimgHeight / 4;
			dst.right = (int) x + oneimgwidth / 2;
			dst.bottom = (int) y + oneimgHeight / 2 - oneimgHeight / 4;
			lastdd = dst;
			// Ӱ�ӵ�֡
			Rect rect_yinzi = new Rect();
			rect_yinzi.left = 0;
			rect_yinzi.top = 0;
			rect_yinzi.right = roloinfo.getYinziImg().getWidth();
			rect_yinzi.bottom = roloinfo.getYinziImg().getHeight();

			// Ӱ�����˵Ľ��²��ŵ�
			Rect rect_yinzi1 = new Rect();
			// ��ʾ���������
			rect_yinzi1.left = (int) x
					- (int) (roloinfo.getYinziImg().getWidth() * 0.7);
			rect_yinzi1.top = (int) y;
			rect_yinzi1.right = (int) x
					+ (int) (roloinfo.getYinziImg().getWidth() / 2);
			rect_yinzi1.bottom = (int) y
					+ (int) (roloinfo.getYinziImg().getHeight() * 1);

			// ѡ�й⻷��֡
			Rect rect_select = new Rect();
			rect_select.left = 0;
			rect_select.top = 0;
			rect_select.right =(int)(bmp_select.getWidth()*1.6);
			rect_select.bottom = (int)(bmp_select.getHeight()*1.3);

			// Ӱ�����˵Ľ��²��ŵ�
			Rect rect_select1 = new Rect();
			// ��ʾ���������
			rect_select1.left = (int) x - (int) (bmp_select.getWidth() * 0.3);
			rect_select1.top = (int) y - (int)(bmp_select.getHeight() * 0.3);
			rect_select1.right = (int) x + (int) (bmp_select.getWidth() / 1.4);
			rect_select1.bottom = (int) y + (int) (bmp_select.getHeight() * 0.9);

			// ����˳��������ʾ,Ӱ�ӣ��ˣ�����

			canvas.drawBitmap(roloinfo.getYinziImg(), rect_yinzi, rect_yinzi1,
					paint);
			// �Ƿ�ѡ��
			if (selectflag == true) {
//				BaseInfo.setAttack_X(x);
//				BaseInfo.setAttack_Y(y);
				canvas.drawBitmap(bmp_select, rect_select, rect_select1, paint);
			}

			canvas.drawBitmap(bmp, bmpRect, dst, paint);

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
			canvas.drawText(roloinfo.getPlayerName(), x
					- (int) (oneimgwidth / 2.8) + 1, y
					- (int) (oneimgHeight / 1.2) + 1, p2);
			canvas.drawText(roloinfo.getPlayerName(), x
					- (int) (oneimgwidth / 2.8),
					y - (int) (oneimgHeight / 1.2), paint);

			// �Ի�������
			Rect rect_talkpaopao = new Rect();
			rect_talkpaopao.left = 0;
			rect_talkpaopao.top = 0;
			rect_talkpaopao.right = roloinfo.getTalkbdImg().getWidth();
			rect_talkpaopao.bottom = roloinfo.getTalkbdImg().getHeight();

			Rect rect_talkpaopao1 = new Rect();
			// ������ʾ������ͷ��
			rect_talkpaopao1.left = (int) x
					- roloinfo.getTalkbdImg().getWidth() / 2;
			rect_talkpaopao1.top = (int) y
					- (int) (roloinfo.getTalkbdImg().getHeight() * 5.5)
					- (int) (roloinfo.getTalkbdImg().getHeight() * talkline);
			rect_talkpaopao1.right = (int) x
					+ roloinfo.getTalkbdImg().getWidth() / 2;
			rect_talkpaopao1.bottom = (int) y
					- (int) (roloinfo.getTalkbdImg().getHeight() * 4.5);

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

			String str = roloinfo.getTalkAbout();
			// ˵������
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
				if (talkflag <= talktime) {
					canvas.drawBitmap(roloinfo.getTalkbdImg(), rect_talkpaopao,
							rect_talkpaopao1, paint);
					int j = 0;
					for (int i = talkline - 1; i >= 0; i--) {

						canvas.drawText(strlist.get(j),
								x
										- (int) (roloinfo.getTalkbdImg()
												.getWidth() * 0.45) + 2, y
										- (int) (roloinfo.getTalkbdImg()
												.getHeight() * (5.5 + i)) + 2,
								p_talk2);
						canvas.drawText(strlist.get(j),
								x
										- (int) (roloinfo.getTalkbdImg()
												.getWidth() * 0.45), y
										- (int) (roloinfo.getTalkbdImg()
												.getHeight() * (5.5 + i)),
								p_talk);
						j++;
					}
					talkflag++;
				}
			}

			bmp.recycle();
			bmp = null;
			// dst = null;
		}
	}

	public void SetSelectPlay(String RoleImgUrl_select) {
		select_lastpath = RoleImgUrl_select + "000" + playflag_select[flag_select] + ".png";
		flag_select++;
		if (flag_select >= 18) {
			flag_select = 0;
		}
	}

	// ��ȡ����
	public void SetFangXiang(String RoleImgUrl_pao, String RoleImgUrl_zhan,
			Direction fangxiang, boolean pao) {
		if (fangxiang == Direction.��) {
			if (pao != true) {
				if (flag < 10) {
					lastpath = RoleImgUrl_zhan + "0700" + flag + ".png";
				} else {
					lastpath = RoleImgUrl_zhan + "070" + flag + ".png";
				}
				flag++;
				if (flag >= roloinfo.getZhan_max()) {
					flag = 0;
				}
			} else {
				if (flagpao < 10) {
					lastpath = RoleImgUrl_pao + "0700" + flagpao + ".png";
				} else {
					lastpath = RoleImgUrl_pao + "070" + flagpao + ".png";
				}
				flagpao++;
				if (flagpao >= roloinfo.getPao_max()) {
					flagpao = 0;
				}
			}
		} else if (fangxiang == Direction.��) {
			if (pao != true) {

				if (flag < 10) {
					lastpath = RoleImgUrl_zhan + "0500" + flag + ".png";
				} else {
					lastpath = RoleImgUrl_zhan + "050" + flag + ".png";
				}
				flag++;
				if (flag >= roloinfo.getZhan_max()) {
					flag = 0;
				}
			} else {
				if (flagpao < 10) {
					lastpath = RoleImgUrl_pao + "0500" + flagpao + ".png";
				} else {
					lastpath = RoleImgUrl_pao + "050" + flagpao + ".png";
				}
				flagpao++;
				if (flagpao >= roloinfo.getPao_max()) {
					flagpao = 0;
				}
			}
		} else if (fangxiang == Direction.��) {
			if (pao != true) {

				if (flag < 10) {
					lastpath = RoleImgUrl_zhan + "0600" + flag + ".png";
				} else {
					lastpath = RoleImgUrl_zhan + "060" + flag + ".png";
				}
				if (flag >= roloinfo.getZhan_max()) {
					flag = 0;
				}
			} else {
				if (flagpao < 10) {
					lastpath = RoleImgUrl_pao + "0600" + flagpao + ".png";
				} else {
					lastpath = RoleImgUrl_pao + "060" + flagpao + ".png";
				}
				flagpao++;
				if (flagpao >= roloinfo.getPao_max()) {
					flagpao = 0;
				}
			}
		} else if (fangxiang == Direction.��) {
			if (pao != true) {

				if (flag < 10) {
					lastpath = RoleImgUrl_zhan + "0400" + flag + ".png";
				} else {
					lastpath = RoleImgUrl_zhan + "040" + flag + ".png";
				}
				flag++;
				if (flag >= roloinfo.getZhan_max()) {
					flag = 0;
				}
			} else {
				if (flagpao < 10) {
					lastpath = RoleImgUrl_pao + "0400" + flagpao + ".png";
				} else {
					lastpath = RoleImgUrl_pao + "040" + flagpao + ".png";
				}
				flagpao++;
				if (flagpao >= roloinfo.getPao_max()) {
					flagpao = 0;
				}
			}
		} else if (fangxiang == Direction.����) {
			if (pao != true) {

				if (flag < 10) {
					lastpath = RoleImgUrl_zhan + "0300" + flag + ".png";
				} else {
					lastpath = RoleImgUrl_zhan + "030" + flag + ".png";
				}
				flag++;
				if (flag >= roloinfo.getZhan_max()) {
					flag = 0;
				}
			} else {
				if (flagpao < 10) {
					lastpath = RoleImgUrl_pao + "0300" + flagpao + ".png";
				} else {
					lastpath = RoleImgUrl_pao + "030" + flagpao + ".png";
				}
				flagpao++;
				if (flagpao >= roloinfo.getPao_max()) {
					flagpao = 0;
				}
			}
		} else if (fangxiang == Direction.����) {
			if (pao != true) {

				if (flag < 10) {
					lastpath = RoleImgUrl_zhan + "0000" + flag + ".png";
				} else {
					lastpath = RoleImgUrl_zhan + "000" + flag + ".png";
				}
				flag++;
				if (flag >= roloinfo.getZhan_max()) {
					flag = 0;
				}
			} else {
				if (flagpao < 10) {
					lastpath = RoleImgUrl_pao + "0000" + flagpao + ".png";
				} else {
					lastpath = RoleImgUrl_pao + "000" + flagpao + ".png";
				}
				flagpao++;
				if (flagpao >= roloinfo.getPao_max()) {
					flagpao = 0;
				}
			}
		} else if (fangxiang == Direction.����) {
			if (pao != true) {

				if (flag < 10) {
					lastpath = RoleImgUrl_zhan + "0100" + flag + ".png";
				} else {
					lastpath = RoleImgUrl_zhan + "010" + flag + ".png";
				}
				flag++;
				if (flag >= roloinfo.getZhan_max()) {
					flag = 0;
				}
			} else {
				if (flagpao < 10) {
					lastpath = RoleImgUrl_pao + "0100" + flagpao + ".png";
				} else {
					lastpath = RoleImgUrl_pao + "010" + flagpao + ".png";
				}
				flagpao++;
				if (flagpao >= roloinfo.getPao_max()) {
					flagpao = 0;
				}
			}
		} else if (fangxiang == Direction.����) {
			if (pao != true) {

				lastpath = RoleImgUrl_zhan + "0200" + playflag[flag] + ".png";
				flag++;
				if (flag >= roloinfo.getZhan_max()) {
					flag = 0;
				}
			} else {
				if (flagpao < 10) {
					lastpath = RoleImgUrl_pao + "0200" + flagpao + ".png";
				} else {
					lastpath = RoleImgUrl_pao + "020" + flagpao + ".png";
				}
				flagpao++;
				if (flagpao >= roloinfo.getPao_max()) {
					flagpao = 0;
				}
			}
		}
	}

	// �������¼�
	public boolean onTouch(MotionEvent e) {
		int x = (int) e.getX();
		int y = (int) e.getY();

		if (e.getAction() == MotionEvent.ACTION_UP
				) {
			if (lastdd != null) {
				if (x > lastdd.left && x < lastdd.right && y > lastdd.top
						&& y < lastdd.bottom) {
					selectflag = true;
					BaseInfo.setSetPlay(1);
				}
				else
				{
					BaseInfo.setSetPlay(0);
					selectflag = false;
				}
			}
		}
		return selectflag;
	}
}

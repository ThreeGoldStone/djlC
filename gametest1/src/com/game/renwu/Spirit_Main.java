package com.game.renwu;

import java.util.ArrayList;
import java.util.List;

import com.example.gametest1.GameView;
import com.game.base.PubSet;
import com.game.commen.ActionToDo;
import com.game.commen.BitmapUtil;
import com.game.commen.Direction;
import com.game.commen.EffectName;
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

public class Spirit_Main {

	private RoleData_Main roloinfo;
	private List<SpecialEffect> effectlist;
	String lastpath = "";
	int flag = 0;
	int flagpao = 0;
	public int flaggongji = 0;

	public int[] playflag = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	public int[] playflag2 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	public int[] playflag3 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };
	public List<String> strlist;
	int talkline = 0;// 有几行对话的字
	int talktime = 100;// 每条信息显示多久
	public static int talkflag = 0;// 循环参数

	public Spirit_Main(RoleData_Main roloinfo, List<SpecialEffect> effectlist) {
		super();
		this.effectlist = effectlist;
		this.roloinfo = roloinfo;
	}

	public void myDraw_Spirit(Context context, Canvas canvas, Paint paint,
			int x, int y, Direction fangxiang, ActionToDo acttodo) {

		// 设置方向和人物图
		SetFangXiang(roloinfo.getPlayerImgUrl_pao(),
				roloinfo.getPlayerImgUrl_zhan(),
				roloinfo.getPlayerImgUrl_attack(), fangxiang, acttodo);

		Bitmap bmp = BitmapUtil.getBitmapFromAssets(context, lastpath);
		if (bmp != null) {

			// 设置动画帧数
			int oneimgwidth = bmp.getWidth();
			int oneimgHeight = bmp.getHeight();

			Rect bmpRect = new Rect();
			int left = 0;
			int top = 0;
			int right = oneimgwidth;
			int bottom = oneimgHeight;
			bmpRect.set(left, top, right, bottom);

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
			rect_yinzi.bottom = roloinfo.getYinziImg().getHeight();

			// 影是在人的脚下踩着的
			Rect rect_yinzi1 = new Rect();
			// 显示在人物脚下
			rect_yinzi1.left = (int) x
					- (int) (roloinfo.getYinziImg().getWidth() * 0.7);
			rect_yinzi1.top = (int) y;
			rect_yinzi1.right = (int) x
					+ (int) (roloinfo.getYinziImg().getWidth() / 2);
			rect_yinzi1.bottom = (int) y
					+ (int) (roloinfo.getYinziImg().getHeight() * 1);

			// 按照顺序依次显示,影子，人，武器
			canvas.drawBitmap(roloinfo.getYinziImg(), rect_yinzi, rect_yinzi1,
					paint);
			canvas.drawBitmap(bmp, bmpRect, dst, paint);

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
			canvas.drawText(roloinfo.getPlayerName(), x
					- (int) (oneimgwidth / 2.8) + 1, y
					- (int) (oneimgHeight / 1.2) + 1, p2);
			canvas.drawText(roloinfo.getPlayerName(), x
					- (int) (oneimgwidth / 2.8),
					y - (int) (oneimgHeight / 1.2), paint);

			String str = "";
			if (roloinfo.getTalkAbout() != null) {
				str = roloinfo.getTalkAbout();
			}

			// 说话气泡
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

			// 对话框气泡
			Rect rect_talkpaopao = new Rect();
			rect_talkpaopao.left = 0;
			rect_talkpaopao.top = 0;
			rect_talkpaopao.right = roloinfo.getTalkbdImg().getWidth();
			rect_talkpaopao.bottom = roloinfo.getTalkbdImg().getHeight();

			Rect rect_talkpaopao1 = new Rect();
			// 气泡显示在人物头上
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
			// 炫上黑色背景
			Paint p_talk2 = new Paint();
			p_talk2.setTextSize(16);
			Typeface font4 = Typeface.create(familyName, Typeface.BOLD);
			p_talk2.setColor(Color.BLACK);
			p_talk2.setTypeface(font4);
			if (str.length() > 0) {
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
			
			// 人物特效
			if (effectlist != null) {
				for (int i = 0; i < effectlist.size(); i++) {
					effectlist.get(i).myDraw_Effect(context, canvas, paint, x, y);
					
					
				}
			}
			
			bmp.recycle();
			bmp = null;
			dst = null;
		}
	}

	// 获取方向
	public void SetFangXiang(String RoleImgUrl_pao, String RoleImgUrl_zhan,
			String RoleImgUrl_attack, Direction fangxiang, ActionToDo acttodo) {
		if (fangxiang == Direction.左) {
			if (acttodo == ActionToDo.站立) {

				lastpath = RoleImgUrl_zhan + "0700" + playflag[flag] + ".png";
				flag++;
				if (flag >= roloinfo.getZhan_max()) {
					flag = 0;
				}
			} else if (acttodo == ActionToDo.跑动) {
				lastpath = RoleImgUrl_pao + "0700" + playflag2[flagpao]
						+ ".png";
				flagpao++;
				if (flagpao >= roloinfo.getPao_max()) {
					flagpao = 0;
				}
			}
		} else if (fangxiang == Direction.右) {
			if (acttodo == ActionToDo.站立) {

				lastpath = RoleImgUrl_zhan + "0500" + playflag[flag] + ".png";
				flag++;
				if (flag >= roloinfo.getZhan_max()) {
					flag = 0;
				}
			} else if (acttodo == ActionToDo.跑动) {
				lastpath = RoleImgUrl_pao + "0500" + playflag2[flagpao]
						+ ".png";
				flagpao++;
				if (flagpao >= roloinfo.getPao_max()) {
					flagpao = 0;
				}
			}
		} else if (fangxiang == Direction.上) {
			if (acttodo == ActionToDo.站立) {

				lastpath = RoleImgUrl_zhan + "0600" + playflag[flag] + ".png";
				if (flag >= roloinfo.getZhan_max()) {
					flag = 0;
				}
			} else if (acttodo == ActionToDo.跑动) {
				lastpath = RoleImgUrl_pao + "0600" + playflag2[flagpao]
						+ ".png";
				flagpao++;
				if (flagpao >= roloinfo.getPao_max()) {
					flagpao = 0;
				}
			}
		} else if (fangxiang == Direction.下) {
			if (acttodo == ActionToDo.站立) {

				lastpath = RoleImgUrl_zhan + "0400" + playflag[flag] + ".png";
				flag++;
				if (flag >= roloinfo.getZhan_max()) {
					flag = 0;
				}
			} else if (acttodo == ActionToDo.跑动) {
				lastpath = RoleImgUrl_pao + "0400" + playflag2[flagpao]
						+ ".png";
				flagpao++;
				if (flagpao >= roloinfo.getPao_max()) {
					flagpao = 0;
				}
			}
		} else if (fangxiang == Direction.右上) {
			if (acttodo == ActionToDo.站立) {

				lastpath = RoleImgUrl_zhan + "0300" + playflag[flag] + ".png";
				flag++;
				if (flag >= roloinfo.getZhan_max()) {
					flag = 0;
				}
			} else if (acttodo == ActionToDo.跑动) {
				lastpath = RoleImgUrl_pao + "0300" + playflag2[flagpao]
						+ ".png";
				flagpao++;
				if (flagpao >= roloinfo.getPao_max()) {
					flagpao = 0;
				}
			} else if (acttodo == ActionToDo.攻击) {
				lastpath = RoleImgUrl_attack + "0300" + playflag3[flaggongji]
						+ ".png";
				flaggongji++;
				if (flaggongji >= roloinfo.getPao_max()) {
					GameView.paodong=ActionToDo.站立;
				}
			}
		} else if (fangxiang == Direction.右下) {
			if (acttodo == ActionToDo.站立) {

				lastpath = RoleImgUrl_zhan + "0000" + playflag[flag] + ".png";
				flag++;
				if (flag >= roloinfo.getZhan_max()) {
					flag = 0;
				}
			} else if (acttodo == ActionToDo.跑动) {
				lastpath = RoleImgUrl_pao + "0000" + playflag2[flagpao]
						+ ".png";
				flagpao++;
				if (flagpao >= roloinfo.getPao_max()) {
					flagpao = 0;
				}
			} else if (acttodo == ActionToDo.攻击) {
				lastpath = RoleImgUrl_attack + "0000" + playflag3[flaggongji]
						+ ".png";
				flaggongji++;
				if (flaggongji >= roloinfo.getPao_max()) {
					GameView.paodong=ActionToDo.站立;
				}
			}
		} else if (fangxiang == Direction.左下) {
			if (acttodo == ActionToDo.站立) {

				lastpath = RoleImgUrl_zhan + "0100" + playflag[flag] + ".png";
				flag++;
				if (flag >= roloinfo.getZhan_max()) {
					flag = 0;
				}
			} else if (acttodo == ActionToDo.跑动) {
				lastpath = RoleImgUrl_pao + "0100" + playflag2[flagpao]
						+ ".png";
				flagpao++;
				if (flagpao >= roloinfo.getPao_max()) {
					flagpao = 0;
				}
			} else if (acttodo == ActionToDo.攻击) {
				lastpath = RoleImgUrl_attack + "0100" + playflag3[flaggongji]
						+ ".png";
				flaggongji++;
				if (flaggongji >= roloinfo.getPao_max()) {
					GameView.paodong=ActionToDo.站立;
				}
			}
		} else if (fangxiang == Direction.左上) {
			if (acttodo == ActionToDo.站立) {

				lastpath = RoleImgUrl_zhan + "0200" + playflag[flag] + ".png";
				flag++;
				if (flag >= roloinfo.getZhan_max()) {
					flag = 0;
				}
			} else if (acttodo == ActionToDo.跑动) {
				lastpath = RoleImgUrl_pao + "0200" + playflag2[flagpao]
						+ ".png";
				flagpao++;
				if (flagpao >= roloinfo.getPao_max()) {
					flagpao = 0;
				}
			} else if (acttodo == ActionToDo.攻击) {
				lastpath = RoleImgUrl_attack + "0200" + playflag3[flaggongji]
						+ ".png";
				flaggongji++;
				if (flaggongji >= roloinfo.getPao_max()) {
					GameView.paodong=ActionToDo.站立;
				}
			}
		}
	}

}

package com.example.gametest1;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.game.base.BaseInfo;
import com.game.base.GameBackGround;
import com.game.base.GameMap;
import com.game.base.PubSet;
import com.game.commen.ActionToDo;
import com.game.commen.Direction;
import com.game.commen.EffectName;
import com.game.commen.GetImgCommen;
import com.game.commen.MapName;
import com.game.commen.ToDo;
import com.game.data.RoleData;
import com.game.data.RoleData_Main;
import com.game.effect.SpecialEffect;
import com.game.renwu.Objs;
import com.game.renwu.Spirit;
import com.game.renwu.SpiritMain;
import com.game.renwu.Spirit_Main;
import com.game.renwu.Spirit_NPC;

public class GameView extends SurfaceView implements Callback, Runnable {
	public static SurfaceHolder holder;
	public static Canvas canvas;
	public static boolean draw_flag;// �滭�߳�
	private Thread th;
	private GameBackGround gameBG;// ��Ϸ������ʵ��
	// ����� -С��
	private SpiritMain s_xiaoming;
	private static List<Spirit> npclist;

	private Objs Obj_ChuanSong;// ��һ������
	private Objs Obj_ChuanSong2;// �ڶ�������
	// ���
	private Objs Obj_tianhu;// �ڶ�������
	// ����
	private Objs Obj_caishen;// �ڶ�������

	// �����С��
	public static RoleData_Main role_xiaohua;

	private Context mContext;
	private Boolean over = false;
	private int of = 0;
	private float mX, mY;
	private Path mPath;
	private static final float TOUCH_TOLERANCE = 4;
	private Paint lPaint;
	private Paint noPaint;
	private static boolean shownpc = false;

	public static int paodongflag = 0; // 0,Ĭ��ģʽ 1������ģʽ 2�����ģʽ 3���������ģʽ
	public static MapName mapflag = MapName.����ɽ;// �������� 1������ɽ 2��Ů���ֶ��� 3��ţħ������
	// ��ŵ�X,Y����
	public static float zhujiao_x = 200;
	public static float zhujiao_y = 300;
	public static float zhujiao_x_new = 200;
	public static float zhujiao_y_new = 300;

	// Ĭ��ˢ�¶���֡��ʱ��
	private int paotime = 20;

	// ���Ĭ�Ϸ���
	Direction dc = Direction.��;
	// ���Ĭ���Ƿ��ܲ�״̬
	public static ActionToDo paodong = ActionToDo.վ��;
	// ����ܲ��ٶ�
	private int paospeed = 20;// ��ֵԽ������ܵ�Խ��
	// NPC�ƶ�·��
	private ArrayList<PointF> graphics = new ArrayList<PointF>();

	// ��ͼ�༭����
	GameMap maps_fangcunshan;// ����ɽ
	GameMap maps_niumowang;// ţħ������
	GameMap maps_taohuadong;// Ů���ֶ���

	public static Spirit_Main sprirt_xiaohua;
	private Spirit_NPC sprirt_shusheng;
	// private Spirit_NPC sprirt_npc_xianren;
	// private Spirit_NPC sprirt_npc_tudi;
	// private Spirit_NPC sprirt_npc_nvxianren;
	// private Spirit_NPC sprirt_npc_dawang;
	// private Spirit_NPC sprirt_npc_longwang;

	// �����༭��--�ͷŴ�ħ��
	public static SpecialEffect jineng1;
	public static SpecialEffect feijian;
	private float Effect_x = 0;
	private float Effect_y = 0;
	private float Effect_x_new = 0;
	private float Effect_y_new = 0;

	//
	private boolean zhanliflag = false;

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		holder = this.getHolder();
		holder.addCallback(this);
		setFocusable(true);
		setFocusableInTouchMode(true);
		// ���ñ�������
		this.setKeepScreenOn(true);

		// �������������滭��Ϸ
		mPath = new Path();
		lPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		lPaint.setColor(Color.BLACK);
		lPaint.setStyle(Paint.Style.STROKE);// ����
		lPaint.setStrokeJoin(Paint.Join.ROUND);
		lPaint.setStrokeCap(Paint.Cap.ROUND);
		lPaint.setStrokeWidth(5);
		noPaint = new Paint();
		npclist = new ArrayList<Spirit>();
	}

	public static void add_NPC(RoleData npc) {
		if (npc != null) {
			Spirit spiritnpc = new Spirit(npc);
			npclist.add(spiritnpc);
			// ��ʾ
			shownpc = true;
		}
	}

	// װ�ػ�����Ϸ�¼������壬NPC��
	public void initGameBase() {
		// ����С��������ŵڶ��ֶ����༭��ʽ
		role_xiaohua = new RoleData_Main();
		role_xiaohua.setPlayerImgUrl_pao("spirit/zhujiao/pao/2314-f44c3fde-");
		role_xiaohua.setPlayerImgUrl_zhan("spirit/zhujiao/zhan/1700-af8399b0-");
		role_xiaohua.setPlayerImgUrl_attack("spirit/zhujiao/attack/0642-3e26809d-");
		role_xiaohua.setPao_max(7);
		role_xiaohua.setZhan_max(6);
		role_xiaohua.setPlayerName("��̫��");
		role_xiaohua.setYinziImg(GetImgCommen.readBitMap(mContext, R.drawable.yinzi));
		role_xiaohua.setTalkbdImg(GetImgCommen.readBitMap(mContext, R.drawable.duihuakuang));

		RoleData_Main role_shusheng = new RoleData_Main();
		role_shusheng.setPlayerImgUrl_pao("spirit/shusheng/pao/0168-122d197f-");
		role_shusheng.setPlayerImgUrl_zhan("spirit/shusheng/zhan/0573-3878205a-");
		role_shusheng.setPlayerImgUrl_selectflag("spirit/npcpublic/0003-16c93edf-");
		role_shusheng.setPao_max(7);
		role_shusheng.setZhan_max(6);
		role_shusheng.setPlayerName("��ң��");
		role_shusheng.setTalkAbout("�Բۣ���̫˧�ˣ��Һ��ҵ�С����Ƕ�������...");
		role_shusheng.setYinziImg(GetImgCommen.readBitMap(mContext, R.drawable.yinzi));
		role_shusheng.setTalkbdImg(GetImgCommen.readBitMap(mContext, R.drawable.duihuakuang));
		sprirt_shusheng = new Spirit_NPC(role_shusheng);

		// RoleData_Mode2 role_xianren = new RoleData_Mode2();
		// role_xianren.setPlayerImgUrl_pao("spirit/shusheng/pao/0168-122d197f-");
		// role_xianren
		// .setPlayerImgUrl_zhan("spirit/npc/xianren/1952-ca3334ff-");
		// role_xianren
		// .setPlayerImgUrl_selectflag("spirit/npcpublic/0003-16c93edf-");
		// role_xianren.setPao_max(7);
		// role_xianren.setZhan_max(10);
		// role_xianren.setPlayerName("̫�׽���");
		// role_xianren.setTalkAbout("�ٺٺ٣�С�����ҿ�������ѣ��Ǹ������˵����Ӱ�.�����Ҿ�������...");
		// role_xianren.setYinziImg(GetImgCommen.readBitMap(mContext,
		// R.drawable.yinzi));
		// role_xianren.setTalkbdImg(GetImgCommen.readBitMap(mContext,
		// R.drawable.duihuakuang));
		// sprirt_npc_xianren = new Spirit_NPC(role_xianren);
		//
		// RoleData_Mode2 role_dawang = new RoleData_Mode2();
		// role_dawang.setPlayerImgUrl_pao("spirit/shusheng/pao/0168-122d197f-");
		// role_dawang
		// .setPlayerImgUrl_zhan("spirit/npc/dawang/2158-e1a120ff-");
		// role_dawang
		// .setPlayerImgUrl_selectflag("spirit/npcpublic/0003-16c93edf-");
		// role_dawang.setPao_max(7);
		// role_dawang.setZhan_max(8);
		// role_dawang.setPlayerName("ɽկ����");
		// role_dawang.setTalkAbout("���գ��Ҵ�ħ�����λ�������Ⱦƣ�������̫�������٣��Һܸ��ˣ�̫����!");
		// role_dawang.setYinziImg(GetImgCommen.readBitMap(mContext,
		// R.drawable.yinzi));
		// role_dawang.setTalkbdImg(GetImgCommen.readBitMap(mContext,
		// R.drawable.duihuakuang));
		// sprirt_npc_dawang = new Spirit_NPC(role_dawang);
		//
		// RoleData_Mode2 role_tudi = new RoleData_Mode2();
		// role_tudi.setPlayerImgUrl_pao("spirit/shusheng/pao/0168-122d197f-");
		// role_tudi
		// .setPlayerImgUrl_zhan("spirit/npc/tudi/1276-81895d83-");
		// role_tudi
		// .setPlayerImgUrl_selectflag("spirit/npcpublic/0003-16c93edf-");
		// role_tudi.setPao_max(7);
		// role_tudi.setZhan_max(13);
		// role_tudi.setPlayerName("��������");
		// role_tudi.setTalkAbout("������лл�������룬��ͷ��������ƺܺúȰ����ǲ��Ǵ�˵�е�ę́��");
		// role_tudi.setYinziImg(GetImgCommen.readBitMap(mContext,
		// R.drawable.yinzi));
		// role_tudi.setTalkbdImg(GetImgCommen.readBitMap(mContext,
		// R.drawable.duihuakuang));
		// sprirt_npc_tudi = new Spirit_NPC(role_tudi);

		// ����������Ч
		feijian = new SpecialEffect(EffectName.�����콣ǵ��, "texiao/zhujiao/tianjian2/0004-4db6c0a-",
				20, false);
		List<SpecialEffect> effectlist = new ArrayList<SpecialEffect>();
		effectlist.add(feijian);
		sprirt_xiaohua = new Spirit_Main(role_xiaohua, effectlist);
		// ����С��������ŵ�һ�ֶ����༭��ʽ
		RoleData role_xiaoming = new RoleData();
		role_xiaoming.setPlayerName("С��");
		role_xiaoming.setPlayerImg(GetImgCommen.readBitMap(mContext, R.drawable.rw_nanzhujiao));
		role_xiaoming.setWeaponsImg(GetImgCommen.readBitMap(mContext, R.drawable.weapons_0));
		role_xiaoming.setYinziImg(GetImgCommen.readBitMap(mContext, R.drawable.yinzi));
		role_xiaoming.setTalkbdImg(GetImgCommen.readBitMap(mContext, R.drawable.duihuakuang));
		role_xiaoming.setHorizontalcutnum(9);
		role_xiaoming.setVerticalcutnum(8);
		role_xiaoming.setPlaynum(3);
		s_xiaoming = new SpiritMain(role_xiaoming);

		Obj_ChuanSong = new Objs(GetImgCommen.readBitMap(mContext, R.drawable.chuansong));
		Obj_ChuanSong2 = new Objs(GetImgCommen.readBitMap(mContext, R.drawable.chuansong2));

		Obj_tianhu = new Objs(GetImgCommen.readBitMap(mContext, R.drawable.tianhu));
		Obj_caishen = new Objs(GetImgCommen.readBitMap(mContext, R.drawable.caishen));

		List<Objs> objslist = new ArrayList<Objs>();
		objslist.add(Obj_ChuanSong);
		objslist.add(Obj_ChuanSong2);
		objslist.add(Obj_tianhu);
		objslist.add(Obj_caishen);

		// ����
		jineng1 = new SpecialEffect(EffectName.NPC�콣ǵ��, "texiao/zhujiao/tianjian/0057-1ee9406c-",
				18, false);

		List<Spirit_NPC> Spiritlist = new ArrayList<Spirit_NPC>();
		Spiritlist.add(sprirt_shusheng);
		// Spiritlist.add(sprirt_npc_xianren);
		// Spiritlist.add(sprirt_npc_dawang);
		// Spiritlist.add(sprirt_npc_tudi);

		maps_niumowang = new GameMap(GetImgCommen.readBitMap(mContext, R.drawable.map_niumowang),
				93, 116, Spiritlist, objslist, MapName.ţħ������);
		maps_taohuadong = new GameMap(GetImgCommen.readBitMap(mContext, R.drawable.map_dongfu), 52,
				765, Spiritlist, objslist, MapName.Ů���ֶ���);
		maps_fangcunshan = new GameMap(
				GetImgCommen.readBitMap(mContext, R.drawable.map_fangcunshan), 472, 1585,
				Spiritlist, objslist, MapName.����ɽ);

	}

	// ��Ϸ�ĳ�ʼ��
	public void StartGame() {

		// װ����Ϸ
		initGameBase();

		gameBG = new GameBackGround(GetImgCommen.readBitMap(mContext, R.drawable.background));
		// �߳�ʵ��----------------- �����߳�
		draw_flag = true;
		th = new Thread(this);
		th.start();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (draw_flag) {
			try {
				myDraw();
				Thread.sleep(PubSet.reFlashTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private void myDraw() {
		// TODO Auto-generated method stub
		try {
			canvas = holder.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);

				if (paodongflag == 0) {
					gameBG.myDraw(canvas, noPaint);
					Obj_ChuanSong.myDraw_obj(canvas, noPaint, 60, 380, 7, 1, 6, 0.3f, 0, 0,
							ToDo.�л�����, null, 0, 0, 0, 0);
					Obj_ChuanSong2.myDraw_obj(canvas, noPaint, 700, 350, 8, 1, 7, 1f, 0, 0,
							ToDo.�л�����, null, 0, 0, 0, 0);
					Obj_tianhu.myDraw_obj(canvas, noPaint, 150, 350, 3, 1, 2, 0.3f, 0, 0,
							ToDo.���Ի���, null, 0, 0, 0, 0);
					Obj_caishen.myDraw_obj(canvas, noPaint, 200, 250, 8, 1, 7, 0.5f, 0, 0,
							ToDo.���Ի���, null, 0, 0, 0, 0);

					if (shownpc == true) {
						for (int i = 0; i < npclist.size(); i++) {
							npclist.get(i).myDraw_Spirit(canvas, noPaint, 50 + (i * 5), 400);
						}
					}
					s_xiaoming.myDraw_Spirit(canvas, noPaint, 550, 400, Direction.����, false);
					sprirt_xiaohua.myDraw_Spirit(mContext, canvas, noPaint, 680, 400, Direction.����,
							paodong);

					jineng1.myDraw_Effect(mContext, canvas, noPaint, (int) Effect_x, (int) Effect_y);

				} else if (paodongflag == 1) {
					gameBG.myDraw(canvas, noPaint);
					canvas.drawPath(mPath, lPaint);
					if (over && graphics.size() > 0) {
						s_xiaoming.myDraw_Spirit(canvas, noPaint, (int) graphics.get(of).x,
								(int) graphics.get(of).y, Direction.��, true);
						of += 1;
						if (of < graphics.size()) {
							if (of == graphics.size() - 1) {
								mPath.reset();// �ƶ���ɺ��Ƴ�����
							}
							invalidate();
						}
					}
				} else if (paodongflag == 2) {
					float from_x = 0;
					float from_y = 0;
					if (paotime > 0) {
						from_x = (zhujiao_x_new - zhujiao_x) / paotime;
						from_y = (zhujiao_y_new - zhujiao_y) / paotime;
					}

					// �򵥵�ʵ��4��б����������ܶ�
					if (from_x > 0 && from_y > 0) {
						dc = Direction.����;
						paodong = ActionToDo.�ܶ�;
					} else if (from_x > 0 && from_y < 0) {
						dc = Direction.����;
						paodong = ActionToDo.�ܶ�;
					} else if (from_x < 0 && from_y < 0) {
						dc = Direction.����;
						paodong = ActionToDo.�ܶ�;
					} else if (from_x < 0 && from_y > 0) {
						dc = Direction.����;
						paodong = ActionToDo.�ܶ�;
					} else if (from_x == 0 && from_y == 0) {

						if (BaseInfo.getAttack_X() > 0 && BaseInfo.getAttack_Y() > 0) {
							dc = Direction.����;
							int zhandc_x = (int) (BaseInfo.getAttack_X() - zhujiao_x);
							int zhandc_y = (int) (BaseInfo.getAttack_Y() - zhujiao_y);

							if (zhandc_x > 0 && zhandc_y > 0) {
								dc = Direction.����;
							} else if (zhandc_x > 0 && zhandc_y < 0) {
								dc = Direction.����;
							} else if (zhandc_x < 0 && zhandc_y < 0) {
								dc = Direction.����;
							} else if (zhandc_x < 0 && zhandc_y > 0) {
								dc = Direction.����;
							}
						} else {
							dc = Direction.����;
						}

						if (zhanliflag == false) {
							paodong = ActionToDo.վ��;
							zhanliflag = true;
						}
					}

					zhujiao_x += from_x;
					zhujiao_y += from_y;

					if (zhujiao_x > (PubSet.screenWidth * 0.95)
							&& zhujiao_y > (PubSet.screenHeight * 0.95)) {
						dc = Direction.����;
						paodong = ActionToDo.�ܶ�;
					}
					if (zhujiao_x < (PubSet.screenWidth * 0.05)
							&& zhujiao_y < (PubSet.screenHeight * 0.05)) {
						dc = Direction.����;
						paodong = ActionToDo.�ܶ�;
					}
					if (zhujiao_x > (PubSet.screenWidth * 0.95)
							&& zhujiao_y < (PubSet.screenHeight * 0.05)) {
						dc = Direction.����;
						paodong = ActionToDo.�ܶ�;
					}
					if (zhujiao_x < (PubSet.screenWidth * 0.05)
							&& zhujiao_y > (PubSet.screenHeight * 0.95)) {
						dc = Direction.����;
						paodong = ActionToDo.�ܶ�;
					}
					if (zhujiao_x > (PubSet.screenWidth * 0.05)
							&& zhujiao_x < (PubSet.screenWidth * 0.95)
							&& zhujiao_y > (PubSet.screenHeight * 0.95)) {
						dc = Direction.��;
						paodong = ActionToDo.�ܶ�;
					}
					if (zhujiao_x > (PubSet.screenWidth * 0.05)
							&& zhujiao_x < (PubSet.screenWidth * 0.95)
							&& zhujiao_y < (PubSet.screenHeight * 0.05)) {
						dc = Direction.��;
						paodong = ActionToDo.�ܶ�;
					}
					if (zhujiao_x > (PubSet.screenWidth * 0.95)
							&& zhujiao_y > (PubSet.screenHeight * 0.05)
							&& zhujiao_y < (PubSet.screenHeight * 0.95)) {
						dc = Direction.��;
						paodong = ActionToDo.�ܶ�;
					}
					if (zhujiao_x < (PubSet.screenWidth * 0.05)
							&& zhujiao_y > (PubSet.screenHeight * 0.05)
							&& zhujiao_y < (PubSet.screenHeight * 0.95)) {
						dc = Direction.��;
						paodong = ActionToDo.�ܶ�;
					}

					// ��������
					if (mapflag == MapName.����ɽ) {
						maps_fangcunshan.myDraw_Map(canvas, noPaint, (int) zhujiao_x,
								(int) zhujiao_y);
					} else if (mapflag == MapName.Ů���ֶ���) {
						maps_taohuadong.myDraw_Map(canvas, noPaint, (int) zhujiao_x,
								(int) zhujiao_y);
					} else if (mapflag == MapName.ţħ������) {
						maps_niumowang
								.myDraw_Map(canvas, noPaint, (int) zhujiao_x, (int) zhujiao_y);
					}
					// s_xiaoming.myDraw_Spirit(canvas, noPaint, 300,
					// 300,Direction.����,false);
					sprirt_xiaohua.myDraw_Spirit(mContext, canvas, noPaint, (int) zhujiao_x,
							(int) zhujiao_y, dc, paodong);
					jineng1.myDraw_Effect(mContext, canvas, noPaint, (int) BaseInfo.getAttack_X2(),
							(int) BaseInfo.getAttack_Y2());
					paotime--;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("eee", "e=" + e.toString());
		} finally {
			if (canvas != null) {
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		PubSet.resouces = this.getResources();
		PubSet.context = mContext;
		// ��Ļ���
		PubSet.screenWidth = this.getWidth();
		PubSet.screenHeight = this.getHeight();
		StartGame();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		draw_flag = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (paodongflag == 1) {
				over = false;
				graphics.clear();
				of = 0;
				graphics.add(new PointF(x, y));
				touch_start(x, y);
				invalidate();

			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (paodongflag == 1) {
				graphics.add(new PointF(x, y));
				touch_move(x, y);
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:

			if (paodongflag == 0) {
				Effect_x = x;
				Effect_y = y;
				sprirt_xiaohua.flaggongji = 0;
				jineng1.flag = 0;
				paodong = ActionToDo.����;
			} else if (paodongflag == 1) {
				over = true;
				touch_up();
				invalidate();
			} else if (paodongflag == 2) {
				if (sprirt_shusheng.onTouch(event) == false) {
					zhujiao_x_new = x;
					zhujiao_y_new = y;
					// �������õ��ٶ��������ܶ�
					paotime = (int) (((int) Math.abs(zhujiao_x_new - zhujiao_x) + (int) Math
							.abs(zhujiao_y_new - zhujiao_y)) / paospeed);
					zhanliflag = false;
					BaseInfo.setAttack_X(0);
					BaseInfo.setAttack_Y(0);
				} else {
					BaseInfo.setAttack_X(x);
					BaseInfo.setAttack_Y(y);
					BaseInfo.setAttack_X2(x);
					BaseInfo.setAttack_Y2(y);
				}

			}
			break;
		}

		return true;
	}

	private void touch_up() {
		mPath.lineTo(mX, mY);
	}

	private void touch_start(float x, float y) {
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
	}

}

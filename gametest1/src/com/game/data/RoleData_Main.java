package com.game.data;

import android.graphics.Bitmap;

public class RoleData_Main {

	// 人物角色名
	private String PlayerName;
	// 人物图路径名_跑动姿势
	private String PlayerImgUrl_pao;
    // 人物路径_站立
	private String PlayerImgUrl_zhan;
	//attack
	private String PlayerImgUrl_attack;
	// 人物说话气泡
	private Bitmap TalkbdImg;
	// 人物说话气泡
	private String TalkAbout;
	// NPC选中状态
	private String PlayerImgUrl_selectflag;
	

	// 最大跑步帧数
	private int pao_max;
	// 最大站步帧数
	private int zhan_max;
	
	// ——————————————————————————————————————————————————————————————//


	// 猪脚的特殊属性如下
	// 坐姿图片
	private Bitmap SetdownImg;
	// 武器图片
	private Bitmap WeaponsImg;
	// 坐骑图片
	private Bitmap MountImg;
	// 影子图片
	private Bitmap YinziImg;

	public String getPlayerImgUrl_selectflag() {
		return PlayerImgUrl_selectflag;
	}

	public void setPlayerImgUrl_selectflag(String playerImgUrl_selectflag) {
		PlayerImgUrl_selectflag = playerImgUrl_selectflag;
	}
	
	public String getPlayerImgUrl_attack() {
		return PlayerImgUrl_attack;
	}

	public void setPlayerImgUrl_attack(String playerImgUrl_attack) {
		PlayerImgUrl_attack = playerImgUrl_attack;
	}
	
	public String getPlayerName() {
		return PlayerName;
	}

	public void setPlayerName(String playerName) {
		PlayerName = playerName;
	}
	
	public Bitmap getWeaponsImg() {
		return WeaponsImg;
	}

	public void setWeaponsImg(Bitmap weaponsImg) {
		WeaponsImg = weaponsImg;
	}

	public Bitmap getMountImg() {
		return MountImg;
	}

	public void setMountImg(Bitmap mountImg) {
		MountImg = mountImg;
	}

	public Bitmap getYinziImg() {
		return YinziImg;
	}

	public void setYinziImg(Bitmap yinziImg) {
		YinziImg = yinziImg;
	}

	public Bitmap getTalkbdImg() {
		return TalkbdImg;
	}

	public void setTalkbdImg(Bitmap talkbdImg) {
		TalkbdImg = talkbdImg;
	}

	public Bitmap getSetdownImg() {
		return SetdownImg;
	}

	public void setSetdownImg(Bitmap setdownImg) {
		SetdownImg = setdownImg;
	}
	public String getPlayerImgUrl_pao() {
		return PlayerImgUrl_pao;
	}

	public void setPlayerImgUrl_pao(String playerImgUrl_pao) {
		PlayerImgUrl_pao = playerImgUrl_pao;
	}

	public String getPlayerImgUrl_zhan() {
		return PlayerImgUrl_zhan;
	}

	public void setPlayerImgUrl_zhan(String playerImgUrl_zhan) {
		PlayerImgUrl_zhan = playerImgUrl_zhan;
	}
	
	public int getPao_max() {
		return pao_max;
	}

	public void setPao_max(int pao_max) {
		this.pao_max = pao_max;
	}

	public int getZhan_max() {
		return zhan_max;
	}

	public void setZhan_max(int zhan_max) {
		this.zhan_max = zhan_max;
	}
	public String getTalkAbout() {
		return TalkAbout;
	}

	public void setTalkAbout(String talkAbout) {
		TalkAbout = talkAbout;
	}

}

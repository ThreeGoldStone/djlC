package com.game.data;

import android.graphics.Bitmap;

public class RoleData_Main {

	// �����ɫ��
	private String PlayerName;
	// ����ͼ·����_�ܶ�����
	private String PlayerImgUrl_pao;
    // ����·��_վ��
	private String PlayerImgUrl_zhan;
	//attack
	private String PlayerImgUrl_attack;
	// ����˵������
	private Bitmap TalkbdImg;
	// ����˵������
	private String TalkAbout;
	// NPCѡ��״̬
	private String PlayerImgUrl_selectflag;
	

	// ����ܲ�֡��
	private int pao_max;
	// ���վ��֡��
	private int zhan_max;
	
	// ����������������������������������������������������������������������������������������������������������������������������//


	// ��ŵ�������������
	// ����ͼƬ
	private Bitmap SetdownImg;
	// ����ͼƬ
	private Bitmap WeaponsImg;
	// ����ͼƬ
	private Bitmap MountImg;
	// Ӱ��ͼƬ
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

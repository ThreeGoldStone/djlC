package com.game.data;

import android.graphics.Bitmap;

public class RoleData {

	// �����ɫ��
	private String PlayerName;
	// �����ͼ
	private Bitmap PlayerImg;
	// ����֡��
	private int horizontalcutnum;
	// ����֡��
	private int verticalcutnum;
	// ��ʾ����֡
	private int playnum;
	// ����˵������
	private Bitmap TalkbdImg;
	
	
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

	public String getPlayerName() {
		return PlayerName;
	}

	public void setPlayerName(String playerName) {
		PlayerName = playerName;
	}

	public Bitmap getPlayerImg() {
		return PlayerImg;
	}

	public void setPlayerImg(Bitmap playerImg) {
		PlayerImg = playerImg;
	}

	public int getHorizontalcutnum() {
		return horizontalcutnum;
	}

	public void setHorizontalcutnum(int horizontalcutnum) {
		this.horizontalcutnum = horizontalcutnum;
	}

	public int getVerticalcutnum() {
		return verticalcutnum;
	}

	public void setVerticalcutnum(int verticalcutnum) {
		this.verticalcutnum = verticalcutnum;
	}

	public int getPlaynum() {
		return playnum;
	}

	public void setPlaynum(int playnum) {
		this.playnum = playnum;
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

}

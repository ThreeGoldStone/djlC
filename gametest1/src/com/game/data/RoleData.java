package com.game.data;

import android.graphics.Bitmap;

public class RoleData {

	// 人物角色名
	private String PlayerName;
	// 人物大图
	private Bitmap PlayerImg;
	// 横向帧数
	private int horizontalcutnum;
	// 竖向帧数
	private int verticalcutnum;
	// 显示多少帧
	private int playnum;
	// 人物说话气泡
	private Bitmap TalkbdImg;
	
	
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

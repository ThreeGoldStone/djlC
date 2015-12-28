package com.game.base;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class GameBackGround {
	private Bitmap bmpGBG;// ±≥æ∞

	public GameBackGround(Bitmap bmpGBG) {
		super();
		this.bmpGBG = Bitmap.createScaledBitmap(bmpGBG, PubSet.screenWidth, PubSet.screenHeight,
				false);
	}
	// ªÊ÷∆±≥æ∞
	public void myDraw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bmpGBG, 0, 0, paint);
	};
	
	
	
	
}

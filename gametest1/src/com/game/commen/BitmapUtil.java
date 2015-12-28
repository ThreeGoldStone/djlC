package com.game.commen;

import java.io.IOException;
import java.io.InputStream;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {
	public static Bitmap getBitmapFromAssets(Context ctx, String path) {
		InputStream iis;
		Bitmap bitmap = null;
		try {
			iis = ctx.getAssets().open(path);
			bitmap= BitmapFactory.decodeStream(iis);
			iis=null;
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			bitmap=null;
			System.gc();
		}
	}
}

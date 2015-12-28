package com.android.volley.djlpatch;

import java.io.Serializable;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * @author DJL E-mail:
 * @date 2015-9-16 上午11:26:37
 * @version 1.0
 * @parameter
 * @since
 */
public class DataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4494174324249423292L;

	/**
	 * 
	 * @param context
	 * @param handler
	 *            数据回调的出口
	 * @param args
	 */
	public void loadFromNet(Context context, Handler handler, String... args) {

	}

	public class SimpleHandler extends Handler {
		private static final int NetError = 0;
		private static final int Success = 1;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case NetError:

				break;
			case Success:
				parse();
				break;

			}
		}

		private void parse() {
			// TODO Auto-generated method stub
			
		}
	}
}

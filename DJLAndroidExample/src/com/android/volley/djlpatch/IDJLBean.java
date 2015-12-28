package com.android.volley.djlpatch;

import android.content.Context;
import android.os.Handler;

/**
 * @author DJL E-mail:
 * @date 2015-9-17 上午10:24:47
 * @version 1.0
 * @parameter
 * @since
 */
public interface IDJLBean {

	void loadFromNet(Context context,int Tag, Handler handler);

	IDJLBean loadFromSp(Object... tags);

	IDJLBean loadFromDB(Object... tags);

	IDJLBean loadFromFile(Object... tags);
}

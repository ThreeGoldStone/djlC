package com.djl.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.TextView;

/**
 * @author DJL E-mail:
 * @date 2015-12-28 下午5:05:39
 * @version 1.0
 * @parameter
 * @since
 */
public class LableView extends TextView implements Checkable {

	private boolean checked;
	public LableView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LableView(Context context) {
		super(context);
	}

	@Override
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public boolean isChecked() {
		return checked;
	}

	@Override
	public void toggle() {
	}

}

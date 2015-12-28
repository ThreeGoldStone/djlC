package com.djl.util.view;

import android.app.Activity;
import android.view.View;

import com.djl.util.activity.SimpleFragment;

/**
 * 
 * @author DJL E-mail:
 * @date 2015-6-25 下午5:34:50
 * @version 1.0
 * @parameter
 * @since
 */
public class ViewFinder {

	private View view;
	private Activity activity;
	private SimpleFragment fragment;

	public ViewFinder(View view) {
		this.view = view;
	}

	public ViewFinder(Activity activity) {
		this.activity = activity;
	}

	public ViewFinder(SimpleFragment fragment) {
		this.fragment = fragment;
	}

	public View findViewById(int id) {
		return activity == null ? (fragment == null ? view.findViewById(id)
				: fragment.findViewById(id)) : activity.findViewById(id);
	}

	public View findViewById(int id, int pid) {
		View pView = null;
		if (pid > 0) {
			pView = this.findViewById(pid);
		}

		View view = null;
		if (pView != null) {
			view = pView.findViewById(id);
		} else {
			view = this.findViewById(id);
		}
		return view;
	}

}

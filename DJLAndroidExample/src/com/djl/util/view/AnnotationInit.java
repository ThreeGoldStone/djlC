package com.djl.util.view;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.view.View;
import android.view.View.OnClickListener;

import com.djl.util.view.annotation.Click;
import com.djl.util.view.annotation.ContentView;
import com.djl.util.view.annotation.VID;

/**
 * @author DJL E-mail:
 * @date 2015-6-25 下午3:49:13
 * @version 1.0
 * @parameter
 * @since
 */
public class AnnotationInit {
	public static void inject(OnClickListener contain, ViewFinder finder) {
		Class<?> containType = contain.getClass();
		// inject ContentView
		ContentView contentView = containType.getAnnotation(ContentView.class);
		if (contentView != null) {
			try {
				Method setContentViewMethod = containType.getMethod(
						"setContentView", int.class);
				setContentViewMethod.invoke(contain, contentView.value());
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		// inject View and set onClick
		Field[] fields = containType.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				initView(contain, finder, field);
			}
		}
	}

	private static void initView(OnClickListener contain, ViewFinder finder,
			Field field) {
		View view = null;
		VID VID = field.getAnnotation(VID.class);
		if (VID != null) {
			try {
				view = finder.findViewById(VID.value(), VID.pId());
				if (view != null) {
					Click Click = field.getAnnotation(Click.class);
					if (Click != null) {
						view.setOnClickListener(contain);
					}
					field.setAccessible(true);
					field.set(contain, view);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}

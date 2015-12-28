package com.djl.stringsuck;

import java.lang.reflect.Field;

import com.djl.screenmatch.*;

/**
 * @author DJL E-mail:
 * @date 2015-12-15 ÏÂÎç3:56:11
 * @version 1.0
 * @parameter
 * @since
 */
public class ClassSucker {
	public static void main(String[] args) {
		try {
			Class<?> c = Class.forName(
					"E:\\Android_WorkSpace_1\\HHY2Test2\\src\\com.ssjr.bean2.BaseBean", true,
					ClassLoader.getSystemClassLoader());
			Field[] declaredFields = c.getDeclaredFields();
			for (Field field : declaredFields) {
				System.out.println(field.getName());
			}
			new Screen("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Package[] packages = Package.getPackages();
		for (Package package1 : packages) {
			System.out.println(package1.getName());
		}
	}
}

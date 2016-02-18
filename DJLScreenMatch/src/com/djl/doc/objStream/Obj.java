package com.djl.doc.objStream;

import java.io.Serializable;

/**
 * @author DJL E-mail:
 * @date 2016-1-25 ÏÂÎç5:18:09
 * @version 1.0
 * @parameter
 * @since
 */
public class Obj implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7338731758763881277L;
	public int a = 100;
	public String d = "sb";

	public Obj obj = null;

	public Obj(int a, String d) {
		this.a = a;
		this.d = d;
		// obj = null;
	}

	public void add() {
		obj = new Obj(200, "sb2");
	}

	public Obj() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Obj [a=" + a + ", d=" + d + ", obj=" + (obj == null ? "null" : obj.toString())
				+ "]";
	}

}

package com.djl.doc;

import java.io.File;
import java.util.ArrayList;

import com.djl.doc.ScreenMatcher.ScreenSize;

public class Screen {
	// /** ���ظ� */
	// public int hpx;
	// /** ���ؿ� */
	// public int wpx;
	// /** px/dp */
	// public float px_dp;
	/** dp�� */
	public int hdp;
	/** dp�� */
	public int wdp;
	/** ��Ÿķֱ���dp����xml��·�� */
	private String DimensPath;
	private String LayoutPath;
	private String projectPath;

	public Screen(ScreenSize screenSize, String ProjectPath, boolean isDefaultPath) {
		projectPath = ProjectPath;
		// float px_dp = (float) screenSize.dpi / 160;
		// this.hdp = Math.round(screenSize.hpx / px_dp);
		// this.wdp = Math.round(screenSize.wpx / px_dp);
		this.hdp = screenSize.hdp;
		this.wdp = screenSize.wdp;
		if (isDefaultPath) {
			setDimensPath(projectPath + "res\\values\\dimens.xml");
			setLayoutPath(projectPath + "res\\layout\\");
		} else {
			StringBuilder sbDimensPath = new StringBuilder();
			sbDimensPath.append(projectPath).append("res\\values")
//					.append("-").append(screenSize.sizeRage)
					.append("-").append(screenSize.densityRage)
//					.append("-").append("w").append(wdp).append("dp")
//					.append("-").append("h").append(hdp).append("dp")
					.append("-").append(screenSize.hpx).append("x")
					.append(screenSize.wpx).append("\\");

			File filePath = new File(sbDimensPath.toString());
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			sbDimensPath.append("dimens.xml");
			setDimensPath(sbDimensPath.toString());
			StringBuilder sbLayoutPath = new StringBuilder();
			// .append("-").append("w")
			// .append(wdp).append("dp").append("-").append("h").append(hdp).append("dp")
			// // .append("-").append(screenSize.dpi).append("dpi")
			// ;
			// sbDimensPath.append(projectPath).append("res\\values")
			sbLayoutPath.append(projectPath).append("res\\layout").append("-")
					.append(screenSize.sizeRage).append("-").append(screenSize.densityRage)
					.append("-").append("w").append(wdp).append("dp").append("-").append("h")
					.append(hdp).append("dp").append("\\");
			setLayoutPath(sbLayoutPath.toString());
		}
	}

	public boolean equals(Object obj) {
		Screen s = (Screen) obj;
		if (s == null) {
			return false;
		}
		/** ֻҪ��Ļdp��Сһ������Ϊ��һ���� */
		return hdp == s.hdp && wdp == s.wdp;
	}

	public float getScaleX(Screen input) {
		return (float) this.wdp / input.wdp;
	}

	public float getScaleY(Screen input) {
		return (float) this.hdp / input.hdp;
	}

	public String getDimensPath() {
		return DimensPath;
	}

	public void setDimensPath(String dimensPath) {
		DimensPath = dimensPath;
	}

	public String getLayoutPath() {
		return LayoutPath;
	}

	public void setLayoutPath(String layoutPath) {
		LayoutPath = layoutPath;
	}

	public static final class Dimen {
		public static final String elementName = "";
		public static final String[] dpValueAttrNames = { "name" };
		public static final ArrayList<String> dpValueAttrValues = new ArrayList<>();
	}
}

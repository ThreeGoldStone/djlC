package com.djl.doc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * @author DJL E-mail:
 * @date 2015-9-10 下午3:03:33
 * @version 1.0
 * @parameter
 * @since
 */
public class ScreenMatcher {
	public static String[] XAttrs = { "horizontal", "width", "top", "bottom", "side" };
	public static String[] YAttrs = { "vertical", "height", "left", "right" };
	public static String[] TextSizeAttrs = { "textsize" };
	public String ProjectPath;
	public Screen defaultScreen;

	public ScreenMatcher(String projectPath, ScreenSize defaultScreenSize) {
		ProjectPath = projectPath;
		this.defaultScreen = new Screen(defaultScreenSize, projectPath, true);
	}

	public static class ScreenSize {
		public int hdp, wdp, wpx, hpx;
		/*** 单位英寸 */
		public float size;
		public int scale, ppi;
		SizeRage sizeRage;
		DensityRage densityRage;

		public enum SizeRage {
			small, normal, large, xlarge, xxlarge, xxxlarge
		}

		public enum DensityRage {
			ldpi, mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi
		}

		public ScreenSize(int wpx, int hpx, float size) {
			// package android.content.res.Configuration
			int screenLayoutSize;
			boolean screenLayoutLong;
			boolean screenLayoutCompatNeeded;
			this.wpx = wpx;
			this.hpx = hpx;
			this.size = size;

			ppi = (int) Math.round(Math.sqrt((wpx * wpx + hpx * hpx)) / size);
			System.out.println("ppi" + ppi);
			// 设置为两倍的真实scale ，原本是ppi/160
			scale = Math.round(ppi / 80f);
			System.out.println("scale" + scale);
			wdp = wpx / scale * 2;
			System.out.println("wdp" + wdp);
			hdp = hpx / scale * 2;
			System.out.println("hdp" + hdp);
			// These semi-magic numbers define our compatibility modes for
			// applications with different screens. These are guarantees to
			// app developers about the space they can expect for a particular
			// configuration. DO NOT CHANGE!
			if (hdp > wdp) {
				if (hdp < 470) {
					// This is shorter than an HVGA normal density screen (which
					// is 480 pixels on its long side).
					sizeRage = SizeRage.small;
					screenLayoutLong = false;
					screenLayoutCompatNeeded = false;
				} else {
					// What size is this screen screen?
					if (hdp >= 960 && wdp >= 720) {
						// 1.5xVGA or larger screens at medium density are the
						// point
						// at which we consider it to be an extra large screen.
						sizeRage = SizeRage.xlarge;
						// screenLayoutSize = SCREENLAYOUT_SIZE_XLARGE;
					} else if (hdp >= 640 && wdp >= 480) {
						sizeRage = SizeRage.large;
					} else {
						// screenLayoutSize = SCREENLAYOUT_SIZE_NORMAL;
						sizeRage = SizeRage.normal;
					}

				}
			} else if (hdp == wdp) {
				// 方屏
			} else {
				// 横屏
			}
			switch (scale) {
			case 0:
				densityRage = DensityRage.ldpi;
				break;
			// 80
			case 1:
				densityRage = DensityRage.ldpi;
				break;
			// 160
			case 2:
				densityRage = DensityRage.hdpi;
				break;
			// 240
			case 3:
				densityRage = DensityRage.hdpi;
				break;
			// 320
			case 4:
				densityRage = DensityRage.xhdpi;
				break;
			// 400
			case 5:
				densityRage = DensityRage.xhdpi;
				break;
			// 480
			case 6:
				densityRage = DensityRage.xxhdpi;
				break;

			default:
				break;
			}

		}

		/**
		 * 
		 * @param wpx
		 *            像素宽
		 * @param hpx
		 *            像素高
		 * @param size
		 *            单位英寸
		 */
		public ScreenSize(int wpx, int hpx, float size, String tag) {
			this.wpx = wpx;
			this.hpx = hpx;
			this.size = size;

			ppi = (int) Math.round(Math.sqrt((wpx * wpx + hpx * hpx)) / size);
			System.out.println("ppi" + ppi);
			// 设置为两倍的真实scale ，原本是ppi/160
			scale = Math.round(ppi / 80f);
			System.out.println("scale" + scale);
			wdp = wpx / scale * 2;
			System.out.println("wdp" + wdp);
			hdp = hpx / scale * 2;
			System.out.println("hdp" + hdp);
			if (hdp > wdp) {
				// 竖屏426,470,640,960
				if (hdp > 0 && hdp < 426) {
					// small
					sizeRage = SizeRage.small;
				} else if (hdp >= 426 && hdp < 470) {
					// small
					sizeRage = SizeRage.small;
				} else if (hdp >= 470 && hdp < 640) {
					// normal
					sizeRage = SizeRage.normal;
				} else if (hdp >= 640 && hdp < 960) {
					// large
					sizeRage = SizeRage.large;
				} else if (hdp >= 960) {
					// xlarge
					sizeRage = SizeRage.xlarge;
				}
			} else if (hdp == wdp) {
				// 方屏
			} else {
				// 横屏
			}
			switch (scale) {
			case 0:
				densityRage = DensityRage.ldpi;
				break;
			// 80
			case 1:
				densityRage = DensityRage.ldpi;
				break;
			// 160
			case 2:
				densityRage = DensityRage.hdpi;
				break;
			// 240
			case 3:
				densityRage = DensityRage.hdpi;
				break;
			// 320
			case 4:
				densityRage = DensityRage.xhdpi;
				break;
			// 400
			case 5:
				densityRage = DensityRage.xhdpi;
				break;
			// 480
			case 6:
				densityRage = DensityRage.xxhdpi;
				break;

			default:
				break;
			}
		}
	}

	/**
	 * 开始转化
	 * 
	 * @param outPutSreen
	 */
	public void start(ScreenSize... outPutSreen) {
		if (ProjectPath == null || "".equals(ProjectPath)) {
			System.out.println("项目路径为空！");
		} else {
			System.out.println("项目路径:" + ProjectPath);
		}
		for (int i = 0; i < outPutSreen.length; i++) {
			parserAndChangeAndSave(defaultScreen, new Screen(outPutSreen[i], ProjectPath, false));
		}
	}

	/**
	 * 
	 * @param input
	 *            输入的长度
	 * @param rat
	 *            长度转化率
	 * @return
	 */
	public static String change(String input, float rat) {
		System.out.println("change  " + input + "  " + rat);
		if (input == null) {
			return null;
		}
		// 获得单位
		int danweidechengdu = 2;
		if (input.contains("dip")) {
			danweidechengdu = 3;
		}
		char[] danwei = new char[danweidechengdu];
		input = input.trim();
		input.getChars(input.length() - danweidechengdu, input.length(), danwei, 0);
		// 长度
		char[] chengdu = new char[input.length() - danweidechengdu];
		input.getChars(0, input.length() - danweidechengdu, chengdu, 0);
		String pstring = new String(danwei);
		String lstring = new String(chengdu);
		System.out.println(pstring);
		System.out.println(lstring);
		int parseInt = Integer.parseInt(lstring);
		int output = Math.round(parseInt * rat);
		if (parseInt - output == 0) {
			return null;
		}
		return output + pstring;

	}

	/**
	 * 先暂时只针对values
	 * 
	 * @param input
	 * @param ScreenOutPut
	 * @param isView
	 */
	@SuppressWarnings("unchecked")
	public static void parserAndChangeAndSave(Screen input, Screen ScreenOutPut) {
		SAXBuilder saxBuilder = new SAXBuilder();
		Document build = null;
		try {
			build = saxBuilder.build(new File(input.getDimensPath()));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		if (build == null)
			return;
		Element root = build.getRootElement();
		List<Element> dimens = root.getChildren();
		// 保存适配文件不需要的节点
		List<Element> needRemovedimens = new ArrayList<Element>();
		// 便利节点
		for (Element e : dimens) {
			List<Attribute> attributes = e.getAttributes();
			// 遍历节点的属性
			for (Attribute attribute : attributes) {
				// 区分节点的类型
				switch (getAttrNameType(e, attribute)) {
				// 在dimens.xml文件下的，并且表示的与高有关
				case 20:
					String value = e.getContent(0).getValue();
					String change = change(value, ScreenOutPut.getScaleY(input));
					if (change != null) {
						e.removeContent();
						e.addContent(change);
					} else {
						// 存入适配无关项
						needRemovedimens.add(e);
					}
					System.out.println(value);
					break;
				case 21:
					String valueY = e.getContent(0).getValue();
					String changeY = change(valueY, ScreenOutPut.getScaleY(input));
					if (changeY != null) {
						e.removeContent();
						e.addContent(changeY);
					} else {
						// 存入适配无关项
						needRemovedimens.add(e);
					}
					System.out.println(valueY);
					break;
				// 在dimens.xml文件下的，并且表示的与宽有关
				case 22:
					String valueX = e.getContent(0).getValue();
					String changeX = change(valueX, ScreenOutPut.getScaleX(input));
					if (changeX != null) {
						e.removeContent();
						e.addContent(changeX);
					} else {
						// 存入适配无关项
						needRemovedimens.add(e);
					}
					System.out.println(valueX);
					break;

				default:
					break;
				}
			}
		}
		// 将所有不需要的节点删除
		dimens.removeAll(needRemovedimens);
		XMLOutputter xop = new XMLOutputter(Format.getCompactFormat().setEncoding("utf-8"));
		try {
			File file = new File(ScreenOutPut.getDimensPath());
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdir();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

			System.out.println("outputFile " + file.getAbsolutePath());
			xop.output(root, new FileOutputStream(file));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 
	 * @param e
	 * @param attribute
	 * @return xx <br>
	 *         第一以位1表示为布局文件，2表示为dimens，0表示其他 <br>
	 *         第二以位1表示为与高度有关，2表示与宽度有关，0表示其他
	 */
	private static int getAttrNameType(Element e, Attribute attribute) {
		int i1 = 0, i2 = 0;
		if (e.getQualifiedName().equals("dimen")) {
			i1 = 2;
			if (attribute.getName().equals("name")) {
				for (int i = 0; i < TextSizeAttrs.length; i++) {
					if (attribute.getValue().toLowerCase().contains(TextSizeAttrs[i])) {
						i2 = 0;
					}
				}
				for (int i = 0; i < YAttrs.length; i++) {
					if (attribute.getValue().toLowerCase().contains(YAttrs[i])) {
						i2 = 1;
					}
				}
				for (int i = 0; i < XAttrs.length; i++) {
					if (attribute.getValue().toLowerCase().contains(XAttrs[i])) {
						i2 = 2;
					}
				}

			}

		} else {
			i1 = 1;
		}
		System.out.println(i1 * 10 + i2);
		return i1 * 10 + i2;
	}

}

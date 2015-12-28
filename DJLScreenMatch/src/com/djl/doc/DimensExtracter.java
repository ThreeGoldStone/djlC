package com.djl.doc;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author DJL E-mail:
 * @date 2015-11-21 上午9:38:58
 * @version 1.0
 * @parameter
 * @since
 */
public class DimensExtracter {
	private static DimensInfo[] mRange;

	public static void main(String[] args) {
		DimensInfo[] mDPRanges = createRange(2, 400, "dp");
		DimensInfo[] mSPRanges = createRange(8, 50, "sp");
		DimensInfo[] mdipRanges = createRange(2, 400, "dip");
		File layoutFile = new File("E:\\Android_WorkSpace_1\\HHY2Test2\\res\\layout");
		// File layoutFile = new File("C:\\Users\\Administrator\\Desktop\\a");
		File dimens = new File("E:\\Android_WorkSpace_1\\HHY2Test2\\res\\values\\dimens.xml");
		mRange = Arrays.copyOf(mDPRanges, mDPRanges.length + mSPRanges.length + mdipRanges.length);
		for (int i = 0; i < mSPRanges.length; i++) {
			mRange[mDPRanges.length + i] = mSPRanges[i];
		}
		for (int i = 0; i < mdipRanges.length; i++) {
			mRange[mDPRanges.length + mSPRanges.length + i] = mdipRanges[i];
		}
		ann(layoutFile, mRange);
		StringBuilder sb = new StringBuilder();
		HashSet<String> valueAbleRanges = new HashSet<>();
		for (int i = 0; i < mRange.length; i++) {
			if (mRange[i].findTimes > 0) {
				valueAbleRanges.add(mRange[i].getDimenItem());
			}
		}
		for (String s : valueAbleRanges) {
			sb.append("\n").append(s);
		}
		FormatDoc.annotation(dimens, "</resources>", sb.append("</resources>").toString());
	}

	/** 遍历f文件夹下的文件并抽取Dp SP DIP 的单位，并替换为目标单位 */
	private static void ann(File f, DimensInfo[] dPRanges) {
		File[] listFiles = f.listFiles();
		for (File file : listFiles) {
			annotation(file, dPRanges);
		}

	}

	/**
	 * f 将要修改其中特定内容的文件 src 将被替换的内容 dst 将被替换层的内容
	 */
	public static void annotation(File f, DimensInfo[] dPRanges) {
		String content = FormatDoc.read(f);
		for (int i = 0; i < dPRanges.length; i++) {
			if (content.contains(dPRanges[i].getTargetL())) {
				dPRanges[i].findTimes++;
				System.out.println(dPRanges[i].toString());
				content = content.replace(dPRanges[i].getTargetL(), dPRanges[i].getNameL());
			}
		}
		FormatDoc.write(content, f);
	}

	// public static void parseDi
	/**
	 * 
	 * @param start
	 * 
	 * @param end
	 * 
	 * @param tail
	 * @return
	 */
	private static DimensInfo[] createRange(int start, int end, String tail) {
		DimensInfo[] sps = new DimensInfo[end - start + 1];
		for (int i = 0; i < sps.length; i++) {
			sps[i] = new DimensInfo();
			sps[i].setTarget((start + i), tail);
		}
		return sps;
	}

	public static class DimensInfo {
		// 名称_18_SP
		private String name;
		// 目标大小如18sp
		private String target;
		// 发现次数
		public int findTimes;

		@Override
		public String toString() {
			return "DimensInfo [name=" + name + ", target=" + target + ", findTimes=" + findTimes
					+ ", getDimenItem=" + getDimenItem() + "]";
		}

		public String getNameL() {
			return "\"@dimen/" + name + "\"";
		}

		public String getName() {
			return name;
		}

		public String getTargetL() {
			return "\"" + target + "\"";
		}

		public String getTarget() {
			return target;
		}

		public void setTarget(int l, String tail) {
			this.target = l + tail;
			this.name = "_" + l + "_" + tail.toUpperCase();
		}

		// <dimen name="textsize_mainproductfragment">20dp</dimen>
		public String getDimenItem() {
			return "<dimen name=\"" + name + "\">" + target + "</dimen>";

		}
	}
}

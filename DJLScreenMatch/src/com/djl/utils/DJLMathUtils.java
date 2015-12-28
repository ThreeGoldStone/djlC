package com.djl.utils;

import java.math.BigDecimal;
import java.util.ArrayList;

public final class DJLMathUtils {
	private static final int ZONE_FLAG_FRONT = 0;
	private static final int ZONE_FLAG_BEHIND = 1;
	private static final int ZONE_FLAG_INDEPENDENT = 2;

	/**
	 * ��ȡ���������Լ��
	 * 
	 * @return
	 */
	public static int getGCD(int a, int b) {
		int min = Math.abs(a);
		int max = Math.abs(b);
		if (a > b) {
			min = b;
			max = a;
		}
		if (min == 0)
			return max;
		else
			return getGCD(min, max - min);

	}

	/**
	 * 
	 * @param target
	 *            ��Ҫ�ж��������������
	 * @param zoneType
	 *            �������� ǰ����{@link #ZONE_FLAG_BEHIND}, �����{@link #ZONE_FLAG_FRONT}
	 *            , ������{@link #ZONE_FLAG_INDEPENDENT}
	 * @param points
	 *            �����ڵ�(������δ�����),����Ϊ����
	 * @param points1
	 *            �����ڵ�(������δ�����),����Ϊ����
	 * @return ����0~n �����ǰ����ĵķ���λ��,�쳣����-1
	 */
	public static int zone(int target, int zoneType, int[] points,
			int... points1) {
		if (points == null)
			points = points1;
		points = sort(removeDuplicates(points), true);
		switch (zoneType) {
		/**
		 * ǰ����
		 */
		case ZONE_FLAG_FRONT:

			for (int j = 0; j < points.length - 1; j++) {
				if (points[j] <= target && target < points[j + 1]) {
					return j + 1;
				}
			}
			if (target < points[0])
				return 0;
			if (target >= points[points.length - 1])
				return points.length;
			break;
		/**
		 * �����
		 */
		case ZONE_FLAG_BEHIND:
			for (int j = 0; j < points.length - 1; j++) {
				if (points[j] < target && target <= points[j + 1]) {
					return j + 1;
				}
			}
			if (target <= points[0])
				return 0;
			if (target > points[points.length - 1])
				return points.length;
			break;
		/**
		 * ������
		 */
		case ZONE_FLAG_INDEPENDENT:
			for (int j = 0; j < points.length - 1; j++) {
				if (target == points[j])
					return (j + 1) * 2 - 1;
				if (points[j] < target && target < points[j + 1]) {
					return (j + 1) * 2;
				}
			}
			if (target < points[0])
				return 0;
			if (target > points[points.length - 1])
				return points.length * 2 + 1;
			if (target == points[points.length - 1])
				return points.length * 2;
			break;
		/**
		 * Ĭ����ǰ������
		 */
		default:
			for (int j = 0; j < points.length - 1; j++) {
				if (points[j] <= target && target < points[j + 1]) {
					return j + 1;
				}
			}
			if (target < points[0])
				return 0;
			if (target >= points[points.length - 1])
				return points.length;
			break;
		}
		return -1;

	}

	/**
	 * 
	 * @param target
	 *            ��Ҫ�ж��������������
	 * @param zoneType
	 *            �������� ǰ����{@link #ZONE_FLAG_BEHIND}, �����{@link #ZONE_FLAG_FRONT}
	 *            , ������{@link #ZONE_FLAG_INDEPENDENT}
	 * @param points
	 *            �����ڵ�(������δ�����),����Ϊ����
	 * @param points1
	 *            �����ڵ�(������δ�����),����Ϊ����
	 * @return ����0~n �����ǰ����ĵķ���λ��,�쳣����-1
	 */
	public static int zone(double target, int zoneType, double[] points,
			double... points1) {
		if (points == null)
			points = points1;
		points = sort(removeDuplicates(points), true);
		switch (zoneType) {
		/**
		 * ǰ����
		 */
		case ZONE_FLAG_FRONT:

			for (int j = 0; j < points.length - 1; j++) {
				if (points[j] <= target && target < points[j + 1]) {
					return j + 1;
				}
			}
			if (target < points[0])
				return 0;
			if (target >= points[points.length - 1])
				return points.length;
			break;
		/**
		 * �����
		 */
		case ZONE_FLAG_BEHIND:
			for (int j = 0; j < points.length - 1; j++) {
				if (points[j] < target && target <= points[j + 1]) {
					return j + 1;
				}
			}
			if (target <= points[0])
				return 0;
			if (target > points[points.length - 1])
				return points.length;
			break;
		/**
		 * ������
		 */
		case ZONE_FLAG_INDEPENDENT:
			for (int j = 0; j < points.length - 1; j++) {
				if (target == points[j])
					return (j + 1) * 2 - 1;
				if (points[j] < target && target < points[j + 1]) {
					return (j + 1) * 2;
				}
			}
			if (target < points[0])
				return 0;
			if (target > points[points.length - 1])
				return points.length * 2 + 1;
			if (target == points[points.length - 1])
				return points.length * 2;
			break;
		/**
		 * Ĭ����ǰ������
		 */
		default:
			for (int j = 0; j < points.length - 1; j++) {
				if (points[j] <= target && target < points[j + 1]) {
					return j + 1;
				}
			}
			if (target < points[0])
				return 0;
			if (target >= points[points.length - 1])
				return points.length;
			break;
		}
		return -1;

	}

	/**
	 * �Ƴ��������ظ���Ԫ��
	 * 
	 * @param withRepeat
	 *            �����ظ�Ԫ�ص�����
	 * @return
	 */
	public static int[] removeDuplicates(int[] withRepeat) {
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < withRepeat.length; i++) {
			if (!list.contains(withRepeat[i])) {
				list.add(withRepeat[i]);
			}
		}
		int[] is = new int[list.size()];
		for (int i = 0; i < is.length; i++) {
			is[i] = list.get(i);
		}
		return is;
	}

	/**
	 * �Ƴ��������ظ���Ԫ��
	 * 
	 * @param withRepeat
	 *            �����ظ�Ԫ�ص�����
	 * @return
	 */
	public static double[] removeDuplicates(double[] withRepeat) {
		ArrayList<Double> list = new ArrayList<>();
		for (int i = 0; i < withRepeat.length; i++) {
			if (!list.contains(withRepeat[i])) {
				list.add(withRepeat[i]);
			}
		}
		double[] is = new double[list.size()];
		for (int i = 0; i < is.length; i++) {
			is[i] = list.get(i);
		}
		return is;
	}

	/**
	 * 
	 * @param is
	 *            ���������
	 * @param sort_flag
	 *            ����true,����false
	 * @return
	 */
	public static int[] sort(int[] is, boolean sort_flag) {
		for (int i = 0; i < is.length; i++) {
			for (int j = 0; j < is.length - i - 1; j++) {
				int temp;
				if (sort_flag) {
					if (is[j] > is[j + 1]) {
						temp = is[j];
						is[j] = is[j + 1];
						is[j + 1] = temp;
					}
				} else {
					if (is[j] < is[j + 1]) {
						temp = is[j];
						is[j] = is[j + 1];
						is[j + 1] = temp;
					}
				}

			}
		}
		return is;
	}

	/**
	 * 
	 * @param is
	 *            ���������
	 * @param sort_flag
	 *            ����true,����false
	 * @return
	 */
	public static double[] sort(double[] is, boolean sort_flag) {
		for (int i = 0; i < is.length; i++) {
			for (int j = 0; j < is.length - i - 1; j++) {
				double temp;
				if (sort_flag) {
					if (is[j] > is[j + 1]) {
						temp = is[j];
						is[j] = is[j + 1];
						is[j + 1] = temp;
					}
				} else {
					if (is[j] < is[j + 1]) {
						temp = is[j];
						is[j] = is[j + 1];
						is[j + 1] = temp;
					}
				}

			}
		}
		return is;
	}

	/**
	 * 
	 * @param f
	 * @param i
	 *            ����0�����������뵽���� ������1�ͱ�����С�����һλ����֮����С����ǰ
	 * @return
	 */
	public static double round(double f, int i) {
		BigDecimal b = new BigDecimal(f);
		double f1 = b.setScale(i, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;

	}
}

package com.djl.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {
	/**
	 * 
	 * @param uper
	 * @param lower
	 * @return ����ʽ���ַ���
	 */
	public static String getSimplestFraction(int uper, int lower) {
		StringBuilder sb = new StringBuilder();
		int gcd = DJLMathUtils.getGCD(uper, lower);
		sb.append(uper / gcd).append("/").append(lower / gcd);
		return sb.toString();

	}

	// String a="\d";
	private final static Pattern emailer = Pattern
			.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	// private final static Pattern IDcard = Pattern
	// .compile("/^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$/");
	private final static Pattern phone = Pattern
			.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

	/**
	 * �жϸ����ַ����Ƿ�հ״� �հ״���ָ�ɿո��Ʊ�����س��������з���ɵ��ַ��� �������ַ���Ϊnull����ַ���������true
	 */
	public static boolean isEmpty(CharSequence input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * �ж��ǲ���һ���Ϸ��ĵ����ʼ���ַ
	 */
	public static boolean isEmail(CharSequence email) {
		if (isEmpty(email))
			return false;
		return emailer.matcher(email).matches();
	}

	// /**
	// * �ж��ǲ���һ����ʽ��ȷ�����֤
	// */
	// public static boolean isIDCard(CharSequence idCard) {
	// if (isEmpty(idCard))
	// return false;
	// return IDcard.matcher(idCard).matches();
	// }

	/**
	 * �ж��ǲ���һ���Ϸ����ֻ�����
	 */
	public static boolean isPhone(CharSequence phoneNum) {
		if (isEmpty(phoneNum))
			return false;
		return phone.matcher(phoneNum).matches();
	}

	/**
	 * �ַ���ת����
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * ����ת��
	 * 
	 * @param obj
	 * @return ת���쳣���� 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	/**
	 * Stringתlong
	 * 
	 * @param obj
	 * @return ת���쳣���� 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * Stringתdouble
	 * 
	 * @param obj
	 * @return ת���쳣���� 0
	 */
	public static double toDouble(String obj) {
		try {
			return Double.parseDouble(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0D;
	}

	/**
	 * �ַ���ת����
	 * 
	 * @param b
	 * @return ת���쳣���� false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * �ж�һ���ַ����ǲ�������
	 */
	public static boolean isNumber(CharSequence str) {
		try {
			Integer.parseInt(str.toString());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * byte[]����ת��Ϊ16���Ƶ��ַ�����
	 * 
	 * @param data
	 *            Ҫת�����ֽ����顣
	 * @return ת����Ľ����
	 */
	public static final String byteArrayToHexString(byte[] data) {
		StringBuilder sb = new StringBuilder(data.length * 2);
		for (byte b : data) {
			int v = b & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase(Locale.getDefault());
	}

	/**
	 * 16���Ʊ�ʾ���ַ���ת��Ϊ�ֽ����顣
	 * 
	 * @param s
	 *            16���Ʊ�ʾ���ַ���
	 * @return byte[] �ֽ�����
	 */
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] d = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			// ��λһ�飬��ʾһ���ֽ�,��������ʾ��16�����ַ�������ԭ��һ�������ֽ�
			d[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(
					s.charAt(i + 1), 16));
		}
		return d;
	}

	/**
	 * ����Ľ���Է�Ϊ��λ
	 * 
	 * @param a
	 * @return ������ԪΪ��λ��������λС��
	 */
	public static String ShowMoney(double input) {
		StringBuilder sb = new StringBuilder();
		if (input < 0) {
			sb.append("-");
			input = -input;
		}
		double d = input / 100;
		// int w = (int) (d / 10000);
		// int y = (int) (d / 100000000);
		// // double d2 = DJLMathUtils.round(d, 2);
		// if (y != 0) {
		// sb.append(DJLMathUtils.round(d / 100000000, 10)).append("��"); ��һ����ʵ��ʽ
		// } else if (w != 0) {
		// sb.append(DJLMathUtils.round(d / 10000, 6)).append("��");
		// } else {
		// sb.append(DJLMathUtils.round(d, 2));
		// }
		if (d >= 1.0) {
			DecimalFormat df = new DecimalFormat("#,###.00");
			sb.append(df.format(d));
		} else {
			sb.append(DJLMathUtils.round(d, 2));
		}
		return sb.toString();

	}

	public static int getAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}

		return age;
	}

	/**
	 * ����s�еĴ�start��end֮���charΪ***
	 * 
	 * @param s
	 * @param start
	 * @param end
	 * @param hidLableWidth
	 *            *�ŵĸ�����-1��ʾ*�ŵĸ���Ϊend-start
	 * @return
	 */

	public static String hide(String s, int start, int end, int hidLableWidth) {
		try {
			char[] bufferBefore = new char[start];
			s.getChars(0, start, bufferBefore, 0);
			char[] bufferAfter = new char[s.length() - end];
			s.getChars(end, s.length(), bufferAfter, 0);
			StringBuilder sb = new StringBuilder();
			sb.append(bufferBefore);
			if (hidLableWidth < 0) {
				hidLableWidth = end - start;
			}
			for (int i = 0; i < hidLableWidth; i++) {
				sb.append("*");
			}
			sb.append(bufferAfter);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * ��ȡ������IP(Ҫ����Url��Ҫ�ŵ���̨�߳��ﴦ��)
	 * 
	 * @Title: GetNetIp
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String GetNetIp() {
		URL infoUrl = null;
		InputStream inStream = null;
		String ipLine = "";
		HttpURLConnection httpConnection = null;
		try {
			infoUrl = new URL("http://ip168.com/");
			URLConnection connection = infoUrl.openConnection();
			httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				inStream = httpConnection.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
				StringBuilder strber = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null)
					strber.append(line + "\n");

				Pattern pattern = Pattern
						.compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
				Matcher matcher = pattern.matcher(strber.toString());
				if (matcher.find()) {
					ipLine = matcher.group();
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inStream.close();
				httpConnection.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ipLine;
	}

	/**
	 * �ж�str�Ƿ�Ϊ������
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChineseOnly(String str) {

		char[] chars = str.toCharArray();
		boolean isGB2312 = true;
		for (int i = 0; i < chars.length; i++) {
			byte[] bytes = ("" + chars[i]).getBytes();
			if (bytes.length == 1) {
				// int[] ints = new int[2];
				// ints[0] = bytes[0] & 0xff;
				// ints[1] = bytes[1] & 0xff;
				// if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 &&
				// ints[1] <= 0xFE) {
				// } else {
				// isGB2312 = false;
				// break;
				// }
				// } else {
				isGB2312 = false;
				break;
			}
		}
		return isGB2312;
	}

	public static String[] vs = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c",
			"d", "e", "f", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
			"v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "H", "I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	/**
	 * [0-9A-Za-z]�������
	 * 
	 * @param length
	 *            λ��
	 * @return
	 */
	public static String getRandom(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(vs[new Random().nextInt(vs.length)]);
		}
		return sb.toString();

	}
}

package com.aotai.util;

public class DataFormat {

	/**
	 * 
	 * 将十进制数转换为定长的二进制字符串
	 * 
	 * @param value
	 * @param len
	 * @return
	 */
	public static String toBinaryString(int value, int len) {
		String str = Integer.toBinaryString(value);
		if (len > str.length()) {
			len = len - str.length();
			for (int i = 0; i < len; i++) {
				str = "0" + str;
			}
		}
		return str;
	}
}

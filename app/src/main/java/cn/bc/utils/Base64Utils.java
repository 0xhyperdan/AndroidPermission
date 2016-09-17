package cn.bc.utils;

import java.io.UnsupportedEncodingException;

import android.util.Base64;


public class Base64Utils {
	private static final int BUFFER_SIZE = 1024;

	/**
	 * BASE64 加密
	 * @param str
	 * @return
	 */
	public static  String encrypt(byte[] str) {
		try {
			// base64 加密
			return new String(Base64.encode(str, 0, str.length, Base64.DEFAULT), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * BASE64 解密
	 * @param str
	 * @return
	 */
	public static  byte[] decrypt(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		try {
			byte[] encode = str.getBytes("UTF-8");
			// base64 解密
			return Base64.decode(encode, 0, encode.length, Base64.DEFAULT);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}
	

}

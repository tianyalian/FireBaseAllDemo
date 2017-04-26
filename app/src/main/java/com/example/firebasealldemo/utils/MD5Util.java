package com.example.firebasealldemo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Util {


	public static String getUserIDMD5(String encoding) {
		String resultString = "";
		try {
			resultString = new String(encoding);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteToString(md.digest(encoding.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return resultString.substring(0,9);
	}

	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(bByte[i]);
		}
		return sBuffer.toString().replace("-","");
	}

	public static String getMessageIDMD5(String encoding) {
		String resultString = "";
		try {
			resultString = new String(encoding);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteToString(md.digest(encoding.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return resultString.substring(0,10);
	}

}

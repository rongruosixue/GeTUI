package com.aotai.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

 

public class UtilFactory {
	private static Properties pro;
	static {
		pro = new Properties();
		InputStream in;
		try {
			in = new FileInputStream(CMD.LOG_PATH + "config.properties");
			try {
				pro.load(in);
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new ExceptionInInitializerError(e);
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static String getProperty(String name) {
		String value = "";
		try {
			value = pro.getProperty(name);
		} catch (Exception e) {

		}
		return value;
	}

	public static void setProperty(String name, String value, String property) {
		pro.setProperty(name, value);
		saveFile(property);
	}

	public static void saveFile(String property) {
		try {
			OutputStream fout = new FileOutputStream(CMD.LOG_PATH
					+ "config.properties");
			try {
				pro.store(fout, property);
				fout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

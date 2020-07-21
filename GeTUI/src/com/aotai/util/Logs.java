package com.aotai.util;
/**
 * @author miao 2010-6-3
 * 生成日志文件日志文件路径必须指定
 */


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import com.aotai.util.UtilTools;

 

public class Logs {
	/**
	 * 当前应用程序打开的串口
	 */
	private static String serialPortName;

	public static void setSerial(String serialPortName) {
		Logs.serialPortName = serialPortName;
	}

	public static void init() {

		File dir = new File(CMD.LOG_PATH);
		BufferedWriter bufOut = null;
		if (!dir.isDirectory()) {// 非目录
			dir.mkdir();
		}
		File f = new File(CMD.LOG_PATH + "config.properties");

		if ((f.exists() == true) && (f.length() > 0)) {
			return;
		} else {
			List<String> initStr = new ArrayList<String>();
			
			String temp = "dataLenInvalid=the length of datas is invalid";
			initStr.add(temp);
			temp = "wireLessWorkshopNo=2";
			initStr.add(temp);
			temp = "IP=127.0.0.1";//10.39.8.25   127.0.0.1
			initStr.add(temp);
			temp = "dbname=solar_info_db";
			initStr.add(temp);
			temp = "LANGUAGE = 0";//0:汉语 1：英语
			initStr.add(temp);
			temp = "insertError=Insert fails!";
			initStr.add(temp);
			temp = "com=Serial port:";
			initStr.add(temp);
			temp = "maxConnections = 10";
			initStr.add(temp);
			temp = "invalidData=Invalid data";
			initStr.add(temp);
			temp = "serialOpenFailed=Failed to open serial port!";
			initStr.add(temp);
			temp = "baudRate=Baud rate:";
			initStr.add(temp);
			temp = "serialPort=COM1";
			initStr.add(temp);
			temp = "dataCheck=Data validation failure";
			initStr.add(temp);
			temp = "receiveError=receive data error";
			initStr.add(temp);
			temp = "databaseServerException=Can not open database";
			initStr.add(temp);
			temp = "wait=waiting ...";
			initStr.add(temp);
			temp = "open=Open";
			initStr.add(temp);
			temp = "updateError=Update failed";
			initStr.add(temp);
			temp = "connError=Failed to obtain database connection";
			initStr.add(temp);
			temp = "selectError=Query failed";
			initStr.add(temp);
			temp = "close=Close";
			initStr.add(temp);
			temp = "projectName=Meshgate";
			initStr.add(temp);
			temp = "lossPackageCount=3";// 丢包次数
			initStr.add(temp);
			temp = "timeCompensation=5000";// 丢包时间补偿
			initStr.add(temp);
			temp = "password=Password:";
			initStr.add(temp);
			temp = "send=OK";
			initStr.add(temp);
			temp = "COMM=1";//通讯方式  com = 1;  tcp = 2;  udp=3
			initStr.add(temp);
			temp = "tcpport=9000";// 通讯端口
			initStr.add(temp);
			temp = "testRemote=1";// 通讯端口
			initStr.add(temp);
			temp = "dailyAndEnvironment=1";// 通讯端口
			initStr.add(temp);
			temp = "envId=1";// 通讯端口
			initStr.add(temp);
			temp = "plantId=1";// 通讯端口
			initStr.add(temp);
			temp = null;
			
			try {
				bufOut = new BufferedWriter(new FileWriter(f));

				for (int i = 0; i < initStr.size(); i++) {
					bufOut.write("\r\n" + initStr.get(i));
				}

				bufOut.close();

			} catch (IOException e) {
				Calendar c = GregorianCalendar.getInstance();
				String datetime = c.get(c.YEAR) + "-"
						+ fillZero(1 + c.get(c.MONTH) + "", 2) + "-"
						+ fillZero("" + c.get(c.DAY_OF_MONTH), 2) + " "
						+ fillZero("" + c.get(c.HOUR_OF_DAY), 2) + ":"
						+ fillZero("" + c.get(c.MINUTE), 2) + ":"
						+ fillZero("" + c.get(c.SECOND), 2);
				// System.out.println(datetime + "   创建配置文件失败.");
			
				c = null;
				datetime = null;
			} finally {
				dir = null;
				f = null;
				bufOut = null;
				initStr.clear();
				initStr = null;
			}
		}
	}

	/**
	 * 写日志文件
	 * 
	 * @param str
	 *            要写入的日志文件内容
	 */
	public synchronized static void writeLogs(String str) {

		Calendar c = GregorianCalendar.getInstance();
		String filename = CMD.LOG_PATH+"Logs/" + c.get(c.YEAR)
				+ fillZero(1 + c.get(c.MONTH) + "", 2)
				+ fillZero("" + c.get(c.DAY_OF_MONTH), 2) + ".log";

		BufferedWriter bufOut = null;
		String datetime = null;
		FileWriter fw = null;
		try {
			File dir = new File(CMD.LOG_PATH+"Logs"+java.io.File.separator);
			if (!dir.exists()) {
				System.out.println(CMD.LOG_PATH+"Logs"+java.io.File.separator);
				dir.mkdirs();
			}

			File f = new File(filename);
			if (f.exists() == true) {
				fw = new FileWriter(f, true);
				bufOut = new BufferedWriter(fw);// 文件存在
				// 尾写
			} else {
				fw = new FileWriter(f);
				bufOut = new BufferedWriter(fw);
			}

			datetime = c.get(c.YEAR) + "-"
					+ fillZero(1 + c.get(c.MONTH) + "", 2) + "-"
					+ fillZero("" + c.get(c.DAY_OF_MONTH), 2) + " "
					+ fillZero("" + c.get(c.HOUR_OF_DAY), 2) + ":"
					+ fillZero("" + c.get(c.MINUTE), 2) + ":"
					+ fillZero("" + c.get(c.SECOND), 2);
			
			//xiefei	20151009	同时打印日志
			if (UtilTools.textInfor != null) {
				UtilTools.textInfor.append(datetime + "  " + str);
			}
			
			if (serialPortName == null) {
				bufOut.write("\r\n" + datetime + "   " + str);
			} else {
				bufOut.write("\r\n" + datetime + "   " + serialPortName + ":  "
						+ str);
			}

			bufOut.flush();

			fw.close();
			bufOut.close();
			dir = null;

			f = null;
			datetime = null;
			bufOut = null;

		} catch (Exception e) {
			try {
				if (fw != null) {
					fw.close();
					if (bufOut != null) {
						bufOut.close();
					}
				}
			} catch (IOException e1) {

			}
		} finally {

			str = null;
			fw = null;// 清除FileWriter
			bufOut = null;
			datetime = null;
			c = null;
			filename = null;
		}
	}
	/**
	 * 写日志文件
	 * 
	 * @param str
	 *            要写入的日志文件内容
	 */
	public synchronized static void writeDatas(String str) {
		
		String filename = CMD.LOG_PATH  + "zhujianbu.txt";
		BufferedWriter bufOut = null;	
		FileWriter fw = null;
		try {
			File dir = new File(CMD.LOG_PATH);
			if (!dir.isDirectory()) {// 非目录
				dir.mkdir();
			}
			File f = new File(filename);
			if (f.exists() == true) {
				fw = new FileWriter(f, false);
				bufOut = new BufferedWriter(fw);// 文件存在
				// 尾写
			} else {
				fw = new FileWriter(f);
				bufOut = new BufferedWriter(fw);
			}
			bufOut.write(str);
			 
			bufOut.flush();

			fw.close();
			bufOut.close();
			dir = null;
			f = null;			
			bufOut = null;

		} catch (Exception e) {
			try {
				if (fw != null) {
					fw.close();
					if (bufOut != null) {
						bufOut.close();
					}
				}
			} catch (IOException e1) {

			}
		} finally {

			str = null;
			fw = null;// 清除FileWriter
			bufOut = null;			
			filename = null;
		}
	}
	
	/**
	 * 写日志文件
	 * 
	 * @param str
	 *            要写入的日志文件内容
	 */
	public synchronized static void writeDailyDatas(String str) {
		
		Calendar c = GregorianCalendar.getInstance();
		String filename = CMD.LOG_PATH+"daily/" + "daily"+c.get(c.YEAR)
				+ fillZero(1 + c.get(c.MONTH) + "", 2)
				+ fillZero("" + c.get(c.DAY_OF_MONTH), 2) + ".log";
 
		c = null;
		BufferedWriter bufOut = null;	
		FileWriter fw = null;
		try {
			File dir = new File(CMD.LOG_PATH+"daily/");
			if (!dir.isDirectory()) {// 非目录
				dir.mkdir();
			}
			File f = new File(filename);
			if (f.exists() == true) {
				fw = new FileWriter(f, false);
				bufOut = new BufferedWriter(fw);// 文件存在
				// 尾写
			} else {
				fw = new FileWriter(f);
				bufOut = new BufferedWriter(fw);
			}
			bufOut.write(str);
			 
			bufOut.flush();

			fw.close();
			bufOut.close();
			dir = null;
			f = null;			
			bufOut = null;

		} catch (Exception e) {
			try {
				if (fw != null) {
					fw.close();
					if (bufOut != null) {
						bufOut.close();
					}
				}
			} catch (IOException e1) {

			}
		} finally {

			str = null;
			fw = null;// 清除FileWriter
			bufOut = null;			
			filename = null;
		}
	}
	
	/**
	 * 存储当前的环境检测仪信息及逆变器发电量信息
	 * D:/AOTAI_DATA/DAT.TXT 
		温度：一位小数，单位：℃ 
		湿度：一位小数，单位：% 
		风向：一位小数，范围0~359.9 
		风速：一位小数，单位：m/S 
		总发电量：一位小数,单位：kwh 
		日发电量：一位小数,单位：kwh 
		当前功率：一位小数,单位：kw 
		二氧化碳减排量：一位小数,单位：kg
	 * @param str
	 *     
	 */
	public synchronized static void writeExtendsDatas(String subDir,String fileNamePrefix,String str) {
		
		String filename = CMD.Log_Inverter_Data+subDir+"/"+fileNamePrefix + ".txt";
		 
		BufferedWriter bufOut = null;	
		FileWriter fw = null;
		try {
			File dir = new File(CMD.Log_Inverter_Data+subDir+"/");
			if (!dir.isDirectory()) {// 非目录
				dir.mkdir();
			}
			File f = new File(filename);
			if (f.exists() == true) {
				fw = new FileWriter(f, false);
				bufOut = new BufferedWriter(fw);// 文件存在
				// 尾写
			} else {
				fw = new FileWriter(f);
				bufOut = new BufferedWriter(fw);
			}
			bufOut.write(str);
			 
			bufOut.flush();

			fw.close();
			bufOut.close();
			dir = null;
			f = null;			
			bufOut = null;

		} catch (Exception e) {
			try {
				if (fw != null) {
					fw.close();
					if (bufOut != null) {
						bufOut.close();
					}
				}
			} catch (IOException e1) {

			}
		} finally {
			subDir = null;
			fileNamePrefix = null;
			str = null;
			fw = null;// 清除FileWriter
			bufOut = null;			
			filename = null;
		}
	}
	/**
	 * 
	 * @param str
	 *            处理的字符串
	 * @param len
	 *            左补零后总长度
	 * @return
	 */
	private static String fillZero(String str, int len) {
		int tmp = str.length();
		int t;
		String str1 = str;
		if (tmp == len)
			return str1;
		t = len - tmp;
		for (int i = 0; i < t; i++)
			str1 = "0" + str1;
		return str1;
	}

}

package com.aotai.mail;

import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aotai.util.HttpClientUtils;
import com.aotai.util.Logs;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MailGeTuiExcel {
	
	/*
	 * 从Mail_GeTui.xls表格中加载需要发送邮件的账号，处理读取Excel表格
	 * */
	
	private static String excel = "Mail_GeTui.xls";
	public static List<StationAccount> salist = new ArrayList<StationAccount>();
	
	public static void loadAccountFromExcel() {
		
		String dir = System.getProperty("user.dir");
		dir = dir + java.io.File.separator;
		String filename = dir + excel;
		File fsa = new File(filename);
		if (!fsa.exists()) {
			Logs.writeLogs("文件不存在 " + filename + "\r\n");
			return;//文件不存在则直接返回
		}
		
		salist.clear();
		
		Sheet sheet = null;
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(fsa);
			sheet = workbook.getSheet(0);
			int row=1;
			while (true) {
				String type = "";
				try {///*这段代码就是为了测试避免有空行导致异常
					type = sheet.getCell(0, row).getContents();
					if ("".equals(type) || (type == null)) {
						break;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					break;
				}
				
				String username = sheet.getCell(0, row).getContents();
				String password = sheet.getCell(1, row).getContents();
				String language = sheet.getCell(2, row).getContents();
				String time = sheet.getCell(3, row).getContents();
				String mail = sheet.getCell(4, row).getContents();
				row++;
				StationAccount sa = new StationAccount();
				try{
					sa.setUsername(username);
					sa.setPassword(password);
					sa.setLanguage(language);
					sa.setMail(mail);
					sa.setTime(Integer.parseInt(time));
				} catch (NumberFormatException ne) {
					sa.setTime(18);
				}
				Calendar c = Calendar.getInstance();
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int time1 = sa.getTime();
				///*由于窗口中定时器是两小时执行一次
				if ((time1>=hour-1) && (time1<=hour+1)) {
					salist.add(sa);
				}
			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			sheet = null;
			workbook.close();
			fsa = null;
		}
		
		Logs.writeLogs("读取文件完毕 + " + filename + "\r\n");
	}
	
	public static void sendMail(StationAccount sa) {
		
		Properties properties = new Properties();
        // 设置认证属性
        properties.setProperty("mail.smtp.auth", "true");
        // 设置通信协议
        properties.setProperty("mail.transport.protocol", "smtp");
        // 邮件环境信息
        Session session = Session.getInstance(properties);
        // 调试,打印信息
        session.setDebug(false);

        // 邮件
        Message message = new MimeMessage(session);
        // 主题
        try {
			message.setSubject("power-station-information");
			// 发送人
	        message.setFrom(new InternetAddress("17753173678@163.com"));
	        // 内容
	        message.setText(sa.getMessage());

	        // 邮件传输对象
	        Transport transport = session.getTransport();
	        // 传输连接：host，port，user，pass/主机，端口，用户名，密码
	        transport.connect("smtp.163.com", 25, "17753173678@163.com", "zzy112358");
	        // 发送邮件
	        transport.sendMessage(message, new Address[] { new InternetAddress(sa.getMail()) });

	        // 关闭连接
	        transport.close();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 获取一个账号的电站信息，设置完成之后可以发送 邮件 
	 * */
	public static void getOneAccountMessage(StationAccount sa) {
		
//		String url = "http://www.aotaicloud.com/ATSolarInfo/GeTuiQueryByAccount.action?username=";
		String url = "http://192.168.0.40/ATSolarInfo/GeTuiQueryByAccount.action?username=";
		url += sa.getUsername() + "&password=" + sa.getPassword();
		
		String result = HttpClientUtils.httpGet(url);
		JSONObject jo = JSONObject.parseObject(result);
		JSONArray ja = jo.getJSONArray("plantlist");
		for (int i=0; i<ja.size(); i++) {
			JSONObject j = (JSONObject) ja.get(i);
			sa.setMessageBody(j.getString("plantName"), j.getString("eday"), j.getString("etot"), String.format("%.2f", j.getDouble("etot")/75));
		}
	}
	
	
	public static void MailRoutine() {
		try {
			loadAccountFromExcel();
			for (StationAccount sa : salist) {
				getOneAccountMessage(sa);
				sendMail(sa);
			}
			Logs.writeLogs("邮件处理完毕");
		} catch (Exception e) {
			Logs.writeLogs("邮件处理过程异常");
		}
	}

}

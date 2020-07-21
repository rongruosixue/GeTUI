package com.aotai.mail;

public class StationAccount {
	/*
	 * 从Excel表格Mail_GeTui.xls中加载需要发送邮件的账号
	 * */
	
	private String username;
	private String password;
	private String language;
	private int time;
	private String mail;
	
	private String message;
	private String bodytemp = "";
	
	private String msgheaderen = "Dear Customer, \r\n";
	private String msgbodyen = "Your power plant PLANT: Total power generation is etotalKwh, equivalent to planting TREE trees. Generation today is edayKwh. Thanks & have a nice day! \r\n";
	private String msgfooten = "Shandong Aotai Electric Co., Ltd.";
	
	private String msgheadercn = "尊敬的用户您好：\r\n";
	private String msgbodycn = "您的电站PLANT，已经累计发电etotal千瓦，当日发电eday千瓦。相当于植树TREE棵，祝您工作愉快再见。\r\n";
	private String msgfootcn = "该信息来自山东奥太电气有限公司。";
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getMessage() {
		if ("cn".contentEquals(language)) {
			message = msgheadercn + bodytemp + msgfootcn;
		} else {
			message = msgheaderen + bodytemp + msgfooten;
		}
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public void setMessageBody(String plantname, String eday, String etot, String trees) {
		String str = "";
		if ("cn".contentEquals(language)) {
			str = msgbodycn.replace("PLANT", plantname);
			str = str.replace("etotal", etot);
			str = str.replace("eday", eday);
			str = str.replace("TREE", trees);
		} else {
			str = msgbodyen.replace("PLANT", plantname);
			str = str.replace("etotal", etot);
			str = str.replace("eday", eday);
			str = str.replace("TREE", trees);
		}
		
		this.bodytemp += str;
	}
	
}

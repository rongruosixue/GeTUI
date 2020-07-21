package com.aotai.getui;

public class GeTuiAccount {
	private String clientid;
	private String username;
	private String password;
	private String language;
	///* 新增加的根据os类型判断是ios还是Android
	private String os;
	private GeTuiMessage gtMessage = new GeTuiMessage();
	
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
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
	public GeTuiMessage getGtMessage() {
		return gtMessage;
	}
	public void setGtMessage(GeTuiMessage gtMessage) {
		this.gtMessage = gtMessage;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	
	
	
}

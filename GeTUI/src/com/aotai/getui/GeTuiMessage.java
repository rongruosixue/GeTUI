package com.aotai.getui;

import com.aotai.util.Logs;

public class GeTuiMessage {
	
	/*
	 * 每个个推客户端的推送信息，绑定到用户注册的个推clientid中  
	 * */
	private String title;
	private String content;
	private String acpower;
	private String eday;
	private String etot;
	private int plantcount;
	private int fault;
	private int jianshe;
	private int normal;
	private int offline;
	private int standby;
	private int warn;
	private boolean flagpush = false;
	
	/*
	 * 根据设置好的信息组织出对应的推送信息
	 * */
	public void dealMessage(String language) {
		///*如果全部运行正常则不进行推送,只有正常和离线也不进行推送
		if ((plantcount == normal)||(plantcount == (offline+normal))) {
			flagpush = false;
			return;
		} else {
			flagpush = true;
		}
		///*中文推送
		if (language==null||"".equals(language)||"CN".equals(language)||"cn".equals(language)) {
			title = "ATSolar 信息推送";
			content = "今日发电："+eday+"KWH，当前功率：" + acpower +"KW，正常电站数量："+ normal + ",异常电站数量："+ (plantcount-normal);
			
		} else {
			title = "ATSolar alarm information";
			content = "Generation today:" + eday + "KWH,real-time power:" + acpower + "KW,";
			content += normal + " of stations in normal," + (plantcount-normal) + " of stations in alarm";
		}
		
	}
	
	public String getAcpower() {
		return acpower;
	}
	public void setAcpower(String acpower) {
		this.acpower = acpower;
	}
	public String getEday() {
		return eday;
	}
	public void setEday(String eday) {
		this.eday = eday;
	}
	public String getEtot() {
		return etot;
	}
	public void setEtot(String etot) {
		this.etot = etot;
	}
	public int getPlantcount() {
		return plantcount;
	}
	public void setPlantcount(int plantcount) {
		this.plantcount = plantcount;
	}
	public int getFault() {
		return fault;
	}
	public void setFault(int fault) {
		this.fault = fault;
	}
	public int getJianshe() {
		return jianshe;
	}
	public void setJianshe(int jianshe) {
		this.jianshe = jianshe;
	}
	public int getNormal() {
		return normal;
	}
	public void setNormal(int normal) {
		this.normal = normal;
	}
	public int getOffline() {
		return offline;
	}
	public void setOffline(int offline) {
		this.offline = offline;
	}
	public int getStandby() {
		return standby;
	}
	public void setStandby(int standby) {
		this.standby = standby;
	}
	public int getWarn() {
		return warn;
	}
	public void setWarn(int warn) {
		this.warn = warn;
	}
	public boolean isFlagpush() {
		return flagpush;
	}
	public void setFlagpush(boolean flagpush) {
		this.flagpush = flagpush;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}

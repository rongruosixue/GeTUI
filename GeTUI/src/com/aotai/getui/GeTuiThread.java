package com.aotai.getui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.aotai.JDBC.JdbcUtils;
import com.aotai.util.HttpClientUtils;
import com.aotai.util.Logs;

public class GeTuiThread extends Thread {
	
	private List<GeTuiAccount> listGtAccount = new ArrayList<GeTuiAccount>();
	
	@Override
	public void run() {
		try {
			getGeTuiAccountList();
			getAccountMessage();
			pushInformation();
		} catch (Exception e) {
			
		} finally {
			Logs.writeLogs("完成本次账号故障信息推送\r\n");
		}
	}
	
	/*
	 * 从服务器获取个推client与绑定的账号列表
	 * */
	private void getGeTuiAccountList() {
		String sql = "select * from push_getui";
		
		Connection con = JdbcUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				GeTuiAccount gta = new GeTuiAccount();
				String str = "";
				str = rs.getString("clientid");
				gta.setClientid(str);
				str = rs.getString("username");
				gta.setUsername(str);
				str = rs.getString("password");
				gta.setPassword(str);
				str = rs.getString("language");
				gta.setLanguage(str);
				str = rs.getString("os");
				gta.setOs(str);
				this.listGtAccount.add(gta);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JdbcUtils.closeConnection(con);
			Logs.writeLogs("获取clientid与账号信息完毕\r\n");
		}
	}
	
	/*
	 * 从aotaicloud.com平台获取一个账号底下的电站运行信息，并且确定好推送信息
	 * */
	private void getOneAccountMessage(GeTuiAccount gta) {
		
//		String url = "http://www.aotaicloud.com/ATSolarInfo/GeTuiQueryByAccount.action?username=";
		String url = "http://192.168.0.40/ATSolarInfo/GeTuiQueryByAccount.action?username=";
		
		try {
			String account = URLEncoder.encode(gta.getUsername(), "UTF-8");
			gta.setUsername(account);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		url += gta.getUsername() + "&password=" + gta.getPassword();
		
		String result = HttpClientUtils.httpGet(url);
		JSONObject jo = JSONObject.parseObject(result);
		GeTuiMessage gtm = gta.getGtMessage();
		gtm.setAcpower(jo.getString("acpower"));
		gtm.setEday(jo.getString("eday"));
		gtm.setEtot(jo.getString("etot"));
		gtm.setFault(jo.getIntValue("faultcount"));
		gtm.setJianshe(jo.getIntValue("jianshecount"));
		gtm.setNormal(jo.getIntValue("normalcount"));
		gtm.setOffline(jo.getIntValue("offlinecount"));
		gtm.setStandby(jo.getIntValue("standbycount"));
		gtm.setPlantcount(jo.getIntValue("plantcount"));
		gtm.setWarn(jo.getIntValue("warncount"));
		gtm.dealMessage(gta.getLanguage());
	}
	
	/*
	 * 获取所有客户端的推送信息
	 * */
	private void getAccountMessage() {
		
		if (listGtAccount.size() <= 0) {
			return;
		}
		
		for ( GeTuiAccount gta : listGtAccount) {
			try {
				getOneAccountMessage(gta);
			} catch (Exception e) {
				Logs.writeLogs("getOneAccountMessage()运行异常:" + e.getMessage() + "\r\n");
			}
		}
		
		Logs.writeLogs("获取所有账号电站信息完毕\r\n");
	}
	
	private void pushInformation() {
		if (listGtAccount.size() <= 0) {
			return;
		}
		for (GeTuiAccount gta : listGtAccount) {
			try {
				if (gta.getGtMessage().isFlagpush()) {
					String os = gta.getOs();
					if ( (os==null) || ("Android".equals(os)) || ("android".equals(os)) ) {
						GeTuiPush.pushInformationToClient(gta);
					} else {///* 针对iOS推送
						GeTuiPush.pushInformationToiOSClient(gta);
						System.out.println("推送iOS设备：" + gta.getClientid() + ", 推送信息是" + 
						    gta.getGtMessage().getContent() + ", " + os);
					}
				}
			} catch (Exception e) {
				Logs.writeLogs("GeTuiPush.pushInformationToClient()运行异常:" + e.getMessage() + "\r\n");
			}
		}
		
		Logs.writeLogs("推送完成所有个推信息\r\n");
	}

}

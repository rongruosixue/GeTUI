package com.aotai.getui;

import com.aotai.util.Logs;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.MultiMedia;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style1;
import com.gexin.rp.sdk.template.style.Style6;

public class GeTuiPush {
	
	/*
	 * 使用个推SDK向一个用户推送消息
	 * */
	
	 private static String appKey = "c6ejIiEZgW8D0HHMNXjvD";
	 private static String masterSecret = "O4QChgOjoD6hasv9BsW5v3";
	 private static String url = "http://sdk.open.api.igexin.com/apiex.htm";
	 private static String appId = "gFOZ8FJ6nM6TpXFm8nhSY8";
	 private static String masterSecretGeTui = "7H7uYP2uyO94wCpvHdf8z4";
	 
	 public static NotificationTemplate notificationTemplateDemo(String appId, String appkey, GeTuiAccount gta) {
		  NotificationTemplate template = new NotificationTemplate();
		  // 设置APPID与APPKEY
		  template.setAppId(appId);
		  template.setAppkey(appkey);
		  // 透传消息设置，1为强制启动应⽤，客户端接收到消息后就会⽴即启动应⽤；2为等待应⽤启动
		  template.setTransmissionType(1);
		  template.setTransmissionContent(gta.getGtMessage().getContent());
		  // 设置定时展示时间
		  // template.setDuration("2015-01-16 11:40:00", "2015-01-1612:24:00");
		  Style6 style = new Style6();
		  // 设置通知栏标题与内容
		  style.setTitle(gta.getGtMessage().getTitle());
		  style.setText(gta.getGtMessage().getContent());
		  // 配置通知栏图标
//		  style.setLogo("icon.png");
//		  // 配置通知栏⽹络图标
//		  style.setLogoUrl("");
		  // 设置通知是否响铃，震动，或者可清除
		  style.setRing(true);
		  style.setVibrate(true);
		  style.setClearable(true);
		  style.setTitle(gta.getGtMessage().getTitle());
		  style.setText(gta.getGtMessage().getContent());
		  style.setBigStyle2(gta.getGtMessage().getContent());
		  template.setStyle(style);
		  template.setChannelLevel(4);
		  return template;
	 }
	 
	 /*
	  * 将账号运行信息推送至用户端app
	  * */
	 public static void pushInformationToClient(GeTuiAccount gta) {
		 
		 IGtPush push = new IGtPush(appKey, masterSecretGeTui);
		 NotificationTemplate template = notificationTemplateDemo(appId, appKey, gta);
		 SingleMessage message = new SingleMessage();
		 message.setOffline(true);
		 // 离线有效时间，单位为毫秒，可选
		 message.setOfflineExpireTime(2 * 3600 * 1000);
		 message.setData(template);
		 message.setPushNetWorkType(0);
		 Target target = new Target();
		 target.setAppId(appId);
		 target.setClientId(gta.getClientid());
		 IPushResult ret = null;
		 try {
			 ret = push.pushMessageToSingle(message, target);
	     } catch (RequestException e) {
			 e.printStackTrace();
			 ret = push.pushMessageToSingle(message, target, e.getRequestId());
		 }
		 if (ret != null) {
//			 Logs.writeLogs(ret.getResponse().toString());
		 } else {
			 Logs.writeLogs("服务器响应异常");
		 }
	 }
	 
	 /*
	  * 以下的代码是处理iOS的透传推送
	  * */
	 
	 public static TransmissionTemplate transmissionTemplateDemo(String appId, String appkey, GeTuiAccount gta) {
		 TransmissionTemplate template = new TransmissionTemplate();
		 template.setAppId(appId);
		 template.setAppkey(appkey);
		 template.setTransmissionContent(gta.getGtMessage().getContent());
		 template.setTransmissionType(1);
		 APNPayload payload = new APNPayload();
		 //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
//		 payload.setAutoBadge("+1");
		 payload.setContentAvailable(1);
		 //ios 12.0 以上可以使⽤ Dictionary 类型的 sound
		 payload.setSound("default");
		 payload.setCategory("$由客户端定义");
		 payload.addCustomMsg("payload", "payload");
		 //简单模式APNPayload.SimpleMsg
		 payload.setAlertMsg(new APNPayload.SimpleAlertMsg(gta.getGtMessage().getContent()));
		 //字典模式使⽤APNPayload.DictionaryAlertMsg
		 //payload.setAlertMsg(getDictionaryAlertMsg());
		 //设置语⾳播报类型，int类型，0.不可⽤ 1.播放body 2.播放⾃定义⽂本
		 payload.setVoicePlayType(0);
		 //设置语⾳播报内容，String类型，⾮必须参数，⽤户⾃定义播放内容，仅在voicePlayMessage=2时⽣效
		 //注：当"定义类型"=2, "定义内容"为空时则忽略不播放
		 payload.setVoicePlayMessage(gta.getGtMessage().getContent());
		 // 添加多媒体资源
		 //payload.addMultiMedia(new MultiMedia().setResType(MultiMedia.MediaType.video).setResUrl("http://ol5mrj259.bkt.clouddn.com/test2.mp4").setOnlyWifi(true));
		 template.setAPNInfo(payload);
		 return template;
	 }
	 
	 public static void pushInformationToiOSClient(GeTuiAccount gta) {
		 
		 IGtPush push = new IGtPush(appKey, masterSecretGeTui);
		 TransmissionTemplate template = transmissionTemplateDemo(appId, appKey, gta);
		 SingleMessage message = new SingleMessage();
		 message.setOffline(true);
		 // 离线有效时间，单位为毫秒，可选
		 message.setOfflineExpireTime(2 * 3600 * 1000);
		 message.setData(template);
		 message.setPushNetWorkType(0);
		 Target target = new Target();
		 target.setAppId(appId);
		 target.setClientId(gta.getClientid());
		 IPushResult ret = null;
		 try {
			 ret = push.pushMessageToSingle(message, target);
	     } catch (RequestException e) {
			 e.printStackTrace();
			 ret = push.pushMessageToSingle(message, target, e.getRequestId());
		 }
		 if (ret != null) {
//			 Logs.writeLogs(ret.getResponse().toString());
		 } else {
			 Logs.writeLogs("服务器响应异常");
		 }
	 }

}

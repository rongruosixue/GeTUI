package com.aotai.util;
 
public class CMD {	
	
	public static final String COLLECTOREXCEL = "Collector.xls";//采集器类型Excel表格
	public static final String COLLECTORSIMID = "SimCCID.xls"; //采集器与SIM卡ccid的对应表格
	
	/*
	 * 底下的这些参数基本用不到，不需要关心
	 * */
	public static final int STORE_COUNT = 100;
	
	/*************  设备型号   ********************/
	public static final byte MODE_INDEX = 72;//设备型号下标 add 2014-09-22
	
	
	/***********   超时定时器     *****************/
	public static final int TIMER_OUT = 960;// 定时5分一组数据  * 3 + 1分 = 300*3+60 = 960
	
	/*
	 * 设备型号
	 */
	public static final int MIN_INVERTER = 1;//小功率
	public static final int MIDDLE_INVERTER = 2;//中功率
	public static final int MAX_INVERTER = 3;//大功率
	
	/* 
	 * 通讯数据
	 */
	
	
	public static final byte DATA_HEAD = 0x7e;
	public static final byte DATA_TAIL = 0x7d;
	public static final byte DATA_7C = 0x7c;
	public static final byte DATA_00 = 0x00;
	
	
	/*
	 *设备类型
	 *
	 *逆变房 		1
	逆变器		2
	环境监测仪		3
	配电箱		4
	汇流箱		5
	配电柜		6
	防逆流控制箱		7
	测控柜		8
	通信采集器		9

	 */
	
	public static final byte INVERTER = 2;//逆变器
	public static final byte ENVIROMENT = 3;//环境监测仪
	public static final byte JUNCTIONBOX= 5;//汇流箱
	public static final byte SWITCHBOARD = 6;//配电柜
	public static final byte BLACKFLOW = 7;//防逆流
	
	
	/*
	 *逆变器状态 
	 */
	public static final byte OFFLINE  = 0;//0	离线
	public static final byte RUN_STATE = 1;//1	运行
	public static final byte  STANDBY = 2;//2	待机
	public static final byte  ALARM_STATE = 4;//4	故障
	//8	紧急停机
	//16	按键停机

	
	 
	/*
	 * 命令码控制字段值	 
	 */
	
	public static final byte SEND_OR_RECEIVE_COLLECTOR_ADDRESS = 0X01;//上位机向通讯采集器发送该通讯采集器对应的网络设备信息及采集器相应的回复信息
	public static final byte SEND_OR_RECEIVE_INVERTER_DATA = 0X02;//逆变器实时数据
	public static final byte INVERTER_ACTUAL_CONTROL = 0X03;//逆变器控制信息
    public static final byte SWITCH_BOARD_ACTUAL_DATA = 0X04;//配电柜实时数据
    public static final byte JUNCTION_BOX_ACTUAL_DATA = 0X05;//汇流箱实时数据
    public static final int ENVIROMENT_ACTUAL_DATA = 0XF1;//环境监测仪发送的实时数据    
    public static final byte SEND_OR_RECEIVE_INVERTER_DATA_4B =0x06;//逆变器实时数据4B采集器地址
	public static final byte INVERTER_ACTUAL_CONTROL_4B = 0X07;//逆变器控制信息
    public static final byte SWITCH_BOARD_ACTUAL_DATA_4B = 0X08;//配电柜实时数据
    public static final byte JUNCTION_BOX_ACTUAL_DATA_4B = 0X09;//汇流箱实时数据
    public static final int ENVIROMENT_ACTUAL_DATA_4B = 0XF2;//环境监测仪发送的实时数据    

    
   /*
    * 
    *  返回的ack
    * 
    */

	public static final int COLLECTOR_ADDRESS_INDEX=3;//采集器地址下标
	public static final int EQUIP_ADDRESS_INDEX = 4;//设备地址下标
 
	
    /*
	 * 
	 * 有效数据包长度
	 * 
	 */
    public static final byte INVERTER_ACTUAL_LEN = 65;//55;//逆变器实时数据包长度
    public static final byte SWITCH_ACTUAL_LEN = 47;//配电柜实时数据包长度
    public static final byte JUNCTION_ACTUAL_LEN = 47;//汇流箱实时数据包长度
    public static final byte ENVIROMENT_ACTUAL_LEN = 39;//汇流箱实时数据包长度 
	
	/*
	 * 
	 * 标准数据包命令
	 * 
	 */
    public static final byte CMD_OPERATOR_INDEX1 = 1;// 控制命令1
    public static final byte CMD_OPERATOR_INDEX2 = 2;// 控制命令2
	 
    
    /*
	 * 下传时相关参数上限值
	 */
	public static final int MAX_SEND_NUM = 5;//单次重发次数
	public static final int MAX_RESEND_NUM = 5;//最大重复次数
	
	/*
	 * 参数下传时状态
	 */
	
	public static final byte NOT_SEND_STATE= -1;//此节点的信息不需发送 删除
	public static final byte SEND_NORMAL = 0;//非下传态
	public static final byte SEND_SETTING = 1;//需要下传的控制命令
	public static final byte SEND_SENDING = 2;//下传处理中。。。。
	public static final byte WAIT_EQUIP_ACK = 3;//等待设备的回复信息
	public static final byte SEND_FAILED = 4;//下传失败
	public static final byte SEND_SUCCESS = 5;//下传成功
	
	/*
	 * 下传控制状态
	 */
	public static final byte SEND_READY = 0;//节点就绪态 可进行下传
	
	/*
	 *远程设置 数据，数据转发功能模块
	 */
	
	public static final byte REMOTE_SET_SEND = 0x10;
	public static final byte REMOTE_SET_RECEIVE = 0x11;
	/*//设置参数二级控制命令
	public static final byte MODBUS_BAUT = 0x01;//设置波特率
	public static final byte SERVER_IP_PORT = 0x02;//配置数据中心服务器地址及端口
	public static final byte MODBUS_ADDRESS = 0x03;//设置Modbus设备地址
	public static final byte MODBUS_ADDRESS_BY_EQUIP = 0x04;//根据机器代码配置modbus设备地址
	public static final byte MODBUS_ADDRESS_TABLE = 0x05;//modbus设备列表
	public static final byte RESET = 0x06;//恢复出厂设置
	public static final int RESTART = 0xFF;//重启采集器*/
	
	

	
    /*
     * 系统配置文件
     */
	public static final boolean DEBUG = false;
	public static final boolean TEXT_INFO_SHOW = true;	
	
	public static  String LOG_PATH =  "C:/pvLogs/";// 日志文件路径
	public static  String Log_Inverter_Data = "D:/AOTAI_DATA/";// 显示屏显示当前逆变器的数据
 
}

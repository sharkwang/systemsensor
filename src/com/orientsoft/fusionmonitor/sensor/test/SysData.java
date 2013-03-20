package com.orientsoft.fusionmonitor.sensor.test;

public class SysData {

		private String data; 	//采集日期
		private String time; 	//采集时间
	 /*
	  *  	以下信息从配置文件中读取
	  */
		private String App1; 	//本机运行应用系统
		private String MultiApp; //多个应用系统名称，以|隔离
		private String Type; 	//本机系统类型：AS-J2EE服务器 DB-数据库服务器 
					//Tux-Tuxedo Any-其他类型
	        // 系统信息
		private String hostname; 	//主机名
		private String OSname; 	//操作系统及版本
		private String CpuType; //CPU 类型
		private String CpuMhz; //CPU 主频速度
		private String CpuTotalCores; //CPU 核心数
		private String CpuTotalIdlePercent; //CPU 空闲率
		private String usedMem; // 已使用内存总量
		private String MemPercent; //内存空闲率
		private String ActiveDiskUsage; //监控磁盘使用率
		private String ActiveDiskRead; //监控磁盘读
		private String ActiveDiskWrite; //监控磁盘写
		private String IPaddress; 	// IP地址
		private String MacAddress; 	// 第一张网卡物理地址
		private String NetRxBytes; 	//接收总字节数
		private String NetTxBytes; 	//发送总字节数
		private String NetRxErrors; 	//接收错误包数
		private String NetTxError; 	//发送错误包数
		private String MultiIPaddress; 	// 第二张IP地址 
		private String MultiMacAddress; //第二张网卡物理地址
		private String NetRxBytes_2; 	//接收总字节数
		private String NetTxBytes_2; 	//发送总字节数
		private String NetRxErrors_2; 	//接收错误包数
		private String NetTxError_2; 	//发送错误包数
		
		public void SystemData(){

		}

}

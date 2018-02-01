package com.etech.myteam.global;

public class AppConst {
	
	public static final String sCode = "utf-8";
	public static int iSocketTimeOut = 5000;
	
	//public static String sBaseURL = "localhost";
	//本地测试
	public static String sBaseURL = "192.168.0.103";
	//for hbin wifi-ip
	//public static String sBaseURL = "39.174.39.43";
	//for shuaibinliu pc-ip
	//public static String sBaseURL = "120.79.182.43";
	
	//实战url
	public static int iApiPort = 7443;
	//for hbin服务器
	//public static int iApiPort = 8080;
	//for shuaibinliu服务器
	public static String sServerURL = sBaseURL + ":" + iApiPort;

}

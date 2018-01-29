package com.etech.myteam.common;

public class NumToString {
	
	//技能等级转化为文字
	public static String getLevel(int level){
    	String level_name = null;
    	switch(level){
    	case 1:
    		level_name = "入门";
    		break;
    	case 2:
    		level_name = "一般";
    		break;
    	case 3:
    		level_name = "掌握";
    		break;
    	case 4:
    		level_name = "熟练";
    		break;
    	case 5:
    		level_name = "精通";
    		break;
    	default:
    		level_name = "未知";
    		break;
    	}
    	return level_name;
    }
	
	//竞赛等级转化为文字
	public static String getCLevel(int level){
		String competitions_level = null;
		switch(level){
		case 1:
			competitions_level = "院初赛";
			break;
		case 2:
			competitions_level = "院复赛";
			break;
		case 3:
			competitions_level = "院决赛";
			break;
		case 4:
			competitions_level = "校初赛";
			break;
		case 5:
			competitions_level = "校复赛";
			break;
		case 6:
			competitions_level = "校决赛";
			break;
		case 7:
			competitions_level = "省初赛";
			break;
		case 8:
			competitions_level = "省复赛";
			break;
		case 9:
			competitions_level = "省决赛";
			break;
		case 10:
			competitions_level = "国初赛";
			break;
		case 11:
			competitions_level = "国复赛";
			break;
		case 12:
			competitions_level = "国决赛";
			break;
		default:
    		competitions_level = "未知";
    		break;
		}
		return competitions_level;
	}
	
	//团队成员身份转化为文字
	public static String getTStype(int TStype){
		String TStype_name = null;
		switch(TStype){
		case 1000:
			TStype_name = "创建者";
			break;
		case 1001:
			TStype_name = "管理员";
			break;
		case 1002:
			TStype_name = "团队成员";
			break;
		default:
			TStype_name = "未知";
			break;
		}
		return TStype_name;
	}

}

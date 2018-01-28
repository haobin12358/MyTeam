package com.etech.myteam.common;

public class NumToString {
	
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
    	}
    	return level_name;
    }

}

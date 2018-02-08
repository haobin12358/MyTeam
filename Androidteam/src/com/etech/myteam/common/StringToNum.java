package com.etech.myteam.common;

public class StringToNum {

	public static int getLevel(String level){
    	int level_no;
    	switch(level){
    	case "入门":
    		level_no = 1;
    		break;
    	case "一般":
    		level_no = 2;
    		break;
    	case "掌握":
    		level_no = 3;
    		break;
    	case "熟练":
    		level_no = 4;
    		break;
    	case "精通":
    		level_no = 5;
    		break;
    	default:
    		level_no = -99;
    		break;
    	}
    	return level_no;
    }
}

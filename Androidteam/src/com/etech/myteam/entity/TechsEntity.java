package com.etech.myteam.entity;


/*
 * 个人技能实体
 * 设置get与set方法
 */
public class TechsEntity {
	
	private String STid;//主ID
	private String Sid;//学生ID
	private String STname;//技能名称
	private String STlevel;//技能等级
	
	public String getSTid() {
		return STid;
	}
	public void setSTid(String sTid) {
		STid = sTid;
	}
	public String getSid() {
		return Sid;
	}
	public void setSid(String sid) {
		Sid = sid;
	}
	public String getSTname() {
		return STname;
	}
	public void setSTname(String sTname) {
		STname = sTname;
	}
	public String getSTlevel() {
		return STlevel;
	}
	public void setSTlevel(String sTlevel) {
		STlevel = sTlevel;
	}	
}

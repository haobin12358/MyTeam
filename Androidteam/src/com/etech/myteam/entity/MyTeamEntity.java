package com.etech.myteam.entity;

/*
 * 个人团队实体
 * 设置get与set方法
 */
public class MyTeamEntity {

	private String TEid;//团队id
	private String Cname;//竞赛名称
	private int Cno;//竞赛届次
	private int Clevel;//竞赛等级 
	private String TEname;//团队名称
	
	public String getTEid() {
		return TEid;
	}
	public void setTEid(String tEid) {
		TEid = tEid;
	}
	public String getCname() {
		return Cname;
	}
	public void setCname(String cname) {
		Cname = cname;
	}
	public int getCno() {
		return Cno;
	}
	public void setCno(int cno) {
		Cno = cno;
	}
	public int getClevel() {
		return Clevel;
	}
	public void setClevel(int clevel) {
		Clevel = clevel;
	}
	public String getTEname() {
		return TEname;
	}
	public void setTEname(String tEname) {
		TEname = tEname;
	}
}

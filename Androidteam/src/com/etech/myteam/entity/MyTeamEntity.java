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
	
	//团队list需要用到的字段
	private String Tetname; //教师姓名
	
	public void setIsfull(String isfull) {
		Isfull = isfull;
	}
	private String Teleader; //团队leader
	private String Isfull; //是否满员
	
	
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
	public String getTetname() {
		return Tetname;
	}
	public void setTetname(String tetname) {
		Tetname = tetname;
	}
	public String getTeleader() {
		return Teleader;
	}
	public void setTeleader(String teleader) {
		Teleader = teleader;
	}
	public String getIsfull() {
		return Isfull;
	}
}

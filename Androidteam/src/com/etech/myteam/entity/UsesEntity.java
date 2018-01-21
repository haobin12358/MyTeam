package com.etech.myteam.entity;

/*
 * 个人比赛经历实体
 * 设置get和set方法
 */
public class UsesEntity {

	private String id;//主id，对应scid和tcid
	private String Uid;//用户id，对应sid和tid
	private String Cname;//竞赛名称，对应scname和tcname
	private String Cno;//竞赛结果，对应scno和tcno
	private String TCnum;//对应的队伍数量，与tid共存
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUid() {
		return Uid;
	}
	public void setUid(String uid) {
		Uid = uid;
	}
	public String getCname() {
		return Cname;
	}
	public void setCname(String cname) {
		Cname = cname;
	}
	public String getCno() {
		return Cno;
	}
	public void setCno(String cno) {
		Cno = cno;
	}
	public String getTCnum() {
		return TCnum;
	}
	public void setTCnum(String tCnum) {
		TCnum = tCnum;
	}
	
}

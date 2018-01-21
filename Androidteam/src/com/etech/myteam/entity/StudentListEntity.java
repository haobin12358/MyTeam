package com.etech.myteam.entity;

import org.json.JSONObject;

import android.util.Log;

public class StudentListEntity {
	private String id; // id -- Sid,Tid,Cid
	private String name; // 名称 -- Sname,Tname,Cname
	private String school; // 学院信息 -- Tschool,Sschool
	private String btn_name;// 区分button的显示文字
	private int level;//竞赛等级 --与CId绑定
	private int Ttime;//教师任教时间 -- 与TId绑定
	private String start_time; // Cstart, Sgrade
	private String end_time;//竞赛报名起止时间 --Cend 与Cid绑定
	private int grade; // 学生年级 Sid

	public void setStudentInfo(JSONObject jsonobj, String btnName){
		setBtn_name(btnName);
		try{
			setSchool((String) jsonobj.get("Sschool"));
			setName((String) jsonobj.get("Sname"));
			setGrade((Integer) jsonobj.get("Sgrade"));
			setId((String) jsonobj.get("Sid"));
		}catch (Exception e){
			e.printStackTrace();
			Log.e("Student list error","error");
		}		
	}
	
	public void setTeacherInfo(JSONObject jsonobj, String btnName){
		setBtn_name(btnName);
		try {
			setId(jsonobj.optString("Tid"));
			setName(jsonobj.optString("Tname"));
			setTtime(jsonobj.optInt("TTime"));			
			setSchool(jsonobj.optString("Tshool"));
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Teacher list error","error");
		}
	}
	
	public void setCompetitionInfo(JSONObject jsonobj, String btnName){
		setBtn_name(btnName);
		try {
			setId(jsonobj.optString("Cid"));
			setLevel(jsonobj.optInt("Clevel"));
			setGrade(jsonobj.optInt("Cno"));
			setStart_time(jsonobj.optString("Cstart"));
			setEnd_time(jsonobj.optString("Cend"));
			setName(jsonobj.optString("Cname"));
		} catch (Exception e) {
			Log.e("Competition list error","error");
		}
	}
	
	public String getBtn_name() {
		return btn_name;
	}
	public void setBtn_name(String btn_name) {
		this.btn_name = btn_name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getTtime() {
		return Ttime;
	}
	public void setTtime(int ttime) {
		Ttime = ttime;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
	
}

package com.etech.myteam.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.etech.myteam.R;
import com.etech.myteam.adapter.StudentAdapter;
import com.etech.myteam.common.HttpgetEntity;
import com.etech.myteam.common.HttppostEntity;
import com.etech.myteam.common.StringToJSON;
import com.etech.myteam.entity.StudentListEntity;
import com.etech.myteam.global.AppConst;

public class InforFragment extends Fragment{
	private int index = 0;
	private String Uid;
	private int Utype;
	private int infoType = 0;// 0:学生list、1:教师list、2:竞赛list
//	private Map type_map = 
	private String url = "http://"+AppConst.sServerURL ;
	private ListView list;
	private TextView tv_1,tv_2,tv_3;
	private Button btn_doit, btn_student, btn_teach, btn_com;
	private BaseAdapter adapter;	
	private int page_num = 1;
	private List<StudentListEntity> entitys = new ArrayList<StudentListEntity>();
	private Spinner spinner;
	private String name, school, start, end;
	private String result_info;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_student_info, container, false);
		
		get_info();
		while (true) {
			if (result_info != null){
				break;
			}
		} 
		
		try {
			
			init(view);
			Log.e("Info", "init over");
		} catch (JSONException e) {
			Log.e("json error", "no status");
			e.printStackTrace();
		}		
		
		adapter = new StudentAdapter(entitys, getActivity());
		Log.e("adapter over", "over");
		list.setAdapter(adapter);
		return view;
	}
	
	private void init(View view) throws JSONException{
		JSONObject jsonobj = StringToJSON.toJSONObject(result_info);
		
		tv_1 = (TextView)view.findViewById(R.id.tv_1);
		tv_2 = (TextView)view.findViewById(R.id.tv_2);
		tv_3 = (TextView)view.findViewById(R.id.tv_3);
		btn_doit = (Button)view.findViewById(R.id.btn_doit);
		spinner = (Spinner) view.findViewById(R.id.spinner1);
		list = (ListView) view.findViewById(R.id.student_list);
		List<Integer> data_list = new ArrayList<Integer>();

		if ((Integer)jsonobj.get("status") != 200){
			Log.e("status error ", "the status is not 200 ");
			return ;
		}
		int count = (Integer) jsonobj.get("count");
		for(int i=1;i <= (count/10) + 1;i++) {
			data_list.add(i);
		}
		if (entitys != null){
			entitys.clear();
		}
		
		JSONArray data;
		switch (infoType) {
		case 0:
			data = (JSONArray) jsonobj.get("student_list");	
			break;
		case 1:
			data = (JSONArray) jsonobj.get("teacher_list");
			break;
		case 2:
			data = (JSONArray) jsonobj.get("competition_list");
			break;
		default:
			data = null;
			break;
		}
		String btn_value = infoType == 2? "申请加入":"邀请";
		for (int i = 0; i <data.length(); i++){
			StudentListEntity entity = new StudentListEntity();
			JSONObject item = data.getJSONObject(i);
			
			switch (infoType) {
			case 0:
				entity.setStudentInfo(item, btn_value);
				break;
			case 1:
				entity.setTeacherInfo(item, btn_value);
				break;
			case 2:
				entity.setCompetitionInfo(item, btn_value);
				break;
			default:
				Log.e("Error","the infotype is not match");
				break;
			}
			entitys.add(entity);
		}
		ArrayAdapter<Integer> arr_adapter = new ArrayAdapter<Integer>(getActivity(),R.layout.spinner_item,data_list);
		spinner.setAdapter(arr_adapter);
	}
	
	public void get_info(){
		new Thread(){
			public void run(){
				getText();
			}
		}.start();
	}
	
	//获取从上一个界面传来的值
	private void getBd(){
		Intent intent = getActivity().getIntent();
		Bundle bd = intent.getExtras();
		
		if (bd.containsKey("index")) index = getindex(bd.getInt("index"));
		if (bd.containsKey("infoType")) infoType = getindex(bd.getInt("infoType"));
		try{
			Uid = bd.getString("Uid");
			Utype = bd.getInt("Utpe");
		}catch (Exception e){
			e.printStackTrace();
			Log.e("GET Uid and Utype error.","false");
		}
	}
	
	public int getindex(int n){
		if (n == 0 || n == 1 || n == 2) {
			return n;
		}else{
			return 0;
		}
	}
	//封装数据传输
	private void getText(){
		HttpgetEntity httpget = new HttpgetEntity();
		try {			
			String getInfoUrl = getUrl(); //获取url
			result_info = httpget.doGet(getInfoUrl);
			Log.e("result", result_info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("post", "error");
			e.printStackTrace();
		}
	}
	
	/*通过infoType 以及筛选框的内容获取url*/
	private String getUrl(){
		TypeInfo info = new TypeInfo(infoType);
		StringBuffer get_info_url = new StringBuffer();
		get_info_url.append(url).append(info.getUrlType())
		.append("?page_size=10&page_num=").append(page_num)
		.append("&Uid=").append(Uid).append("&Utype=").append(Utype);						
		if (name != null){
			get_info_url.append(info.getName()).append(name);
		}
		if (school != null){
			get_info_url.append(info.getSchool()).append(school);
		}
		if (start != null){
			get_info_url.append(info.getStart()).append(start);
		}
		if (end != null){
			get_info_url.append(info.getEnd()).append(end);
		}
		Log.e("url", get_info_url.toString());
		
		return get_info_url.toString();
	}
	
	private class TypeInfo{
		private String s_list = "/students/list";
		private String t_list = "/teachers/list";
		private String c_list = "/competitions/list";
		private String s_name = "Sname";
		private String t_name = "Tname";
		private String c_name = "Cname";
		private String s_school = "Sschool";
		private String t_school = "Tschool";
		private String c_school = "Clevel";
		private String s_start = "start";
		private String t_start = "Ttime";
		private String c_start = "Cstart";
		private String s_end = "end";
		private String c_end = "Cend";
		
		String urlType, name, school, start, end;
		
		public TypeInfo(int infotype){
			switch (infotype) {
			case 0:
				setUrlType(s_list);
				setName(s_name);
				setSchool(s_school);
				setEnd(s_end);
				setStart(s_start);
				break;
			case 1:
				setUrlType(t_list);
				setName(t_name);
				setSchool(t_school);
				setStart(t_start);
				break;
			case 2:
				setUrlType(c_list);
				setName(c_name);
				setSchool(c_school);
				setEnd(c_end);
				setStart(c_start);
				break;
			default:
				break;
			}			
		}
		
		public String getUrlType() {
			return urlType;
		}

		public void setUrlType(String urlType) {
			this.urlType = urlType;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = "&" +name+ "=";
		}

		public String getSchool() {
			return school;
		}

		public void setSchool(String school) {
			this.school = "&"+ school + "=";
		}

		public String getStart() {
			return start;
		}

		public void setStart(String start) {
			this.start = "&" + start + "=";
		}

		public String getEnd() {
			return end;
		}

		public void setEnd(String end) {
			this.end = "&" + end + "=";
		}
		
	}
	
}

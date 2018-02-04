package com.etech.myteam.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.etech.myteam.R;
import com.etech.myteam.activity.InforActivity;
import com.etech.myteam.activity.MainActivity;
import com.etech.myteam.adapter.CompetitionAdapter;
import com.etech.myteam.adapter.StudentAdapter;
import com.etech.myteam.adapter.TeacherAdapter;
import com.etech.myteam.common.HttpgetEntity;
import com.etech.myteam.common.HttppostEntity;
import com.etech.myteam.common.StringToJSON;
import com.etech.myteam.entity.StudentListEntity;
import com.etech.myteam.global.AppConst;

public class InforFragment extends Fragment{
	private int index;
	private String Uid = null;
	private int Utype;
	private int infoType;// 0:学生list、1:教师list、2:竞赛list
//	private Map type_map = 
	private String url = "http://"+AppConst.sServerURL ;
	private ListView list;	
	private EditText edName, edSchool, edStart, edEnd;
	private Button btn_doit, btn_student, btn_teach, btn_com;
	private BaseAdapter adapter;	
	private int page_num = 1;
	private List<StudentListEntity> entitys = new ArrayList<StudentListEntity>();
	private Spinner spinner;
	private String name, school, start, end, modelid;
	private String result_info, postResult;
	private List<Integer> data_list = new ArrayList<Integer>();
	private HttppostEntity postEntity;
	private ViewGroup title;
	private TextView tv_name, tv_grade, tv_school;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getBd();
		View view = inflater.inflate(R.layout.fragment_student_info, container, false);
		Log.e("Uid", Uid);
		
		get_info();
		while (true) {
			if (result_info != null){
				break;
			}
		} 
		
		init(view);
		Log.e("Info", "init over");
		
		
		return view;
	}
	
	private void init(View view){
		title = (ViewGroup) view.findViewById(R.id.include1);
		tv_name = (TextView) title.findViewById(R.id.tv_name);
		tv_grade = (TextView) title.findViewById(R.id.tv_grade);
		tv_school = (TextView) title.findViewById(R.id.tv_school);
		edName = (EditText) view.findViewById(R.id.ed_name);
		edSchool = (EditText) view.findViewById(R.id.ed_school);		
		edEnd = (EditText) view.findViewById(R.id.ed_grade_end);		
		edStart = (EditText) view.findViewById(R.id.ed_grade_start);		
		btn_student = (Button)view.findViewById(R.id.btn_1);
		btn_teach = (Button)view.findViewById(R.id.btn_2);
		btn_com = (Button)view.findViewById(R.id.btn_3);
		btn_doit = (Button)view.findViewById(R.id.btn_doit);
		spinner = (Spinner) view.findViewById(R.id.spinner1);
		list = (ListView) view.findViewById(R.id.student_list);
		list.setOnItemClickListener(doit);
		btn_doit.setText(R.string.cha_xun);
		initdata();
		btn_student.setOnClickListener(changeInfo);
		btn_teach.setOnClickListener(changeInfo);
		btn_com.setOnClickListener(changeInfo);	
	}
	
	
	//邀请、请求加入的btn的监听事件
    private OnItemClickListener doit = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (view.getId() == R.id.btn_doit){
				//拼接邀请url
				String inviteUrl = "";
				
			}else{
			//跳转
			Intent it = new Intent(getActivity(), InforActivity.class);
			modelid = entitys.get(position).getId();
			it.putExtra("Uid", Uid);
			it.putExtra("Utype", Utype);
			it.putExtra("infoType", infoType);
			it.putExtra("index", index);
			it.putExtra("id", modelid);
			startActivity(it);
			getActivity().finish();
			
			}
		}
    };
    
    //学生 教师 竞赛 监听器 
    private OnClickListener changeInfo = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			
			case R.id.btn_2:
				tv_name.setText(R.string.jiao_shi_xing_ming);
				tv_school.setText(R.string.xue_yuan);
				tv_grade.setText(R.string.nian_ji);
				infoType = 1;
				break;
			case R.id.btn_3:
				tv_name.setText(R.string.jing_sai_ming_cheng);
				tv_school.setText(R.string.jing_sai_deng_ji);
				tv_grade.setText(R.string.bao_ming_shi_jian);
				infoType = 2;
				break;
			case R.id.btn_1:
				
			default:
				tv_name.setText(R.string.xing_ming);
				tv_school.setText(R.string.xue_yuan);
				tv_grade.setText(R.string.nian_ji);
				infoType = 0;
				break;
			}
			page_num = 1;
			setData();
		}

	};
	
	public void setData(){
		page_num = 1;					
		name = edName.getText().toString();
		school = edSchool.getText().toString();
		start = edStart.getText().toString();
		end = edEnd.getText().toString();
		result_info = null;
		get_info();
		while (true){
			if (result_info != null){
				break;
			}
		}
		initdata();
	}
	
	public void initdata(){
		try{
			JSONObject jsonobj = StringToJSON.toJSONObject(result_info);
			if ((Integer)jsonobj.get("status") != 200){
				Log.e("status error ", "the status is not 200 ");
				return ;
			}
			int count = (Integer) jsonobj.get("count");
			if (data_list.size() !=0){
				data_list.clear();
			}
			for(int i=1;i <= (count/10) + 1;i++) {
				data_list.add(i);
			}
			if (entitys.size() !=0){
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
		} catch (Exception e){
			Log.e("error", "get info error");
		}
		ArrayAdapter<Integer> arr_adapter = new ArrayAdapter<Integer>(
				getActivity(),R.layout.spinner_item,data_list);

		spinner.setAdapter(arr_adapter);
		switch (infoType){
		case 0:
			adapter = new StudentAdapter(entitys, getActivity(),Utype);
			break;
		case 1:
			adapter = new TeacherAdapter(entitys, getActivity(),Utype);
			break;
		case 2:
			adapter = new CompetitionAdapter(entitys, getActivity(),Utype);
			break;
		default:
			adapter = new StudentAdapter(entitys, getActivity(),Utype);
			break;
		}
		
//		list.c
		Log.e("adapter over", "over");
		list.setAdapter(adapter);
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
		Uid = ((MainActivity)getActivity()).getUid();
		Utype = ((MainActivity)getActivity()).getUtype();
		index = ((MainActivity)getActivity()).getIndex();
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
	
	private void postText(){
		try{
			JSONObject obj = new JSONObject();
			String post_url = "";
			postResult = postEntity.doPost(obj, url);
		}catch (Exception e){
			e.printStackTrace();
			Log.e("Post error", "post method error.");
		}
	}
	/*通过infoType 以及筛选框的内容获取url*/
	private String getUrl(){		
		TypeInfo info = new TypeInfo(infoType);		
		StringBuffer get_info_url = new StringBuffer();
		get_info_url.append(url).append(info.getUrlType())
		.append("?page_size=10&page_num=").append(page_num)
		.append("&Uid=").append(Uid).append("&Utype=").append(Utype);						
		if (name != null && name.length() != 0){
			get_info_url.append(info.getName()).append(name);
		}
		if (school != null && school.length() != 0){
			get_info_url.append(info.getSchool()).append(school);
		}
		if (start != null && start.length() != 0){
			get_info_url.append(info.getStart()).append(start);
		}
		if (end != null && end.length() != 0){
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

		@Override
		public String toString() {
			return "TypeInfo [urlType=" + urlType + ", name=" + name
					+ ", school=" + school + ", start=" + start + ", end="
					+ end + "]";
		}

		
	}
	
}

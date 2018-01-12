package com.etech.myteam.fragment;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.etech.myteam.R;
import com.etech.myteam.adapter.StudentAdapter;
import com.etech.myteam.common.HttpgetEntity;
import com.etech.myteam.common.HttppostEntity;
import com.etech.myteam.entity.StudentListEntity;
import com.etech.myteam.global.AppConst;

public class InforFragment extends Fragment{
	private int index;
	private String Uid;
	private int Utype;
	private int infoType;// 0:学生list、1:教师list、2:竞赛list
//	private Map type_map = 
	private String url = "http://"+AppConst.sServerURL ;
	private String student_list = "/students/list";
	private String teacher_list = "/teachers/list";
	private String competition_list = "/competitions/list";
	private TextView tv_1,tv_2,tv_3;
	private Button btn_doit;
	private BaseAdapter adapter;	
	private int page_num = 1;
	private List<StudentListEntity> entitys;
	private Spinner spinner;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_student_info, container, false);
		init(view);
//		postText();
		
		
		adapter = new StudentAdapter(entitys, getActivity());
		return view;
	}
	
	private void init(View view){
		tv_1 = (TextView)view.findViewById(R.id.tv_1);
		tv_2 = (TextView)view.findViewById(R.id.tv_2);
		tv_3 = (TextView)view.findViewById(R.id.tv_3);
		btn_doit = (Button)view.findViewById(R.id.btn_doit);
		spinner = (Spinner) view.findViewById(R.id.spinner1);
		List<Integer> data_list = new ArrayList<Integer>();
		for(int i=1;i<10;i++) {
			data_list.add(i);
		}
		
		ArrayAdapter<Integer> arr_adapter = new ArrayAdapter<Integer>(getActivity(),R.layout.spinner_item,data_list);
		spinner.setAdapter(arr_adapter);
		infoType = 0;
	}
	
	//获取从上一个界面传来的值
	private void getBd(){
		Intent intent = getActivity().getIntent();
		try{
			Bundle bd = intent.getExtras();
			int n = bd.getInt("index");
			if (n == 0 || n == 1 || n == 2) {
				index = n;
			}
			Uid = bd.getString("Uid");
			Utype = bd.getInt("Utpe");
		}catch (Exception e) {
			e.printStackTrace();
			Log.e("changeError", "false");
			index = 0;
		}
	}
	
	//封装数据传输
	private void postText(){
		JSONObject obj = new JSONObject();
		try {
			
			obj.put("page_num", page_num);
			obj.put("page_size", 10);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
////		Log.e("url", url+);
//		HttpgetEntity httppost = new HttpgetEntity();
//		try {
//			result_login = httppost.doget(obj, login_url);
//			Log.e("result", result_login);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			Log.e("post", "error");
//			e.printStackTrace();
//		}
//		return null;
	}
}

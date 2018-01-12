package com.etech.myteam.fragment;

import com.etech.myteam.global.AppConst;

import com.etech.myteam.R;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InforFragment extends Fragment{
	private int index;
	private String Uid;
	private int Utype;
//	private Map type_map = 
	private String url = "http://"+AppConst.sServerURL ;
	private String student_list = "/students/list";
	private String teacher_list = "/teachers/list";
	private String competition_list = "/competitions/list";
			
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_info, container, false);
		return view;
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
}

package com.etech.myteam.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.etech.myteam.R;
import com.etech.myteam.common.HttppostEntity;
import com.etech.myteam.global.AppConst;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RegisterActivity extends Activity{
	
	private String register_url = "http://" 
			+ AppConst.sServerURL 
			+ "/users/register";
	
	private TextView tv1, tv2;
	private EditText et1, et2;
	private RadioGroup rg;
	private RadioButton rb1,rb2;
	private Button btn1;
	
	private String Uname, Upwd;
	private int Utype;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		init();
		
		btn1.setOnClickListener(register_button);
	}
	
	//注册所有组件
	private void init(){
		tv1 = (TextView)findViewById(R.id.tv_1);
		tv2 = (TextView)findViewById(R.id.tv_2);
		et1 = (EditText)findViewById(R.id.et_1);
		et2 = (EditText)findViewById(R.id.et_2);
		rg = (RadioGroup)findViewById(R.id.radioGroup);
		rb1 = (RadioButton)findViewById(R.id.rb_1);
		rb2 = (RadioButton)findViewById(R.id.rb_2);
		btn1 = (Button)findViewById(R.id.btn_1);
	}
	
	//获取填写的内容
	private boolean getText(){
		Uname = et1.getText().toString();
		Log.e("Uname", Uname);
		if(Uname.length() == 0){
			new AlertDialog.Builder(this).setTitle(R.string.ti_xing).setMessage(R.string.qing_shu_ru_yong_hu_ming).show();
			return false;
		}
		Upwd = et2.getText().toString();
		Log.e("Upwd", Upwd);
		if(Upwd.length() == 0){
			new AlertDialog.Builder(this).setTitle(R.string.ti_xing).setMessage(R.string.qing_shu_ru_mi_ma).show();
			return false;
		}
		int Utype;
		if(rb1.isChecked()){
			Utype = 100;
		}else if(rb2.isChecked()){
			Utype = 101;
		}else{
			new AlertDialog.Builder(this).setTitle(R.string.ti_xing).setMessage(R.string.qing_xuan_ze_lei_xing).show();
			return false;
		}
		return true;
	}
	
	private String result_register;
	//封装数据传输
	private void postText(){
		JSONObject obj = new JSONObject();
		try {
			obj.put("Uname",Uname);
			obj.put("Upwd", Upwd);
			obj.put("Utype", Utype);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Log.e("url",register_url);
		HttppostEntity httppost = new HttppostEntity();
		try {
			result_register = httppost.doPost(obj, register_url);
			Log.e("result_register",result_register);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("post", "error");
			e.printStackTrace();
		}
	}
	
	private OnClickListener register_button = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(getText()){
				new Thread(){
					public void run(){
						postText();
					}
				}.start();
			}
			Log.e("register","ok");
		}
		
	};

}

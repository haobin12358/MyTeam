package com.etech.myteam.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.etech.myteam.R;
import com.etech.myteam.common.HttppostEntity;
import com.etech.myteam.common.StringToJSON;
import com.etech.myteam.global.AppConst;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity{
	
	private String register_url = "http://" 
			+ AppConst.sServerURL 
			+ "/users/register";
	
	private TextView tv1, tv2;
	private EditText et1, et2;
	private RadioGroup rg;
	private RadioButton rb1,rb2;
	private Button btn1, btn2;
	
	private String Uname, Upwd;
	private int Utype;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		init();
		setListener();
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
		btn2 = (Button)findViewById(R.id.btn_2);
	}
	
	//设置响应事件
	private void setListener(){
		btn1.setOnClickListener(register_button);
		btn2.setOnClickListener(back);
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
	
	//设置获取结果变量
	private String result_register;
	//封装数据传输
	private void postText(){
		JSONObject obj = new JSONObject();
		try {
			obj.put("Uname",Uname);
			obj.put("Upwd", Upwd);
			obj.put("Utype", Utype);
			Log.e("Utype", Integer.toString(Utype));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Log.e("url",register_url);
		HttppostEntity httppost = new HttppostEntity();
		try {
			result_register = httppost.doPost(obj, register_url);
			Log.e("result",result_register);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("post", "error");
			e.printStackTrace();
		}
	}
	
	//注册按键对应的响应内容
	private OnClickListener register_button = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(getText()){
				getText();
				new Thread(){
					public void run(){
						postText();
					}
				}.start();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(result_register == null){
					new AlertDialog.Builder(RegisterActivity.this)
						.setTitle(R.string.ti_xing)
						.setMessage(R.string.xi_tong_yi_chang)
						.show();
				}else{
					JSONObject json_obj = StringToJSON.toJSONObject(result_register);
					if(json_obj.optInt("status") == 200){
						Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT);
						Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
						startActivity(intent);
						finish();
					}else if(json_obj.optInt("status") == 404){
						new AlertDialog.Builder(RegisterActivity.this)
							.setTitle(R.string.ti_xing)
							.setMessage(R.string.xi_tong_yi_chang)
							.show();
					}else if(json_obj.optInt("status") == 405){
						//根据不同的返回码提示不同内容
						if(json_obj.optInt("status_code") == 405100){
							//用户名重复
							new AlertDialog.Builder(RegisterActivity.this)
								.setTitle(R.string.ti_xing)
								.setMessage(R.string.yong_hu_ming_chong_fu)
								.show();
						}else if(json_obj.optInt("status_code") == 405101){
							//密码不合法
							new AlertDialog.Builder(RegisterActivity.this)
								.setTitle(R.string.ti_xing)
								.setMessage(R.string.mi_ma_bu_he_fa)
								.show();
						}else if(json_obj.optInt("status_code") == 405102){
							//用户名不合法
							new AlertDialog.Builder(RegisterActivity.this)
								.setTitle(R.string.ti_xing)
								.setMessage(R.string.yong_hu_ming_bu_he_fa)
								.show();
						}
					}else{
						new AlertDialog.Builder(RegisterActivity.this)
							.setTitle(R.string.ti_xing)
							.setMessage(R.string.wei_zhi_cuo_wu)
							.show();
					}
					Log.e("register","ok");
				}
				
			}
		}
	};

	private OnClickListener back = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
		
	};

}

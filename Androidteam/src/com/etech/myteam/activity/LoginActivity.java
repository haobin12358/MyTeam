package com.etech.myteam.activity;

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
import android.widget.TextView;


public class LoginActivity extends Activity{
	
	//定义调用的url
	private String login_url = "http://" 
			+ AppConst.sServerURL 
			+ "/users/login";
	
	//定义组件变量
	private TextView tv1, tv2;
	private EditText et1, et2;
	private Button btn1, btn2;
	
	//定义数据变量
	private String Uname;
	private String Upwd;
	
	//主线程+主要生命周期
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
		setListener();
	}
	
	//注册组件
	private void init(){
		tv1 = (TextView)findViewById(R.id.tv_1);
		tv2 = (TextView)findViewById(R.id.tv_2);
		et1 = (EditText)findViewById(R.id.et_1);
		et2 = (EditText)findViewById(R.id.et_2);
		btn1 = (Button)findViewById(R.id.btn_1);
		btn2 = (Button)findViewById(R.id.btn_2);
	}
	
	//设置响应事件
	private void setListener(){
		btn1.setOnClickListener(register_button);
		btn2.setOnClickListener(login_button);
	}
	
	//注册按键响应事件
	private OnClickListener register_button = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			finish();
		}
		
	};
	
	private String Uid;
	
	//登录按键响应事件
	private OnClickListener login_button = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(getText()){
				new Thread(){
					public void run(){
						postText();
					}
				}.start();
				JSONObject json_obj = StringToJSON.toJSONObject(result_login);
				if(json_obj.optInt("status") == 200){
					String messages = json_obj.optString("messages");
					JSONObject json_messages = StringToJSON.toJSONObject(messages);
					Uid = json_messages.optString("Uid");
					new AlertDialog.Builder(LoginActivity.this)
						.setTitle(R.string.ti_xing)
						.setMessage(R.string.deng_lu_cheng_gong)
						.setPositiveButton(R.string.que_ding, (android.content.DialogInterface.OnClickListener) positive)
						.show();
				}else if(json_obj.optInt("status") == 405){
					if(json_obj.optInt("status_code") == 405103){
						new AlertDialog.Builder(LoginActivity.this)
							.setTitle(R.string.ti_xing)
							.setMessage(R.string.yong_hu_bu_cun_zai)
							.show();
					}
					else if(json_obj.optInt("status_code") == 405104){
						new AlertDialog.Builder(LoginActivity.this)
							.setTitle(R.string.ti_xing)
							.setMessage(R.string.mi_ma_cuo_wu)
							.show();
					}
				}else if(json_obj.optInt("status") == 404){
					new AlertDialog.Builder(LoginActivity.this)
						.setTitle(R.string.ti_xing)
						.setMessage(R.string.xi_tong_yi_chang)
						.show();
				}else{
					new AlertDialog.Builder(LoginActivity.this)
						.setTitle(R.string.ti_xing)
						.setMessage(R.string.wei_zhi_cuo_wu)
						.show();
				}
				Log.e("register","ok");
			}
		}
		
	};
	
	//设置弹出框后的处理方式
	private OnClickListener positive = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			intent.putExtra("Uid", Uid);
			intent.putExtra("index", 0);
			startActivity(intent);
			finish();
		}
			
	};
	
	//获取填写内容
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
		return true;
	}
	
	//设置结果变量
	private String result_login;
	//封装数据传输
	private void postText(){
		JSONObject obj = new JSONObject();
		try {
			obj.put("Uname", Uname);
			obj.put("Upwd", Upwd);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("url", login_url);
		HttppostEntity httppost = new HttppostEntity();
		try {
			result_login = httppost.doPost(obj, login_url);
			Log.e("result", result_login);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("post", "error");
			e.printStackTrace();
		}
	}
}
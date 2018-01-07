package com.etech.myteam.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.etech.myteam.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends Activity{
	private Button btn;
	private EditText ed_1,ed_2;
	private String pwd="";
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
//		初始化界面
		setContentView(R.layout.login_activity);
		init();

	}

	protected void init(){
        //注册控件
        this.btn = (Button) findViewById(R.id.btn_1);
        this.ed_1 = (EditText)findViewById(R.id.ed_1);
        this.ed_2 = (EditText)findViewById(R.id.ed_2);
        this.btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                String url ="http://"
                        +AppConst.sServerURL
                        +"/Glucky/userByName.ll?uname=";
                String id = ed_1.getText().toString();

                onGet(url+id);
                try {
                    Thread.sleep(3000);

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String password = null ;

                try {
                    Log.e("Pwd",pwd);
                    JSONArray ja = StringToJSON.toJSONArray(pwd);
                    JSONObject jo=ja.getJSONObject(0);
                    password= jo.optString("UserPassword");

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                String ed2 = ed_2.getText().toString();
                if(ed2.equals(password)){

                    startActivity(intent);
                    finish();
                }else {
                    new AlertDialog.Builder(LoginActivity.this).setTitle("提醒").setMessage("账户名或密码错误").show();
                }

            }
        });
    }
}
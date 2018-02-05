package com.etech.myteam.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.etech.myteam.R;
import com.etech.myteam.adapter.MyInfoAdapter;
import com.etech.myteam.common.HttpgetEntity;
import com.etech.myteam.common.HttppostEntity;
import com.etech.myteam.common.NumToString;
import com.etech.myteam.common.StringToJSON;
import com.etech.myteam.entity.MyInfoEntity;
import com.etech.myteam.global.AppConst;

public class MyInfoActivity extends Activity{
	
	private String Uid;
	private int Utype, index;
	private ListView lst;
	private ViewGroup vg;
	private TextView tvbutton, tvtitle;
	private ImageView iv1;
	private MyInfoAdapter adapter_myinfo;
	private List<MyInfoEntity> entitys_myinfo = new ArrayList<MyInfoEntity>();

	private HttpgetEntity getEntity;
	private HttppostEntity postEntity;
	
	private String get_myinfo_url = "http://" 
			+ AppConst.sServerURL 
			+ "/info/allinfo?Uid=";
	private String sub_student_url = "http://" 
			+ AppConst.sServerURL 
			+ "/team/substudent?Uid=";
	private String sub_teacher_url = "http://" 
			+ AppConst.sServerURL 
			+ "/team/subteacher?Uid=";
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getBd();
		setContentView(R.layout.activity_myinfo);
		getMyInfo();
		while(true){
			if(myinfo != null){
				break;
			}
		}
		
		
	}
	
	private void getBd(){
		Intent intent = getIntent();
		try{
			Bundle bd = intent.getExtras();
			int n = bd.getInt("index");
			if (n == 0 || n == 1 || n == 2) {
				index = n;
			}
			Uid = bd.getString("Uid");
			Utype = bd.getInt("Utype");
		}catch (Exception e) {
			e.printStackTrace();
			Log.e("changeError", "false");
			index = 2;
		}
	}
	
	private void init(){
		vg = (ViewGroup)findViewById(R.id.tv_top);
		
		tvbutton = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_editbutton);
		tvbutton.setVisibility(View.GONE);
		tvtitle = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_title);
		tvtitle.setText("我的信息");
		iv1 = (ImageView)findViewById(R.id.tv_top).findViewById(R.id.iv_1);
		iv1.setOnClickListener(back);
		
		lst = (ListView)findViewById(R.id.lst);
		adapter_myinfo = new MyInfoAdapter(this, entitys_myinfo);
		lst.setAdapter(adapter_myinfo);
		lst.setOnItemClickListener(sub);
		
		setText(myinfo);
	}
	
	private String myinfo;
	private void getMyInfo(){
		getEntity = new HttpgetEntity();
		try {
			myinfo = getEntity.doGet(get_myinfo_url + Uid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private OnClickListener back = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MyInfoActivity.this, MainActivity.class);
			intent.putExtra("Uid", Uid);
			intent.putExtra("Utype", Utype);
			intent.putExtra("index", index);
			startActivity(intent);
			finish();
		}
		
	};
	
	private void setText(String myInfo){
		if(myInfo == "[]"){
			
		}else{
			final JSONArray json_info = StringToJSON.toJSONArray(myInfo);
			entitys_myinfo.clear();
			for(int i = 0; i < json_info.length(); i++){
				try {
					JSONObject info_item = json_info.getJSONObject(i);
					MyInfoEntity entity_myinfo = new MyInfoEntity();
					entity_myinfo.setPmessage(info_item.optString("Pmessage"));
					entity_myinfo.setSname(info_item.optString("Sname"));
					entity_myinfo.setCname("第" + info_item.optInt("Cno") + "届" 
							+ NumToString.getCLevel(info_item.optInt("Clevel")) 
							+ info_item.optString("Cname"));
					entity_myinfo.setTEname(info_item.optString("TEname"));
					entitys_myinfo.add(entity_myinfo);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private OnItemClickListener sub = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			final JSONArray json_info = StringToJSON.toJSONArray(myinfo);
			JSONObject info_item;
			try {
				info_item = json_info.getJSONObject(position);
				if(info_item.optInt("Ptype") == 905 || info_item.optInt("Ptype") == 901){
					showDialog("审批信息", info_item.optString("Pid"));
				}else{
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	};
	
	private void showDialog(String title, final String Pid){
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoActivity.this);
		builder.setTitle(title);
		builder.setMessage("是否同意");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				final int sub_index = 1101;
				postEntity = new HttppostEntity();
				final JSONObject post_obj = new JSONObject();
				try {
					post_obj.put("Pid", Pid);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Thread(){
					public void run(){
						if(Utype == 100){
							String post_url = sub_student_url + Uid + "&sub_index=" + sub_index;
							Log.e("Url", post_url);
							try {
								String response = postEntity.doPost(post_obj, post_url);
								Log.e("response", response);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else if(Utype == 101){
							String post_url = sub_teacher_url + Uid + "&sub_index=" + sub_index;
							Log.e("Url", post_url);
							try {
								String response = postEntity.doPost(post_obj, post_url);
								Log.e("response", response);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}.start();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				final int sub_index = 1102;
				postEntity = new HttppostEntity();
				final JSONObject post_obj = new JSONObject();
				try {
					post_obj.put("Pid", Pid);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Thread(){
					public void run(){
						if(Utype == 100){
							String post_url = sub_student_url + Uid + "&sub_index=" + sub_index;
							Log.e("Url", post_url);
							try {
								String response = postEntity.doPost(post_obj, post_url);
								Log.e("response", response);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else if(Utype == 101){
							String post_url = sub_teacher_url + Uid + "&sub_index=" + sub_index;
							Log.e("Url", post_url);
							try {
								String response = postEntity.doPost(post_obj, post_url);
								Log.e("response", response);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}.start();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		builder.show();
	}
}

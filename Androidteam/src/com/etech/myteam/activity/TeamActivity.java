package com.etech.myteam.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.etech.myteam.R;
import com.etech.myteam.adapter.TStudentAdapter;
import com.etech.myteam.common.HttpgetEntity;
import com.etech.myteam.common.HttppostEntity;
import com.etech.myteam.common.NumToString;
import com.etech.myteam.common.StringToJSON;
import com.etech.myteam.common.isEdit;
import com.etech.myteam.entity.TStudentEntity;
import com.etech.myteam.global.AppConst;
import com.etech.myteam.view.MyListView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TeamActivity extends Activity{
	
	private TextView tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tvbutton, tvtitle;
	private EditText tv1, tv2, tv3, tv4, et1, et2, et3;
	private ViewGroup vg;
	private MyListView lst_1;
	private ImageView iv1;
	
	private String TEid;
	private String Uid;
	private int index;
	private int Utype;
	
	private HttpgetEntity getEntity;
	private HttppostEntity postEntity;
	
	private String get_team_abo = "http://" 
			+ AppConst.sServerURL 
			+ "/team/teamabo?TEid="; 
	private String invate_add_team = "http://" 
			+ AppConst.sServerURL 
			+ "/team/invatestudent?Uid=";
	private String update_team = "http://" 
			+ AppConst.sServerURL 
			+ "/team/updateteam?Uid=";
	
	private TStudentAdapter ts_adapter;
	private List<TStudentEntity> ts_entitys = new ArrayList<TStudentEntity>();
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getBd();
		new Thread(){
			public void run(){
				getTeamabo();
			}
		}.start();
		while(true){
			if(team_abo != null){
				break;
			}
		}
		setContentView(R.layout.activity_team_abo);
		init();
	}
	
	private void init(){
		vg = (ViewGroup)findViewById(R.id.tv_top);
		
		tv5 = (TextView)findViewById(R.id.tv_5);
		tv5.setText("创建者：");
		tv6 = (TextView)findViewById(R.id.tv_6);
		tv6.setText("指导教师：");
		tv7 = (TextView)findViewById(R.id.tv_7);
		tv7.setText("团队限制人数：");
		tv8 = (TextView)findViewById(R.id.tv_8);
		tv8.setText("团队成员列表");
		tv9 = (TextView)findViewById(R.id.tv_9);
		tv9.setText("等待人数：");
		tv10 = (TextView)findViewById(R.id.tv_10);
		tv11 = (TextView)findViewById(R.id.tv_11);
		tv11.setText("被拒人数：");
		tv12 = (TextView)findViewById(R.id.tv_12);
		tvbutton = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_editbutton);
		tvtitle = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_title);
		tvtitle.setText(R.string.tuan_dui_xiang_qing);
		
		iv1 = (ImageView)findViewById(R.id.iv_1);
		iv1.setOnClickListener(backto);
		
		tv1 = (EditText)findViewById(R.id.tv_1);
		tv2 = (EditText)findViewById(R.id.tv_2);
		tv3 = (EditText)findViewById(R.id.tv_3);
		tv4 = (EditText)findViewById(R.id.tv_4);
		et1 = (EditText)findViewById(R.id.et_1);
		et2 = (EditText)findViewById(R.id.et_2);
		et3 = (EditText)findViewById(R.id.et_3);
		
		isEdit.notEdit(et1);
		isEdit.notEdit(et2);
		isEdit.notEdit(et3);
		isEdit.notEdit(tv1);
		isEdit.notEdit(tv2);
		isEdit.notEdit(tv3);
		isEdit.notEdit(tv4);
		
		
		lst_1 = (MyListView)findViewById(R.id.lst);
		ts_adapter = new TStudentAdapter(ts_entitys, TeamActivity.this);
		lst_1.setAdapter(ts_adapter);
		Log.e("Utype", Integer.toString(Utype));
		Log.e("index", Integer.toString(index));
		if(index == 1 && Utype == 100){
			tvbutton.setText("申请加入");
		}else if(index == 2){
			if(Utype == 100){
				tvbutton.setText("编辑");
			}else if(Utype == 101){
				tvbutton.setText("发布任务");
			}else{
				tvbutton.setVisibility(View.GONE);
			}
		}
		tvbutton.setOnClickListener(editTeam);
		setText(team_abo);
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
			TEid = bd.getString("TEid");
		}catch (Exception e) {
			e.printStackTrace();
			Log.e("changeError", "false");
			index = 1;
		}
	}
	
	//获取团队详情
	private String team_abo = null;
	private void getTeamabo(){
		getEntity = new HttpgetEntity();
		try {
			String get_teamabo_url = get_team_abo + TEid + "&Uid=" + Uid;
			team_abo = getEntity.doGet(get_teamabo_url);
			Log.e("team_abo", team_abo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("getJSON", "error");
			e.printStackTrace();
		}
	}
	
	//放置获取数据
	private void setText(String team_abo){
		if(team_abo == null){
			Toast.makeText(TeamActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
		}else{
			final JSONObject json_obj = StringToJSON.toJSONObject(team_abo);
			if(json_obj.optInt("status") == 200){
				new Thread(){
					public void run(){
						String abo = json_obj.optString("team_abo");
						JSONObject team_json = StringToJSON.toJSONObject(abo);
						tv1.setText(team_json.optString("TEname"));
						tv2.setText("第"+team_json.optInt("Cno")+"届");
						tv3.setText(NumToString.getCLevel(team_json.optInt("Clevel")));
						tv4.setText(team_json.optString("Cname"));
						et1.setText(team_json.optString("TEleader"));
						et2.setText(team_json.optString("Teachername"));
						et3.setText(team_json.optInt("TEnum")+" ");
						tv10.setText(team_json.optInt("Wait")+"人");
						tv12.setText(team_json.optInt("Refuse")+"人");
						String student_list = team_json.optString("student_list");
						if(student_list == "[]"){
							TStudentEntity ts_entity = new TStudentEntity();
							ts_entity.setSname("无");
							ts_entity.setTStype("");
						}else{
							try{
								JSONArray jsonArray = StringToJSON.toJSONArray(student_list);
								ts_entitys.clear();
								for(int i = 0; i < jsonArray.length(); i++){
									JSONObject st_list = jsonArray.getJSONObject(i);
									TStudentEntity ts_entity = new TStudentEntity();
									ts_entity.setSname(st_list.optString("Sname"));
									ts_entity.setTStype(NumToString.getTStype(st_list.optInt("TStype")));
									ts_entitys.add(ts_entity);
								}
							}catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				}.start();
			}else if(json_obj.optInt("status") == 404){
				Toast.makeText(TeamActivity.this, R.string.xi_tong_yi_chang, Toast.LENGTH_SHORT).show();
			}else if(json_obj.optInt("status") == 405){
				
			}else{
				Toast.makeText(TeamActivity.this, R.string.wei_zhi_cuo_wu, Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private OnClickListener backto = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(TeamActivity.this, MainActivity.class);
			intent.putExtra("index", 1);
			intent.putExtra("Uid", Uid);
			intent.putExtra("Utype", Utype);
			startActivity(intent);
			finish();
		}
		
	};

	private OnClickListener editTeam = new OnClickListener(){

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			if(tvbutton.getText().toString().equals(getText(R.string.shen_qing_jia_ru).toString())){
				final JSONObject invate_team = new JSONObject();
				try {
					invate_team.put("TEid", TEid);
					Log.e("TEid", TEid);
					Log.e("Uid", Uid);
					new Thread(){
						public void run(){
							try {
								String response = postEntity.doPost(invate_team, invate_add_team + Uid);
								Log.e("invate_url", invate_add_team + Uid);
								while(true){
									if(response != null){
										break;
									}
								}
								Log.e("response", response);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								Log.e("error", "1");
								e.printStackTrace();
							}
						}
					}.start();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.e("error", "2");
					e.printStackTrace();
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new AlertDialog.Builder(TeamActivity.this)
					.setTitle(R.string.ti_xing)
					.setMessage("申请成功")
					.show();
			}else if(tvbutton.getText().toString().equals(getText(R.string.bian_ji).toString())){
				isEdit.yesEdit(et3);//团队限制人数
				isEdit.yesEdit(tv1);//团队名称
				tvbutton.setText(R.string.que_ding);
			}else if(tvbutton.getText().toString().equals("发布任务")){
				new AlertDialog.Builder(TeamActivity.this)
					.setTitle(R.string.ti_xing)
					.setMessage("功能开发中")
					.show();
			}else if(tvbutton.getText().toString().equals(getText(R.string.que_ding).toString())){
				final JSONObject json_obj = StringToJSON.toJSONObject(team_abo);
				String abo = json_obj.optString("team_abo");
				JSONObject team_json = StringToJSON.toJSONObject(abo);
				String Cname = team_json.optString("Cname");
				int Clevel = team_json.optInt("Clevel");
				int Cno = team_json.optInt("Cno");
				String TEname = tv1.getText().toString();
				String TEid = team_json.optString("TEid");
				int TEnum;
				if(et3.getText().toString() == ""){
					TEnum = 1;
				}else{
					TEnum = Integer.parseInt(et3.getText().toString());
				}
				final JSONObject post_team = new JSONObject();
				try {
					post_team.put("TEid", TEid);
					post_team.put("TEname", TEname);
					post_team.put("Cname", Cname);
					post_team.put("Cno", Cno);
					post_team.put("Clevel", Clevel);
					post_team.put("TEnum", TEnum);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Thread(){
					public void run(){
						String post_url = update_team + Uid;
						Log.e("post_url", post_url);
						try {
							String response = postEntity.doPost(post_team, post_url);
							while(true){
								if(response != null){
									break;
								}
							}
							Log.e("response", response);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new AlertDialog.Builder(TeamActivity.this)
					.setTitle(R.string.ti_xing)
					.setMessage("修改成功")
					.show();
			}
		}
		
	};
}

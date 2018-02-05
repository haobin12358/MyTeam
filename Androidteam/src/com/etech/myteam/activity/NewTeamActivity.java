package com.etech.myteam.activity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etech.myteam.R;
import com.etech.myteam.common.HttppostEntity;
import com.etech.myteam.common.NumToString;
import com.etech.myteam.common.StringToJSON;
import com.etech.myteam.common.isEdit;
import com.etech.myteam.global.AppConst;
import com.etech.myteam.view.MyListView;

public class NewTeamActivity extends Activity{
	
	private String Uid, Cname;
	private int Utype, index, Cno, Clevel; 
	
	private TextView tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tv13, tvbutton, tvtitle;
	private EditText tv1, tv2, tv3, tv4, et1, et2, et3;
	private ViewGroup vg;
	private MyListView lst_1;
	private ImageView iv1;
	private LinearLayout addstudentview, addteacherview;
	
	private HttppostEntity postEntity;
	
	private String new_team_url = "http://" 
			+ AppConst.sServerURL 
			+ "/team/newteam?Uid=";

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getBd();
		setContentView(R.layout.activity_team_abo);
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
			Cname = bd.getString("Cname");
			Cno = bd.getInt("Cno");
			Clevel = bd.getInt("Clevel");
		}catch (Exception e) {
			e.printStackTrace();
			Log.e("changeError", "false");
			index = 0;
		}
	}
	
	private void init(){
		vg = (ViewGroup)findViewById(R.id.tv_top);
		
		tv5 = (TextView)findViewById(R.id.tv_5);
		tv5.setVisibility(View.GONE);
		tv6 = (TextView)findViewById(R.id.tv_6);
		tv6.setVisibility(View.GONE);
		tv7 = (TextView)findViewById(R.id.tv_7);
		tv7.setText("团队限制人数：");
		tv8 = (TextView)findViewById(R.id.tv_8);
		tv8.setText("邀请学生");
		tv9 = (TextView)findViewById(R.id.tv_9);
		tv9.setVisibility(View.GONE);
		tv10 = (TextView)findViewById(R.id.tv_10);
		tv10.setVisibility(View.GONE);
		tv11 = (TextView)findViewById(R.id.tv_11);
		tv11.setVisibility(View.GONE);
		tv12 = (TextView)findViewById(R.id.tv_12);
		tv12.setVisibility(View.GONE);
		tv13 = (TextView)findViewById(R.id.tv_13);
		tv13.setText("邀请教师");
		tvbutton = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_editbutton);
		tvbutton.setText(R.string.que_ding);
		tvbutton.setOnClickListener(new_team);
		tvtitle = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_title);
		tvtitle.setText("新建团队");
		addstudentview = (LinearLayout)findViewById(R.id.addview);
		addteacherview = (LinearLayout)findViewById(R.id.addview2);
		
		iv1 = (ImageView)findViewById(R.id.iv_1);
		//iv1.setOnClickListener(backto);
		
		tv1 = (EditText)findViewById(R.id.tv_1);
		isEdit.yesEdit(tv1);
		tv2 = (EditText)findViewById(R.id.tv_2);
		tv2.setText("第" + Cno + "届");
		tv3 = (EditText)findViewById(R.id.tv_3);
		tv3.setText(NumToString.getCLevel(Clevel));
		tv4 = (EditText)findViewById(R.id.tv_4);
		tv4.setText(Cname);
		isEdit.notEdit(tv2);
		isEdit.notEdit(tv3);
		isEdit.notEdit(tv4);
		et1 = (EditText)findViewById(R.id.et_1);
		et1.setVisibility(View.GONE);
		et2 = (EditText)findViewById(R.id.et_2);
		et2.setVisibility(View.GONE);
		et3 = (EditText)findViewById(R.id.et_3);
		
		tv8.setOnClickListener(add_student);
		tv13.setOnClickListener(add_teacher);
	}
	
	private OnClickListener add_student = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			showDialog("添加学生", 0);
		}
		
	};
	private JSONArray student_array;
	private JSONArray teacher_array;
	private void showDialog(String title, final int add_index){
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(NewTeamActivity.this);
		final LayoutInflater inflater = LayoutInflater.from(NewTeamActivity.this);
		final View view = inflater.inflate(R.layout.layout_alert, null);
		builder.setTitle(title);
		final EditText et_name = (EditText)view.findViewById(R.id.alert_1);
		final EditText et_no = (EditText)view.findViewById(R.id.alert_2);
		final EditText et_university = (EditText)view.findViewById(R.id.alert_3);
		
		if(add_index == 0){
			et_name.setHint("请输入邀请的学生姓名");
			et_no.setHint("请输入邀请的学生学号");
			et_university.setHint("请输入邀请的学生所在学校");
		}else if(add_index == 1){
			et_name.setHint("请输入邀请的教师姓名");
			et_no.setHint("请输入邀请的教师工号");
			et_university.setHint("请输入邀请的教师任教学校");
		}
		builder.setView(view);
		builder.setPositiveButton("添加", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String name = et_name.getText().toString();
				String no = et_no.getText().toString();
				String university = et_university.getText().toString();
				TextView tv_name = new TextView(NewTeamActivity.this);
				TextView tv_no = new TextView(NewTeamActivity.this);
				TextView tv_university = new TextView(NewTeamActivity.this);
				if(add_index == 0){
					if(tv_name.getText().length() == 0){
						tv_name.setText(name);
						tv_no.setText(no);
						tv_university.setText(university);
						addstudentview.addView(tv_name);
						addstudentview.addView(tv_no);
						addstudentview.addView(tv_university);
						JSONObject student_obj = new JSONObject();
						try {
							student_obj.put("Sno", no);
							student_obj.put("Suniversity", university);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						student_array.put(student_obj);
					}else{
						String old_tv_name = tv_name.getText().toString();
						String old_tv_no = tv_no.getText().toString();
						String old_tv_university = tv_university.getText().toString();
						old_tv_name  = old_tv_name + "\n" + name;
						old_tv_no = old_tv_no + "\n" + no;
						old_tv_university = old_tv_university + "\n" + university;
						addstudentview.removeView(tv_name);
						addstudentview.removeView(tv_no);
						addstudentview.removeView(tv_university);
						tv_name.setText(old_tv_name);
						tv_no.setText(old_tv_no);
						tv_university.setText(old_tv_university);
						addstudentview.addView(tv_name);
						addstudentview.addView(tv_no);
						addstudentview.addView(tv_university);
						JSONObject student_obj = new JSONObject();
						try {
							student_obj.put("Sno", no);
							student_obj.put("Suniversity", university);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						student_array.put(student_obj);
					}
				}else if(add_index == 1){
					if(tv_name.getText().length() == 0){
						tv_name.setText(name);
						tv_no.setText(no);
						tv_university.setText(university);
						addteacherview.addView(tv_name);
						addteacherview.addView(tv_no);
						addteacherview.addView(tv_university);
						JSONObject student_obj = new JSONObject();
						try {
							student_obj.put("Tno", no);
							student_obj.put("Tuniversity", university);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						teacher_array.put(student_obj);
					}else{
						String old_tv_name = tv_name.getText().toString();
						String old_tv_no = tv_no.getText().toString();
						String old_tv_university = tv_university.getText().toString();
						old_tv_name  = old_tv_name + "\n" + name;
						old_tv_no = old_tv_no + "\n" + no;
						old_tv_university = old_tv_university + "\n" + university;
						addteacherview.removeView(tv_name);
						addteacherview.removeView(tv_no);
						addteacherview.removeView(tv_university);
						tv_name.setText(old_tv_name);
						tv_no.setText(old_tv_no);
						tv_university.setText(old_tv_university);
						addteacherview.addView(tv_name);
						addteacherview.addView(tv_no);
						addteacherview.addView(tv_university);
						JSONObject student_obj = new JSONObject();
						try {
							student_obj.put("Tno", no);
							student_obj.put("Tuniversity", university);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						teacher_array.put(student_obj);
					}
				}
			}
		});
		builder.show();
	}
	
	private OnClickListener add_teacher = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			showDialog("添加教师", 1);
		}
		
	};
	
	private OnClickListener new_team = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(getText()){
				new Thread(){
					public void run(){
						postEntity = new HttppostEntity();
						try {
							Log.e("Url", new_team_url + Uid);
							String response = postEntity.doPost(post_obj, new_team_url + Uid);
							Log.e("response", response);
							JSONObject json_response = StringToJSON.toJSONObject(response);
							if(json_response.optInt("status") == 200){
								Toast.makeText(NewTeamActivity.this, "创建成功", Toast.LENGTH_SHORT);
								Intent intent = new Intent(NewTeamActivity.this, MainActivity.class);
								intent.putExtra("Uid", Uid);
								intent.putExtra("Utype", Utype);
								intent.putExtra("index", index);
								startActivity(intent);
								finish();
							}else{
								new AlertDialog.Builder(NewTeamActivity.this)
									.setTitle(R.string.ti_xing)
									.setMessage(json_response.optString("messages"))
									.show();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
			}else{
				new AlertDialog.Builder(NewTeamActivity.this)
					.setTitle(R.string.ti_xing)
					.setMessage("请填写必填信息")
					.show();
			}
		}
		
	};
	
	private JSONObject post_obj;
	private boolean getText(){
		if(tv1.getText().toString().length() <= 0 || et3.getText().toString().length() <= 0 || 
				Cno == 0 || Clevel == 0 || Cname == null){
			return false;
		}
		try {
			post_obj.put("TEname", tv1.getText().toString());
			post_obj.put("Cname", Cname);
			post_obj.put("Cno", Cno);
			post_obj.put("Clevel", Clevel);
			post_obj.put("TEnum", Integer.parseInt(et3.getText().toString()));
			if(student_array.length() > 0){
				post_obj.put("Students", student_array);
			}
			if(teacher_array.length() > 0){
				post_obj.put("Teachers", teacher_array);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}

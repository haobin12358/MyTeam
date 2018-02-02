package com.etech.myteam.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.etech.myteam.R;
import com.etech.myteam.adapter.MyTeamAdapter;
import com.etech.myteam.adapter.TechsAdapter;
import com.etech.myteam.adapter.UsesAdapter;
import com.etech.myteam.common.HttpgetEntity;
import com.etech.myteam.common.HttppostEntity;
import com.etech.myteam.common.NewListView;
import com.etech.myteam.common.NumToString;
import com.etech.myteam.common.StringToJSON;
import com.etech.myteam.common.isEdit;
import com.etech.myteam.entity.MyTeamEntity;
import com.etech.myteam.entity.TechsEntity;
import com.etech.myteam.entity.UsesEntity;
import com.etech.myteam.global.AppConst;
import com.etech.myteam.view.MyListView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InforActivity extends Activity{
	
	//定义组件参数
	private LinearLayout ll1, ll2, ll3, ll4, ll5, ll6, ll7;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tvtitle, tvbutton, tvtech, tvuse;
	private TextView tv_cno, tv_clevel, tv_cname, tv_ctime, tv_cstart, tv_c, tv_cend, tv_cnum, tv_ccnum, tv_cown, tv_cowner, tv_cabo, tv_caboall;
	private EditText et1, et2, et3, et4, et5, et6, et7;
	private MyListView lst1, lst2, lst3, lst4;
	private ViewGroup vg;
	private ImageView iv1;
	private Button btn1;
	
	//设置adapter和entity
	private TechsAdapter adapter_tech;
	private UsesAdapter adapter_use;
	private MyTeamAdapter adapter_myteam;
	
	private List<TechsEntity> entitys_tech = new ArrayList<TechsEntity>();
	private List<UsesEntity> entitys_use = new ArrayList<UsesEntity>();
	private List<MyTeamEntity> entitys_myteam = new ArrayList<MyTeamEntity>();
	//定义默认值
	private int Utype = 100;
	private String Uid = "9f71d450-ebc9-4680-a415-5b86f0e4df15";
	private int index = 0;//fragment的标记码
	private String id = "0d7f21f6-2372-4ca1-9e89-e495ddda6427";//根据infoType判断
	private int infoType = 2;//0学生，1教师，2竞赛
	//定义接口url
	private String get_student_abo = "http://" 
			+ AppConst.sServerURL 
			+ "/students/abo?Sid="
			+ id;
	private String get_teacher_abo = "http://" 
			+ AppConst.sServerURL 
			+ "/teachers/abo?Tid=" 
			+ id;
	private String get_competition_abo = "http://" 
			+ AppConst.sServerURL 
			+ "/competitions/abo?Cid=" 
			+ id;
	
	private HttpgetEntity getEntity;
	private HttppostEntity postEntity;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//getBd();
		new Thread(){
			public void run(){
				getSTText();
			}
		}.start();
		if(infoType == 1 || infoType == 0){
			setContentView(R.layout.fragment_personal);//学生教师的界面样式相同
			while(true){
				if(ts_text != null){
					break;
				}
			}
			init();
		}else if(infoType == 2){
			
			while(true){
				if(ts_text != null){
					break;
				}
			}
			setContentView(R.layout.activity_competitions_abo);//竞赛的样式
			init_com();
		}else{
			Toast.makeText(InforActivity.this, "错误的类型", Toast.LENGTH_SHORT).show();
		}
		
		
	}
	
	//获取教师和学生布局
	private void init(){
		ll1 = (LinearLayout)findViewById(R.id.ll_1);
		ll2 = (LinearLayout)findViewById(R.id.ll_2);
		ll3 = (LinearLayout)findViewById(R.id.ll_3);
		ll4 = (LinearLayout)findViewById(R.id.ll_4);
		ll5 = (LinearLayout)findViewById(R.id.ll_5);
		ll6 = (LinearLayout)findViewById(R.id.ll_6);
		ll7 = (LinearLayout)findViewById(R.id.ll_7);
		
		tv1 = (TextView)findViewById(R.id.tv_1);
		tv2 = (TextView)findViewById(R.id.tv_2);
		tv3 = (TextView)findViewById(R.id.tv_3);
		tv4 = (TextView)findViewById(R.id.tv_4);
		tv5 = (TextView)findViewById(R.id.tv_5);
		tv6 = (TextView)findViewById(R.id.tv_6);
		tv7 = (TextView)findViewById(R.id.tv_7);
		tv8 = (TextView)findViewById(R.id.tv_8);
		tv9 = (TextView)findViewById(R.id.tv_9);
		tv10 = (TextView)findViewById(R.id.tv_10);
		tv11 = (TextView)findViewById(R.id.tv_11);
		tvbutton = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_editbutton);
		tvtitle = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_title);
		tvtech = (TextView)findViewById(R.id.new_tech);
		tvuse = (TextView)findViewById(R.id.new_use);
		
		et1 = (EditText)findViewById(R.id.et_1);
		et2 = (EditText)findViewById(R.id.et_2);
		et3 = (EditText)findViewById(R.id.et_3);
		et4 = (EditText)findViewById(R.id.et_4);
		et5 = (EditText)findViewById(R.id.et_5);
		et6 = (EditText)findViewById(R.id.et_6);
		et7 = (EditText)findViewById(R.id.et_7);
		
		lst1 = (MyListView)findViewById(R.id.lst_1);
		lst2 = (MyListView)findViewById(R.id.lst_2);
		lst3 = (MyListView)findViewById(R.id.lst_3);
		lst4 = (MyListView)findViewById(R.id.lst_4);
		
		vg = (ViewGroup)findViewById(R.id.tv_top);
		
		iv1 = (ImageView)findViewById(R.id.iv_1);
		iv1.setOnClickListener(backto);
		
		btn1 = (Button)findViewById(R.id.btn_1);
		
		if(infoType == 0 || infoType == 1){
			tv1.setText(R.string.xing_ming);
			tv3.setText(R.string.lian_xi_fang_shi);
			tv5.setText(R.string.xue_xiao);
			tv6.setText(R.string.xue_yuan);
			tv9.setText(R.string.jing_sai_jing_li);
			tv10.setText(R.string.tuan_dui);
			tv11.setText(R.string.ren_wu);
			btn1.setVisibility(View.GONE);
			tvuse.setVisibility(View.GONE);
			isEdit.notEdit(et1);
			isEdit.notEdit(et2);
			isEdit.notEdit(et3);
			isEdit.notEdit(et4);
			isEdit.notEdit(et5);
			isEdit.notEdit(et6);
			if(infoType == 0){
				tv2.setText(R.string.xue_hao);
				tv4.setText(R.string.nian_ji);
				tv7.setText(R.string.xing_bie);
				tv8.setText(R.string.ji_neng);
				tvtitle.setText(R.string.xue_sheng_xiang_qing);
				tvtech.setVisibility(View.GONE);
				isEdit.notEdit(et7);
			}else if(infoType == 1){
				tv2.setText(R.string.jiao_gong_hao);
				tv4.setText(R.string.ren_jiao_shi_jian);
				ll5.setVisibility(View.GONE);
				ll6.setVisibility(View.GONE);
				lst1.setVisibility(View.GONE);
				tvtitle.setText(R.string.jiao_shi_xiang_qing);
			}
		}else{
			ll1.setVisibility(View.GONE);
			ll2.setVisibility(View.GONE);
			ll3.setVisibility(View.GONE);
			ll4.setVisibility(View.GONE);
			ll5.setVisibility(View.GONE);
			ll6.setVisibility(View.GONE);
			ll6.setVisibility(View.GONE);
			tv10.setVisibility(View.GONE);
			tv11.setVisibility(View.GONE);
			lst1.setVisibility(View.GONE);
			lst2.setVisibility(View.GONE);
			lst3.setVisibility(View.GONE);
			lst4.setVisibility(View.GONE);
			btn1.setVisibility(View.GONE);
		}
		//tvbutton.setOnClickListener(edit);
		
		if(Utype == 100){
			tvbutton.setText(R.string.yao_qing);
		}else if(Utype == 101){
			tvbutton.setVisibility(View.GONE);
		}
		//NewListView.setListViewHeightBasedOnChildren(lst1);
		adapter_tech = new TechsAdapter(entitys_tech, InforActivity.this);
		lst1.setAdapter(adapter_tech);
		//lst1.setOnScrollListener(NewListView.scroll);
		//NewListView.setListViewHeightBasedOnChildren(lst2);
		adapter_use = new UsesAdapter(entitys_use, InforActivity.this, Utype);
		lst2.setAdapter(adapter_use);
		//lst2.setOnScrollListener(NewListView.scroll);
		//NewListView.setListViewHeightBasedOnChildren(lst3);
		adapter_myteam = new MyTeamAdapter(entitys_myteam, InforActivity.this);
		lst3.setAdapter(adapter_myteam);
		//lst3.setOnScrollListener(NewListView.scroll);
		//NewListView.setListViewHeightBasedOnChildren(lst4);
		//Log.e("json_obj",ts_text);
		setSTText(ts_text);
	}
	
	private void init_com(){
		vg = (ViewGroup)findViewById(R.id.tv_top);
		
		tvbutton = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_editbutton);
		tvtitle = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_title);		
		tvtitle.setText(R.string.jing_sai_xiang_qing);
		
		tv_cno = (TextView)findViewById(R.id.tv_cno);
		tv_clevel = (TextView)findViewById(R.id.tv_clevel);
		tv_cname = (TextView)findViewById(R.id.tv_cname);
		tv_ctime = (TextView)findViewById(R.id.tv_ctime);
		tv_ctime.setText("参赛时间：");
		tv_cstart = (TextView)findViewById(R.id.tv_cstart);
		tv_c = (TextView)findViewById(R.id.tv_c);
		tv_c.setText("-");
		tv_cend = (TextView)findViewById(R.id.tv_cend);
		tv_cnum = (TextView)findViewById(R.id.tv_cnum);
		tv_cnum.setText("参赛人数：");
		tv_ccnum = (TextView)findViewById(R.id.tv_ccnum);
		tv_cown = (TextView)findViewById(R.id.tv_cown);
		tv_cown.setText("组织者：");
		tv_cowner = (TextView)findViewById(R.id.tv_cowner);
		tv_cabo = (TextView)findViewById(R.id.tv_cabo);
		tv_cabo.setText("竞赛详情");
		tv_caboall = (TextView)findViewById(R.id.tv_caboall);
		
		iv1 = (ImageView)findViewById(R.id.iv_1);
		iv1.setOnClickListener(backto);
		
		if(Utype == 100){
			tvbutton.setText(R.string.chuang_jian_dui_wu);
		}else{
			tvbutton.setVisibility(View.GONE);
		}
		setSTText(ts_text);
	}
	
	//获取从上一个界面传来的值
	private void getBd(){
		Intent intent = getIntent();
		try{
			Bundle bd = intent.getExtras();
			int n = bd.getInt("index");
			if (n == 0 || n == 1 || n == 2) {
				index = n;
			}
			Uid = bd.getString("Uid");
			Utype = bd.getInt("Utpe");
			id = bd.getString("id");
			infoType = bd.getInt("infoType");
		}catch (Exception e) {
			e.printStackTrace();
			Log.e("changeError", "false");
			index = 0;
		}
	}
	
	//获取信息详情
	private String ts_text = null;
	private void getSTText(){
		getEntity = new HttpgetEntity();
		try {
			if(infoType == 0){
				Log.e("url", get_student_abo);
				ts_text = getEntity.doGet(get_student_abo);
				Log.e("ts_text", ts_text);
			}else if(infoType == 1){
				Log.e("url", get_teacher_abo);
				ts_text = getEntity.doGet(get_teacher_abo);
				Log.e("ts_text", ts_text);
			}else if(infoType == 2){
				Log.e("url", get_competition_abo);
				ts_text = getEntity.doGet(get_competition_abo);
				Log.e("ts_text", ts_text);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("get_abo", "error");
			e.printStackTrace();
		}
	}
	
	private String tc_use = null;
	private String sc_use = null;
	private String s_tech = null;
	//放置获取到的数据
	private void setSTText(String ts_text){
		if(ts_text == null){
			Toast.makeText(InforActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
		}else{
			final JSONObject json_obj = StringToJSON.toJSONObject(ts_text);
			if(json_obj.optInt("status") == 200){
				new Thread(){
					public void run(){
						if(infoType == 0){
							String abo = json_obj.optString("student_abo");
							//Log.e("student_abo", json_obj.optString("student_abo"));
							JSONArray jsonarray_abo = StringToJSON.toJSONArray(abo);
							JSONObject json_obj = null;
							try {
								json_obj = jsonarray_abo.getJSONObject(0);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							et1.setText(json_obj.optString("Sname"));
							et2.setText(json_obj.optString("Sno"));
							et3.setText(json_obj.optString("Stel"));
							//et4.setText(json_obj.optInt("Sgrade"));
							et5.setText(json_obj.optString("Suniversity"));
							et6.setText(json_obj.optString("Sschool"));
							if(json_obj.optInt("Ssex") == 201){
								et7.setText(R.string.nan);
							}else if(json_obj.optInt("Ssex") == 202){
								et7.setText(R.string.nv);
							}else{
								et7.setText("");
							}
							sc_use = json_obj.optString("SCUse");
							s_tech = json_obj.optString("STech");
							entitys_use.clear();
							UsesEntity entity_use = new UsesEntity();
							if(sc_use == "[]"){
								entity_use.setCname("无");
								entity_use.setCno("");
								entity_use.setTCnum("");
							}
							else{
								try{
									JSONArray json_uses = StringToJSON.toJSONArray(sc_use);
									for(int i = 0;i < json_uses.length();i++){
										JSONObject json_use = json_uses.getJSONObject(i);
										entity_use.setCname(json_use.optString("SCname"));
										entity_use.setCno(json_use.optString("SCno"));
										entitys_use.add(entity_use);
									}
								}catch (JSONException e) {
									e.printStackTrace();
								}
							}
							entitys_tech.clear();
							TechsEntity entity_tech = new TechsEntity();
							if(s_tech == "[]"){
								entity_tech.setSTname("无");
								entity_tech.setSTlevel("");
							}
							else{
								try{
									JSONArray json_techs = StringToJSON.toJSONArray(s_tech);
									for(int i = 0;i < json_techs.length();i++){
										JSONObject json_tech = json_techs.getJSONObject(i);
										entity_tech.setSTname(json_tech.optString("STname"));
										entity_tech.setSTlevel(
												NumToString.getLevel(json_tech.optInt("STlevel")));
										entitys_tech.add(entity_tech);
									}
								}catch(JSONException e){
									e.printStackTrace();
								}
							}
						}else if(infoType == 1){
							String abo = json_obj.optString("teacher_abo");
							//Log.e("student_abo", json_obj.optString("student_abo"));
							JSONArray jsonarray_abo = StringToJSON.toJSONArray(abo);
							JSONObject json_obj = null;
							try {
								json_obj = jsonarray_abo.getJSONObject(0);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							et1.setText(json_obj.optString("Tname"));
							et2.setText(json_obj.optString("Tno"));
							et3.setText(json_obj.optString("Ttel"));
							//et4.setText(json_obj.optInt("Sgrade"));
							et5.setText(json_obj.optString("Tuniversity"));
							et6.setText(json_obj.optString("Tschool"));
							tc_use = json_obj.optString("TCuse");
							entitys_use.clear();
							UsesEntity entity_use = new UsesEntity();
							if(tc_use == "[]"){
								entity_use.setCname("无");
								entity_use.setCno("");
								entity_use.setTCnum("");
							}
							else{
								try{
									JSONArray json_uses = StringToJSON.toJSONArray(tc_use);
									for(int i = 0;i < json_uses.length();i++){
										JSONObject json_use = json_uses.getJSONObject(i);
										entity_use.setCname(json_use.optString("TCname"));
										entity_use.setCno(json_use.optString("TCno"));
										entity_use.setTCnum(json_use.optString("TCnum") + getText(R.string.zhi_dui_wu).toString());
										entitys_use.add(entity_use);
									}
								}catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}else if(infoType == 2){
							String abo = json_obj.optString("competition_abo");
							//Log.e("student_abo", json_obj.optString("student_abo"));
							JSONArray jsonarray_abo = StringToJSON.toJSONArray(abo);
							JSONObject json_obj = null;
							try {
								json_obj = jsonarray_abo.getJSONObject(0);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							tv_cno.setText("第" + json_obj.optInt("Cno") + "届");
							tv_clevel.setText(NumToString.getCLevel(json_obj.optInt("Clevel")));
							tv_cname.setText(json_obj.optString("Cname"));
							tv_cstart.setText(json_obj.optString("Cstart"));
							tv_cend.setText(json_obj.optString("Cend"));
							if(json_obj.has("Cmax")){
								if(json_obj.has("Cmin")){
									tv_ccnum.setText(json_obj.optInt("Cmin") + "-" + json_obj.optInt("Cmax") + "人");
								}else{
									tv_ccnum.setText("最多" + json_obj.optInt("Cmax") + "人");
								}
							}else{
								if(json_obj.has("Cmin")){
									tv_ccnum.setText("最少" + json_obj.optInt("Cmin") + "人");
								}else{
									tv_ccnum.setText("不限人数");
								}
							}
							tv_cowner.setText(json_obj.optString("Cown"));
							tv_caboall.setText(json_obj.optString("Cabo"));
						}else{
							Toast.makeText(InforActivity.this, "无信息", Toast.LENGTH_SHORT).show();
						}
					}
				}.start();
			}else if(json_obj.optInt("status") == 404){
				Toast.makeText(InforActivity.this, R.string.xi_tong_yi_chang, Toast.LENGTH_SHORT).show();
			}else if(json_obj.optInt("status") == 405){
				
			}else{
				Toast.makeText(InforActivity.this, R.string.wei_zhi_cuo_wu, Toast.LENGTH_SHORT).show();
			}
		}
	}

	private OnClickListener backto = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(InforActivity.this, MainActivity.class);
			intent.putExtra("index", 0);
			intent.putExtra("Uid", Uid);
			intent.putExtra("Utype", Utype);
			startActivity(intent);
			finish();
		}
		
	};
}

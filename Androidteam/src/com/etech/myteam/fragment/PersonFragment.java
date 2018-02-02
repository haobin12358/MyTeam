package com.etech.myteam.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.etech.myteam.R;
import com.etech.myteam.adapter.MyTeamAdapter;
import com.etech.myteam.adapter.TechsAdapter;
import com.etech.myteam.adapter.UsesAdapter;
import com.etech.myteam.common.HttpdeleteEntity;
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

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonFragment extends Fragment{
	
	//定义组件参数
	private LinearLayout ll1, ll2, ll3, ll4, ll5, ll6, ll7;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tvtitle, tvbutton, tvtech, tvuse;
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
	private String Uid = "2b7e8e9d-ce2a-4476-bd1b-ddcb77ceee0b";
	private int new_update = 0;//判断应该新增还是更新
	//定义接口url
	private String get_personal_url = "http://" 
			+ AppConst.sServerURL 
			+ "/personal/findall?Uid=" 
			+ Uid;
	
	private String post_personal_url = "http://" 
			+ AppConst.sServerURL 
			+ "/personal/new?Uid=" 
			+ Uid;
	
	private String update_personal_url = "http://" 
			+ AppConst.sServerURL 
			+ "/personal/update?Uid=" 
			+ Uid;
	
	private String post_personal_tech_url = "http://" 
			+ AppConst.sServerURL 
			+ "/personal/stech_new?Uid=" 
			+ Uid;
	
	private String update_personal_tech_url = "http://" 
			+ AppConst.sServerURL 
			+ "/personal/stech_update?Uid=" 
			+ Uid;
	
	private String post_personal_use_url = "http://" 
			+ AppConst.sServerURL 
			+ "/personal/scuse_new?Uid=" 
			+ Uid;
	
	private String update_personal_use_url = "http://" 
			+ AppConst.sServerURL 
			+ "/personal/scuse_update?Uid=" 
			+ Uid;
	
	private String delete_personal_tech_url = "http://" 
			+ AppConst.sServerURL 
			+ "/personal/stech_delete?Uid=" 
			+ Uid;
	
	private String delete_personal_use_url = "http://" 
			+ AppConst.sServerURL 
			+ "/personal/scuse_delete?Uid=" 
			+ Uid;
	
	private String get_myteam_url = "http://" 
			+ AppConst.sServerURL 
			+ "/team/myteam?is_index=0000&Uid=" 
			+ Uid;
	
	private HttpgetEntity getEntity;
	private HttppostEntity postEntity;
	private HttpdeleteEntity deleteEntity;

	//主界面
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view;
		view = inflater.inflate(R.layout.fragment_personal, null);
		
		new Thread(){
			public void run(){
				getPersonalText();
				getMyteam();
			}
		}.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		init(view);
		return view;
	}
	
	//获取布局
	private void init(View view){
		ll1 = (LinearLayout)view.findViewById(R.id.ll_1);
		ll2 = (LinearLayout)view.findViewById(R.id.ll_2);
		ll3 = (LinearLayout)view.findViewById(R.id.ll_3);
		ll4 = (LinearLayout)view.findViewById(R.id.ll_4);
		ll5 = (LinearLayout)view.findViewById(R.id.ll_5);
		ll6 = (LinearLayout)view.findViewById(R.id.ll_6);
		ll7 = (LinearLayout)view.findViewById(R.id.ll_7);
		
		tv1 = (TextView)view.findViewById(R.id.tv_1);
		tv2 = (TextView)view.findViewById(R.id.tv_2);
		tv3 = (TextView)view.findViewById(R.id.tv_3);
		tv4 = (TextView)view.findViewById(R.id.tv_4);
		tv5 = (TextView)view.findViewById(R.id.tv_5);
		tv6 = (TextView)view.findViewById(R.id.tv_6);
		tv7 = (TextView)view.findViewById(R.id.tv_7);
		tv8 = (TextView)view.findViewById(R.id.tv_8);
		tv9 = (TextView)view.findViewById(R.id.tv_9);
		tv10 = (TextView)view.findViewById(R.id.tv_10);
		tv11 = (TextView)view.findViewById(R.id.tv_11);
		tvbutton = (TextView)view.findViewById(R.id.tv_top).findViewById(R.id.tv_editbutton);
		tvtitle = (TextView)view.findViewById(R.id.tv_top).findViewById(R.id.tv_title);
		tvtech = (TextView)view.findViewById(R.id.new_tech);
		tvuse = (TextView)view.findViewById(R.id.new_use);
		
		et1 = (EditText)view.findViewById(R.id.et_1);
		et2 = (EditText)view.findViewById(R.id.et_2);
		et3 = (EditText)view.findViewById(R.id.et_3);
		et4 = (EditText)view.findViewById(R.id.et_4);
		et5 = (EditText)view.findViewById(R.id.et_5);
		et6 = (EditText)view.findViewById(R.id.et_6);
		et7 = (EditText)view.findViewById(R.id.et_7);
		
		lst1 = (MyListView)view.findViewById(R.id.lst_1);
		lst2 = (MyListView)view.findViewById(R.id.lst_2);
		lst3 = (MyListView)view.findViewById(R.id.lst_3);
		lst4 = (MyListView)view.findViewById(R.id.lst_4);
		
		vg = (ViewGroup)view.findViewById(R.id.tv_top);
		
		iv1 = (ImageView)view.findViewById(R.id.iv_1);
		
		btn1 = (Button)view.findViewById(R.id.btn_1);
		
		if(Utype == 100 || Utype == 101){
			//LinearLayoutContain.setText(vg, getResources().getString(R.string.ge_ren_xin_xi));
			tvtitle.setText(R.string.ge_ren_xin_xi);
			//tvbutton.setText(R.string.bian_ji);
			tv1.setText(R.string.xing_ming);
			et1.setHint(R.string.xing_ming);
			tv3.setText(R.string.lian_xi_fang_shi);
			et3.setHint(R.string.lian_xi_fang_shi);
			tv5.setText(R.string.xue_xiao);
			et5.setHint(R.string.xue_xiao);
			tv6.setText(R.string.xue_yuan);
			et6.setHint(R.string.xue_yuan);
			tv9.setText(R.string.jing_sai_jing_li);
			tv10.setText(R.string.tuan_dui);
			tv11.setText(R.string.ren_wu);
			tvtech.setText(R.string.xin_zeng);
			tvuse.setText(R.string.xin_zeng);
			btn1.setVisibility(View.GONE);
			isEdit.notEdit(et1);
			isEdit.notEdit(et2);
			isEdit.notEdit(et3);
			isEdit.notEdit(et4);
			isEdit.notEdit(et5);
			isEdit.notEdit(et6);
			if(Utype == 100){
				tv2.setText(R.string.xue_hao);
				et2.setHint(R.string.xue_hao);
				tv4.setText(R.string.nian_ji);
				et4.setHint(R.string.nian_ji);
				tv7.setText(R.string.xing_bie);
				et7.setHint(R.string.xing_bie);
				tv8.setText(R.string.ji_neng);
				isEdit.notEdit(et7);
			}else if(Utype == 101){
				tv2.setText(R.string.jiao_gong_hao);
				et2.setHint(R.string.jiao_gong_hao);
				tv4.setText(R.string.ren_jiao_shi_jian);
				et4.setHint(R.string.ren_jiao_shi_jian);
				ll5.setVisibility(View.GONE);
				ll6.setVisibility(View.GONE);
				lst1.setVisibility(View.GONE);
			}
		}else{
			ll1.setVisibility(View.GONE);
			ll2.setVisibility(View.GONE);
			ll3.setVisibility(View.GONE);
			ll4.setVisibility(View.GONE);
			ll5.setVisibility(View.GONE);
			ll6.setVisibility(View.GONE);
			ll7.setVisibility(View.GONE);
			tv10.setVisibility(View.GONE);
			tv11.setVisibility(View.GONE);
			lst1.setVisibility(View.GONE);
			lst2.setVisibility(View.GONE);
			lst3.setVisibility(View.GONE);
			lst4.setVisibility(View.GONE);
			btn1.setVisibility(View.GONE);
		}
		tvbutton.setOnClickListener(edit);
		tvtech.setOnClickListener(new_tech);
		tvuse.setOnClickListener(new_use);
		
		if(new_update == 0){
			tvbutton.setText(R.string.xin_zeng);
		}else if(new_update == 1){
			tvbutton.setText(R.string.bian_ji);
		}
		//NewListView.setListViewHeightBasedOnChildren(lst1);
		adapter_tech = new TechsAdapter(entitys_tech, getActivity());
		lst1.setAdapter(adapter_tech);
		//lst1.setOnScrollListener(NewListView.scroll);
		lst1.setOnItemClickListener(itemupdate);
		
		//NewListView.setListViewHeightBasedOnChildren(lst2);
		adapter_use = new UsesAdapter(entitys_use, getActivity(), Utype);
		lst2.setAdapter(adapter_use);
		//lst2.setOnScrollListener(NewListView.scroll);
		lst2.setOnItemClickListener(itemupdate);
		
		//NewListView.setListViewHeightBasedOnChildren(lst3);
		adapter_myteam = new MyTeamAdapter(entitys_myteam, getActivity());
		lst3.setAdapter(adapter_myteam);
		//lst3.setOnScrollListener(NewListView.scroll);
		
		NewListView.setListViewHeightBasedOnChildren(lst4);
		
		setPersonalText(personal_text);
		setPersonalTeam(myteam);
	}
	
	//编辑功能button的监听器
	private OnClickListener edit = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(tvbutton.getText().toString() == getText(R.string.bian_ji) 
					|| tvbutton.getText().toString() == getText(R.string.xin_zeng)){
				isEdit.yesEdit(et1);
				isEdit.yesEdit(et2);
				isEdit.yesEdit(et3);
				isEdit.yesEdit(et4);
				isEdit.yesEdit(et5);
				isEdit.yesEdit(et6);
				isEdit.yesEdit(et7);
				tvbutton.setText(R.string.que_ding);
			}else if(tvbutton.getText().toString() == getText(R.string.que_ding)){
				isEdit.notEdit(et1);
				isEdit.notEdit(et2);
				isEdit.notEdit(et3);
				isEdit.notEdit(et4);
				isEdit.notEdit(et5);
				isEdit.notEdit(et6);
				isEdit.notEdit(et7);
				tvbutton.setText(R.string.bian_ji);
				if(getText()){
					new Thread(){
						public void run(){
							post_personal_infor();
						}
					}.start();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					JSONObject post_result = StringToJSON.toJSONObject(result_of_post_personal_infor);
					if(post_result.optInt("status") == 200){
						new AlertDialog.Builder(getActivity())
							.setTitle(R.string.ti_xing)
							.setMessage(post_result.optString("messages"))
							.setPositiveButton(R.string.que_ding, null)
							.show();
					}else if(post_result.optInt("status") == 404){
						new AlertDialog.Builder(getActivity())
							.setTitle(R.string.ti_xing)
							.setMessage(R.string.xi_tong_yi_chang)
							.show();
					}else if(post_result.optInt("status") == 405){
						if(post_result.optInt("status_code") == 405201){
							new AlertDialog.Builder(getActivity())
								.setTitle(R.string.ti_xing)
								.setMessage(post_result.optString("messages"))
								.show();
						}else if(post_result.optInt("status_code") == 405204){
							new AlertDialog.Builder(getActivity())
								.setTitle(R.string.ti_xing)
								.setMessage(post_result.optString("messages"))
								.show();
						}else if(post_result.optInt("status_code") == 405202){
							new AlertDialog.Builder(getActivity())
								.setTitle(R.string.ti_xing)
								.setMessage(post_result.optString("messages"))
								.show();
						}else if(post_result.optInt("status_code") == 405203){
							new AlertDialog.Builder(getActivity())
								.setTitle(R.string.ti_xing)
								.setMessage(post_result.optString("messages"))
								.show();
						}
					}else{
						new AlertDialog.Builder(getActivity())
							.setTitle(R.string.ti_xing)
							.setMessage(R.string.wei_zhi_cuo_wu)
							.show();
					}
					Log.e("personal", "ok");
				}
			}
		}
	};
	
	private String personal_text = null;
	//获取个人基本信息
	private void getPersonalText(){
		getEntity = new HttpgetEntity();
		try {
			personal_text = getEntity.doGet(get_personal_url);
			Log.e("personal_text", personal_text);
			if(personal_text != null){
				final JSONObject json_obj = StringToJSON.toJSONObject(personal_text);
				if(json_obj.optInt("status") == 200){
					new_update = 1;
				}else{
					new_update = 0;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("getpersonal", "error");
			e.printStackTrace();
		}
	}
	private void getMyteam(){
		getEntity = new HttpgetEntity();
		try{
			myteam = getEntity.doGet(get_myteam_url);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private String tc_use = null;
	private String sc_use = null;
	private String s_tech = null;
	private void setPersonalText(String personal_text){
		if(personal_text == null){
			new AlertDialog.Builder(getActivity())
				.setTitle(R.string.ti_xing)
				.setMessage(R.string.qing_xian_tian_xie_ge_ren_xin_xi)
				.show();
		}else{
			final JSONObject json_obj = StringToJSON.toJSONObject(personal_text);
			if(json_obj.optInt("status") == 200){
				new Thread(){
					public void run(){
						if(Utype == 100){
							String abo = json_obj.optString("student_abo");
							JSONArray json_array = StringToJSON.toJSONArray(abo);
							JSONObject json_personal = null;
							try {
								json_personal = json_array.getJSONObject(0);
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							//JSONObject json_personal = StringToJSON.toJSONObject(abo);
							et1.setText(json_personal.optString("Sname"));
							et2.setText(json_personal.optString("Sno"));
							et3.setText(json_personal.optString("Stel"));
							//et4.setText(json_personal.optInt("Sgrade"));
							et5.setText(json_personal.optString("Suniversity"));
							et6.setText(json_personal.optString("Sschool"));
							if(json_personal.optInt("Ssex") == 201){
								et7.setText(R.string.nan);
							}else if(json_personal.optInt("Ssex") == 202){
								et7.setText(R.string.nv);
							}else{
								et7.setText(" ");
							}
							sc_use = json_personal.optString("SCUse");
							s_tech = json_personal.optString("STech");
							if(sc_use == "[]"){
								UsesEntity entity_use = new UsesEntity();
								entity_use.setCname("无");
								entity_use.setCno("");
								entity_use.setTCnum("");
							}
							else{
								try{
									JSONArray json_uses = StringToJSON.toJSONArray(sc_use);
									entitys_use.clear();
									for(int i = 0;i < json_uses.length();i++){
										JSONObject json_use = json_uses.getJSONObject(i);
										UsesEntity entity_use = new UsesEntity();
										entity_use.setCname(json_use.optString("SCname"));
										entity_use.setCno(json_use.optString("SCno"));
										entitys_use.add(entity_use);
									}
								}catch (JSONException e) {
									e.printStackTrace();
								}
							}
							if(s_tech == "[]"){
								TechsEntity entity_tech = new TechsEntity();
								entity_tech.setSTname("无");
								entity_tech.setSTlevel("");
							}
							else{
								try{
									JSONArray json_techs = StringToJSON.toJSONArray(s_tech);
									entitys_tech.clear();
									for(int i = 0;i < json_techs.length();i++){
										JSONObject json_tech = json_techs.getJSONObject(i);
										TechsEntity entity_tech = new TechsEntity();
										entity_tech.setSTname(json_tech.optString("STname"));
										entity_tech.setSTlevel(NumToString.getLevel(json_tech.optInt("STlevel")));
										entitys_tech.add(entity_tech);
									}
								}catch(JSONException e){
									e.printStackTrace();
								}
							}
						}else if(Utype == 101){
							String abo = json_obj.optString("teacher_abo");
							JSONArray json_personals = StringToJSON.toJSONArray(abo);
							JSONObject json_personal = null;
							try {
								json_personal = json_personals.getJSONObject(0);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							et1.setText(json_personal.optString("Tname"));
							et2.setText(json_personal.optString("Tno"));
							et3.setText(json_personal.optString("Ttel"));
							et4.setText(json_personal.optString("Ttime"));
							et5.setText(json_personal.optString("Tuniversity"));
							et6.setText(json_personal.optString("Tschool"));
							tc_use = json_personal.optString("TCuse");
							
							if(tc_use == "[]"){
								UsesEntity entity_use = new UsesEntity();
								entity_use.setCname("无");
								entity_use.setCno("");
								entity_use.setTCnum("");
							}
							else{
								try{
									JSONArray json_uses = StringToJSON.toJSONArray(tc_use);
									for(int i = 0;i < json_uses.length();i++){
										JSONObject json_use = json_uses.getJSONObject(i);
										entitys_use.clear();
										UsesEntity entity_use = new UsesEntity();
										entity_use.setCname(json_use.optString("TCname"));
										entity_use.setCno(json_use.optString("TCno"));
										entity_use.setTCnum(json_use.optString("TCnum") + getText(R.string.zhi_dui_wu).toString());
										entitys_use.add(entity_use);
									}
								}catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}.start();
			}else if(json_obj.optInt("status") == 404){
				new AlertDialog.Builder(getActivity())
					.setTitle(R.string.ti_xing)
					.setMessage(R.string.xi_tong_yi_chang)
					.show();
			}else if(json_obj.optInt("status") == 405){
				new AlertDialog.Builder(getActivity())
					.setTitle(R.string.ti_xing)
					.setMessage(R.string.qing_xian_tian_xie_ge_ren_xin_xi)
					.show();
			}else{
				new AlertDialog.Builder(getActivity())
					.setTitle(R.string.ti_xing)
					.setMessage(R.string.wei_zhi_cuo_wu)
					.show();
			}
		}
	}
	
	private String myteam = null;
	private void setPersonalTeam(String myteam){
		final JSONArray jsonArray = StringToJSON.toJSONArray(myteam);
		if(myteam == "[]"){
			MyTeamEntity entity_myteam = new MyTeamEntity();
			entity_myteam.setTEname("无");
			entity_myteam.setCname("");
		}
		entitys_myteam.clear();
		for(int i = 1; i < jsonArray.length(); i++){
			try {
				JSONObject jsonobj = jsonArray.getJSONObject(i);
				MyTeamEntity entity_myteam = new MyTeamEntity();
				entity_myteam.setTEname(jsonobj.optString("TEname"));
				entity_myteam.setCname("第" + jsonobj.optInt("Cno") + "届" 
						+ NumToString.getCLevel(jsonobj.optInt("Clevel")) 
						+ jsonobj.optString("Sname"));
				entitys_myteam.add(entity_myteam);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private String name, no, tel, university, school;
	private int time, sex;
	private boolean getText(){
		name = et1.getText().toString();
		if(name.length() == 0){
			new AlertDialog.Builder(getActivity())
				.setTitle(R.string.ti_xing)
				.setMessage(R.string.qing_shu_ru + tv1.getText().toString())
				.show();
			return false;
		}
		no = et2.getText().toString();
		if(no.length() == 0){
			new AlertDialog.Builder(getActivity())
				.setTitle(R.string.ti_xing)
				.setMessage(R.string.qing_shu_ru + tv2.getText().toString())
				.show();
			return false;
		}
		tel = et3.getText().toString();
		if(tel.length() == 0){
			new AlertDialog.Builder(getActivity())
				.setTitle(R.string.ti_xing)
				.setMessage(R.string.qing_shu_ru + tv3.getText().toString())
				.show();
			return false;
		}
		university = et5.getText().toString();
		if(university.length() == 0){
			new AlertDialog.Builder(getActivity())
				.setTitle(R.string.ti_xing)
				.setMessage(R.string.qing_shu_ru + tv5.getText().toString())
				.show();
			return false;
		}
		school = et6.getText().toString();
		if(school.length() == 0){
			new AlertDialog.Builder(getActivity())
				.setTitle(R.string.ti_xing)
				.setMessage(R.string.qing_shu_ru + tv6.getText().toString())
				.show();
			return false;
		}
		if(et4.getText().toString().length() == 0){
			time = -1;
		}else{
			time = Integer.parseInt(et4.getText().toString());
		}
		if(et7.getText().toString().length() == 0){
			sex = -1;
		}else if(et7.getText().toString() == getText(R.string.nan)){
			sex = 201;
		}else if(et7.getText().toString() == getText(R.string.nv)){
			sex = 202;
		}
		return true;
	}
	
	private String result_of_post_personal_infor = null;
	private String url_update_new = null;
	private void post_personal_infor(){
		if(new_update == 0){
			url_update_new = post_personal_url;
		}else{
			url_update_new = update_personal_url;
		}
		JSONObject obj = new JSONObject();
		if(Utype == 100){
			try {
				obj.put("Sname", name);
				obj.put("Sno", no);
				obj.put("Stel", tel);
				if(time == -1){
					obj.put("Sgrade", null);
				}else{
					obj.put("Sgrade", time);
				}
				obj.put("Suniversity", university);
				obj.put("Sschool", school);
				if(sex == -1){
					obj.put("Ssex", null);
				}else{
					obj.put("Ssex", sex);
				}
				postEntity = new HttppostEntity();
				try {
					result_of_post_personal_infor = postEntity.doPost(obj, url_update_new);
					Log.e("result_of_post_personal_infor", result_of_post_personal_infor);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e("post", "error");
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else if(Utype == 101){
			try {
				obj.put("Tname", name);
				obj.put("Tno", no);
				obj.put("Ttel", tel);
				if(time == -1){
					obj.put("Ttime", null);
				}else{
					obj.put("Ttime", time);
				}
				obj.put("Tuniversity", university);
				obj.put("Tschool", school);
				postEntity = new HttppostEntity();
				try {
					result_of_post_personal_infor = postEntity.doPost(obj, url_update_new);
					Log.e("result_of_post_personal_infor", result_of_post_personal_infor);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e("post", "error");
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
    //更新&删除个人技能&个人比赛经历的监听事件
    private OnItemClickListener itemupdate = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			if(parent.getId() == R.id.lst_1){
				JSONArray jsonArray = StringToJSON.toJSONArray(s_tech);
				try {
					JSONObject jsonObject = jsonArray.getJSONObject(position);
					showDialog(jsonObject, "修改或删除这个技能", "st");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(parent.getId() == R.id.lst_2){
				if(Utype == 100){
					JSONArray jsonArray = StringToJSON.toJSONArray(sc_use);
					try {
						JSONObject jsonObject = jsonArray.getJSONObject(position);
						showDialog(jsonObject, "修改或删除这个比赛经历", "sc");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(Utype == 101){
					JSONArray jsonArray = StringToJSON.toJSONArray(tc_use);
					try {
						JSONObject jsonObject = jsonArray.getJSONObject(position);
						showDialog(jsonObject, "修改或删除这个比赛经历", "tc");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
    };
  	
  	private void showDialog(final JSONObject getJSON, String title, final String list_index){
  		//getJSON表示获取的服务端值，title表示标题内容，list_index表示业务类型
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View view = inflater.inflate(R.layout.layout_alert, null);
	    builder.setTitle(title);
	    final EditText editText1 = (EditText)view.findViewById(R.id.alert_1);
	    final EditText editText2 = (EditText)view.findViewById(R.id.alert_2);
	    final EditText editText3 = (EditText)view.findViewById(R.id.alert_3);
	    builder.setView(view);
	    if(list_index == "st" || list_index == "sc" || list_index == "tc"){
		    if(list_index == "tc"){
		    	editText1.setHint("请输入竞赛名称");
		    	editText2.setHint("请输入获奖等级/名次");
		    	editText3.setHint("请输入获奖队伍数目");
		    	editText1.setText(getJSON.optString("TCname"));
		    	editText2.setText(getJSON.optString("TCno"));
		    	editText3.setText(getJSON.optString("TCnum"));
		    }else if(list_index == "st"){
		    	editText1.setHint("请输入技能名称");
		    	editText2.setHint("请输入技能熟练度");
		    	editText3.setVisibility(View.GONE);
		    	editText1.setText(getJSON.optString("STname"));
		    	editText2.setText(getJSON.optString("STlevel"));
		    }else if(list_index == "sc"){
		    	editText3.setVisibility(View.GONE);
		    	editText1.setHint("请输入竞赛名称");
		    	editText2.setHint("请输入获奖等级/名次");
		    	editText1.setText(getJSON.optString("SCname"));
		    	editText2.setText(getJSON.optString("SCno"));
		    }
	    }
	    builder.setPositiveButton("修改",
	            new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                    // TODO Auto-generated method stub
	                	JSONObject obj = new JSONObject();
	                	final JSONArray jsonArray = new JSONArray();
	                	if(list_index == "tc"){
	                		try {
	                			if(editText1.getText().toString().length() == 0 || 
	                					editText2.getText().toString().length() == 0){
	                				Toast.makeText(getActivity(),
	        	                            "请填写竞赛名称和名次",
	        	                            Toast.LENGTH_SHORT).show();
	                			}
								obj.put("TCname", editText1.getText().toString());
								obj.put("TCno", editText2.getText().toString());
								if(editText3.getText().toString().length() == 0){
									obj.put("TCnum", 1);
								}else{
									obj.put("TCnum", Integer.parseInt(editText3.getText().toString()));
								}
								obj.put("TCid", getJSON.opt("TCid"));
								jsonArray.put(obj);
								Log.e("jsonArray", jsonArray.toString());
							} catch (JSONException e) {
								e.printStackTrace();
							}
	                		new Thread(){
	                			public void run(){
	                				try {
										String response = postEntity.doPostA(jsonArray, update_personal_use_url);
										Log.e("response",response);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										Log.e("post", "error");
										e.printStackTrace();
									}
	                			}
	                		}.start();
	                	}else if(list_index == "sc"){
	                		try {
	                			if(editText1.getText().toString().length() == 0 || 
	                					editText2.getText().toString().length() == 0){
	                				Toast.makeText(getActivity(),
	        	                            "请填写竞赛名称和名次",
	        	                            Toast.LENGTH_SHORT).show();
	                			}
								obj.put("SCname", editText1.getText().toString());
								obj.put("SCno", editText2.getText().toString());
								obj.put("SCid", getJSON.opt("SCid"));
								jsonArray.put(obj);
								Log.e("jsonArray", jsonArray.toString());
							} catch (JSONException e) {
								e.printStackTrace();
							}
	                		new Thread(){
	                			public void run(){
	                				try {
										String response = postEntity.doPostA(jsonArray, update_personal_use_url);
										Log.e("response",response);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										Log.e("post", "error");
										e.printStackTrace();
									}
	                			}
	                		}.start();
	                	}else if(list_index == "st"){
	                		try{
	                			if(editText1.getText().toString().length() == 0 || 
	                					editText2.getText().toString().length() == 0){
	                				Toast.makeText(getActivity(), 
	                						"请填写技能名称和等级", 
	                						Toast.LENGTH_SHORT).show();
	                			}
	                			obj.put("STname", editText1.getText().toString());
	                			obj.put("STlevel", editText2.getText().toString());
	                			obj.put("STid", getJSON.opt("STid"));
	                			jsonArray.put(obj);
	                		}catch (JSONException e){
	                			e.printStackTrace();
	                		}
	                		new Thread(){
	                			public void run(){
	                				try {
										String response = postEntity.doPostA(jsonArray, update_personal_tech_url);
										Log.e("response",response);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										Log.e("post", "error");
										e.printStackTrace();
									}
	                			}
	                		}.start();
	                	}
	                	try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                  
	                    Toast.makeText(getActivity(),
	                            "修改成功",
	                            Toast.LENGTH_SHORT).show();
	                }
	            });
	    builder.setNegativeButton("删除",
	            new DialogInterface.OnClickListener() {
	 
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                    // TODO Auto-generated method stub
	                    
	                    JSONObject obj = new JSONObject();
	                	final JSONArray jsonArray = new JSONArray();
	                	try{
	                		if(list_index == "st"){
		                		obj.put("STid", getJSON.opt("STid"));
		                		jsonArray.put(obj);
		                	}else if(list_index == "sc"){
		                		obj.put("SCid", getJSON.opt("SCid"));
		                		jsonArray.put(obj);
		                	}else if(list_index == "tc"){
		                		obj.put("TCid", getJSON.opt("TCid"));
		                		jsonArray.put(obj);
		                	}
	                	} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                	new Thread(){
	                		public void run(){
	                			try{
	                				if(list_index == "st"){
	                					String response = postEntity.doPostA(jsonArray, delete_personal_tech_url);
		                			}else if(list_index == "sc" || list_index == "tc"){
		                				String response = postEntity.doPostA(jsonArray, delete_personal_use_url);
		                			}
	                			}catch (Exception e) {
									// TODO Auto-generated catch block
									Log.e("delete", "error");
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
	                	Toast.makeText(getActivity(), "删除成功",
	                            Toast.LENGTH_SHORT).show();
	                	
	                }
	            });
	 
	    builder.show();
	}
  	
  	private OnClickListener new_tech = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			showDialog_new("新增", 1);
		}
  		
  	};
  	
  	private OnClickListener new_use = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			showDialog_new("新增", 2);
		}
  		
  	};
  	
  	private void showDialog_new(String title, final int index){
  		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View view = inflater.inflate(R.layout.layout_alert, null);
	    builder.setTitle(title);
	    final EditText editText1 = (EditText)view.findViewById(R.id.alert_1);
	    final EditText editText2 = (EditText)view.findViewById(R.id.alert_2);
	    final EditText editText3 = (EditText)view.findViewById(R.id.alert_3);
	    builder.setView(view);
	    if(index == 1 || index == 2){
		    if(index == 2 && Utype == 101){
		    	editText1.setHint("请输入竞赛名称");
		    	editText2.setHint("请输入获奖等级/名次");
		    	editText3.setHint("请输入获奖队伍数目");
		    }else if(index == 1 && Utype == 100){
		    	editText1.setHint("请输入技能名称");
		    	editText2.setHint("请输入技能熟练度");
		    	editText3.setVisibility(View.GONE);
		    }else if(index == 2 && Utype == 100){
		    	editText3.setVisibility(View.GONE);
		    	editText1.setHint("请输入竞赛名称");
		    	editText2.setHint("请输入获奖等级/名次");
		    }
	    }
	    builder.setPositiveButton("新建",
	            new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                    // TODO Auto-generated method stub
	                	JSONObject obj = new JSONObject();
	                	final JSONArray jsonArray = new JSONArray();
	                	if(index == 2 && Utype == 101){
	                		try {
	                			if(editText1.getText().toString().length() == 0 || 
	                					editText2.getText().toString().length() == 0){
	                				Toast.makeText(getActivity(),
	        	                            "请填写竞赛名称和名次",
	        	                            Toast.LENGTH_SHORT).show();
	                			}
								obj.put("TCname", editText1.getText().toString());
								obj.put("TCno", editText2.getText().toString());
								if(editText3.getText().toString().length() == 0){
									obj.put("TCnum", 1);
								}else{
									obj.put("TCnum", Integer.parseInt(editText3.getText().toString()));
								}
								jsonArray.put(obj);
								Log.e("jsonArray", jsonArray.toString());
							} catch (JSONException e) {
								e.printStackTrace();
							}
	                		new Thread(){
	                			public void run(){
	                				try {
										String response = postEntity.doPostA(jsonArray, post_personal_use_url);
										Log.e("response",response);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										Log.e("post", "error");
										e.printStackTrace();
									}
	                			}
	                		}.start();
	                	}else if(index == 2 && Utype == 100){
	                		try {
	                			if(editText1.getText().toString().length() == 0 || 
	                					editText2.getText().toString().length() == 0){
	                				Toast.makeText(getActivity(),
	        	                            "请填写竞赛名称和名次",
	        	                            Toast.LENGTH_SHORT).show();
	                			}
								obj.put("SCname", editText1.getText().toString());
								obj.put("SCno", editText2.getText().toString());
								jsonArray.put(obj);
								Log.e("jsonArray", jsonArray.toString());
							} catch (JSONException e) {
								e.printStackTrace();
							}
	                		new Thread(){
	                			public void run(){
	                				try {
										String response = postEntity.doPostA(jsonArray, post_personal_use_url);
										Log.e("response",response);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										Log.e("post", "error");
										e.printStackTrace();
									}
	                			}
	                		}.start();
	                	}else if(index == 1 && Utype == 100){
	                		try{
	                			if(editText1.getText().toString().length() == 0 || 
	                					editText2.getText().toString().length() == 0){
	                				Toast.makeText(getActivity(), 
	                						"请填写技能名称和等级", 
	                						Toast.LENGTH_SHORT).show();
	                			}
	                			obj.put("STname", editText1.getText().toString());
	                			obj.put("STlevel", Integer.parseInt(editText2.getText().toString()));
	                			jsonArray.put(obj);
	                		}catch (JSONException e){
	                			e.printStackTrace();
	                		}
	                		new Thread(){
	                			public void run(){
	                				try {
										String response = postEntity.doPostA(jsonArray, post_personal_tech_url);
										Log.e("response",response);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										Log.e("post", "error");
										e.printStackTrace();
									}
	                			}
	                		}.start();
	                	}
	                	try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                  
	                    Toast.makeText(getActivity(),
	                            "修改成功",
	                            Toast.LENGTH_SHORT).show();
	                }
	            });
	    builder.setNegativeButton("取消",
	            new DialogInterface.OnClickListener() {
	 
	                @Override
	                public void onClick(DialogInterface dialog, int which) {

	                	Toast.makeText(getActivity(), "取消",
	                            Toast.LENGTH_SHORT).show();
	                	
	                }
	            });
	 
	    builder.show();
  	}
  	
  	
  	
}

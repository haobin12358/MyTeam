package com.etech.myteam.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.etech.myteam.R;
import com.etech.myteam.activity.LoginActivity;
import com.etech.myteam.adapter.MyTeamAdapter;
import com.etech.myteam.adapter.TechsAdapter;
import com.etech.myteam.adapter.UsesAdapter;
import com.etech.myteam.common.HttpgetEntity;
import com.etech.myteam.common.HttppostEntity;
import com.etech.myteam.common.LinearLayoutContain;
import com.etech.myteam.common.StringToJSON;
import com.etech.myteam.entity.MyTeamEntity;
import com.etech.myteam.entity.TechsEntity;
import com.etech.myteam.entity.UsesEntity;
import com.etech.myteam.global.AppConst;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PersonFragment extends Fragment{
	
	//定义组件参数
	private LinearLayout ll1, ll2, ll3, ll4, ll5;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tvtitle, tvbutton;
	private EditText et1, et2, et3, et4, et5, et6, et7;
	private ListView lst1, lst2, lst3, lst4;
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
	private int Utype = 101;
	private String Uid = "9f71d450-ebc9-4680-a415-5b86f0e4df15";
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
	
	private HttpgetEntity getEntity;
	private HttppostEntity postEntity;

	//主界面
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view;
		view = inflater.inflate(R.layout.fragment_personal, null);
		
		new Thread(){
			public void run(){
				getPersonalText();
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
		
		et1 = (EditText)view.findViewById(R.id.et_1);
		et2 = (EditText)view.findViewById(R.id.et_2);
		et3 = (EditText)view.findViewById(R.id.et_3);
		et4 = (EditText)view.findViewById(R.id.et_4);
		et5 = (EditText)view.findViewById(R.id.et_5);
		et6 = (EditText)view.findViewById(R.id.et_6);
		et7 = (EditText)view.findViewById(R.id.et_7);
		
		lst1 = (ListView)view.findViewById(R.id.lst_1);
		lst2 = (ListView)view.findViewById(R.id.lst_2);
		lst3 = (ListView)view.findViewById(R.id.lst_3);
		lst4 = (ListView)view.findViewById(R.id.lst_4);
		
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
			btn1.setVisibility(View.GONE);
			notEdit(et1);
			notEdit(et2);
			notEdit(et3);
			notEdit(et4);
			notEdit(et5);
			notEdit(et6);
			if(Utype == 100){
				tv2.setText(R.string.xue_hao);
				et2.setHint(R.string.xue_hao);
				tv4.setText(R.string.nian_ji);
				et4.setHint(R.string.nian_ji);
				tv7.setText(R.string.xing_bie);
				et7.setHint(R.string.xing_bie);
				tv8.setText(R.string.ji_neng);
				notEdit(et7);
			}else if(Utype == 101){
				tv2.setText(R.string.jiao_gong_hao);
				et2.setHint(R.string.jiao_gong_hao);
				tv4.setText(R.string.ren_jiao_shi_jian);
				et4.setHint(R.string.ren_jiao_shi_jian);
				ll5.setVisibility(View.GONE);
				tv8.setVisibility(View.GONE);
				lst1.setVisibility(View.GONE);
			}
		}else{
			ll1.setVisibility(View.GONE);
			ll2.setVisibility(View.GONE);
			ll3.setVisibility(View.GONE);
			ll4.setVisibility(View.GONE);
			ll5.setVisibility(View.GONE);
			tv8.setVisibility(View.GONE);
			tv9.setVisibility(View.GONE);
			tv10.setVisibility(View.GONE);
			tv11.setVisibility(View.GONE);
			lst1.setVisibility(View.GONE);
			lst2.setVisibility(View.GONE);
			lst3.setVisibility(View.GONE);
			lst4.setVisibility(View.GONE);
			btn1.setVisibility(View.GONE);
		}
		tvbutton.setOnClickListener(edit);
		
		if(new_update == 0){
			tvbutton.setText(R.string.xin_zeng);
		}else if(new_update == 1){
			tvbutton.setText(R.string.bian_ji);
		}
		setListViewHeightBasedOnChildren(lst1);
		adapter_tech = new TechsAdapter(entitys_tech, getActivity());
		lst1.setAdapter(adapter_tech);
		lst1.setOnScrollListener(scroll);
		setListViewHeightBasedOnChildren(lst2);
		adapter_use = new UsesAdapter(entitys_use, getActivity(), Utype);
		lst2.setAdapter(adapter_use);
		lst2.setOnScrollListener(scroll);
		if(Utype == 100){
			lst2.setOnItemClickListener(sc_itemupdate);
		}else if(Utype == 101){
			lst2.setOnItemClickListener(tc_itemupdate);
		}else{
			
		}
		setListViewHeightBasedOnChildren(lst3);
		adapter_myteam = new MyTeamAdapter(entitys_myteam, getActivity());
		lst3.setAdapter(adapter_myteam);
		lst3.setOnScrollListener(scroll);
		setListViewHeightBasedOnChildren(lst4);
		
		setPersonalText(personal_text);
	}
	
	//禁止编辑功能
	private void notEdit(EditText et){
		et.setFocusable(false);
		et.setFocusableInTouchMode(false);
	}
	
	//允许编辑功能
	private void yesEdit(EditText et){
		et.setFocusable(true);
		et.setFocusableInTouchMode(true);
	}
	
	//编辑功能button的监听器
	private OnClickListener edit = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(tvbutton.getText().toString() == getText(R.string.bian_ji) 
					|| tvbutton.getText().toString() == getText(R.string.xin_zeng)){
				yesEdit(et1);
				yesEdit(et2);
				yesEdit(et3);
				yesEdit(et4);
				yesEdit(et5);
				yesEdit(et6);
				yesEdit(et7);
				tvbutton.setText(R.string.que_ding);
			}else if(tvbutton.getText().toString() == getText(R.string.que_ding)){
				notEdit(et1);
				notEdit(et2);
				notEdit(et3);
				notEdit(et4);
				notEdit(et5);
				notEdit(et6);
				notEdit(et7);
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
							JSONObject json_personal = StringToJSON.toJSONObject(abo);
							et1.setText(json_personal.optString("Sname"));
							et2.setText(json_personal.optString("Sno"));
							et3.setText(json_personal.optString("Stel"));
							et4.setText(json_personal.optInt("Sgrade"));
							et5.setText(json_personal.optString("Suniversity"));
							et6.setText(json_personal.optString("Sschool"));
							if(json_personal.optInt("Ssex") == 201){
								et7.setText(R.string.nan);
							}else if(json_personal.optInt("Ssex") == 202){
								et7.setText(R.string.nv);
							}else{
								et7.setText(" ");
							}
							sc_use = json_personal.optString("SCuse");
							s_tech = json_personal.optString("STech");
							
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
										entity_tech.setSTlevel(getLevel(Integer.getInteger(json_tech.optString("STlevel"))));
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
	
	public static void setListViewHeightBasedOnChildren(ListView listView) { 
        ListAdapter listAdapter = listView.getAdapter();  
        if (listAdapter == null) { 
            return; 
        }   
        int totalHeight = 0; 
        for (int i = 0; i < listAdapter.getCount(); i++) { 
            View listItem = listAdapter.getView(i, null, listView); 
            listItem.measure(0, 0); 
            totalHeight += listItem.getMeasuredHeight(); 
        }   
        ViewGroup.LayoutParams params = listView.getLayoutParams(); 
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)); 
        listView.setLayoutParams(params); 
	}
	
	OnScrollListener scroll = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
           switch (scrollState) {   // 当不滚动时
           case OnScrollListener.SCROLL_STATE_IDLE:
              // TODO coding here dosometing
              break;
           }
        }      
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
              int visibleItemCount, int totalItemCount) {
            // TODO coding here dosometing
        }
      };
      
      private String getLevel(int level){
    	  String level_name = null;
    	  switch(level){
    	  case 1:
    		  level_name = "入门";
    		  break;
    	  case 2:
    		  level_name = "一般";
    		  break;
    	  case 3:
    		  level_name = "掌握";
    		  break;
    	  case 4:
    		  level_name = "熟练";
    		  break;
    	  case 5:
    		  level_name = "精通";
    		  break;
    	  default:
    		  level_name = "未知";
    	  }
    	  return level_name;
      }
      
      private String SCid;
      OnItemClickListener sc_itemupdate = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			JSONArray jsonArray = StringToJSON.toJSONArray(sc_use);
			try {
				JSONObject jsonObject = jsonArray.getJSONObject(arg2);
				SCid = jsonObject.optString("SCid");
				new AlertDialog.Builder(getActivity())
					.setTitle(R.string.ti_xing)
					.setMessage("修改或删除这个比赛经历")
					.setPositiveButton("修改", null)
					.setNegativeButton("删除", null)
					.show();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
      };
      private String TCid;
      OnItemClickListener tc_itemupdate = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Log.e("json", tc_use);
			JSONArray jsonArray = StringToJSON.toJSONArray(tc_use);
			try {
				JSONObject jsonObject = jsonArray.getJSONObject(arg2);
				SCid = jsonObject.optString("TCid");
				new AlertDialog.Builder(getActivity())
					.setTitle(R.string.ti_xing)
					.setMessage("修改或删除这个比赛经历")
					.setPositiveButton("修改", null)
					.setNegativeButton("删除", null)
					.show();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
      };
      private String STid;
      OnItemClickListener st_itemupdate = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			JSONArray jsonArray = StringToJSON.toJSONArray(s_tech);
			try {
				JSONObject jsonObject = jsonArray.getJSONObject(arg2);
				SCid = jsonObject.optString("STid");
				new AlertDialog.Builder(getActivity())
					.setTitle(R.string.ti_xing)
					.setMessage("修改或删除这个比赛经历")
					.setPositiveButton("修改", null)
					.setNegativeButton("删除", null)
					.show();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
      };
}

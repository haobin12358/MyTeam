package com.etech.myteam.activity;

import java.util.ArrayList;
import java.util.List;

import com.etech.myteam.R;
import com.etech.myteam.adapter.MyTeamAdapter;
import com.etech.myteam.adapter.TechsAdapter;
import com.etech.myteam.adapter.UsesAdapter;
import com.etech.myteam.common.HttpgetEntity;
import com.etech.myteam.common.HttppostEntity;
import com.etech.myteam.common.NewListView;
import com.etech.myteam.common.isEdit;
import com.etech.myteam.entity.MyTeamEntity;
import com.etech.myteam.entity.TechsEntity;
import com.etech.myteam.entity.UsesEntity;
import com.etech.myteam.global.AppConst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class InforActivity extends Activity{
	
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
	private int index = 0;//fragment的标记码
	private String id = null;//根据id的首字母来判断
	private int infoType = 0;//0学生，1教师，2竞赛
	//定义接口url
	
	
	private HttpgetEntity getEntity;
	private HttppostEntity postEntity;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_personal);
		getBd();
		init();
	}
	
	//获取布局
	private void init(){
		ll1 = (LinearLayout)findViewById(R.id.ll_1);
		ll2 = (LinearLayout)findViewById(R.id.ll_2);
		ll3 = (LinearLayout)findViewById(R.id.ll_3);
		ll4 = (LinearLayout)findViewById(R.id.ll_4);
		ll5 = (LinearLayout)findViewById(R.id.ll_5);
		
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
		
		et1 = (EditText)findViewById(R.id.et_1);
		et2 = (EditText)findViewById(R.id.et_2);
		et3 = (EditText)findViewById(R.id.et_3);
		et4 = (EditText)findViewById(R.id.et_4);
		et5 = (EditText)findViewById(R.id.et_5);
		et6 = (EditText)findViewById(R.id.et_6);
		et7 = (EditText)findViewById(R.id.et_7);
		
		lst1 = (ListView)findViewById(R.id.lst_1);
		lst2 = (ListView)findViewById(R.id.lst_2);
		lst3 = (ListView)findViewById(R.id.lst_3);
		lst4 = (ListView)findViewById(R.id.lst_4);
		
		vg = (ViewGroup)findViewById(R.id.tv_top);
		
		iv1 = (ImageView)findViewById(R.id.iv_1);
		
		btn1 = (Button)findViewById(R.id.btn_1);
		
		if(infoType == 0 || infoType == 1){
			//LinearLayoutContain.setText(vg, getResources().getString(R.string.ge_ren_xin_xi));
			//tvtitle.setText(R.string.ge_ren_xin_xi);
			//tvbutton.setText(R.string.bian_ji);
			tv1.setText(R.string.xing_ming);
			tv3.setText(R.string.lian_xi_fang_shi);
			tv5.setText(R.string.xue_xiao);
			tv6.setText(R.string.xue_yuan);
			tv9.setText(R.string.jing_sai_jing_li);
			tv10.setText(R.string.tuan_dui);
			tv11.setText(R.string.ren_wu);
			btn1.setVisibility(View.GONE);
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
				isEdit.notEdit(et7);
			}else if(infoType == 1){
				tv2.setText(R.string.jiao_gong_hao);
				tv4.setText(R.string.ren_jiao_shi_jian);
				ll5.setVisibility(View.GONE);
				tv8.setVisibility(View.GONE);
				lst1.setVisibility(View.GONE);
			}
		}else if(infoType == 2){
			
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
		//tvbutton.setOnClickListener(edit);
		
		if(new_update == 0){
			tvbutton.setText(R.string.xin_zeng);
		}else if(new_update == 1){
			tvbutton.setText(R.string.bian_ji);
		}
		NewListView.setListViewHeightBasedOnChildren(lst1);
		adapter_tech = new TechsAdapter(entitys_tech, InforActivity.this);
		lst1.setAdapter(adapter_tech);
		lst1.setOnScrollListener(NewListView.scroll);
		NewListView.setListViewHeightBasedOnChildren(lst2);
		adapter_use = new UsesAdapter(entitys_use, InforActivity.this, Utype);
		lst2.setAdapter(adapter_use);
		lst2.setOnScrollListener(NewListView.scroll);
		if(Utype == 100){
			//lst2.setOnItemClickListener(sc_itemupdate);
		}else if(Utype == 101){
			//lst2.setOnItemClickListener(tc_itemupdate);
		}else{
			
		}
		NewListView.setListViewHeightBasedOnChildren(lst3);
		adapter_myteam = new MyTeamAdapter(entitys_myteam, InforActivity.this);
		lst3.setAdapter(adapter_myteam);
		lst3.setOnScrollListener(NewListView.scroll);
		NewListView.setListViewHeightBasedOnChildren(lst4);
		
		//setPersonalText(personal_text);
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

}

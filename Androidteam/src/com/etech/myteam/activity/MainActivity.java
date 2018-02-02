package com.etech.myteam.activity;


import com.etech.myteam.R;
import com.etech.myteam.fragment.InforFragment;
import com.etech.myteam.fragment.PersonFragment;
import com.etech.myteam.fragment.TeamFragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity{
	
	//定义组件变量
	private TextView tv1, tv2, tv3;
	//定义默认参数
	public int index = 0;
	public String Uid;
	public int Utype;
	
	//主线程+主要生命周期
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getBd();
		setContentView(R.layout.activity_main);
		init();
		setListener();
		SwitchFragment(index);
	}
	
	public String getUid(){
		return Uid;
	}
	public int getUtype(){
		return Utype;
	}
	public int getIndex(){
		return index;
	}
	
	//注册所有组件
	private void init(){
		tv1 = (TextView)findViewById(R.id.bar_text1);
		tv2 = (TextView)findViewById(R.id.bar_text2);
		tv3 = (TextView)findViewById(R.id.bar_text3);
	}
	
	//设置响应事件
	private void setListener(){
		tv1.setOnClickListener(tabClickListener);
		tv2.setOnClickListener(tabClickListener);
		findViewById(R.id.bar_text3).setOnClickListener(tabClickListener);
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
		}catch (Exception e) {
			e.printStackTrace();
			Log.e("changeError", "false");
			index = 2;
		}
	}
	
	//底部导航栏响应事件
	private OnClickListener tabClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.bar_text1:
				setTextColorChecked(tv1, 0);
				setTextColorUnChecked(tv2, 1);
				setTextColorUnChecked(tv3, 2);
				SwitchFragment(0);
				break;
			case R.id.bar_text2:
				setTextColorUnChecked(tv1, 0);
				setTextColorChecked(tv2, 1);
				setTextColorUnChecked(tv3, 2);
				SwitchFragment(1);
				break;
			case R.id.bar_text3:
				setTextColorUnChecked(tv1, 0);
				setTextColorUnChecked(tv2, 1);
				setTextColorChecked(tv3, 2);
				SwitchFragment(2);
				break;
			}
		}
		
	};
	
	//底部导航栏颜色变化功能（点击后）
	private void setTextColorChecked(TextView tv, int index){
		tv.setTextColor(getResources().getColor(R.color.cBlue));
		if(index == 0){
			tv.setCompoundDrawablesWithIntrinsicBounds(null,getResources()
					.getDrawable(R.drawable.ic_main_tab_1_p), null, null);
		}else if(index == 1){
			tv.setCompoundDrawablesWithIntrinsicBounds(null,getResources()
					.getDrawable(R.drawable.ic_main_tab_2_p), null, null);
		}else if(index == 2){
			tv.setCompoundDrawablesWithIntrinsicBounds(null,getResources()
					.getDrawable(R.drawable.ic_main_tab_3_p), null, null);
		}
	}
	
	//底部导航栏颜色变化功能（点击前）
	private void setTextColorUnChecked(TextView tv, int index){
		tv.setTextColor(getResources().getColor(R.color.cText));
		if(index == 0){
			tv.setCompoundDrawablesWithIntrinsicBounds(null,getResources()
					.getDrawable(R.drawable.ic_main_tab_1), null, null);
		}else if(index == 1){
			tv.setCompoundDrawablesWithIntrinsicBounds(null,getResources()
					.getDrawable(R.drawable.ic_main_tab_2), null, null);
		}else if(index == 2){
			tv.setCompoundDrawablesWithIntrinsicBounds(null,getResources()
					.getDrawable(R.drawable.ic_main_tab_3), null, null);
		}
	}
	
	//定义fragment变量，实现具体界面功能
	private InforFragment f1;
	private TeamFragment f2;
	private PersonFragment f3;
	
	//底部导航栏切换功能
	private void SwitchFragment(int id){
		FragmentTransaction transaction = this.getFragmentManager()
				.beginTransaction();
		switch(id){
		case 0:
			hideFragment(transaction);
			if(f1 == null){
				f1 = new InforFragment();
				transaction.add(R.id.fl_fragment_layout, f1);
			}
			transaction.show(f1);
			break;
		case 1:
			hideFragment(transaction);
			if(f2 == null){
				f2 = new TeamFragment();
				transaction.add(R.id.fl_fragment_layout, f2);
			}
			transaction.show(f2);
			break;
		case 2:
			hideFragment(transaction);
			if(f3 == null){
				f3 = new PersonFragment();
				transaction.add(R.id.fl_fragment_layout, f3);
			}
			transaction.show(f3);
			break;
		}
		transaction.commitAllowingStateLoss();
	}

	//隐藏fragment功能
	private void hideFragment(FragmentTransaction transaction){
		
		if(f1 != null){
			transaction.hide(f1);
		}if(f2 != null){
			transaction.hide(f2);
		}if(f3 != null){
			transaction.hide(f3);
		}
	}


}

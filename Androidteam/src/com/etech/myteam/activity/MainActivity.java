package com.etech.myteam.activity;


import com.etech.myteam.R;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity{
	
	private TextView tv1,tv2,tv3,tv4;
	private int index = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv1 = (TextView)findViewById(R.id.bar_text1);
		tv1.setOnClickListener(tabClickListener);
		tv2 = (TextView)findViewById(R.id.bar_text2);
		tv2.setOnClickListener(tabClickListener);
		tv3 = (TextView)findViewById(R.id.bar_text3);
		findViewById(R.id.bar_text3).setOnClickListener(tabClickListener);
		Intent intent = getIntent();
		try{
			Bundle bd = intent.getExtras();
			int n = bd.getInt("index");
			if (n == 0 || n == 1 || n == 2 || n == 3 || n == 4 || n == 5) {
				index = n;
			}
		}catch (Exception e) {
			e.printStackTrace();
			Log.e("changeError", "false");
			index = 0;
		}
		SwitchFragment(index);
	}
	
	private OnClickListener tabClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.bar_text1:
				setTextColorChecked(tv1, 0);
				setTextColorUnChecked(tv2, 1);
				setTextColorUnChecked(tv3, 2);
				setTextColorUnChecked(tv4, 3);
				SwitchFragment(0);
				break;
			case R.id.bar_text2:
				setTextColorUnChecked(tv1, 0);
				setTextColorChecked(tv2, 1);
				setTextColorUnChecked(tv3, 2);
				setTextColorUnChecked(tv4, 3);
				SwitchFragment(1);
				break;
			case R.id.bar_text3:
				setTextColorUnChecked(tv1, 0);
				setTextColorUnChecked(tv2, 1);
				setTextColorChecked(tv3, 2);
				setTextColorUnChecked(tv4, 3);
				SwitchFragment(2);
				break;
			}
		}
		
	};
	
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
	
	private void SwitchFragment(int id){
		FragmentTransaction transaction = this.getFragmentManager()
				.beginTransaction();
		switch(id){
		case 0:
			hideFragment(transaction);
			/*
			if(f1 == null){
				f1 = new ShoppingFragment();
				transaction.add(R.id.fl_fragment_layout, f1);
			}
			transaction.show(f1);
			*/
			break;
		case 1:
			hideFragment(transaction);
			/*
			if(f2 == null){
				f2 = new SListFragment();
				transaction.add(R.id.fl_fragment_layout, f2);
			}
			transaction.show(f2);
			*/
			break;
		case 2:
			hideFragment(transaction);
			/*
			if(f3 == null){
				f3 = new SqualFragment();
				transaction.add(R.id.fl_fragment_layout, f3);
			}
			transaction.show(f3);
			*/
			break;
		}
		transaction.commitAllowingStateLoss();
	}
	protected void onResume(){
		super.onResume();
	}

	private void hideFragment(FragmentTransaction transaction){
		/*
		if(f1 != null){
			transaction.hide(f1);
		}if(f2 != null){
			transaction.hide(f2);
		}if(f3 != null){
			transaction.hide(f3);
		}if(f4 != null){
			transaction.hide(f4);
		}
		*/
	}


}

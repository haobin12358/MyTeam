package com.etech.myteam.fragment;

import com.etech.myteam.R;
import com.etech.myteam.common.LinearLayoutContain;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PersonFragment extends Fragment{
	
	private LinearLayout ll1, ll2, ll3, ll4, ll5;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11;
	private EditText et1, et2, et3, et4, et5, et6, et7;
	private ListView lst1, lst2, lst3, lst4;
	private ViewGroup vg;
	private ImageView iv1, iv2;
	private int Utype = 102;
	private String Uid = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view;
		view = inflater.inflate(R.layout.fragment_personal, null);
		init(view);
		return view;
	}
	
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
		iv2 = (ImageView)view.findViewById(R.id.iv_2);
		
		if(Utype == 100 || Utype == 101){
			LinearLayoutContain.setText(vg, getResources().getString(R.string.ge_ren_xin_xi));
			tv1.setText(R.string.xing_ming);
			tv3.setText(R.string.lian_xi_fang_shi);
			tv5.setText(R.string.xue_xiao);
			tv6.setText(R.string.xue_yuan);
			tv9.setText(R.string.jing_sai_jing_li);
			tv10.setText(R.string.tuan_dui);
			tv11.setText(R.string.ren_wu);
			if(Utype == 100){
				tv2.setText(R.string.xue_hao);
				tv4.setText(R.string.nian_ji);
				tv7.setText(R.string.xing_bie);
				tv8.setText(R.string.ji_neng);
			}else if(Utype == 101){
				tv2.setText(R.string.jiao_gong_hao);
				tv4.setText(R.string.ren_jiao_shi_jian);
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
		}
	}
}

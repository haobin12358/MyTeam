package com.etech.myteam.adapter;

import java.util.List;

import com.etech.myteam.entity.MyInfoEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyInfoAdapter extends BaseAdapter{
	
	private Context context;
	private List<MyInfoEntity> entitys;
	private int Ptype;
	
	public MyInfoAdapter(Context context, List<MyInfoEntity> entitys, int Ptype){
		this.context = context;
		this.entitys = entitys;
		this.Ptype = Ptype;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entitys.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return entitys.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		MyInfoEntity entity = entitys.get(position);
		convertView.setTag(holder);
		return convertView;
	}
	
	public class ViewHolder{
		private TextView TEname, Cname, Sname, Pmessage;
	}

}

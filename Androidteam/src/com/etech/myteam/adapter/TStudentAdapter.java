package com.etech.myteam.adapter;

import java.util.List;

import com.etech.myteam.R;
import com.etech.myteam.entity.TStudentEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TStudentAdapter extends BaseAdapter{
	
	private Context context;
	private List<TStudentEntity> entitys;
	
	public TStudentAdapter(List<TStudentEntity> entitys, Context context){
		this.entitys = entitys;
		this.context = context;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_tech_list, null);
			holder = new ViewHolder();
			holder.Sname = (TextView)convertView.findViewById(R.id.tv_1);
			holder.TStype = (TextView)convertView.findViewById(R.id.tv_2);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		TStudentEntity entity = entitys.get(position);
		holder.Sname.setText(entity.getSname());
		holder.TStype.setText(entity.getTStype());
		convertView.setTag(holder);
		return convertView;
	}
	
	private class ViewHolder{
		private TextView Sname, TStype;
	}

}

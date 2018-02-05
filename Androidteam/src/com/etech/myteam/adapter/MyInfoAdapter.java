package com.etech.myteam.adapter;

import java.util.List;

import com.etech.myteam.R;
import com.etech.myteam.entity.MyInfoEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyInfoAdapter extends BaseAdapter{
	
	private Context context;
	private List<MyInfoEntity> entitys;
	
	public MyInfoAdapter(Context context, List<MyInfoEntity> entitys){
		this.context = context;
		this.entitys = entitys;
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
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.layout_item_myinfo, null);
			holder.Pmessage = (TextView)convertView.findViewById(R.id.tv_2);
			holder.Sname = (TextView)convertView.findViewById(R.id.tv_4);
			holder.Cname = (TextView)convertView.findViewById(R.id.tv_6);
			holder.TEname = (TextView)convertView.findViewById(R.id.tv_8);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		MyInfoEntity entity = entitys.get(position);
		holder.Pmessage.setText(entity.getPmessage());
		holder.Sname.setText(entity.getSname());
		holder.Cname.setText(entity.getCname());
		holder.TEname.setText(entity.getTEname());
		convertView.setTag(holder);
		return convertView;
	}
	
	public class ViewHolder{
		private TextView TEname, Cname, Sname, Pmessage;
	}

}

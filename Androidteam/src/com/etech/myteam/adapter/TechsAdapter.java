package com.etech.myteam.adapter;

import java.util.List;

import com.etech.myteam.R;
import com.etech.myteam.entity.TechsEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/*
 * 技能list对应的adapter
 */
public class TechsAdapter extends BaseAdapter{
	
	private List<TechsEntity> entitys;
	private Context context;
	
	/*
	 * 关联adapter和entity的关系
	 */
	public TechsAdapter(List<TechsEntity> entitys, Context context){
		this.entitys = entitys;
		this.context = context;
	}

	/*
	 * 获取entity的长度
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;//测试用
		//return entitys.size();
	}

	/*
	 * 获取对应位置的entity
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return entitys.get(position);
	}

	/*
	 * 获取entity的对应位置
	 */
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
			holder.STname = (TextView)convertView.findViewById(R.id.tv_1);
			holder.STlevel = (TextView)convertView.findViewById(R.id.tv_2);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		TechsEntity entity = entitys.get(position);
		holder.STname.setText(entity.getSTname());
		holder.STlevel.setText(entity.getSTlevel());
		return convertView;
	}
	
	private class ViewHolder{
		private TextView STname,STlevel;
	}

}

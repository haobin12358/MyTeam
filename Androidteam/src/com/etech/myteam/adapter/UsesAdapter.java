package com.etech.myteam.adapter;

import java.util.List;

import com.etech.myteam.R;
import com.etech.myteam.entity.UsesEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/*
 * 竞赛经历对应adapter
 */
public class UsesAdapter extends BaseAdapter{

	private List<UsesEntity> entitys;
	private Context context;
	private int Utype;
	
	/*
	 * 关联adapter与entity的关系
	 * 根据utype展示不同的adapter
	 */
	public UsesAdapter(List<UsesEntity> entitys, Context context, int Utype){
		this.entitys = entitys;
		this.context = context;
		this.Utype = Utype;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_uses_list, null);
			holder = new ViewHolder();
			if(Utype == 100 || Utype == 101){
				holder.Cname = (TextView)convertView.findViewById(R.id.tv_1);
				holder.Cno = (TextView)convertView.findViewById(R.id.tv_2);
				holder.TCnum = (TextView)convertView.findViewById(R.id.tv_3);
				if(Utype == 100){
					holder.TCnum.setVisibility(View.GONE);
				}
			}
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		UsesEntity entity = entitys.get(position);
		holder.Cname.setText(entity.getCname());
		holder.Cno.setText(entity.getCno());
		if(Utype == 101){
			holder.TCnum.setText(entity.getTCnum());
		}
		return convertView;
	}
	private class ViewHolder{
		private TextView Cname, Cno, TCnum;
	}

}

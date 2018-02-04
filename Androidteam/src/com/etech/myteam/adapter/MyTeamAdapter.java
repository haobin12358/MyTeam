package com.etech.myteam.adapter;

import java.util.List;

import com.etech.myteam.R;
import com.etech.myteam.entity.MyTeamEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/*
 * 个人团队list对应的adapter
 */
public class MyTeamAdapter extends BaseAdapter{

	private List<MyTeamEntity> entitys;
	private Context context;
	
	/*
	 * 关联adapter和entity的关系
	 */
	public MyTeamAdapter(List<MyTeamEntity> entitys, Context context){
		this.entitys = entitys;
		this.context = context;
	}
	/*
	 * 获取entity的长度
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return 1;//测试用
		return entitys.size();
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
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.layout_item_myteam_list, null);
			holder = new ViewHolder();
			holder.Cname = (TextView)convertView.findViewById(R.id.tv_1);
			holder.TEname = (TextView)convertView.findViewById(R.id.tv_2);

		}else{
			holder = (ViewHolder)convertView.getTag();
		}		
		MyTeamEntity entity = entitys.get(position);
		holder.Cname.setText(entity.getCname());
		holder.TEname.setText(entity.getTEname());
		//holder.Cname.setText("比赛1");
		//holder.TEname.setText("团队名");
		convertView.setTag(holder);
		return convertView;
	}
	
	public class ViewHolder{
		private TextView Cname, TEname;
	}

}

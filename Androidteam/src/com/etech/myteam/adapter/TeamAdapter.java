package com.etech.myteam.adapter;

import java.util.List;

import com.etech.myteam.R;
import com.etech.myteam.entity.MyTeamEntity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


public class TeamAdapter extends BaseAdapter {
	private List<MyTeamEntity> entitys;
	private Context context;
	public TeamAdapter(List<MyTeamEntity> entitys, Context context) {
		this.entitys = entitys;
		this.context = context; 
	}
	@Override
	public int getCount() {
		return entitys.size();
	}

	@Override
	public Object getItem(int position) {
		return entitys.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.layout_item_team_list, null);
			holder = new ViewHolder();
			holder.TECname = (TextView)convertView.findViewById(R.id.ed_Tecname);
			holder.TEname = (TextView)convertView.findViewById(R.id.ed_Tename);
			holder.TETname = (TextView)convertView.findViewById(R.id.ed_Tetname);
			holder.TECleader = (TextView)convertView.findViewById(R.id.ed_Teleader);
			holder.TEClevel = (TextView)convertView.findViewById(R.id.ed_Teclevel);
			holder.TECno = (TextView)convertView.findViewById(R.id.ed_Tecno);
			holder.IsFull = (TextView)convertView.findViewById(R.id.ed_Isfull);
			holder.join = (Button)convertView.findViewById(R.id.join);
            holder.join.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Log.e("this is btn click", "starts");
					
				}
			});
		}else{
			holder = (ViewHolder)convertView.getTag();
		}		
		MyTeamEntity entity = entitys.get(position);
		holder.TECname.setText(String.valueOf(entity.getCname()));
		holder.TEname.setText(String.valueOf(entity.getTEname()));
		holder.TETname.setText(String.valueOf(entity.getTetname()));
		holder.TECleader.setText(String.valueOf(entity.getTeleader()));
		holder.TEClevel.setText(String.valueOf(entity.getClevel()));
		holder.TECno.setText(String.valueOf(entity.getCno()));
		holder.IsFull.setText(String.valueOf(entity.getIsfull()));
		convertView.setTag(holder);
		return convertView;
	}
	

	private class ViewHolder{
		private TextView TECname, TEname, TETname, TEClevel, TECno, IsFull, TECleader;
		private Button join;
	}

}

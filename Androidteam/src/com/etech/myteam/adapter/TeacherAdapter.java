package com.etech.myteam.adapter;

import java.util.List;

import com.etech.myteam.R;
import com.etech.myteam.adapter.StudentAdapter.ViewHolder;
import com.etech.myteam.entity.StudentListEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherAdapter extends BaseAdapter {
	public List<StudentListEntity> entitys;
	private Context context;
	private int Utype;
	
	public TeacherAdapter(List<StudentListEntity> entitys,Context context, int Utype){
		this.Utype = Utype;
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
		ViewHolder holder;
		if (convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_student_list, null);
			holder = new ViewHolder();
			holder.Sname = (TextView)convertView.findViewById(R.id.tv_name);
			holder.Sscool = (TextView)convertView.findViewById(R.id.tv_school);
			holder.Sgrade = (TextView)convertView.findViewById(R.id.tv_grade);
			holder.btn_doit = (Button)convertView.findViewById(R.id.btn_doit);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		StudentListEntity entity = entitys.get(position);
		if (Utype == 100){
			holder.btn_doit.setText(entity.getBtn_name());
		}else{
			
			holder.btn_doit.setVisibility(View.GONE);
		}
		holder.Sname.setText(String.valueOf(entity.getName()));
		holder.Sgrade.setText(String.valueOf(entity.getTtime()));
		holder.Sscool.setText(String.valueOf(entity.getSchool()));
		holder.btn_doit.setText(String.valueOf(entity.getBtn_name()));
		convertView.setTag(holder);
		holder.btn_doit.setTag(position);
		holder.btn_doit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "该功能尚未开通", Toast.LENGTH_SHORT).show();				
			}
		});
		return convertView;
	}
	

	public class ViewHolder{
		private TextView Sname,Sscool,Sgrade;
		private Button btn_doit;
	}

}

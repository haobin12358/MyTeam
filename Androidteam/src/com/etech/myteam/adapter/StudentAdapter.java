package com.etech.myteam.adapter;

import java.util.List;

import com.etech.myteam.entity.StudentListEntity;
import com.etech.myteam.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class StudentAdapter extends BaseAdapter {
	public List<StudentListEntity> entitys;
	private Context context;
	public StudentAdapter(List<StudentListEntity> entitys,Context context){
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
		holder.Sname.setText(entity.getName());
		holder.Sgrade.setText(String.valueOf(entity.getGrade()));
		holder.Sscool.setText(entity.getSchool());
		holder.btn_doit.setText(entity.getBtn_name());
		convertView.setTag(holder);
		return convertView;
	}
	public class ViewHolder{
		private TextView Sname,Sscool,Sgrade;
		private Button btn_doit;
	}

}

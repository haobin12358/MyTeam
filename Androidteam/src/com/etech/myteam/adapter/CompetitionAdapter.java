package com.etech.myteam.adapter;

import java.util.List;

import com.etech.myteam.R;
import com.etech.myteam.activity.NewTeamActivity;
import com.etech.myteam.entity.StudentListEntity;
import com.etech.myteam.common.NumToString;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.etech.myteam.fragment.InforFragment;

public class CompetitionAdapter extends BaseAdapter{
	public List<StudentListEntity> entitys;
	private Context context;
	private int Utype;
	private String Uid;
	private int  index;
	public CompetitionAdapter(List<StudentListEntity> entitys,
			Context context, int Utype, String Uid, int index){
		this.Utype = Utype;
		this.context = context;
		this.entitys = entitys;
		this.Uid = Uid;
		this.index = index;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
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
		//教师 和管理员不能看到btn
		if (Utype == 100){
			holder.btn_doit.setText(entity.getBtn_name());
		}else{
			
			holder.btn_doit.setVisibility(View.GONE);
		}
		holder.Sname.setText("第"+String.valueOf(entity.getGrade())+"届"
		+ String.valueOf(entity.getName()));
		holder.Sgrade.setText(entity.getStart_time()+"~"+entity.getEnd_time());
		holder.Sscool.setText(NumToString.getCLevel(entity.getLevel()));
		holder.btn_doit.setText(String.valueOf(entity.getBtn_name()));
		holder.btn_doit.setTag(position); 
		holder.btn_doit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				add_team(position);
				
			}
		});
		convertView.setTag(holder);
		return convertView;
	}
	public class ViewHolder{
		private TextView Sname,Sscool,Sgrade;
		private Button btn_doit;
	}
	public void add_team(int position){
		Intent it = new Intent(context, NewTeamActivity.class);
		it.putExtra("Uid", Uid);
		it.putExtra("Utype", Utype);
		it.putExtra("Cname", entitys.get(position).getName());
		it.putExtra("Clevel", entitys.get(position).getLevel());
		it.putExtra("Cno", entitys.get(position).getGrade());
		it.putExtra("index", index);
		context.startActivity(it);
	}

}

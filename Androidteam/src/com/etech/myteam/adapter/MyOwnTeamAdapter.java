package com.etech.myteam.adapter;

import java.util.List;

import com.etech.myteam.R;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyOwnTeamAdapter extends BaseAdapter{
		
	private Activity activity;
	private List<String> data_list;

    public MyOwnTeamAdapter(Activity activity, List<String> data_list) {
        this.activity = activity;
        this.data_list = data_list;
    }

    @Override
    public int getCount() {
        return data_list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = View.inflate(activity, R.layout.layout_myownteam_item,null);
        TextView tvCarId = (TextView) view.findViewById(R.id.tv_listitem);
        tvCarId.setText("豫A88888"+i);

        return view;
    }

}

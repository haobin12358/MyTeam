package com.etech.myteam.common;

import com.etech.myteam.R;

import android.view.ViewGroup;
import android.widget.TextView;


public class LinearLayoutContain {
	
	

	static public void setText(ViewGroup ll, String sText) {

		TextView tv = null;

		if (ll != null) {
			tv = (TextView) ll.findViewById(R.id.tv_1);
			if (tv != null) {
				tv.setText(sText);
			}
		}
	}

}

package com.etech.myteam.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class NewListView {

	public static void setListViewHeightBasedOnChildren(ListView listView) { 
        ListAdapter listAdapter = listView.getAdapter();  
        if (listAdapter == null) { 
            return; 
        }   
        int totalHeight = 0; 
        for (int i = 0; i < listAdapter.getCount(); i++) { 
            View listItem = listAdapter.getView(i, null, listView); 
            listItem.measure(0, 0); 
            totalHeight += listItem.getMeasuredHeight();
        }   
        ViewGroup.LayoutParams params = listView.getLayoutParams(); 
        params.height = totalHeight;
        //+ (listView.getDividerHeight() * (listAdapter.getCount() - 1)); 
        listView.setLayoutParams(params); 
	}
	
	public static OnScrollListener scroll = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
           switch (scrollState) {   // 当不滚动时
           case OnScrollListener.SCROLL_STATE_IDLE:
              // TODO coding here dosometing
              break;
           }
        }      
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
              int visibleItemCount, int totalItemCount) {
            // TODO coding here dosometing
        }
      };
}

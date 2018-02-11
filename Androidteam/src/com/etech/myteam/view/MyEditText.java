package com.etech.myteam.view;

import org.json.JSONObject;

import com.etech.myteam.R;

import android.widget.EditText;

public class MyEditText{
	
	public void setMyEdit(EditText et,JSONObject obj){
		if(obj.has("Color")){
			setBackColor(et, obj.optInt("Color"));
		}else{
			setBackColor(et, R.color.yin_bo);
		}
		if(obj.has("HintText")){
			setHint(et, obj.optString("HintText"));
		}else{
			setHint(et, "请输入...");
		}
		if(obj.has("TextColor")){
			setTextColor(et, obj.optInt("TextColor"));
		}else{
			setTextColor(et, R.color.qing_hui);
		}
		if(obj.has("Text")){
			setText(et, obj.optString("Text"));
		}else{
			setText(et, "");
		}
		setPadding(et);
		
	}
	
	//设置背景颜色
	private void setBackColor(EditText et, int color){
		et.setBackgroundColor(color);
	} 
	
	//设置提示字符
	private void setHint(EditText et, String text){
		et.setHint(text);
	}
	
	//设置字体颜色
	private void setTextColor(EditText et, int color){
		et.setTextColor(color);
	}
	
	//设置字体
	private void setText(EditText et, String text){
		et.setText(text);
	}
	
	//设置字符边距
	private void setPadding(EditText et){
		et.setPadding(10, 10, 10, 10);//��������
	}

}

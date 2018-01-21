package com.etech.myteam.common;

import android.widget.EditText;

public class isEdit {
	
	//禁止编辑功能
	public static void notEdit(EditText et){
		et.setFocusable(false);
		et.setFocusableInTouchMode(false);
	}
	
	//允许编辑功能
	public static void yesEdit(EditText et){
		et.setFocusable(true);
		et.setFocusableInTouchMode(true);
	}

}

package com.etech.myteam.common;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class StringToJSON {
	
	static public JSONObject toJSONObject(String sRecv) {
		JSONObject json = null;
		try{
			json = new JSONObject(sRecv);
		} catch (Exception e) {
			Log.e("toJSONObject", "error");
		}
		return json;
	}
	
	static public JSONArray toJSONArray(String sRecv) {
		JSONArray json = null;
		try{
			json = new JSONArray(sRecv);
		} catch (Exception e) {
			Log.e("toJSONArray", "error");
		}
		return json;
	}

}

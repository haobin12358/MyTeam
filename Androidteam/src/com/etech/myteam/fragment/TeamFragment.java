package com.etech.myteam.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.etech.myteam.R;
import com.etech.myteam.activity.InforActivity;
import com.etech.myteam.activity.MainActivity;
import com.etech.myteam.activity.TeamActivity;
import com.etech.myteam.adapter.CompetitionAdapter;
import com.etech.myteam.adapter.StudentAdapter;
import com.etech.myteam.adapter.TeacherAdapter;
import com.etech.myteam.adapter.TeamAdapter;
import com.etech.myteam.common.HttpgetEntity;
import com.etech.myteam.common.HttppostEntity;
import com.etech.myteam.common.StringToJSON;
import com.etech.myteam.entity.MyTeamEntity;
import com.etech.myteam.entity.StudentListEntity;
import com.etech.myteam.global.AppConst;

public class TeamFragment extends Fragment{
	private int index = 0;
	private String Uid;
	private int Utype = 101;
	private String TEid;
	private String url = "http://"+AppConst.sServerURL ;
	private ListView team_list;
//	private TextView tv_1,tv_2,tv_3;
	private EditText ed_Tetname, ed_Tename, ed_Teleader,
	ed_Tecname, ed_Teclevel, ed_Tecno;
	private Button btn_add, btn_search, btn_join;
	private BaseAdapter adapter;	
	private int page_num = 1;
	private List<MyTeamEntity> entitys = new ArrayList<MyTeamEntity>();
	private Spinner spinner, sp_Isfull;
	private String tetName, tename, teleader, tecname, teclevel, tecno;
	private String result_info, postResult;
	private HttppostEntity postEntity;
	private List<Integer> data_list = new ArrayList<Integer>();
	private List<String> is_full_list = new ArrayList<String>();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getBd();
		View view = inflater.inflate(R.layout.fragment_team_info, container, false);
		
		get_info();
		while (true) {
			if (result_info != null){
				break;
			}
		} 
		
		init(view);
		Log.e("Info", "init over");
		
		
		return view;
	}
	
	private void init(View view){
		ed_Tetname = (EditText) view.findViewById(R.id.ed_Tetname);
		ed_Tename = (EditText) view.findViewById(R.id.ed_Tename);		
		ed_Teleader = (EditText) view.findViewById(R.id.ed_Teleader);		
		ed_Tecname = (EditText) view.findViewById(R.id.ed_Tecname);		
		ed_Teclevel = (EditText) view.findViewById(R.id.ed_Teclevel);
		ed_Tecno = (EditText) view.findViewById(R.id.ed_Tecno);
		
		btn_add = (Button)view.findViewById(R.id.btn_add);
		btn_search = (Button)view.findViewById(R.id.btn_search);
		sp_Isfull = (Spinner)view.findViewById(R.id.sp_Isfull);
		spinner = (Spinner)view.findViewById(R.id.spinner1);
		team_list = (ListView) view.findViewById(R.id.team_list);
		is_full_list.add("已满员");
		is_full_list.add("未满员");
		is_full_list.add("");
		ArrayAdapter<String> is_full_adapter = new ArrayAdapter<String>(
				getActivity(),R.layout.spinner_item, is_full_list);
		sp_Isfull.setAdapter(is_full_adapter);
		initdata();
		btn_add.setOnClickListener(changeInfo);
		Log.e("start i", "doit");
		team_list.setOnItemClickListener(doit);
		Log.e("end","doit");
	}
	private void getBd(){
		Uid = ((MainActivity)getActivity()).getUid();
		Utype = ((MainActivity)getActivity()).getUtype();
		index = ((MainActivity)getActivity()).getIndex();
	}
	
	//邀请、请求加入的btn的监听事件
    private OnItemClickListener doit = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			//跳转
			if (view.getId() == R.id.btn_add){
				//拼接加入url
				String addUrl = "";
			}
			Log.e("doit","start");
			Intent it = new Intent(getActivity(), TeamActivity.class);
			TEid = entitys.get(position).getTEid();
			it.putExtra("Uid", Uid);
			it.putExtra("Utype", Utype);
			it.putExtra("TEid", TEid);
			it.putExtra("index", index);
			startActivity(it);
			getActivity().finish();
			Log.e("doit", "end");
		}
    };
    
    //团队search 监听器 
    private OnClickListener changeInfo = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			page_num = 1;
			setData();
		}

	};
	
	public void setData(){
		page_num = 1;					
		tetName = ed_Tetname.getText().toString();
		tename = ed_Tename.getText().toString();
		teleader = ed_Teleader.getText().toString();
		tecname = ed_Tecname.getText().toString();
		teclevel = ed_Teclevel.getText().toString();
		tecno = ed_Tecno.getText().toString();		
		result_info = null;
		get_info();
		while (true){
			if (result_info != null){
				break;
			}
		}
		initdata();
	}
	
	private void postText(){
		try{
			JSONObject obj = new JSONObject();
			String post_url = "";
			postResult = postEntity.doPost(obj, url);
		}catch (Exception e){
			e.printStackTrace();
			Log.e("Post error", "post method error.");
		}
	}
	
	public void initdata(){
		JSONObject jsonobj = null;
		try{
			jsonobj = StringToJSON.toJSONObject(result_info);
			if (jsonobj.optInt("status") == 200){
//				int count = (Integer) jsonobj.get("count");
				int count = 11;
				if (data_list.size() !=0){
					data_list.clear();
				}
				for(int i=1;i <= (count/10) + 1;i++) {
					data_list.add(i);
				}
				if (entitys.size() !=0){
					entitys.clear();
				}
				
				JSONArray data = jsonobj.getJSONArray("team_list");
				for (int i = 0; i <data.length(); i++){
					JSONObject item = data.getJSONObject(i);
					MyTeamEntity entity = new MyTeamEntity();
					entity.setClevel(item.optInt("Clevel"));
					entity.setCname(item.optString("Cname"));
					entity.setCno(item.optInt("Cno"));
					entity.setIsfull(item.optInt("isFull") == 1? "已满员":"未满员");
					entity.setTEid(item.optString("TEid"));
					entity.setTeleader(item.optString("TEleader"));
					entity.setTEname(item.optString("TEname"));
					entity.setTetname(item.optString("TEteachername"));
					entitys.add(entity);
					}
				ArrayAdapter<Integer> arr_adapter = new ArrayAdapter<Integer>(
						getActivity(),R.layout.spinner_item,data_list);				
				spinner.setAdapter(arr_adapter);
				adapter = new TeamAdapter(entitys, getActivity());
				team_list.setAdapter(adapter);
			}
			else if(jsonobj.optInt("status") == 404){
				new AlertDialog.Builder(getActivity())
					.setTitle(R.string.ti_xing)
					.setMessage(R.string.xi_tong_yi_chang)
					.show();
			}else if(jsonobj.optInt("status") == 405){
			
				new AlertDialog.Builder(getActivity())
					.setTitle(R.string.ti_xing)
					.setMessage(jsonobj.optString("messages"))
					.show();
			}
			
		}catch (Exception e) {
				 e.printStackTrace();
				 Log.e("get error", result_info);
			}
			
	}
	
	public void get_info(){
		new Thread(){
			public void run(){
				getText();
			}
		}.start();
	}
	
	public int getindex(int n){
		if (n == 0 || n == 1 || n == 2) {
			return n;
		}else{
			return 0;
		}
	}
	//封装数据传输
	private void getText(){
		HttpgetEntity httpget = new HttpgetEntity();
		try {			
			String getInfoUrl = getUrl(); //获取url
			result_info = httpget.doGet(getInfoUrl);
			Log.e("result", result_info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("post", "error");
			e.printStackTrace();
		}
	}
	
	/*通过infoType 以及筛选框的内容获取url*/
	private String getUrl(){		
		StringBuffer get_info_url = new StringBuffer();
		get_info_url.append(url).append("/team/allteam")
		.append("?page_size=10&page_num=").append(page_num)
		.append("&Uid=").append(Uid).append("&Utype=").append(Utype);						
		
		Log.e("url", get_info_url.toString());
		
		return get_info_url.toString();
	}
	
}
package com.etech.myteam.common;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;

public class HttpdeleteEntity {
	
	public static String doPost(JSONObject obj, String url)
            throws Exception {
        String result = null;
        // 获取HttpClient对象
        HttpClient httpClient = new DefaultHttpClient();
        // 新建HttpPost对象
        HttpDelete httpDelete = new HttpDelete(url);
        /*
        if (obj != null) {
            // 设置字符集
            StringEntity entity = new StringEntity(obj.toString(), "utf-8");
            // 设置参数实体
            httpDelete.setEntity(entity);
        }
*/
        /*// 连接超时
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
        // 请求超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                3000);*/
        // 获取HttpResponse实例
        HttpResponse httpResp = httpClient.execute(httpDelete);
        Log.e("status",httpResp.getStatusLine().getStatusCode()+" ");
        // 判断是够请求成功
        if (httpResp.getStatusLine().getStatusCode() == 200) {
            // 获取返回的数据
            result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
        } else {
            Log.e("HttpPost", "HttpPost方式请求失败");
        }

        return result;
    }

}

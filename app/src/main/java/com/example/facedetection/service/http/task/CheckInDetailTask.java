package com.example.facedetection.service.http.task;

import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.facedetection.data.vo.CheckInDetailVO;
import com.example.facedetection.data.vo.CheckInItemVO;
import com.example.facedetection.service.http.ApiService;
import com.example.facedetection.util.SharedPreferencesUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CheckInDetailTask extends AsyncTask<String, Void, CheckInDetailVO> {

    private static final String TAG = "LoginTask";
    private Exception exception;

    protected CheckInDetailVO doInBackground(String ...params) {
    
        OkHttpClient client = new OkHttpClient();

        String url  = ApiService.baseUrl + "checkIn/" + params[0];

        // 创建一个请求 Builder
        Request.Builder builder = new Request.Builder();
        // 创建一个 request
        Request request = builder.url(url).build();
        HttpUrl.Builder urlBuilder = request.url().newBuilder();
        urlBuilder.addQueryParameter("userId", SharedPreferencesUtils.getString(SharedPreferencesUtils.USER_ID));
        builder.url(urlBuilder.build());

        Response response = null;
        String responseString;
        try {
            response = client.newCall(builder.build()).execute();
            responseString = response.body().string();

            Log.d(TAG, "login: response " + responseString);

        } catch (IOException e) {
            e.printStackTrace();
            
            try {
                Log.e(TAG, "error: " + response.body().string());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;

        }

        if (response.code() == 200) {

            JSONObject jsonObject = JSON.parseObject(responseString);
            
            CheckInDetailVO checkInDetailVO = JSON.parseObject(jsonObject.toJSONString(), CheckInDetailVO.class);
    
            Log.d(TAG, "checkInDetailVO " + checkInDetailVO);
            
            return checkInDetailVO;

        } else {
            return null;
        }
    }
    
    protected void onPostExecute(List<CheckInItemVO> checkInItemVOS) {
            // TODO: check this.exception
            // TODO: do something with the feed
    }
}

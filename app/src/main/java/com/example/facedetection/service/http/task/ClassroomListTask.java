package com.example.facedetection.service.http.task;

import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.facedetection.data.vo.CheckInItemVO;
import com.example.facedetection.data.vo.ClassRoomItemVO;
import com.example.facedetection.service.http.ApiService;
import com.example.facedetection.util.SharedPreferencesUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ClassroomListTask extends AsyncTask<String, Void, List<ClassRoomItemVO>> {

    private static final String TAG = "ClassroomListTask";
    private Exception exception;

    protected List<ClassRoomItemVO> doInBackground(String ...params) {

        OkHttpClient client = new OkHttpClient();

        String url  = ApiService.baseUrl + "classroom/list";


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

            Log.d(TAG, "response " + responseString);

        } catch (IOException e) {
            e.printStackTrace();

            Log.e(TAG, "error: " + response);
            return null;

        }

        if (response.code() == 200) {

            JSONArray jsonObject = JSON.parseArray(responseString);

            List<ClassRoomItemVO> classRoomItemVOList = JSON.parseArray(jsonObject.toJSONString(), ClassRoomItemVO.class);

            Log.d(TAG, "ClassroomListTask " + classRoomItemVOList);

            return classRoomItemVOList;

        } else {
            return null;
        }
    }

    protected void onPostExecute(List<ClassRoomItemVO> classRoomItemVOS) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
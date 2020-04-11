package com.example.facedetection.service.http.task;

import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.facedetection.data.vo.CheckInItemVO;
import com.example.facedetection.data.vo.User;
import com.example.facedetection.service.http.ApiService;
import com.example.facedetection.util.SharedPreferencesUtils;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class CheckInItemTask extends AsyncTask<String, Void, List<CheckInItemVO>> {

    private static final String TAG = "CheckInItemTask";
    private Exception exception;

    protected List<CheckInItemVO> doInBackground(String ...params) {

        OkHttpClient client = new OkHttpClient();

        String url  = ApiService.baseUrl + "checkIn/getCheckInList";


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

            List<CheckInItemVO> checkInItemVOList = JSON.parseArray(jsonObject.toJSONString(), CheckInItemVO.class);

            Log.d(TAG, "checkInItemVOList " + checkInItemVOList);

            return checkInItemVOList;

        } else {
            return null;
        }
    }

    protected void onPostExecute(List<CheckInItemVO> checkInItemVOS) {
            // TODO: check this.exception
            // TODO: do something with the feed
    }
}

package com.example.facedetection.service.http.task;

import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.facedetection.data.model.LoggedInUser;
import com.example.facedetection.data.vo.User;
import com.example.facedetection.service.http.ApiService;
import com.example.facedetection.util.SharedPreferencesUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UpdateStudentStatusTask extends AsyncTask<String, Void, String> {

    private static final String TAG = "LoginTask";
    private Exception exception;

    protected String doInBackground(String ...params) {

        OkHttpClient client = new OkHttpClient();

        String url  = ApiService.baseUrl + "checkIn/updateStudentStatus";

        Log.d(TAG, "login: " +url);

        RequestBody body =  new FormBody.Builder()
                .add("checkInId", params[0])
                .add("studentId", params[1])
                .add("status",  params[2])
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = null;
        String responseString;
        try {
            response = client.newCall(request).execute();
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

            return "更新成功";

        } else {
            return null;
        }

    }

    protected void onPostExecute(LoggedInUser loggedInUser) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
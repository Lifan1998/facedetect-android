package com.example.facedetection.service.http.task;

import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.facedetection.data.vo.User;
import com.example.facedetection.data.model.LoggedInUser;
import com.example.facedetection.service.http.ApiService;
import com.example.facedetection.util.SharedPreferencesUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginTask extends AsyncTask<String, Void, LoggedInUser> {

    private static final String TAG = "LoginTask";
    private Exception exception;

    protected LoggedInUser doInBackground(String ...params) {

        OkHttpClient client = new OkHttpClient();

        String url  = ApiService.baseUrl + "user/login";

        Log.d(TAG, "login: " +url);

        RequestBody body =  new FormBody.Builder()
                .add("password", params[1])
                .add("account", params[0])
                .add("type",  params[2])
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

            JSONObject jsonObject = JSON.parseObject(responseString);
            User user = null;
            user = JSON.parseObject(jsonObject.toJSONString(), User.class);

            Log.d(TAG, "login: user " + user);

            LoggedInUser loggedInUser = new LoggedInUser();
            loggedInUser.setDisplayName(user.getUsername());
            loggedInUser.setUserId(user.getId() + "");
            SharedPreferencesUtils.putBoolean(SharedPreferencesUtils.IS_LOGIN, true);
            SharedPreferencesUtils.putString(SharedPreferencesUtils.USER_ID, user.getId() + "");
            SharedPreferencesUtils.setUser(user);
            return loggedInUser;

        } else {
            return null;
        }

    }

    protected void onPostExecute(LoggedInUser loggedInUser) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}

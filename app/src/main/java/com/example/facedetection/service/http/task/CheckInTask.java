package com.example.facedetection.service.http.task;

import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.facedetection.data.model.LoggedInUser;
import com.example.facedetection.data.vo.User;
import com.example.facedetection.service.http.ApiService;
import com.example.facedetection.service.http.request.FaceDetectMultifaceRequest;
import com.example.facedetection.util.HttpUtils;
import com.example.facedetection.util.SharedPreferencesUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class CheckInTask extends AsyncTask<String, Void, Object> {

    private static final String TAG = "CheckInTask";
    private Exception exception;

    protected Object doInBackground(String ...params) {

        FaceDetectMultifaceRequest multifaceRequest = JSON.parseObject(params[0], FaceDetectMultifaceRequest.class);

        OkHttpClient client = new OkHttpClient();

        String url = ApiService.baseUrl;
        if (multifaceRequest.isCreateCheckIn()) {
            url += "createCheckIn";
        } else {
            url += "updateCheckIn";
        }

        Log.d(TAG, "CheckInTask: " +url);

        RequestBody body = HttpUtils.getOkHttpRequest(multifaceRequest);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = null;
        String responseString;
        try {
            response = client.newCall(request).execute();
            responseString = response.body().string();

            Log.d(TAG, "CheckInTask: response " + responseString);

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

            Log.d(TAG, "CheckInTask " + responseString);

            return responseString;

        } else {
            return "";
        }

    }

    protected void onPostExecute(LoggedInUser loggedInUser) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}

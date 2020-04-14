package com.example.facedetection.service.http.task;

import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.facedetection.data.Result;
import com.example.facedetection.data.model.LoggedInUser;
import com.example.facedetection.data.vo.User;
import com.example.facedetection.service.http.ApiService;
import com.example.facedetection.service.http.request.FaceDetectMultifaceRequest;
import com.example.facedetection.util.HttpUtils;
import com.example.facedetection.util.SharedPreferencesUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class CheckInTask extends AsyncTask<String, Void, Result> {

    private static final String TAG = "CheckInTask";
    private Exception exception;

    protected Result doInBackground(String ...params) {

        FaceDetectMultifaceRequest multifaceRequest = JSON.parseObject(params[0], FaceDetectMultifaceRequest.class);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        String url = ApiService.baseUrl + "checkIn";
        if (multifaceRequest.isCreateCheckIn()) {
            url += "/createCheckIn";
        } else {
            url += "/updateCheckIn";
        }

        Log.d(TAG, "CheckInTask: " +url);

        RequestBody body = HttpUtils.getOkHttpRequest(multifaceRequest);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = null;
        String responseString;
        JSONObject responseBody;
        try {
            response = client.newCall(request).execute();
            responseString = response.body().string();
            responseBody = JSON.parseObject(responseString);
            Log.d(TAG, "CheckInTask: response " + responseString);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "error: " + response.message());
            return new Result.Error(new RuntimeException(response.message()));

        }

        if (response.code() == 200) {

            Log.d(TAG, "CheckInTask " + responseBody);

            return new Result.Success(responseBody.getString("checkInId"));

        } else {
            Log.v(TAG, response.message());
            return new Result.Error(new RuntimeException(responseBody.getString("message")));
        }

    }

    protected void onPostExecute(LoggedInUser loggedInUser) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}

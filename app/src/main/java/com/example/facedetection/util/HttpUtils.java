package com.example.facedetection.util;

import com.alibaba.fastjson.JSON;
import com.example.facedetection.service.http.request.FaceDetectMultifaceRequest;

import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HttpUtils {
    public static final MediaType JSONType = MediaType.parse("application/json; charset=utf-8");
    public static RequestBody getOkHttpRequest(FaceDetectMultifaceRequest request) {

        String jsonSring = JSON.toJSONString(request);
        return RequestBody.create(JSONType, jsonSring);
    }
}

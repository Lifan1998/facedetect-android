package com.example.facedetection.util;

import com.alibaba.fastjson.JSON;
import com.example.facedetection.service.http.request.FaceDetectMultifaceRequest;

import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class HttpUtils {
    public static RequestBody getOkHttpRequest(FaceDetectMultifaceRequest request) {

        String jsonSring = JSON.toJSONString(request);
        Map<String, String> params = JSON.parseObject(jsonSring, Map.class);
        FormBody.Builder build = new FormBody.Builder();


        Iterator iterator = params.keySet().iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            build.add(key, params.get(key));
        }

        return build.build();
    }
}

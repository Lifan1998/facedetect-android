package com.example.facedetection.service.http;

public class ApiService {

    public static String localhost = "http://10.0.2.2:8081/";
    public static String remoteHost = "http://106.14.141.90:8081/";
    public static String host = "http://192.168.199.159:8081/";
    public static String baseUrl;


    static {
        baseUrl = remoteHost;
    }
}

package com.example.facedetection.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtils {

    public static final String IS_LOGIN = "isLogin";
    public static final String USER_ID = "userId";
    public static final String TOKEN = "token";

    private static SharedPreferences sharedPreferences;
    public static void init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("data", MODE_PRIVATE);
        }
    }
    public static SharedPreferences getShardPreferences() {
        return sharedPreferences;
    }

    public static String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public static Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public static void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }
}

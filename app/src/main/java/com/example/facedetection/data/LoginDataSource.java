package com.example.facedetection.data;

import android.util.Log;

import com.example.facedetection.data.model.LoggedInUser;
import com.example.facedetection.service.http.task.LoginTask;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private static final String TAG = "LoginDataSource";

    public Result<LoggedInUser> login(String username, String password) {

        Log.d(TAG, "login: " + username + "  " + password);

        LoggedInUser loggedInUser = null;
        try {
            loggedInUser = new LoginTask().execute(username, password, "2").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (loggedInUser != null) {

            Log.d(TAG, "login: " + loggedInUser);
            return new Result.Success<>(loggedInUser);
        } else {

            Log.d(TAG, "login: " + loggedInUser);
            return new Result.Error(new IOException("Error logging in"));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}

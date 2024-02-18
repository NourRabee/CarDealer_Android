package com.example.androidproject;

import android.content.Context;
import android.content.SharedPreferences;

public class LoggedInUser {
    private static LoggedInUser sharedInstance = null;

    public User user = new User();

    private LoggedInUser() {
    }

    public static LoggedInUser getInstance() {
        if (sharedInstance == null) {
            synchronized (LoggedInUser.class) {
                if (sharedInstance == null) {
                    sharedInstance = new LoggedInUser();
                }
            }
        }
        return sharedInstance;
    }

}
package com.example.firebasealldemo.utils.imageUtil;

import android.app.Application;
import android.content.Context;

import com.example.firebasealldemo.constant.Constants;

/**
 * Created by seeker on 2017/4/25.
 */

public class MainApplication  extends Application{
    static Context ctx;

    public static Context getAppContext() {
        return  ctx;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ctx=MainApplication.this.getApplicationContext();
        String chatList = Constants.ChatList;
    }
}

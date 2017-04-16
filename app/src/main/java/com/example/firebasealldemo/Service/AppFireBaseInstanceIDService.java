package com.example.firebasealldemo.Service;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by 瑜哥 on 2017/4/16.
 */

public class AppFireBaseInstanceIDService extends FirebaseInstanceIdService{

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: id服务已经启动");
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        if (token != null) {
            Log.d(TAG, "onTokenRefresh: "+token);
        }
    }

}

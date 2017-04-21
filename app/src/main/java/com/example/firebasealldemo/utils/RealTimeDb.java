package com.example.firebasealldemo.utils;

import android.content.Context;

import com.example.firebasealldemo.Constants;
import com.example.firebasealldemo.bean.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by seeker on 2017/4/21.
 */

public class RealTimeDb {
     Context ctx;
    private static RealTimeDb realTimeDb;
    public final DatabaseReference dbRef;



    private RealTimeDb(Context ctx) {
        this.ctx = ctx;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbRef = database.getReference(Constants.realTimeDb_refence).child(Constants.Users);

   }

    public static RealTimeDb getInstance(Context ctx) {
        if (realTimeDb == null) {
            synchronized ("a") {
                if (realTimeDb == null) {
                    realTimeDb=new RealTimeDb(ctx);
                }
            }
        }
        return realTimeDb;
    }

    public RealTimeDb savaUserInfo(String userid,User user) {
        dbRef.child(userid).setValue(user);
        return realTimeDb;
    }
}

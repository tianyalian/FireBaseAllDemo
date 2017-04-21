package com.example.firebasealldemo.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.firebasealldemo.Constants;
import com.example.firebasealldemo.bean.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void sendStingTodb(String content) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        dbRef = database.getReference(Constants.realTimeDb_refence).child(Constants.Users);
        DatabaseReference message  = database.getReference("message");
        message.setValue("hellow Firebase db");
        message.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Toast.makeText(ctx, ""+value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

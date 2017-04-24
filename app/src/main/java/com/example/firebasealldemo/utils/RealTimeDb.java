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


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            User value = dataSnapshot.getValue(User.class);
            Toast.makeText(ctx, "提交成功!", Toast.LENGTH_SHORT).show();
            if (dataChangelistener != null) {
                dataChangelistener.onDataChange(value);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(ctx, "提交失败,请重试...", Toast.LENGTH_SHORT).show();
        }
    };

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


    public void sendStingTodb(String content) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
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


    /**
     * 设置用户信息
     * @param userid
     * @param user 改变的值
     * @return RealTimeDb
     */
    public RealTimeDb savaUserInfo(String userid, User user) {
        getUserRef(userid).setValue(user);
        return realTimeDb;
    }

    /**
     * dbRef.addValueEventListener 第一次加载和数据改变都会执行
     * @param userid
     * @return
     */
    public DatabaseReference getUserRef(String userid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("users").child(userid);
        dbRef.addValueEventListener(valueEventListener);
        return dbRef;
    }


    /**
     * 用户信息回调接口
     */
    UserDataChange dataChangelistener;
    public interface UserDataChange{
        void onDataChange(User user);
    }

    public void setOnUserDataChagne(UserDataChange dataChange) {
        this.dataChangelistener = dataChange;
    }

    /**
     * 获取用户信息  第一次获取信息和信息改变都是一个回调
     * @param dataChange 用户信息回调接口
     */
    public RealTimeDb getUserData(UserDataChange dataChange) {
        setOnUserDataChagne(dataChange);
        return realTimeDb;
    }

}

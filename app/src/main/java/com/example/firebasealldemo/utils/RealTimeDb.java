package com.example.firebasealldemo.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.firebasealldemo.Constants;
import com.example.firebasealldemo.bean.SessionBean;
import com.example.firebasealldemo.bean.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by seeker on 2017/4/21.
 */

public class RealTimeDb {
     Context ctx;
    private static RealTimeDb realTimeDb;
    public final DatabaseReference dbRef;
    private final FirebaseDatabase database;


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
        database = FirebaseDatabase.getInstance();
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


    //测试存储数据使用
    public void sendStingTodb(String content) {
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
        DatabaseReference dbRef = database.getReference(Constants.Users).child(userid);
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

    /**
     * 获取会话列表信息
     * @param userid
     * @return
     */
    public DatabaseReference getSessionRef(String userid) {
        DatabaseReference dbRef = database.getReference(Constants.MessageList).child(userid);
        dbRef.addValueEventListener(valueEventListener);
        return dbRef;
    }

    public void updataSession(String username, String content) {
        DatabaseReference reference = database.getReference(Constants.Messages).child(username);
        ArrayList<SessionBean> list= new ArrayList<>();
        SessionBean sessionBean = new SessionBean("hellow "+username+"!", "", "", "ake", System.currentTimeMillis() + "");
        SessionBean sessionBean1 = new SessionBean("hellow ake!", "", "", username, System.currentTimeMillis() + "");
        list.add(sessionBean);
        list.add(sessionBean1);
        reference.setValue(list);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(ctx, "添加成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

package com.example.firebasealldemo.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.firebasealldemo.bean.ChatListBean;
import com.example.firebasealldemo.bean.SessionBean;
import com.example.firebasealldemo.bean.User;
import com.example.firebasealldemo.constant.Constants;
import com.example.firebasealldemo.interf.ChatListDataChange;
import com.example.firebasealldemo.interf.MessageDataChange;
import com.example.firebasealldemo.interf.UserDataChange;
import com.example.firebasealldemo.listener.MyValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by seeker on 2017/4/21.
 */

public class RealTimeDb {
     Context ctx;
    private static RealTimeDb realTimeDb;
    public final DatabaseReference dbRef;
    private final FirebaseDatabase database;
    private final MyValueEventListener valueEventListener;


    private RealTimeDb(Context ctx) {
        this.ctx = ctx;
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference(Constants.realTimeDb_refence).child(Constants.Users);
        String chatList = Constants.ChatList;
        valueEventListener = new MyValueEventListener(ctx);
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
        message.addValueEventListener(valueEventListener);
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
     * 设置用户信息改变的监听
     * @param dataChange
     */
    public void setOnUserDataChagne(UserDataChange dataChange) {
        valueEventListener.dataChangelistener = dataChange;
    }

    /**
     * 设置会话信息改变的监听
     * @param messageDataChange
     */
    public void setOnMessageDataChange( MessageDataChange messageDataChange) {
        valueEventListener.messageDataChange = messageDataChange;
    }

    /**
     * 设置会话列表改变的监听
     * @param chatListDataChange
     */
    public void setOnChatListDataChange(ChatListDataChange chatListDataChange) {
        valueEventListener.chatListDataChange = chatListDataChange;
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
     * 获取会话列表信息的引用
     * @param userid
     * @return
     */
    public DatabaseReference getChatListRef(String userid) {
        DatabaseReference dbRef = database.getReference(Constants.ChatList).child(userid);
        dbRef.addValueEventListener(valueEventListener);
        return dbRef;
    }

    /**
     * 获取用户信息的引用
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
     * 会话信息的引用
     * @param messageid
     * @return
     */
    public DatabaseReference getMessageRef(String messageid) {
        DatabaseReference dbRef = database.getReference(Constants.Messages).child(messageid);
        dbRef.addValueEventListener(valueEventListener);
        return dbRef;
    }

    /**
     * 更新会话信息
     * @param username
     * @param content
     */
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

    public void updataChatList(String username){
        DatabaseReference reference = database.getReference(Constants.ChatList).child(username);
        ArrayList<ChatListBean> list= new ArrayList<>();
//        ChatListBean itemBean = new ChatListBean(list);
//        list.add(itemBean);.
//        reference.setValue(list);
//        reference.addChildEventListener();
    }



}

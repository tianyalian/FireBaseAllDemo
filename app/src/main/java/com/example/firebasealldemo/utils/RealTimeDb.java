package com.example.firebasealldemo.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.firebasealldemo.bean.ChatListItemBean;
import com.example.firebasealldemo.bean.SessionBean;
import com.example.firebasealldemo.bean.User;
import com.example.firebasealldemo.constant.Constants;
import com.example.firebasealldemo.interf.ChatListDataChange;
import com.example.firebasealldemo.interf.MessageDataChange;
import com.example.firebasealldemo.interf.UserDataChange;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

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
            DatabaseReference zzamu = dataSnapshot.getRef();
            String callback = zzamu.zzcra().toString();
            callback.contains("chatlist");
            Log.d(TAG, "onDataChange: "+dataSnapshot.toString());
            Toast.makeText(ctx, "私は戻ってきました !", Toast.LENGTH_SHORT).show();
            if (callback.contains(Constants.Users) && dataChangelistener != null) {
                dataChangelistener.onDataChange(dataSnapshot.getValue(User.class));
            }

            if (callback.contains(Constants.Messages) && messageDataChange != null) {
                messageDataChange.onMessageDataChange();
            }
//
            if (callback.contains(Constants.ChatList) && chatListDataChange != null ) {
                chatListDataChange.onChatListDataChange(null);
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
        String chatList = Constants.ChatList;
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
     * 用户信息回调接口
     */
    UserDataChange dataChangelistener;

    /**
     * 会话信息改变的监听
     */
    MessageDataChange messageDataChange;

    /**
     * 会话列表改变的监听
     */
    ChatListDataChange chatListDataChange;

    /**
     * 设置用户信息改变的监听
     * @param dataChange
     */
    public void setOnUserDataChagne(UserDataChange dataChange) {
        this.dataChangelistener = dataChange;
    }

    /**
     * 设置会话信息改变的监听
     * @param messageDataChange
     */
    public void setOnMessageDataChange( MessageDataChange messageDataChange) {
        this.messageDataChange = messageDataChange;
    }

    /**
     * 设置会话列表改变的监听
     * @param chatListDataChange
     */
    public void setOnChatListDataChange(ChatListDataChange chatListDataChange) {
        this.chatListDataChange = chatListDataChange;
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
     * @param userid
     * @return
     */
    public DatabaseReference getMessageRef(String userid) {
        DatabaseReference dbRef = database.getReference(Constants.Messages).child(userid);
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
        ArrayList<ChatListItemBean> list= new ArrayList<>();
        ChatListItemBean itemBean = new ChatListItemBean("","去哪里吃饭?","1426548624","","","");
        list.add(itemBean);
//        reference.setValue(list);
//        reference.addChildEventListener();
    }



}

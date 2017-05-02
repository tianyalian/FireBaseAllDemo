package com.example.firebasealldemo.listener;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.firebasealldemo.bean.ChatListBean;
import com.example.firebasealldemo.bean.SessionBean;
import com.example.firebasealldemo.bean.User;
import com.example.firebasealldemo.constant.Constants;
import com.example.firebasealldemo.interf.ChatListDataChange;
import com.example.firebasealldemo.interf.MessageDataChange;
import com.example.firebasealldemo.interf.UserDataChange;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by seeker on 2017/4/27.
 */

public class  MyValueEventListener implements ValueEventListener {

    public MyValueEventListener(Context context) {
        this.context = context;
        String messages = Constants.Messages;
    }

    private Context context;
    /**
     * 用户信息回调接口
     */
    public UserDataChange dataChangelistener;

    /**
     * 会话信息改变的监听
     */
    public MessageDataChange messageDataChange;

    /**
     * 会话列表改变的监听
     */
    public ChatListDataChange chatListDataChange;

    public MyValueEventListener( Context context,ChatListDataChange chatListDataChange
                  ,MessageDataChange messageDataChange, UserDataChange dataChangelistener
    ) {
        this.context = context;
        this.messageDataChange = messageDataChange;
        this.dataChangelistener = dataChangelistener;
        this.chatListDataChange = chatListDataChange;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        DatabaseReference zzamu = dataSnapshot.getRef();
        String callback = zzamu.zzcra().toString();
        Log.d(TAG, "onDataChange: "+dataSnapshot.toString());
        Toast.makeText(context, "他大姨妈!", Toast.LENGTH_SHORT).show();
        //用户信息回调
        if (callback.contains(Constants.Users) && dataChangelistener != null) {
            dataChangelistener.onDataChange(dataSnapshot.getValue(User.class));
        }
        //会话内容回调
        if (callback.contains(Constants.Messages) && messageDataChange != null) {
            GenericTypeIndicator<List<SessionBean>> t = new GenericTypeIndicator<List<SessionBean>>() {};
            List<SessionBean> value = dataSnapshot.getValue(t);
            messageDataChange.onMessageDataChange(value);
        }
        //会话列表回调
        if (callback.contains(Constants.ChatList) && chatListDataChange != null ) {
            GenericTypeIndicator<List<ChatListBean>> t = new GenericTypeIndicator<List<ChatListBean>>() {};
            List<ChatListBean> list=dataSnapshot.getValue(t);
            chatListDataChange.onChatListDataChange(list);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Toast.makeText(context, "提交失败,请重试...", Toast.LENGTH_SHORT).show();
    }
}

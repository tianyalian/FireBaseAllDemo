package com.example.firebasealldemo.bean;

import java.io.Serializable;

/**
 * Created by seeker on 2017/4/24.
 */

public class ChatListBean implements Serializable{
    public ChatListBean() {
    }

    public ChatListBean(String title, String lastMessage, String timestamp, String member, String fridlogo, String messageid) {
        this.title = title;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
        this.member = member;
        this.fridlogo = fridlogo;
        this.messageid = messageid;
    }

    public String title,lastMessage,timestamp,member,fridlogo,messageid;


}

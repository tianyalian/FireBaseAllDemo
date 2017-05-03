package com.example.firebasealldemo.bean;

/**
 * Created by seeker on 2017/4/18.
 */

public class SessionBean {
    public SessionBean() {
    }

    public String content,img_receive,img_send,name,timestamp,userId,friendsId;
    public boolean isReceive;


    public SessionBean(String content, String img_receive, String img_send,
                       String name,String timestamp , boolean isReceive,String userId,String friendsId) {
        this.content = content;
        this.img_receive = img_receive;
        this.img_send = img_send;
        this.name=name;
        this.timestamp = timestamp;
        this.isReceive = isReceive;
        this.userId = userId;
        this.friendsId = friendsId;
    }


}

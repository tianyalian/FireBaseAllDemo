package com.example.firebasealldemo.bean;

/**
 * Created by seeker on 2017/4/27.
 */

public class chat {

    public chat() {
    }

    public chat(String fridlogo, String lastMessage, String member, String messageid, String timestamp, String title) {
        this.fridlogo = fridlogo;
        this.lastMessage = lastMessage;
        this.member = member;
        this.messageid = messageid;
        this.timestamp = timestamp;
        this.title = title;
    }

    /**
         * fridlogo :
         * lastMessage : 该吃午饭了
         * member : 小丽
         * messageid :
         * timestamp : 14276845248
         * title :
         */

        public String fridlogo;
        public String lastMessage;
        public String member;
        public String messageid;
        public String timestamp;
        public String title;

}

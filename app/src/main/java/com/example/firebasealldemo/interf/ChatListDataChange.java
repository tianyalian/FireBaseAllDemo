package com.example.firebasealldemo.interf;

import com.example.firebasealldemo.bean.ChatListBean;

import java.util.List;

/**
 * Created by seeker on 2017/4/26.
 */

public interface ChatListDataChange {
    void onChatListDataChange( List<ChatListBean> list);
}

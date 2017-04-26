package com.example.firebasealldemo.interf;

import com.example.firebasealldemo.bean.ChatListItemBean;

import java.util.List;

/**
 * Created by seeker on 2017/4/26.
 */

public interface ChatListDataChange {
    void onChatListDataChange(List<ChatListItemBean> list);
}

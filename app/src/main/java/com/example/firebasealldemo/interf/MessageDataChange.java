package com.example.firebasealldemo.interf;

import com.example.firebasealldemo.bean.SessionBean;

import java.util.List;

/**
 * Created by seeker on 2017/4/26.
 */

public interface MessageDataChange {
    void onMessageDataChange(List<SessionBean> value);
}

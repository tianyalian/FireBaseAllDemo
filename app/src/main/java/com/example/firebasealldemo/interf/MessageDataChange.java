package com.example.firebasealldemo.interf;

import com.example.firebasealldemo.bean.SessionBean;
import com.example.firebasealldemo.constant.Constants;

import java.util.List;

/**
 * Created by seeker on 2017/4/26.
 */

public interface MessageDataChange {
    String messages = Constants.Messages;
    void onMessageDataChange(List<SessionBean> value);
}

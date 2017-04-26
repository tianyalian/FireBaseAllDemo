package com.example.firebasealldemo.interf;

import com.example.firebasealldemo.bean.User;

/**
 * 用户信息接口
 * Created by seeker on 2017/4/26.
 */
public interface UserDataChange {
    void onDataChange(User user);
}

package com.example.firebasealldemo.activity.session;

import android.content.Context;

import com.example.firebasealldemo.mvp.BasePresenter;
import com.example.firebasealldemo.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SessionContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}

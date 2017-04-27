package com.example.firebasealldemo.fragment.chat;

import com.example.firebasealldemo.mvp.BasePresenter;
import com.example.firebasealldemo.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 747327606@qq.com
 */

public class ChatContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
       public void  initPageView(android.view.View view);
    }
}

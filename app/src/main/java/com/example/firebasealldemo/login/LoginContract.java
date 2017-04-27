package com.example.firebasealldemo.login;

import com.example.firebasealldemo.mvp.BasePresenter;
import com.example.firebasealldemo.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 747327606@qq.com
 */

public class LoginContract {
    interface View extends BaseView {

    }

    interface  Presenter extends BasePresenter<View> {
         public void creatAccount(String account,String pwd);
    }
}

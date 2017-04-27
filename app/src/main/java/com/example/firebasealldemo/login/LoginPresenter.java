package com.example.firebasealldemo.login;

import com.example.firebasealldemo.mvp.BasePresenterImpl;
import com.google.firebase.auth.FirebaseAuth;

/**
 * MVPPlugin
 *  邮箱 747327606@qq.com
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter{

        private FirebaseAuth mAuth;
    @Override
    public void creatAccount(String account,String pwd) {
        mAuth = FirebaseAuth.getInstance();

    }
}

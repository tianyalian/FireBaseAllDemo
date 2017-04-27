package com.example.firebasealldemo.mvp;

/**
 * MVPPlugin
 *  邮箱 747327606@qq.com
 */

public interface  BasePresenter <V extends BaseView>{
    void attachView(V view);

    void detachView();
}

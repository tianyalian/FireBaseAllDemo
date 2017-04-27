package com.example.firebasealldemo.mvp;

/**
 * MVPPlugin
 *  邮箱 747327606@qq.com
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V>{
    protected V mView;
    @Override
    public void attachView(V view) {
        mView=view;
    }

    @Override
    public void detachView() {
        mView=null;
    }
}

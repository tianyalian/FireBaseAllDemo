package com.example.firebasealldemo.fragment.chat;


import android.view.View;

import com.example.firebasealldemo.R;
import com.example.firebasealldemo.mvp.MVPBaseFragment;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ChatFragment extends MVPBaseFragment<ChatContract.View, ChatPresenter> implements ChatContract.View {

    @Override
    public int getLayoutResId() {
        return R.layout.activity_session;
    }

    @Override
    public void initView(View view) {
        mPresenter.initPageView(view);
    }

    @Override
    public void initListener() {
        mPresenter.initListener();
    }

    @Override
    public void initData() {

    }
}

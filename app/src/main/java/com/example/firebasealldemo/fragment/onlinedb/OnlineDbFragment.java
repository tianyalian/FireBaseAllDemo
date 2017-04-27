package com.example.firebasealldemo.fragment.onlinedb;


import android.view.View;

import com.example.firebasealldemo.R;
import com.example.firebasealldemo.mvp.MVPBaseFragment;

/**
 * MVPPlugin
 *  邮箱 747327606@qq.com
 */

public class OnlineDbFragment extends MVPBaseFragment<OnlineDbContract.View, OnlineDbPresenter> implements OnlineDbContract.View {

    @Override
    public int getLayoutResId() {
        return R.layout.onlinedb_fragment;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}

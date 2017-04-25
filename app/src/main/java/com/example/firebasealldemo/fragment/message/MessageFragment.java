package com.example.firebasealldemo.fragment.message;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.firebasealldemo.R;
import com.example.firebasealldemo.adapter.SessionListAdapter;
import com.example.firebasealldemo.bean.ChatListItemBean;
import com.example.firebasealldemo.mvp.MVPBaseFragment;

import java.util.ArrayList;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MessageFragment extends MVPBaseFragment<MessageContract.View, MessagePresenter> implements MessageContract.View, View.OnClickListener {


    private RecyclerView recycle_view;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initView(View view) {
        recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        ArrayList<ChatListItemBean> list = new ArrayList<ChatListItemBean>();
        SessionListAdapter adapter = new SessionListAdapter(context, list);
        recycle_view.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.button2:
//
//                break;
//
//            case R.id.button3:
//
//
//                break;
        }
    }


}

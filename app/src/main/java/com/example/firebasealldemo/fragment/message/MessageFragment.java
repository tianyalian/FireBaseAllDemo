package com.example.firebasealldemo.fragment.message;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.firebasealldemo.R;
import com.example.firebasealldemo.adapter.SessionListAdapter;
import com.example.firebasealldemo.bean.ChatListItemBean;
import com.example.firebasealldemo.constant.Constants;
import com.example.firebasealldemo.interf.ChatListDataChange;
import com.example.firebasealldemo.mvp.MVPBaseFragment;
import com.example.firebasealldemo.utils.RealTimeDb;
import com.example.firebasealldemo.utils.imageUtil.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 会话列表页面
 *  邮箱 784787081@qq.com
 */

public class MessageFragment extends MVPBaseFragment<MessageContract.View, MessagePresenter> implements MessageContract.View, View.OnClickListener {


    private RecyclerView recycle_view;
    private ArrayList<ChatListItemBean> chatlist;

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
        RealTimeDb instance = RealTimeDb.getInstance(getActivity());
        instance.getChatListRef(SPUtil.getString(Constants.UserID, ""));
        instance.setOnChatListDataChange(new ChatListDataChange() {

            private SessionListAdapter adapter;

            @Override
            public void onChatListDataChange(List<ChatListItemBean> list) {
                if (adapter == null) {
                    adapter = new SessionListAdapter(context, chatlist);
                    recycle_view.setAdapter(adapter);
                } else {

                }
            }
        });
    }

    @Override
    public void initData() {
        chatlist = new ArrayList<ChatListItemBean>();


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

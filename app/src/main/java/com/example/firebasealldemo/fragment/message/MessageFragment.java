package com.example.firebasealldemo.fragment.message;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.firebasealldemo.R;
import com.example.firebasealldemo.activity.session.SessionActivity;
import com.example.firebasealldemo.adapter.SessionListAdapter;
import com.example.firebasealldemo.bean.ChatListBean;
import com.example.firebasealldemo.constant.Constants;
import com.example.firebasealldemo.interf.ChatListDataChange;
import com.example.firebasealldemo.listener.MyDecoration;
import com.example.firebasealldemo.mvp.MVPBaseFragment;
import com.example.firebasealldemo.utils.RealTimeDb;
import com.example.firebasealldemo.utils.imageUtil.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 会话列表页面
 *  邮箱 747327606@qq.com
 */

public class MessageFragment extends MVPBaseFragment<MessageContract.View, MessagePresenter> implements MessageContract.View{


    private RecyclerView recycle_view;
    private ArrayList<ChatListBean> chatlist;
    private SessionListAdapter adapter;
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
        chatlist = new ArrayList<ChatListBean>();
        adapter = new SessionListAdapter(context, chatlist);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recycle_view.setLayoutManager(manager);
        recycle_view.addItemDecoration(new MyDecoration(context,LinearLayoutManager.VERTICAL));
        recycle_view.setAdapter(adapter);
        adapter.setOnItemClickListener(new SessionListAdapter.OnItemClick() {
            @Override
            public void onRootItemclick(int position) {
                click(position);
            }
        });

    }

    private void click(int position) {
        Toast.makeText(context, "position:"+position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), SessionActivity.class);
        intent.putExtra("chatinfo", chatlist.get(position));
        startActivity(intent);
    }

    @Override
    public void initData() {
        RealTimeDb instance = RealTimeDb.getInstance(getActivity());
        instance.getChatListRef(SPUtil.getString(Constants.UserID, ""));
        instance.setOnChatListDataChange(new ChatListDataChange() {
            @Override
            public void onChatListDataChange(List<ChatListBean> list) {
                if (chatlist != null && list!=null) {
                chatlist.clear();
                chatlist.addAll(list);
                adapter.notifyDataSetChanged();
                }
            }
        });
    }

}

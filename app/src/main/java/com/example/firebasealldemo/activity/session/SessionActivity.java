package com.example.firebasealldemo.activity.session;


import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.firebasealldemo.R;
import com.example.firebasealldemo.adapter.SessionAdapter;
import com.example.firebasealldemo.bean.SessionBean;
import com.example.firebasealldemo.constant.Constants;
import com.example.firebasealldemo.interf.MessageDataChange;
import com.example.firebasealldemo.listener.UploadListenerImpl;
import com.example.firebasealldemo.mvp.MVPBaseActivity;
import com.example.firebasealldemo.utils.RealTimeDb;
import com.example.firebasealldemo.utils.StorageUtil;
import com.example.firebasealldemo.utils.imageUtil.SPUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 747327606@qq.com
 */

public class SessionActivity extends MVPBaseActivity<SessionContract.View, SessionPresenter> implements SessionContract.View, View.OnClickListener {


    public EditText etContent;
    public Button btnSend;
    public RecyclerView recyclerView;
    private ArrayList<SessionBean> list;
    private SessionAdapter adapter;
    private RealTimeDb instance;
    int position;
    public static String friendsLogo = "",friendsId;
    private String ortherId;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_session;
    }

    @Override
    public void initView() {
        etContent = (EditText) findViewById(R.id.etContent);
        btnSend = (Button) findViewById(R.id.btnSend);
        recyclerView = (RecyclerView) findViewById(R.id.cvMessage);
    }

    @Override
    public void initListener() {
        list = new ArrayList<>();
        btnSend.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(SessionActivity.this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        adapter = new SessionAdapter(SessionActivity.this, list);
        recyclerView.setAdapter(adapter);
        instance = RealTimeDb.getInstance(SessionActivity.this);
        instance.setOnMessageDataChange(new MessageDataChange() {
            @Override
            public void onMessageDataChange(List<SessionBean> value) {
                if (list != null && value!=null) {

                    if (value.size() > 0) {
                        ortherId = value.get(0).userId;
                        friendsId = value.get(0).friendsId;
                        getLogoUrl(friendsId);
                    }

                    position = value.size();
                    list.clear();
                    list.addAll(value);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        instance.getMessageRef(Constants.messageid);
    }

    @Override
    public void initData() {}

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                submit();
                break;
        }
    }

    public void submit() {
        String s = etContent.getText().toString().toString();
        String userId = SPUtil.getString(Constants.UserID, "");
        if (list.size() > 0) {
            ortherId = list.get(0).userId;
            friendsId = list.get(0).friendsId;
        }
        SessionBean sessionBean = new SessionBean(s,"","","ake", System.currentTimeMillis()+"",userId.equals(ortherId), userId,friendsId);
        instance.updataSession(Constants.messageid, sessionBean,position);
    }

    public void getLogoUrl(String userId) {
        StorageUtil.getInstance().getUrlFromUserId(userId + ".jpeg", new UploadListenerImpl() {
            @Override
            public void onSuccess(Uri uri) {
                friendsLogo = uri.toString();
                adapter.notifyDataSetChanged();
            }
        });
    }



}

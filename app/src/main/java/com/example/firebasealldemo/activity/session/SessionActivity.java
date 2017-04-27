package com.example.firebasealldemo.activity.session;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.firebasealldemo.R;
import com.example.firebasealldemo.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 *  邮箱 747327606@qq.com
 */

public class SessionActivity extends MVPBaseActivity<SessionContract.View, SessionPresenter> implements SessionContract.View, View.OnClickListener {


    public EditText etContent;
    public Button btnSend;
    public RecyclerView recyclerView;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_session;
    }

    @Override
    public void initView() {
        etContent = (EditText) findViewById(R.id.etContent);
        btnSend = (Button) findViewById(R.id.btnSend);
        recyclerView = (RecyclerView)findViewById(R.id.cvMessage);
    }

    @Override
    public void initListener() {
        btnSend.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                submit();
                break;
        }
    }

    public  void submit(){
        String s = etContent.getText().toString().toString();

    }
}

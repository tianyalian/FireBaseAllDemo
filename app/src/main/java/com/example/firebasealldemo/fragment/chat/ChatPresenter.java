package com.example.firebasealldemo.fragment.chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.firebasealldemo.R;
import com.example.firebasealldemo.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ChatPresenter extends BasePresenterImpl<ChatContract.View> implements ChatContract.Presenter, View.OnClickListener {


    public  EditText etContent;
    public  Button btnSend;
    public RecyclerView recyclerView;


    @Override
    public void initPageView(View view) {
        etContent = (EditText) view.findViewById(R.id.etContent);
        btnSend = (Button) view.findViewById(R.id.btnSend);
        recyclerView = (RecyclerView) view.findViewById(R.id.cvMessage);
    }

    public void initListener() {
        btnSend.setOnClickListener(this);

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

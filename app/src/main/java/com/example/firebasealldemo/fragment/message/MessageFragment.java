package com.example.firebasealldemo.fragment.message;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.firebasealldemo.R;
import com.example.firebasealldemo.mvp.MVPBaseFragment;
import com.example.firebasealldemo.utils.RealTimeDb;
import com.google.firebase.iid.FirebaseInstanceId;

import static android.content.ContentValues.TAG;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MessageFragment extends MVPBaseFragment<MessageContract.View, MessagePresenter> implements MessageContract.View, View.OnClickListener {

    private Button btn,test;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initView(View view) {
        btn = (Button) view.findViewById(R.id.button2);
        test = (Button) view.findViewById(R.id.button3);
        test.setOnClickListener(this);
        btn.setOnClickListener(this);
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, token);
                Toast.makeText(context, token, Toast.LENGTH_SHORT).show();
                break;

            case R.id.button3:
                RealTimeDb instance = RealTimeDb.getInstance(context);
                instance.sendStingTodb("");
                break;
        }
    }
}

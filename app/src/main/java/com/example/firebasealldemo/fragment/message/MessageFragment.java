package com.example.firebasealldemo.fragment.message;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.firebasealldemo.R;
import com.example.firebasealldemo.mvp.MVPBaseFragment;
import com.google.firebase.iid.FirebaseInstanceId;

import static android.content.ContentValues.TAG;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MessageFragment extends MVPBaseFragment<MessageContract.View, MessagePresenter> implements MessageContract.View {

    private Button btn;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initView(View view) {
        btn = (Button) view.findViewById(R.id.button2);
    }

    @Override
    public void initListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, token);
                Toast.makeText(context, token, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void initData() {

    }
}

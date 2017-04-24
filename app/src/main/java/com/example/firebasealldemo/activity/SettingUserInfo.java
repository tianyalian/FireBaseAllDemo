package com.example.firebasealldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasealldemo.Constants;
import com.example.firebasealldemo.R;
import com.example.firebasealldemo.bean.User;
import com.example.firebasealldemo.listener.UploadListenerImpl;
import com.example.firebasealldemo.utils.HttpUtil;
import com.example.firebasealldemo.utils.RealTimeDb;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class SettingUserInfo extends AppCompatActivity implements View.OnClickListener {

    private EditText nick;
    private EditText birthday;
    private EditText cellphoneNumber;
    private EditText emailAddress;
    private EditText name;
    private EditText address;
    private ImageView imageView;
    private TextView textView3;
    private Button commit;
    private MainPresenter mainPresenter;
    Uri uri = Uri.parse("content://photo");
    ContentObserver observer = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            uploadPic();
        }
    };
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_layout);
        initView();
        initData();
    }

    private void initData() {
        getContentResolver().registerContentObserver(uri, true, observer);
        mainPresenter = new MainPresenter();
    }

    private void initView() {
        Context ctx = this;
        nick = (EditText) findViewById(R.id.nick);
        commit = (Button) findViewById(R.id.submit);
        birthday = (EditText) findViewById(R.id.birthday);
        cellphoneNumber = (EditText) findViewById(R.id.cellphoneNumber);
        emailAddress = (EditText) findViewById(R.id.emailAddress);
        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
        commit.setOnClickListener(this);
        textView3 = (TextView) findViewById(R.id.textView3);
    }

    private void submit() {
        // validate
//        String nickString = nick.getText().toString().trim();
//        if (TextUtils.isEmpty(nickString)) {
//            Toast.makeText(this, "nick", Toast.LENGTH_SHORT).show();
////            return;
//        }

        String birthdayString = birthday.getText().toString().trim();
//        if (TextUtils.isEmpty(birthdayString)) {
//            Toast.makeText(this, "birthday", Toast.LENGTH_SHORT).show();
////            return;
//        }

        String cellphoneNumberString = cellphoneNumber.getText().toString().trim();
//        if (TextUtils.isEmpty(cellphoneNumberString)) {
//            Toast.makeText(this, "cellphoneNumber", Toast.LENGTH_SHORT).show();
////            return;
//        }

        String emailAddressString = emailAddress.getText().toString().trim();


//        String nameString = name.getText().toString().trim();
//        if (TextUtils.isEmpty(nameString)) {
//            Toast.makeText(this, "name", Toast.LENGTH_SHORT).show();
////            return;
//        }

        String addressString = address.getText().toString().trim();
//        if (TextUtils.isEmpty(addressString)) {
//            Toast.makeText(this, "address", Toast.LENGTH_SHORT).show();
//            return;
//        }

        User user = new User();
//        user.id = Constants.UserID;
//        user.address = addressString;
//        user.name = nameString;
//        user.nick = nickString;
//        user.birthday = birthdayString;
//        user.cellphoneNumber = cellphoneNumberString;
//        user.emailAddress = emailAddressString;
//        user.level = "1";
//        user.friends = new String[]{"甜甜", "安安"};

        user.id = Constants.UserID;
        user.address = "老鸦陈";
        user.name = "ake";
        user.nick = "天涯恋";
        user.birthday = "0510";
        user.cellphoneNumber = "15617771050";
        user.emailAddress = "747327606@qq.com";
        user.level = "1";
        ArrayList<String> list = new ArrayList<>();
        list.add("天天");
        list.add("圆圆");
        user.friends =list;




        uploadData(Constants.UserID, user);

    }

    //上传用户数据
    private void uploadData(String userid,User user) {
//        RealTimeDb.getInstance(SettingUserInfo.this).savaUserInfo(userid, user);
        RealTimeDb.getInstance(SettingUserInfo.this).savaUserInfo(userid, user);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                submit();
                break;

            case R.id.imageView:
        mainPresenter.selectPhoto(SettingUserInfo.this,1,12);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mainPresenter.onMyActivityResult(SettingUserInfo.this,requestCode, resultCode, data);
    }


    /**
     * 上传图片
     */
    private void uploadPic() {
        HttpUtil.getInstance()
                .upLoadFile(Constants.header_refence)
                .setUpLoadListener(new UploadListenerImpl() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(SettingUserInfo.this, "Upload IMG Failed,Try Again!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(SettingUserInfo.this, "Upload Success!", Toast.LENGTH_SHORT).show();
                        mainPresenter.reFreshHeader(taskSnapshot.getDownloadUrl().toString(), ctx,imageView);
                    }
                });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(observer);
    }
}

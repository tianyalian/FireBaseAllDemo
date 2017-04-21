package com.example.firebasealldemo.activity;

import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.firebasealldemo.Constants;
import com.example.firebasealldemo.R;
import com.example.firebasealldemo.fragment.chat.ChatFragment;
import com.example.firebasealldemo.fragment.message.MessageFragment;
import com.example.firebasealldemo.fragment.onlinedb.OnlineDbFragment;
import com.example.firebasealldemo.listener.ItemSelectListener;
import com.example.firebasealldemo.listener.UploadListenerImpl;
import com.example.firebasealldemo.mvp.MVPBaseActivity;
import com.example.firebasealldemo.utils.HttpUtil;
import com.example.firebasealldemo.view.CircleImageView;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends MVPBaseActivity<MainContract.View, MainPresenter> implements View.OnClickListener,MainContract.View {

    private ChatFragment chat_fragment;
    private  MessageFragment message_fragment;
    private  OnlineDbFragment onlinedb_fragment;
    Uri uri = Uri.parse("content://photo");
    ContentObserver observer = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            uploadPic();
        }
    };

   /**
     * 上传图片
     */
    private void uploadPic() {
        HttpUtil.getInstance()
                .upLoadFile("")
                .setUpLoadListener(new UploadListenerImpl() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(MainActivity.this, "Upload IMG Failed,Try Again!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mPresenter.reFreshHeader(taskSnapshot.getDownloadUrl().toString(),ctx,civ_user_head);
            }
        });


    }

    private FragmentManager supportFragmentManager;
    private Toolbar toolbar;
    private CircleImageView civ_user_head;

   @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        getContentResolver().registerContentObserver(uri, true, observer);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout
                , null, R.string.open_draw, R.string.close_draw);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        civ_user_head = (CircleImageView) findViewById(R.id.civ_user_head);
        civ_user_head.setOnClickListener(this);
        chat_fragment = new ChatFragment();
        onlinedb_fragment = new OnlineDbFragment();
        message_fragment = new MessageFragment();
        supportFragmentManager = getSupportFragmentManager();
        ItemSelectListener selectListener =new ItemSelectListener(supportFragmentManager,
                chat_fragment,message_fragment,onlinedb_fragment );
        navigation.setOnNavigationItemSelectedListener(selectListener);
        supportFragmentManager.beginTransaction().
                add(R.id.content, chat_fragment)
                .add(R.id.content, message_fragment)
                .add(R.id.content, onlinedb_fragment)
                .show(message_fragment)
                .hide(chat_fragment)
                .hide(onlinedb_fragment)
                .commit();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        StorageReference storageRef = HttpUtil.getInstance().getStorageRef(Constants.header_refence);
        HttpUtil.getInstance().getUrlFromRef(storageRef,new UploadListenerImpl(){
            @Override
            public void onSuccess(Uri uri) {
                super.onSuccess(uri);
                 mPresenter.reFreshHeader(uri.toString(),ctx,civ_user_head);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.civ_user_head:
                mPresenter.selectPhoto(MainActivity.this, 1, 22);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onMyActivityResult(MainActivity.this,requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(observer);
    }
}
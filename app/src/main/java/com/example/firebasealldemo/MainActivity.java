package com.example.firebasealldemo;

import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.firebasealldemo.fragment.chat.ChatFragment;
import com.example.firebasealldemo.fragment.message.MessageFragment;
import com.example.firebasealldemo.fragment.onlinedb.OnlineDbFragment;
import com.example.firebasealldemo.mvp.MVPBaseActivity;
import com.example.firebasealldemo.view.CircleImageView;

public class MainActivity extends MVPBaseActivity<MainContract.View, MainPresenter> implements View.OnClickListener,MainContract.View {

    private ChatFragment chat_fragment;
    private  MessageFragment message_fragment;
    private  OnlineDbFragment onlinedb_fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    supportFragmentManager.beginTransaction()
                            .hide(chat_fragment)
                            .hide(onlinedb_fragment)
                            .hide(message_fragment)
                            .show(message_fragment)
                            .commit();

                    return true;
                case R.id.navigation_dashboard:
                    supportFragmentManager.beginTransaction()
                            .hide(message_fragment)
                            .hide(onlinedb_fragment)
                            .hide(chat_fragment)
                            .show(chat_fragment)
                            .commit();
                    return true;
                case R.id.navigation_notifications:
                    supportFragmentManager.beginTransaction()
                            .hide(message_fragment)
                            .hide(chat_fragment)
                            .hide(onlinedb_fragment)
                            .show(onlinedb_fragment)
                            .commit();
                    return true;
            }
            return false;
        }

    };

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

    }

    private FragmentManager supportFragmentManager;
    private Toolbar toolbar;
    private CircleImageView civ_user_head;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        initView();
//    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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

    }

    private final int SELECT_PHOTO = 11;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.civ_user_head:
                mPresenter.selectPhoto(MainActivity.this, 1, 22);
                break;
        }
    }
    private String  base_path = Environment.getExternalStorageDirectory().getPath()+"/";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onMyActivityResult(MainActivity.this,requestCode, resultCode, data);
    }
}

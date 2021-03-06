package com.example.firebasealldemo.activity;

import android.graphics.Color;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.firebasealldemo.R;
import com.example.firebasealldemo.constant.Constants;
import com.example.firebasealldemo.fragment.chat.ChatFragment;
import com.example.firebasealldemo.fragment.message.MessageFragment;
import com.example.firebasealldemo.fragment.onlinedb.OnlineDbFragment;
import com.example.firebasealldemo.listener.ItemSelectListener;
import com.example.firebasealldemo.mvp.MVPBaseActivity;
import com.example.firebasealldemo.view.CircleImageView;

public class MainActivity extends MVPBaseActivity<MainContract.View, MainPresenter> implements View.OnClickListener,MainContract.View {

    private ChatFragment chat_fragment;
    private  MessageFragment message_fragment;
    private  OnlineDbFragment onlinedb_fragment;



    private FragmentManager supportFragmentManager;
    private Toolbar toolbar;
    private CircleImageView civ_user_head;
    TextView   tv_name ;

   @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout
                , null, R.string.open_draw, R.string.close_draw);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        civ_user_head = (CircleImageView) findViewById(R.id.civ_user_head);
        tv_name = (TextView) findViewById(R.id.tv_user_name);
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
        String userID = Constants.UserID;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
         mPresenter.reFreshUserInfo(MainActivity.this,tv_name,civ_user_head);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.civ_user_head:
                startActivity(getIntent().setClass(MainActivity.this,SettingUserInfo.class));
                break;
        }
    }
}

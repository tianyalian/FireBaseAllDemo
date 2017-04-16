package com.example.firebasealldemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.firebasealldemo.fragment.chat.ChatFragment;
import com.example.firebasealldemo.fragment.message.MessageFragment;
import com.example.firebasealldemo.fragment.onlinedb.OnlineDbFragment;

public class MainActivity extends AppCompatActivity {

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
                            .show(message_fragment)
                            .hide(chat_fragment)
                            .hide(onlinedb_fragment)
                            .commit();

                    return true;
                case R.id.navigation_dashboard:
                    supportFragmentManager.beginTransaction()
                            .show(chat_fragment)
                            .hide(message_fragment)
                            .hide(onlinedb_fragment)
                            .commit();
                    return true;
                case R.id.navigation_notifications:
                    supportFragmentManager.beginTransaction()
                            .show(onlinedb_fragment)
                            .hide(message_fragment)
                            .hide(chat_fragment)
                            .commit();
                    return true;
            }
            return false;
        }

    };
    private FragmentManager supportFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initView();
    }

    private void initView() {
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

}

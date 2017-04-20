package com.example.firebasealldemo.listener;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.example.firebasealldemo.R;
import com.example.firebasealldemo.fragment.chat.ChatFragment;
import com.example.firebasealldemo.fragment.message.MessageFragment;
import com.example.firebasealldemo.fragment.onlinedb.OnlineDbFragment;

/**
 * Created by seeker on 2017/4/19.
 */

public class ItemSelectListener implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FragmentManager supportFragmentManager;
    private ChatFragment chat_fragment;
    private MessageFragment message_fragment;
    private OnlineDbFragment onlinedb_fragment;

    public ItemSelectListener(FragmentManager supportFragmentManager,
                              ChatFragment chat_fragment,
                              MessageFragment message_fragment,
                              OnlineDbFragment onlinedb_fragment) {
        this.supportFragmentManager = supportFragmentManager;
        this.chat_fragment = chat_fragment;
        this.message_fragment = message_fragment;
        this.onlinedb_fragment = onlinedb_fragment;
    }

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


}


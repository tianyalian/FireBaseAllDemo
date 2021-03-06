package com.example.firebasealldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebasealldemo.constant.Constants;
import com.example.firebasealldemo.mvp.BasePresenterImpl;
import com.example.firebasealldemo.mvp.BaseView;

/**
 * Created by seeker on 2017/4/19.
 */

public class MainContract extends BasePresenterImpl<MainContract.View> {

    public interface View extends BaseView {

    }

    public interface Presenter {
        String userID = Constants.UserID;
        public void  onMyActivityResult(Context context , int requestCode, int resultCode, Intent data);
        public void  selectPhoto(Context context , int count,int requestCode);
        public void reFreshHeader(String url, Context context, ImageView imageView);
        public void reFreshUserInfo(Context context ,TextView tvName,ImageView imageView);
    }
}

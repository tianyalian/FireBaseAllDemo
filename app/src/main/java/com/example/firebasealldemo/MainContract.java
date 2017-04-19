package com.example.firebasealldemo;

import android.content.Context;
import android.content.Intent;

import com.example.firebasealldemo.mvp.BasePresenterImpl;
import com.example.firebasealldemo.mvp.BaseView;

/**
 * Created by seeker on 2017/4/19.
 */

public class MainContract extends BasePresenterImpl<MainContract.View> {

    public interface View extends BaseView {

    }

    public interface Presenter {
        public void  onMyActivityResult(Context context , int requestCode, int resultCode, Intent data);
        public void  selectPhoto(Context context , int count,int requestCode);

    }
}

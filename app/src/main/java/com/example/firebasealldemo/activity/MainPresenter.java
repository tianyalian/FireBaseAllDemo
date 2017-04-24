package com.example.firebasealldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebasealldemo.Constants;
import com.example.firebasealldemo.R;
import com.example.firebasealldemo.bean.User;
import com.example.firebasealldemo.listener.UploadListenerImpl;
import com.example.firebasealldemo.mvp.BasePresenterImpl;
import com.example.firebasealldemo.utils.HttpUtil;
import com.example.firebasealldemo.utils.RealTimeDb;
import com.example.firebasealldemo.utils.imageUtil.GlideImageLoader;
import com.google.firebase.storage.StorageReference;
import com.yanzhenjie.album.Album;

import net.bither.util.NativeUtil;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by seeker on 2017/4/19.
 */

public  class MainPresenter  extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter {
    private String  base_path = Environment.getExternalStorageDirectory().getPath()+"/";
    int MyrequestCode;
    @Override
    public void onMyActivityResult(Context context ,int requestCode, int resultCode, Intent data) {
        if (requestCode == MyrequestCode) {
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> strings = Album.parseResult(data);
                if (strings.size() > 0) {
                    NativeUtil.compressBitmapBackGround(context,strings.get(0),base_path+"0.jpg",40);
                }
            }
        }
    }

    @Override
    public void selectPhoto(Context context, int count, int requestCode) {
        MyrequestCode = requestCode;
        Album.startAlbum((SettingUserInfo)context, requestCode
                , count                                                // 指定选择数量。
                , ContextCompat.getColor(context, R.color.colorPrimary)        // 指定Toolbar的颜色。
                , ContextCompat.getColor(context, R.color.colorPrimaryDark));
    }


    @Override
    public void reFreshHeader(String url, Context context, ImageView imageView) {
        GlideImageLoader.getInstance(context).displayImage(url,imageView);
    }

    @Override
    public void reFreshUserInfo(final Context context , final TextView tvName, final ImageView imageView) {
        StorageReference storageRef = HttpUtil.getInstance().getStorageRef(Constants.header_refence);
        HttpUtil.getInstance().getUrlFromRef(storageRef,new UploadListenerImpl(){
            @Override
            public void onSuccess(Uri uri) {
                super.onSuccess(uri);
               reFreshHeader(uri.toString(),context,imageView);
            }
        });

        RealTimeDb.getInstance(context).getUserData(new RealTimeDb.UserDataChange() {
            @Override
            public void onDataChange(User user) {
                tvName.setText(user.nick);
            }
        }).getUserRef(Constants.UserID);

    }




}

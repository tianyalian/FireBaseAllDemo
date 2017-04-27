package com.example.firebasealldemo.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebasealldemo.R;
import com.example.firebasealldemo.bean.User;
import com.example.firebasealldemo.constant.Constants;
import com.example.firebasealldemo.interf.UserDataChange;
import com.example.firebasealldemo.listener.UploadListenerImpl;
import com.example.firebasealldemo.mvp.BasePresenterImpl;
import com.example.firebasealldemo.utils.HttpUtil;
import com.example.firebasealldemo.utils.RealTimeDb;
import com.example.firebasealldemo.utils.imageUtil.GlideImageLoader;
import com.example.firebasealldemo.utils.imageUtil.SPUtil;
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
        refReshHeader(context,imageView);
        RealTimeDb.getInstance(context).getUserData(new UserDataChange() {
            @Override
            public void onDataChange(User user) {
                if (user != null) {
                   tvName.setText(user.nick);
                    SPUtil.put(context, Constants.UserID,user.id);
                    SPUtil.put(context, Constants.address,user.address);
                    SPUtil.put(context, Constants.createTime,user.createTime);
                    SPUtil.put(context, Constants.level,user.level);
                    SPUtil.put(context, Constants.birthday,user.birthday);
                    SPUtil.put(context, Constants.cellphoneNumber,user.cellphoneNumber);
                    SPUtil.put(context, Constants.emailAddress,user.emailAddress);
                    SPUtil.put(context, Constants.nick,user.nick);
                    SPUtil.put(context, Constants.name,user.name);
                    SPUtil.put(context, Constants.friends,user.friends);
                }
            }
        }).getUserRef(SPUtil.getString(Constants.UserID,"" ));
    }


    private void refReshHeader(final Context context , final ImageView imageView) {
        StorageReference storageRef = HttpUtil.getInstance().getStorageRef(Constants.header_refence);
        HttpUtil.getInstance().getUrlFromRef(storageRef,new UploadListenerImpl(){
            @Override
            public void onSuccess(Uri uri) {
                super.onSuccess(uri);
                reFreshHeader(uri.toString(),context,imageView);
            }
        });
    }



}

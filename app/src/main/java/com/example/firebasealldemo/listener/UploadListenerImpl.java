package com.example.firebasealldemo.listener;

import android.net.Uri;

import com.example.firebasealldemo.utils.HttpUtil;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.UploadTask;

/**
 * Created by seeker on 2017/4/20.
 */

public class UploadListenerImpl implements HttpUtil.UpLoadListener {
    @Override
    public void onFailure(Exception e) {

    }

    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

    }

    @Override
    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

    }

    @Override
    public void onSuccess(Uri uri) {

    }
}

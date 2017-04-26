package com.example.firebasealldemo.utils;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.firebasealldemo.constant.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by seeker on 2017/4/20.
 */

public class HttpUtil {
  private static HttpUtil httpUtil = null;
    private static StorageReference storageRef;

    /**
     * "image/header.jpg"
     * @param refence 上传文件夹的位置和文件名
     */
    public HttpUtil upLoadFile(String refence){
    StorageReference headerImagesRef = storageRef.child(refence);
        try {
        InputStream stream = new FileInputStream(new File(Constants.header_compressed));
        UploadTask task = headerImagesRef.putStream(stream);
        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                if (listener != null) {
                    listener.onSuccess(taskSnapshot);
                }
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (listener != null) {
                    listener.onFailure(e);
                }
            }
        });
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }

        return httpUtil;
    }

    /**
     *
     * @param refence 下载文件夹的位置和文件名
     * @param filePath 存储文件的位置
     * @return
     */
    public HttpUtil downLoadFile(String refence,String filePath) {
        StorageReference fielRef = storageRef.child(refence);
        File file =new File(filePath);
        fielRef.getFile(file).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (listener != null) {
                    listener.onFailure(e);
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                if (listener != null) {
                    listener.onSuccess(taskSnapshot);
                }
            }
        });

        return httpUtil;
    }


    public  static  HttpUtil getInstance() {
        if (httpUtil == null) {
            synchronized ("1") {
                if (httpUtil == null) {
                    httpUtil = new HttpUtil();
                    //访问存储段的第一步是创建一个 FirebaseStorage 实例
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    // Points to the root reference
                    storageRef = storage.getReferenceFromUrl(Constants.ROOT_gs);
                }
            }
        }
        return httpUtil;
    }

    public StorageReference getStorageRef(String childRef) {
        return storageRef.child(childRef);
    }
    /**
     * 上传文件Complete后回调
     */
    public interface UpLoadListener  {
        void onFailure(Exception e);
        void onSuccess(UploadTask.TaskSnapshot taskSnapshot);
        void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot);
        void onSuccess(Uri uri);
    }

    UpLoadListener listener;
    public void setUpLoadListener(UpLoadListener listener) {
        this.listener = listener;
    }

    public void getUrlFromRef(StorageReference ref, final UpLoadListener listener) {
        this.listener = listener;
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                if (listener != null) {
                    listener.onSuccess(uri);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                if (listener != null) {
                    listener.onFailure(exception);
                }
            }
        });
    }

}

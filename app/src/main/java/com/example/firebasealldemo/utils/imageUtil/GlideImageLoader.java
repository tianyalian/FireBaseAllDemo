package com.example.firebasealldemo.utils.imageUtil;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.io.File;

/**
 * Description: GlideImageLoader
 * Data: 2017/4/1 17:32
 *
 * @author: wxw
 */
public class GlideImageLoader implements ImageLoader {

    private  Context context;
    private RequestManager mRequestManager;
    private static GlideImageLoader imageLoader;

    public static GlideImageLoader getInstance(Context context) {
        if (imageLoader == null) {
            synchronized ("1"){
                imageLoader = new GlideImageLoader(context);
            }
        }

        return imageLoader;
    }

    private GlideImageLoader(Context context) {
        this.context = context;
        mRequestManager= Glide.with(context);
    }

    @Override
    public void init(Context context) {
//        if (mRequestManager==null) {
            if(context==null){
                context=context;
            }
            mRequestManager= Glide.with(context);
//        }
    }

    @Override
    public void displayImage(String imageUrl, ImageView imageView, int defaultImage, int errorImage) {
        if(mRequestManager==null){
            mRequestManager=Glide.with(context);
        }
        mRequestManager.load(imageUrl).placeholder(defaultImage).error(errorImage).into(imageView);
    }

     @Override
    public void displayImage(File file, ImageView imageView, int defaultImage, int errorImage) {
        if(file==null){
            displayImage(defaultImage,imageView);
            return;
        }
        if(mRequestManager==null){
            mRequestManager=Glide.with(context);
        }
        mRequestManager.load(file).placeholder(defaultImage).error(errorImage).into(imageView);
    }

    @Override
    public void displayImage(File file, ImageView imageView) {
        if(file==null){
//            mRequestManager.load(defaultImage).into(imageView);
            return;
        }
            if(mRequestManager==null){
            mRequestManager=Glide.with(context);
        }

    }

    @Override
    public void displayImage(File file, ImageView imageView, boolean isNeedDefault) {

    }


    @Override
    public void displayImage(String imageUrl, ImageView imageView) {
        if(mRequestManager==null){
            mRequestManager=Glide.with(context);
        }
        mRequestManager.load(imageUrl).into(imageView);
    }

    @Override
    public void displayImage(String imageUrl, ImageView imageView,boolean isNeedDefault) {
        if(mRequestManager==null){
            mRequestManager=Glide.with(context);
        }
            mRequestManager.load(imageUrl).into(imageView);

    }

    @Override
    public void displayImage(Integer resId, ImageView imageView) {
        if(mRequestManager==null){
            mRequestManager=Glide.with(context);
        }
        mRequestManager.load(resId).into(imageView);
    }
}
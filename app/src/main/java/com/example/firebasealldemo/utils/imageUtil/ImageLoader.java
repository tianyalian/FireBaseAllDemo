package com.example.firebasealldemo.utils.imageUtil;


import android.content.Context;
import android.widget.ImageView;

import java.io.File;

/**
 * 图片加载器功能接口；
 * 添加或者替换新的图片加载器实现该接口即可
 */
public interface ImageLoader {
    /**
     * Init ImageLoader
     */
    void init(Context context);
    /**
     *Show Image
     * @param imageUrl
     * @param imageView
     * @param defaultImage
     * @param errorImage
     */
    void displayImage(String imageUrl, ImageView imageView, int defaultImage, int errorImage);
    /**
     *Show Image
     * @param file
     * @param imageView
     * @param defaultImage
     * @param errorImage
     */
    void displayImage(File file, ImageView imageView, int defaultImage, int errorImage);
    /**
     *Show Image
     * @param file
     * @param imageView
     */
    void displayImage(File file, ImageView imageView);
    /**
     *Show Image
     * @param file
     * @param imageView
     */
    void displayImage(File file, ImageView imageView, boolean isNeedDefault);
    /**
     *Show Image
     * @param imageView
     */
    void displayImage(String imageUrl, ImageView imageView);
    /**
     *Show Image
     * @param imageView
     */
    void displayImage(String imageUrl, ImageView imageView, boolean isNeedDefault);
    /**
     *Show Image
     * @param imageView
     */
    void displayImage(Integer resId, ImageView imageView);

}
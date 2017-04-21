package net.bither.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class NativeUtil {
    private static int DEFAULT_QUALITY = 95;
    private static Thread photoThread;
    private static Context context;

    /**
     * @param bit      bitmap对象
     * @param fileName 指定保存目录名
     * @param optimize 是否采用哈弗曼表数据计算 品质相差5-10倍
     * @Description: JNI基本压缩
     */
    public static void compressBitmap(Bitmap bit, String fileName, boolean optimize) {
        saveBitmap(bit, DEFAULT_QUALITY, fileName, optimize);
    }

    public synchronized static void  compressBitmap(String sourceFilePath, String targetFilePath) {
        // 最大图片大小 150KB
        int maxSize = 150;
        //根据地址获取bitmap
        Bitmap result = getBitmapFromFile(sourceFilePath);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int quality = 45;
//        result.compress(Bitmap.CompressFormat.JPEG, quality, baos);
//        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
//        while (baos.toByteArray().length / 1024 > maxSize) {
//            // 重置baos即清空baos
//            baos.reset();
//            // 每次都减少10
//            quality -= 10;
//            // 这里压缩quality，把压缩后的数据存放到baos中
//            result.compress(Bitmap.CompressFormat.JPEG, quality, baos);
//        }
        // JNI保存图片到SD卡 这个关键
        NativeUtil.saveBitmap(result, quality, targetFilePath, true);
        // 释放Bitmap
        if (!result.isRecycled()) {
            result.recycle();
        }

    }

    public synchronized static void  compressBitmap(String sourceFilePath,
                                                    String targetFilePath,
                                                    int quality) {
        Bitmap result = getBitmapFromFile(sourceFilePath);
        NativeUtil.saveBitmap(result, quality, targetFilePath, true);
        if (!result.isRecycled()) {
            result.recycle();
        }
    }

    public synchronized static void compressBitmapBackGround(Context ctx, final String sourceFilePath,
                                                final String targetFilePath, final int qualty){
        context=ctx;
            photoThread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    compressBitmap(sourceFilePath, targetFilePath,qualty);
                    context.getContentResolver().notifyChange(Uri. parse("content://photo"),null);
                }
            };
            photoThread.start();
    }


    public synchronized static void compressBitmapBackGround(Context ctx,
                                                             final List<String> sourceFilePath
                                                          , final List<String> targetFilePath ){
        context=ctx;

//        ArrayList<String>sourcePath = null;
//        ArrayList<String>targetPath =null;
//
//        Iterator<String> it = sourceFilePath.iterator();
//        while(it.hasNext()){
//            String str = it.next();
//            if(str.contains(".png") || str.contains(".PNG")){
//                if (sourcePath == null) {
//                    sourcePath = new ArrayList<>();
//                } else {
//                    sourcePath.add(str);
//                }
//                it.remove();
//            }
//        }
//
//        it = targetFilePath.iterator();
//        while(it.hasNext()){
//            String str = it.next();
//            if(str.contains(".png") || str.contains(".PNG")){
//                if (targetPath == null) {
//                    targetPath = new ArrayList<>();
//                } else {
//                    targetPath.add(str);
//                }
//                it.remove();
//            }
//        }
//
//        if (sourcePath != null) {
//            for (int i=0;i<sourcePath.size();i++) {
//                    compressPng(sourcePath.get(i),targetPath.get(i));
//            }
//            if (sourceFilePath.size() == 0) {
//                return;
//            }
//        }


        photoThread = new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i=0;i<sourceFilePath.size();i++) {
                    if (sourceFilePath.get(i).contains(".png") || sourceFilePath.get(i).contains(".PNG")) {
                        compressPng(sourceFilePath.get(i),targetFilePath.get(i));
                    } else {
                         compressBitmap(sourceFilePath.get(i), targetFilePath.get(i),40);
                    }
                }
                context.getContentResolver().notifyChange(Uri. parse("content://photo"),null);
            }
        };
        photoThread.start();

    }


    /**
     * 通过文件路径读获取Bitmap防止OOM以及解决图片旋转问题
     *
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapFromFile(String filePath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        BitmapFactory.decodeFile(filePath, newOpts);
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 获取尺寸压缩倍数
        newOpts.inSampleSize = NativeUtil.getRatioSize(w, h);
        newOpts.inJustDecodeBounds = false;//读取所有内容
        newOpts.inDither = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        newOpts.inTempStorage = new byte[32 * 1024];
        Bitmap bitmap = null;
        File file = new File(filePath);
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (fs != null) {
                bitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, newOpts);
                //旋转图片
                int photoDegree = readPictureDegree(filePath);
                if (photoDegree != 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(photoDegree);
                    // 创建新的图片
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                            bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
    /**
     * @param image    bitmap对象
     * @param filePath 要保存的指定目录
     * @Description: 通过JNI图片压缩把Bitmap保存到指定目录
     */
    public static void compressBitmap(Bitmap image, String filePath) {
        // 最大图片大小 1000KB
        int maxSize = 1000;
        // 获取尺寸压缩倍数
        int ratio = NativeUtil.getRatioSize(image.getWidth(), image.getHeight());
        // 压缩Bitmap到对应尺寸
        Bitmap result = Bitmap.createBitmap(image.getWidth() / ratio, image.getHeight() / ratio, Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, image.getWidth() / ratio, image.getHeight() / ratio);
        canvas.drawBitmap(image, null, rect, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        result.compress(Bitmap.CompressFormat.JPEG, options, baos);
        // 循环判断如果压缩后图片是否大于最大值,大于继续压缩
        while (baos.toByteArray().length / 1024  > maxSize) {
            // 重置baos即清空baos
            baos.reset();
            // 每次都减少10
            options -= 10;
            // 这里压缩options%，把压缩后的数据存放到baos中
            result.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        // JNI调用保存图片到SD卡 这个关键
        NativeUtil.saveBitmap(result, options, filePath, true);
        // 释放Bitmap
        if (result != null && !result.isRecycled()) {
            result.recycle();
            result = null;
        }
    }

    public  synchronized static void compressPng(String sourcePath,String savePath){

        Bitmap image = getBitmapFromFile(sourcePath);
        // 压缩Bitmap到对应尺寸
        Bitmap result = Bitmap.createBitmap( (int)(image.getWidth() *0.7), (int)(image.getHeight() *0.7), Config.ARGB_4444);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, (int)(image.getWidth() *0.7),  (int)(image.getHeight() *0.7));
        canvas.drawBitmap(image, null, rect, null);
        try {
            FileOutputStream stream=new FileOutputStream(new File(savePath));
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            result.compress(Bitmap.CompressFormat.PNG, 80, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 计算缩放比
     *
     * @param bitWidth  当前图片宽度
     * @param bitHeight 当前图片高度
     * @return
     * @Description:函数描述
     */
    public static int getRatioSize(int bitWidth, int bitHeight) {
        // 图片最大分辨率
        int imageHeight = 1280;
        int imageWidth = 720;
        // 缩放比
        int ratio = 1;
        // 缩放比,由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        if (bitWidth > bitHeight && bitWidth > imageHeight) {
            // 如果图片宽度比高度大,以宽度为基准
            ratio = bitWidth / imageHeight;
        } else if (bitWidth < bitHeight && bitHeight > imageHeight) {
            // 如果图片高度比宽度大，以高度为基准
            ratio = bitHeight / imageHeight;
        }
        // 最小比率为1
        if (ratio <= 0)
            ratio = 1;
        return ratio;
    }

    /**
     * 调用native方法
     *
     * @param bit
     * @param quality
     * @param fileName
     * @param optimize
     * @Description:函数描述
     */
    private static void saveBitmap(Bitmap bit, int quality, String fileName, boolean optimize) {
        compressBitmap(bit, bit.getWidth(), bit.getHeight(), quality, fileName.getBytes(), optimize);
    }



    /**
     * 调用底层 bitherlibjni.c中的方法
     *
     * @param bit
     * @param w
     * @param h
     * @param quality
     * @param fileNameBytes
     * @param optimize
     * @return
     * @Description:函数描述
     */
    private static native String compressBitmap(Bitmap bit, int w, int h, int quality, byte[] fileNameBytes,
                                                boolean optimize);

    /**
     * 加载lib下两个so文件
     */
    static {
        System.loadLibrary("jpegbither");
        System.loadLibrary("bitherjni");
    }
}

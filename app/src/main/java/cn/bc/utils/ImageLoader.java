package cn.bc.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.baichang.huishoufang.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.*;
import com.bumptech.glide.util.Util;

import java.io.File;
import java.util.concurrent.ExecutionException;

import cn.bc.retrofit.APIConstants;


/**
 * Created by iscod.
 * Time:2016/6/22-10:24.
 */
public class ImageLoader {
    /**
     * 大图
     */
    public static final String BIG_IMAGE = "@!big";
    /**
     * 中图
     */
    public static final String MIDDLE_IMAGE = "@!middle";
    /**
     * 小图
     */
    public static final String SMALL_IMAGE = "@!small";
    /**
     * 原图
     */
    public static final String ORIGINAL_IMAGE = "";

    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param size      图片大小
     * @param imageView
     */
    public static void loadImage(Context context, String url, String size, ImageView imageView) {
        if (Util.isOnMainThread()) {
            Glide.with(context)
                    .load(APIConstants.API_LOAD_IMAGE + url + size)
                    .error(R.mipmap.place_image)
                    .crossFade()
                    //.placeholder(R.mipmap.place_image) 否则第一次进来会不显示图片显示的是占位图
                    .into(imageView);
        }
    }

    /**
     * 加载 圆角控件的图片 会冲突 关掉加载动画即可
     *
     * @param context
     * @param url
     * @param size      图片大小
     * @param imageView
     */
    public static void loadRoundImage(Context context, String url, String size, ImageView imageView) {
        if (Util.isOnMainThread()) {
            Glide.with(context)
                    .load(APIConstants.API_LOAD_IMAGE + url + size)
                    .error(R.mipmap.place_image)
                    .dontAnimate()//不加载动画
                    //.placeholder(R.mipmap.place_image)
                    .into(imageView);
        }

    }

    /**
     * ImageView 加载圆形图片
     *
     * @param context
     * @param url
     * @param size      图片大小
     * @param imageView
     */
    public static void loadCircleHeadImage(Context context, String url, String size, ImageView imageView) {
        if (Util.isOnMainThread()) {
            Glide.with(context)
                    .load(APIConstants.API_LOAD_IMAGE + url + size)
                    .crossFade()
                    //.placeholder(R.mipmap.place_head)
                    .error(R.mipmap.place_head)
                    .into(imageView);
        }

    }

    /**
     * ImageView 加载圆角图片
     *
     * @param context
     * @param url
     * @param size      图片大小
     * @param round     圆角弧度
     * @param imageView
     */
    public static void loadRoundImage(Context context, String url, String size, int round, ImageView imageView) {
        if (Util.isOnMainThread()) {
            Glide.with(context)
                    .load(APIConstants.API_LOAD_IMAGE + url + size)
                    .dontAnimate()
                    .transform(new RoundTransform(context, round))
                    //.placeholder(R.mipmap.place_image)
                    .into(imageView);
        }
    }

    /**
     * 加载本地图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void loadLocationImage(Context context, String path, ImageView imageView) {
        if (Util.isOnMainThread()) {
            Glide.with(context)
                    .load(path)
                    .crossFade()
                    .error(R.mipmap.place_head)
                    .into(imageView);
        }
    }

    /**
     * Glide 下载图片返回文件
     * 异步下载
     *
     * @param context
     * @param path
     * @return
     */
    public static File asyDownloadImage2File(Context context, String path) {
        File file = null;
        try {
            file = Glide.with(context)
                    .load(APIConstants.API_LOAD_IMAGE + path)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)//Target.SIZE_ORIGINAL 原图大笑
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Glide 下载图片返回文件
     * 同步下载
     *
     * @param context
     * @param path
     * @return
     */
    public static File downloadImage2File(Context context, String path) {
        FutureTarget<File> future = Glide.with(context)
                .load(APIConstants.API_LOAD_IMAGE + path)
                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);//Target.SIZE_ORIGINAL 原图大笑
        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Glide 下载图片返回Bitmap
     *
     * @param context
     * @param path
     * @return
     */
    public static Bitmap downloadImage2Bitmap(Context context, String path) {
        Bitmap bitmap = null;
        if (Util.isOnMainThread()) {
            try {
                bitmap = Glide.with(context)
                        .load(APIConstants.API_LOAD_IMAGE + path)
                        .asBitmap()
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)//Target.SIZE_ORIGINAL 原图大笑
                        .get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static void pause(Context context) {
        if (Util.isOnMainThread()) {
            Glide.with(context).pauseRequests();
        }
    }

}

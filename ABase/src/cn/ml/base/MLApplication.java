package cn.ml.base;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.lidroid.xutils.DbUtils;

import java.io.File;

import cn.ml.base.utils.ACache;
import cn.ml.base.utils.MLFolderUtils;
import cn.ml.base.utils.MLStrUtil;
import cn.ml.base.widget.photoGallery.MyImageView;
import cn.ml.base.widget.photoview.PhotoView;

public class MLApplication extends Application {
    private static MLApplication instance;

    // 数据库
    public static DbUtils db;
    // 图片缓存
    public static final String TAG_CACHE = "image_cache";
    // public static final ImageCache IMAGE_CACHE = new ImageCache();
    // public static OnImageCallbackListener imageCallBack;
    public static String DEFAULT_CACHE_FOLDER = "";

    //本地缓存
    public static ACache aCache;

    // 屏幕宽度
    public static int screenWidth;
    // 屏幕高度
    public static int screenHeight;

    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initScreenSize();
        //初始化图片缓存
        //initImageCache();
        // 初始化数据库
        initDB();
        initAcache();
    }

    private void initScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        if (screenWidth > screenHeight) {
            int t = screenHeight;
            screenHeight = screenWidth;
            screenWidth = t;
        }
//        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//        screenWidth = wm.getDefaultDisplay()
//                .getWidth();
//        screenHeight = wm.getDefaultDisplay()
//                .getHeight();
    }

    private void initAcache() {
        //	aCache = ACache.get(instance, "acache");
        aCache = ACache.get(MLFolderUtils.getDiskFile("acache"));
        Log.d("CID", "initAcache: " + MLFolderUtils.getDiskCacheDir("acache"));

    }

//    private void initImageCache() {
//
//        // StringBuilder().append(getCacheDir().getAbsolutePath()).append(File.separator).append("TImageCache").toString();
//        DEFAULT_CACHE_FOLDER = MLFolderUtils.getDiskFile("TImageCache").getAbsolutePath();
//        IMAGE_CACHE.initData(this, TAG_CACHE);
//        IMAGE_CACHE.setContext(this);
//        IMAGE_CACHE.setCacheFolder(DEFAULT_CACHE_FOLDER);
//
//        File p = new File(DEFAULT_CACHE_FOLDER);
//        if (!p.exists() || p.listFiles().length == 0) {
//            IMAGE_CACHE.clear();
//        }
//    }

    public static MLApplication getInstance() {
        return instance;
    }

    private void initDB() {
        db = DbUtils.create(this, "app.db");
        db.configAllowTransaction(true);
        db.configDebug(false);
    }

//    static {
//        /** init icon cache **/
//        imageCallBack = new OnImageCallbackListener() {
//            @Override
//            public void onGetSuccess(String imageUrl, Bitmap loadedImage,
//                                     View view, boolean isInCache) {
//                if (view != null && loadedImage != null) {
//
//                    if (view instanceof MyImageView) {
//                        MyImageView imageView = (MyImageView) view;
//                        imageView.setImageBitmap(loadedImage);
//                        return;
//                    }
//
//                    ImageView imageView = (ImageView) view;
//                    String imageUrlTag = (String) imageView.getTag();
//
//                    // imageView.setImageBitmap(loadedImage);
//
//                    if (ObjectUtils.isEquals(imageUrlTag, imageUrl)) {
//                        imageView.setImageBitmap(loadedImage);
//                        imageView.setScaleType(ScaleType.CENTER_CROP);
//                    }
//
//
//                    if (MLStrUtil.isEmpty(imageUrlTag)) {
//                        imageView.setImageBitmap(loadedImage);
//                        imageView.setScaleType(ScaleType.CENTER_CROP);
//                    }
//                    //缩放PhotoView
//                    if (view instanceof PhotoView) {
//                        imageView.setScaleType(ScaleType.FIT_CENTER);
//                    }
//
//					/*
//                     * if (!isInCache) {
//					 * imageView.startAnimation(getInAlphaAnimation(2000)); }
//					 */
//                }
//            }
//
//            @Override
//            public void onPreGet(String imageUrl, View view) {
//            }
//
//            @Override
//            public void onGetFailed(String imageUrl, Bitmap loadedImage,
//                                    View view, FailedReason failedReason) {
//                Log.e(TAG_CACHE,
//                        new StringBuilder(128).append("get image ")
//                                .append(imageUrl)
//                                .append(" error, failed type is: ")
//                                .append(failedReason.getFailedType())
//                                .append(", failed reason is: ")
//                                .append(failedReason.getCause().getMessage())
//                                .toString());
//                /*
//				 * if (view != null) { ImageView imageView = (ImageView)view;
//				 * imageView.setImageResource(R.drawable.test2); }
//				 */
//            }
//
//            @Override
//            public void onGetNotInCache(String imageUrl, View view) {
//				/*
//				 * if (view != null && view instanceof ImageView) {
//				 * ((ImageView)view).setImageResource(R.drawable.trinea); }
//				 */
//            }
//        };

	/*	IMAGE_CACHE.setCompressListener(new CompressListener() {
            @Override
			public int getCompressSize(String imagePath) {
				if (FileUtils.isFileExist(imagePath)) {
					long fileSize = FileUtils.getFileSize(imagePath) / 1000;
					*//**
     * if image bigger than 100k, compress to 1/(n + 1) width
     * and 1/(n + 1) height, n is fileSize / 100k
     **//*
                    if (fileSize > 100) {
						return (int) (fileSize / 100) + 1;
					}
				}
				return 1;
			}
		});*/
//        IMAGE_CACHE.setOnImageCallbackListener(imageCallBack);
//        IMAGE_CACHE
//                .setCacheFullRemoveType(new RemoveTypeLastUsedTimeFirst<Bitmap>());
//        IMAGE_CACHE.setHttpReadTimeOut(10000);
//        IMAGE_CACHE.setOpenWaitingQueue(true);
//        IMAGE_CACHE.setValidTime(-1);
    // IMAGE_CACHE.setRequestProperty("Connection", "false");
//    }
}

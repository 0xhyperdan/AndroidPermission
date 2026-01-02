package com.baichang.android.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by iscod.
 * Time:2016/11/10-13:35.
 */

public class PhotoUtil {

    private static File tempFile = null;

    /*	  private File sdcardTempFile =new File(Environment.getExternalStorageDirectory(),
                     getPhotoFileName());*/
    public static void choose(Activity activity, int which) {
        //初始化文件路径
        String path = getPhotoFileName(activity);
        tempFile = new File(path);
        Uri outputUri = getUriForFile(activity, tempFile);
        if (which == 1) {
            //选择拍照
            Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 指定调用相机拍照后照片的储存路径
            cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            cameraintent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivityForResult(cameraintent, 101);

        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

            activity.startActivityForResult(intent, 100);
        }
    }

    /**
     * 拍照完成处理
     */
    public static void photoZoom(Activity activity, Uri u) {
        //	saveBitmap(tempFile,AbImageUtil.scaleImg(tempFile, 500, 500));
        Uri uri;
        if (u == null) {
            uri = getUriForFile(activity, tempFile);
        } else {
            uri = u;
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        //保存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, tempFile));
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 600);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, 102);
    }

    /**
     * 拍照完成处理
     */
    public static void photoZoomFree(Activity activity, Uri u) {
        //	saveBitmap(tempFile,AbImageUtil.scaleImg(tempFile, 500, 500));
        Uri uri;
        if (u == null) {
            uri = getUriForFile(activity, tempFile);
        } else {
            uri = u;
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        //保存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, tempFile));
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, 102);
    }

    /**
     * 返回从相册选择的图片  路径
     *
     * @return
     */
    public static String getPhotoPath() {
        if (tempFile == null || !tempFile.exists()) {
            return "";
        }
        return tempFile.getAbsolutePath();
    }


    /**
     * 拍照返回  是否取消
     *
     * @return
     */
    public static boolean IsCancel() {
        if (tempFile == null || !tempFile.exists()) return true;
        return !tempFile.exists();
    }

    /**
     * 清除
     */
    // 使用系统当前日期加以调整作为照片的名称
    private static String getPhotoFileName(Context context) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir == null) {
            cacheDir = context.getCacheDir();
        }
        return cacheDir + "/" + dateFormat.format(date) + ".jpg";
    }

    public static String getPhotoName() {
        String str = "";
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd/HHmmss");
        str += dateFormat.format(date);
        return str;
    }

    public static void saveBitmap(File f, Bitmap bm) {
        if (bm == null) {
            return;
        }
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Uri getUriForFile(Context context, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
        }
        return Uri.fromFile(file);
    }
}

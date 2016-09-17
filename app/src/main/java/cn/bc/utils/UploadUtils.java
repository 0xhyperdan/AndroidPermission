package cn.bc.utils;

import android.content.Context;

import java.util.Random;

import cn.ml.base.utils.MLDialogUtils;

/**
 * Created by iscod.
 * Time:2016/7/21-9:49.
 */
public class UploadUtils {
    private static UploadUtils Instance = null;
    private PutFileToOSS oss = null;
    private Context mContext;
    private UploadResultListener resultCallBack;
    private String keys;
    private String name;
    private String filePath;

    private UploadUtils(Context context, String filePath, UploadResultListener callback) {
        if (oss == null) {
            mContext = context;
            this.filePath = filePath;
            resultCallBack = callback;
            oss = new PutFileToOSS(context);
        }
    }

    private UploadUtils(Context context) {
        if (oss == null) {
            oss = new PutFileToOSS(context);
        }
    }

    public static UploadUtils getInstance(Context context, String filePath, UploadResultListener callback) {
        if (Instance == null) {
            Instance = new UploadUtils(context, filePath, callback);
        }
        return Instance;
    }

    public static UploadUtils getInstance(Context context) {
        if (Instance == null) {
            Instance = new UploadUtils(context);
        }
        return Instance;
    }

    public UploadUtils filePath(String filePath) {
        this.filePath = filePath;
        return Instance;
    }

    public UploadUtils fileName(String name) {
        this.name = name;
        return Instance;
    }

    public UploadUtils CallBack(UploadResultListener callback) {
        resultCallBack = callback;
        return Instance;
    }

    /**
     * 上传图片
     */
    public void UploadImage() {
        keys = "images" + "/" + name + getRandom(9999);
        MLDialogUtils.showProgressDialog(mContext);
        oss.putObjectFromLocalFile(keys, filePath, new UploadResultListener() {
            @Override
            public void uploadSuccess(String key) {
                MLDialogUtils.dismissProgressDialog();
                resultCallBack.uploadSuccess(keys);
                repair();
            }

            @Override
            public void uploadFailed(String msg) {
                MLDialogUtils.dismissProgressDialog();
                resultCallBack.uploadFailed(msg);
                repair();
            }
        });
    }

    /**
     * 上传文件
     */
    public void UploadFile() {
        keys = "files" + "/" + name + getRandom(9999);
        MLDialogUtils.showProgressDialog(mContext);
        oss.putObjectFromLocalFile(keys, filePath, new UploadResultListener() {
            @Override
            public void uploadSuccess(String key) {
                MLDialogUtils.dismissProgressDialog();
                resultCallBack.uploadSuccess(keys);
                repair();
            }

            @Override
            public void uploadFailed(String msg) {
                MLDialogUtils.dismissProgressDialog();
                resultCallBack.uploadFailed(msg);
                repair();
            }
        });
    }

    /**
     * 上传视频
     */
    public void UploadVideo() {
        keys = "videos" + "/" + name + getRandom(9999);
        MLDialogUtils.showProgressDialog(mContext);
        oss.putObjectFromLocalFile(keys, filePath, new UploadResultListener() {
            @Override
            public void uploadSuccess(String key) {
                MLDialogUtils.dismissProgressDialog();
                resultCallBack.uploadSuccess(keys);
                repair();
            }

            @Override
            public void uploadFailed(String msg) {
                MLDialogUtils.dismissProgressDialog();
                resultCallBack.uploadFailed(msg);
                repair();
            }
        });
    }

    private int getRandom(int digit) {
        return new Random().nextInt(digit);
    }

    private void repair() {
        if (Instance != null) {
            Instance = null;
        }
        if (mContext != null) {
            mContext = null;
        }
        if (oss != null) {
            oss = null;
        }
    }
}

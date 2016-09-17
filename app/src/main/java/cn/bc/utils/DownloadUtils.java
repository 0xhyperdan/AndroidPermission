package cn.bc.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by iscod.
 * Time:2016/8/17-10:43.
 */
public class DownloadUtils {
    private static DownloadFileToOSS oss = null;
    private static DownloadUtils Instance = null;
    private String fileUrl;
    private File file;

    private DownloadUtils() {

    }

    /**
     * 获取下载实例
     *
     * @param context
     * @return
     */
    public static DownloadUtils getInstance(Context context) {
        if (Instance == null) {
            Instance = new DownloadUtils();
            if (oss == null) {
                oss = new DownloadFileToOSS(context);
            }
        }
        return Instance;
    }

    /**
     * 设置下载的url
     *
     * @param fileUrl
     * @return
     */
    public DownloadUtils FileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return Instance;
    }

    /**
     * 设置存放的文件
     *
     * @param file
     * @return
     */
    public DownloadUtils File(File file) {
        this.file = file;
        return Instance;
    }

    /**
     * 设置回调接口并执行下载
     *
     * @param callBack
     */
    public void CallBack(final DownloadResultListener callBack) {
        if (TextUtils.isEmpty(fileUrl)) {
            throw new NullPointerException("FileUrl not null,please execute method FileUrl()");
        }
        if (file == null) {
            throw new NullPointerException("File not null,please execute method File()");
        }
        if (callBack == null) {
            throw new NullPointerException("CallBack not null");
        }
        oss.asyncGetObject(fileUrl, file, new DownloadResultListener() {
            @Override
            public void downloadSuccess(File file) {
                callBack.downloadSuccess(file);
                repair();
            }

            @Override
            public void downloadFailed(String error) {
                callBack.downloadFailed(error);
                repair();
            }
        });
    }

    private void repair() {
        if (Instance != null) {
            Instance = null;
        }
        if (oss != null) {
            oss = null;
        }
    }
}

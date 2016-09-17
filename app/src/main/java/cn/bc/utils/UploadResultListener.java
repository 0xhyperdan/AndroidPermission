package cn.bc.utils;

/**
 * Created by iscod.
 * Time:2016/7/21-10:12.
 */
public interface UploadResultListener {
    void uploadSuccess(String key);

    void uploadFailed(String msg);
}

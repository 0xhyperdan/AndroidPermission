package cn.bc.utils;

import java.io.File;

/**
 * Created by iCong on 2016/9/17.
 */
public interface DownloadResultListener {
    void downloadSuccess(File file);
    void downloadFailed(String error);
}

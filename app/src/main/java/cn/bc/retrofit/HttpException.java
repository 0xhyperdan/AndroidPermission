package cn.bc.retrofit;

import android.text.TextUtils;

import java.io.IOException;

/**
 * Created by iscod.
 * Time:2016/6/29-17:23.
 */
public class HttpException extends IOException {
    public HttpException(String detailMessage) {
        super(detailMessage);
    }

    private static final long serialVersionUID = -2199603193956026137L;

    @Override
    public String getMessage() {
        if (TextUtils.isEmpty(super.getMessage()))
            return "请求服务器异常";
        else {
            return super.getMessage();
        }
    }
}

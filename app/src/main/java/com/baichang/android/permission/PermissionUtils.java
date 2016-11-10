package com.baichang.android.permission;

import android.Manifest;
import android.content.Context;
import android.text.TextUtils;

import com.tbruyelle.rxpermissions.RxPermissions;


/**
 * Created by iCong on 2016/9/17.
 */
public class PermissionUtils {
    public static final String CAMERA = Manifest.permission.CAMERA;
    public static final String CALL = Manifest.permission.CALL_PHONE;
    public static final String WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String LOCATION = Manifest.permission.LOCATION_HARDWARE;
    private static RxPermissions rxPermission;

    public PermissionUtils(Context context) {
        if (context == null) {
            throw new NullPointerException("Context not null");
        }
        if (rxPermission == null) {
            rxPermission = RxPermissions.getInstance(context);
        }
    }

    public static PermissionUtils getInstance(Context context) {
        return new PermissionUtils(context);
    }

    public void setPermission(String permission, Listener listener) {
        if (TextUtils.isEmpty(permission)) {
            throw new NullPointerException("permission not null");
        }
        if (listener == null) {
            throw new NullPointerException("PermissionListener not null");
        }
        if (TextUtils.equals(permission, CAMERA)) {
            rxPermission.request(CAMERA).subscribe(listener::isPermission);
        } else if (TextUtils.equals(permission, CALL)) {
            rxPermission.request(CALL).subscribe(listener::isPermission);
        } else if (TextUtils.equals(permission, WRITE)) {
            rxPermission.request(WRITE).subscribe(listener::isPermission);
        } else if (TextUtils.equals(permission, READ)) {
            rxPermission.request(READ).subscribe(listener::isPermission);
        } else if (TextUtils.equals(permission, CONTACTS)) {
            rxPermission.request(CONTACTS).subscribe(listener::isPermission);
        } else if (TextUtils.equals(permission, LOCATION)) {
            rxPermission.request(LOCATION).subscribe(listener::isPermission);
        } else {
            throw new IllegalArgumentException("Please enter the correct permissions");
        }
    }

    public interface Listener {
        void isPermission(boolean grant);
    }
}

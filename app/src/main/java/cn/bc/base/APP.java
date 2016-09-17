package cn.bc.base;

import android.Manifest;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.squareup.leakcanary.LeakCanary;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;

import cn.bc.utils.MLCrashHandler;
import cn.jpush.android.api.JPushInterface;
import cn.ml.base.MLApplication;

public class APP extends MLApplication {

    private static APP instance;

    //环信
    public static Context applicationContext;

    private static String TOKEN = "";
    private static String MKID = "";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        applicationContext = this;
        //极光推送
        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush*/
        //JPushInterface.setDefaultPushNotificationBuilder(new CustomPushNotificationBuilder(getApplicationContext(),));
        //百度地图
        //SDKInitializer.initialize(instance);
        //友盟统计
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        //内存泄漏
        LeakCanary.install(this);
    }

    public static APP getInstance() {
        return instance;
    }

    public static String getToken() {
        return TOKEN;
    }

    public static void setToken(String Token) {
        TOKEN = Token;
    }

    public static void setMkid(String mkid) {
        MKID = mkid;
    }

    public static String getMkid() {
        return MKID;
    }

    public static void cleanMkid() {
        MKID = "";
    }

    public static void cleanToken() {
        TOKEN = "";
    }

    public static void clean() {
        cleanMkid();
        cleanToken();
    }
}

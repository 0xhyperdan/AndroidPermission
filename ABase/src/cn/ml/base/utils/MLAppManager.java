package cn.ml.base.utils;

import java.io.Serializable;
import java.util.Stack;

import cn.ml.base.MLBaseConstants;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class MLAppManager {

    private static Stack<Activity> activityStack;
    private static MLAppManager instance;

    private MLAppManager() {
    }

    /**
     * 单一实例
     */
    public static MLAppManager getAppManager() {
        if (instance == null) {
            instance = new MLAppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 跳转Activity
     *
     * @param act
     * @param cls
     * @param obj
     * @param requestCode
     */
    public static void startActivity(Activity act, Class cls, Object obj, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(act, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //	intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (obj != null) {
            intent.putExtra(MLBaseConstants.TAG_INTENT_DATA, (Serializable) obj);
        }

        if (requestCode != -1) {
            act.startActivityForResult(intent, requestCode);
        } else {
            act.startActivity(intent);
        }
    }

    /**
     * 跳转Activity
     *
     * @param act
     * @param cls
     * @param obj
     * @param requestCode
     */
    public static void startActivity2(Activity act, Class cls, Object obj, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(act, cls);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //	intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (obj != null) {
            intent.putExtra(MLBaseConstants.TAG_INTENT_DATA, (Serializable) obj);
        }

        if (requestCode != -1) {
            act.startActivityForResult(intent, requestCode);
        } else {
            act.startActivity(intent);
        }
    }


    /**
     * 跳转Activity For Frg
     *
     * @param act
     * @param cls
     * @param obj
     * @param requestCode
     */
    public static void startActivityForFrg(Fragment act, Class cls, Object obj, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(act.getActivity(), cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //	intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (obj != null) {
            intent.putExtra(MLBaseConstants.TAG_INTENT_DATA, (Serializable) obj);
        }

        if (requestCode != -1) {
            act.startActivityForResult(intent, requestCode);
        } else {
            act.startActivity(intent);
        }
    }


    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
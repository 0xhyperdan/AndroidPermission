package cn.bc.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;


import java.util.Locale;

import cn.jpush.android.api.JPushInterface;
import cn.ml.base.utils.MLAppManager;
import cn.ml.base.utils.MLDialogUtils;
import cn.ml.base.utils.MLToastUtils;

public class BaseFrag extends Fragment {

    // ==========HTTP Request请求==================
//    protected void loadData(Context context, MLHttpRequestMessage httpMessage, IHttpResultSuccess success) {
//        loadData(context, null, httpMessage, success, null);
//    }
//
//    protected void loadData(Context context, String message, MLHttpRequestMessage httpMessage, IHttpResultSuccess success) {
//        loadData(context, message, httpMessage, success, null);
//    }
//
//    protected void loadData(Context context, Object message, MLHttpRequestMessage httpMessage, IHttpResultSuccess success) {
//        loadData(context, message, httpMessage, success, null);
//    }
//
//    protected void loadData(Context context, Object message, MLHttpRequestMessage httpMessage, IHttpResultSuccess success, IHttpResultError error) {
//        httpMessage.setHttpResultSuccess(success);
//        httpMessage.setHttpResultError(error);
//        httpMessage.mContext = context;
//        MLHttpRequestUtils.loadData(context, message, httpMessage);
//    }

    // ==========加载数据对话框ProgressDialog==================
    public void showProgressDialog(Context context) {
        showProgressDialog(context, null);
    }

    public void showProgressDialog(Context context, String message) {
        //MLDialogUtils.showProgressDialog(context, message);
    }

    public void dismissProgressDialog() {
        MLDialogUtils.dismissProgressDialog();
    }

    // ==========Toast 提示=======================
    public void showMessage(Context contex, Object obj) {
        MLToastUtils.showMessage(contex, obj);
    }

    // 成功Message
    public void showMessageSuccess(Context contex, Object obj) {
        MLToastUtils.showMessageSuccess(contex, obj);
    }

    // 失败Message
    public void showMessageError(Context contex, Object obj) {
        MLToastUtils.showMessageError(contex, obj);
    }

    // 警告Message
    public void showMessageWarning(Context contex, Object obj) {
        MLToastUtils.showMessageWarning(contex, obj);
    }

    // 笑脸Message
    public void showMessageSmile(Context contex, Object obj) {
        MLToastUtils.showMessageSmile(contex, obj);
    }

    // ==========跳转页面=======================
    protected void startAct(Fragment act, Class cls) {
        startAct(act, cls, null, -1);
    }

    protected void startAct(Fragment act, Class cls, Object obj) {
        startAct(act, cls, obj, -1);
    }

    protected void startAct(Fragment act, Class cls, Object obj, int requestCode) {
        MLAppManager.startActivityForFrg(act, cls, obj, requestCode);
    }

    public Context getContext() {
        return getActivity();
    }

    protected Fragment getFragment() {
        return this;
    }

    protected void switchLanguage(String language) {
        // 本地语言设置
        Locale myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        //保存设置语言的类型
//        PreferenceUtil.commitString("language", language);
    }

}

package cn.bc.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;
import cn.ml.base.MLBaseConstants;
import cn.ml.base.utils.MLAppManager;
import cn.ml.base.utils.MLDialogUtils;
import cn.ml.base.utils.MLToastUtils;
import cn.ml.base.utils.ToolsUtil;
import cn.ml.base.utils.SystemBarTintManager;

public class BaseAct extends FragmentActivity {

    //protected static Context mBaseContext;

    private Object mIntentData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 设置标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (!this.isTaskRoot()) { //判断该Activity是不是任务空间的源Activity，“非”也就是说是被系统重新实例化出来
            //如果你就放在launcher Activity中话，这里可以直接return了
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;//finish()之后该活动会继续执行后面的代码，你可以logCat验证，加return避免可能的exception
            }
        }

        //mBaseContext = this;
        initIntentData();

        MLAppManager.getAppManager().addActivity(this);

        //沉浸式状态栏
        initSystemBar();
    }

    @Override
    protected void onPause() {
        //JPushInterface.onPause(this);//极光统计分析
        //友盟统计
        MobclickAgent.onPause(this);
        super.onPause();
//        if (mBaseContext != null) {
//            ToolsUtil.closeSoftInput(mBaseContext);
//        }
        MLDialogUtils.dismissProgressDialog();
    }


    private void initSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 状态栏透明 需要在创建SystemBarTintManager 之前调用。
            setTranslucentStatus(true);

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            // 使StatusBarTintView 和 actionbar的颜色保持一致，风格统一。
            tintManager.setStatusBarTintResource(0x000000000);
            //tintManager.setStatusBarTintResource(R.color.topBar_bg);
            // tintManager.setStatusBarTintDrawable(getResources().getDrawable(R.drawable.bg1));
            // 设置状态栏的文字颜色
            tintManager.setStatusBarDarkMode(false, this);
        }
    }


    //环信使用
    public void back(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        //JPushInterface.onResume(this);//极光统计分析
        //友盟统计
        MobclickAgent.onResume(this);
        super.onResume();
//        if (mBaseContext != null) {
//            ToolsUtil.closeSoftInput(mBaseContext);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MLAppManager.getAppManager().finishActivity(this);
        //APP.IMAGE_CACHE.saveDataToDb(this, APP.TAG_CACHE);
    }

    // 获取传递值
    private void initIntentData() {
        mIntentData = getIntent().getSerializableExtra(
                MLBaseConstants.TAG_INTENT_DATA);
    }

    public Object getIntentData() {
        return mIntentData;
    }

    // ==========HTTP Request请求==================
//    protected void loadData(Context context, MLHttpRequestMessage httpMessage, IHttpResultSuccess success) {
//        //MLHttpRequestUtils.loadData(context, null, httpMessage);
//        loadData(context, "", httpMessage, success, null);
//    }
//
//    protected void loadData(Context context, MLHttpRequestMessage httpMessage, IHttpResultSuccess success, IHttpResultError error) {
//        loadData(context, null, httpMessage, success, error);
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

    // ==========加载数据对话框ProgressDialog===========
    public void showProgressDialog(Context context) {
        showProgressDialog(context, null);
    }

    public void showProgressDialog(Context context, String message) {
        MLDialogUtils.showProgressDialog1(context, message);
    }


    public void dismissProgressDialog() {
        MLDialogUtils.dismissProgressDialog();
    }

    // ==========Toast 提示=========================
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
    protected void startAct(Activity act, Class cls) {
        startAct(act, cls, null, -1);
    }

    protected void startAct(Activity act, Class cls, Object obj) {
        startAct(act, cls, obj, -1);
    }

    protected void startAct(Activity act, Class cls, Object obj, int requestCode) {
        MLAppManager.startActivity(act, cls, obj, requestCode);
    }

    protected void startAct2(Activity act, Class cls) {
        startAct2(act, cls, null, -1);
    }

    protected void startAct2(Activity act, Class cls, Object obj) {
        startAct2(act, cls, obj, -1);
    }

    protected void startAct2(Activity act, Class cls, Object obj, int requestCode) {
        MLAppManager.startActivity2(act, cls, obj, requestCode);
    }
    // =======================================


    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected Activity getAty() {
        return this;
    }
}

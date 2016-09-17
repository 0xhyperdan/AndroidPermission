package cn.ml.base.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.github.ybq.android.spinkit.SpinKitView;

import cn.ml.base.R;

/**
 * Created by iscod.
 * Time:2016/7/2-12:07.
 */
public class LoadingDialog extends Dialog {
    private SpinKitView mSpinKitView;
    private static LoadingDialog loadingDialog;

    private LoadingDialog(Context context, int theme) {
        super(context, theme);
        mSpinKitView = (SpinKitView) findViewById(R.id.spin_kit);
    }

    public static LoadingDialog getInstance(Context context) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(context, R.style.loading_dialog);
            loadingDialog.setContentView(R.layout.loading_layout);
            loadingDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        }
        return loadingDialog;
    }

    public void repair() {
        if (mSpinKitView != null) {
            mSpinKitView = null;
        }
        if (loadingDialog != null) {
            loadingDialog = null;
        }
    }
}

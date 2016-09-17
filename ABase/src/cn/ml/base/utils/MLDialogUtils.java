package cn.ml.base.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;


import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.ml.base.R;
import cn.ml.base.widget.dialog.LoadingDialog;

public class MLDialogUtils {

    protected static ProgressDialog progressDialog = null;
    private static AlertDialog builder = null;
    private static LoadingDialog loadingDialog = null;

    /**
     * 显示加载 ProgressDialog
     *
     * @param context
     */
    public static void showProgressDialog(Context context) {
        if (context == null) return;
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.getInstance(context);
            loadingDialog.setCancelable(false);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        if (!((Activity) context).isFinishing()) {
            //show dialog
            try {
                loadingDialog.show();
            } catch (Exception e) {
                Log.d("Dialog", "error: " + e.getMessage());
            }
        }
    }

    /**
     * 显示加载 ProgressDialog
     *
     * @param context
     * @param message
     */
    public static void showProgressDialog1(Context context, Object message) {
        if (context == null) return;

        String content = ToolsUtil
                .getResourceString(R.string.loading_message);
        if (message != null && message instanceof Integer) {
            content = ToolsUtil.getResourceString((Integer) message);
        } else if (message != null && message instanceof String && !MLStrUtil.isEmpty((String) message)) {
            content = (String) message;
        }

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context,
                    ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(content);
        }
        progressDialog.setMessage(content);

        try {
            progressDialog.show();
        } catch (Exception e) {
        }

    }

    /**
     * dismiss ProgressDialog
     */
    public static void dismissProgressDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
            if (loadingDialog != null) {
                loadingDialog.dismiss();
                loadingDialog.repair();
                loadingDialog = null;
            }
        } catch (Exception e) {
        }

    }


    /**
     * 返回AlertDialog
     *
     * @param mContext
     * @return
     */

    public static AlertDialog.Builder getAlertDialog(Context mContext) {

        if (Build.VERSION.SDK_INT >= 11) {
            return new AlertDialog.Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        } else {
            return new AlertDialog.Builder(mContext);
        }

    }


    public static void setDialogTitleColor(Dialog builder, int color) {
        try {
            Context context = builder.getContext();
            int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = builder.findViewById(divierId);
            divider.setBackgroundColor(context.getResources().getColor(color));

            int alertTitleId = context.getResources().getIdentifier("alertTitle", "id", "android");
            TextView alertTitle = (TextView) builder.findViewById(alertTitleId);
            alertTitle.setTextColor(context.getResources().getColor(color));
        } catch (Exception e) {

        }

    }


    public static void setPickerDividerColor(DatePicker datePicker, int color) {

        // 获取 mSpinners
        LinearLayout llFirst = (LinearLayout) datePicker.getChildAt(0);

        // 获取 NumberPicker
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);

            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    try {
                        pf.set(picker, new ColorDrawable(datePicker.getContext().getResources().getColor(color)));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    public static void getInputDialog(Context context, View source, String title, DialogInterface.OnClickListener queding, DialogInterface.OnClickListener quxiao) {
        AlertDialog builder = MLDialogUtils.getAlertDialog(context)
                .setView(source).setTitle(title).setPositiveButton(context.getResources().getString(R.string.ml_dialog_ok), queding).setNegativeButton(context.getResources().getString(R.string.ml_dialog_cancel), quxiao).create();
        builder.show();
        setDialogTitleColor(builder, R.color.cm_app_color);
    }

    public static void choiceTime(Context context, String date, DatePickerDialog.OnDateSetListener dateSetListener) {
        Calendar c = Calendar.getInstance();
        if (!MLStrUtil.isEmpty(date)) {
            c.setTime(strToDate("yyyy-MM-dd", date));
        }
        DatePickerDialog dialog = new DatePickerDialog(
                context, DatePickerDialog.THEME_HOLO_LIGHT,
                dateSetListener,
                c.get(Calendar.YEAR), // 传入年份
                c.get(Calendar.MONTH), // 传入月份
                c.get(Calendar.DAY_OF_MONTH) // 传入天数
        );
        dialog.show();

        setDialogTitleColor(dialog, R.color.cm_app_color);
        setPickerDividerColor(dialog.getDatePicker(), R.color.cm_app_color);
    }

    public static void choiceTime3(Context context, String date, DialogInterface.OnClickListener wancheng, DialogInterface.OnClickListener quxiao) {
        Calendar c = Calendar.getInstance();
        if (!MLStrUtil.isEmpty(date)) {
            c.setTime(strToDate("yyyy-MM-dd", date));
        }
        DatePickerDialog dialog = new DatePickerDialog(
                context, DatePickerDialog.THEME_HOLO_LIGHT,
                null,
                c.get(Calendar.YEAR), // 传入年份
                c.get(Calendar.MONTH), // 传入月份
                c.get(Calendar.DAY_OF_MONTH) // 传入天数
        );

        //手动设置按钮
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getResources().getString(R.string.ml_dialog_ok), wancheng);
        //取消按钮，如果不需要直接不设置即可
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getResources().getString(R.string.ml_dialog_cancel), quxiao);

        dialog.show();

        setDialogTitleColor(dialog, R.color.cm_app_color);
        setPickerDividerColor(dialog.getDatePicker(), R.color.cm_app_color);
    }

    public static void choiceTime2(Context context, String date, DatePickerDialog.OnDateSetListener dateSetListener) {
        Calendar c = Calendar.getInstance();
        if (!MLStrUtil.isEmpty(date)) {
            c.setTime(strToDate("yyyy", date));
        }
        DatePickerDialog dialog = new DatePickerDialog(
                context, DatePickerDialog.THEME_HOLO_LIGHT,
                dateSetListener,
                c.get(Calendar.YEAR), // 传入年份
                c.get(Calendar.MONTH), // 传入月份
                c.get(Calendar.DAY_OF_MONTH) // 传入天数
        );
        dialog.show();


        DatePicker dp = findDatePicker((ViewGroup) dialog.getWindow().getDecorView());
        if (dp != null) {
            ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
            ((ViewGroup) (((ViewGroup) dp.getChildAt(0)).getChildAt(0)))
                    .getChildAt(1).setVisibility(View.GONE);
        }
        setDialogTitleColor(dialog, R.color.cm_app_color);
        setPickerDividerColor(dialog.getDatePicker(), R.color.cm_app_color);
    }

    private static DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }

    /**
     * 获取items Dialog
     *
     * @param context
     * @param title
     * @param items
     * @param listener
     */
    public static void getDialogItem(Context context, String title, String[] items, final DialogInterface.OnClickListener listener, DialogInterface.OnDismissListener dismissListener) {
        AlertDialog builder = MLDialogUtils.getAlertDialog(context)
                .setItems(items, listener).setTitle(title).setOnDismissListener(dismissListener).create();
        builder.show();
        MLDialogUtils.setDialogTitleColor(builder, R.color.cm_app_color);
    }

    public static void getDialog(final Context context, String title, String content, DialogInterface.OnClickListener sureListener, DialogInterface.OnClickListener cancelListener, DialogInterface.OnDismissListener dismissListener) {
        builder = MLDialogUtils.getAlertDialog(context).setNegativeButton(R.string.ml_dialog_cancel, cancelListener).setPositiveButton(R.string.ml_dialog_ok, sureListener).setOnDismissListener(dismissListener).create();
        builder.setTitle(title);
        builder.setMessage(content);
        builder.show();

        setDialogTitleColor(builder, R.color.cm_app_color);
    }

    public static void getDialog(final Context context, String title, String content) {
        builder = MLDialogUtils.getAlertDialog(context).create();
        builder.setTitle(title);
        builder.setMessage(content);
        builder.show();

        setDialogTitleColor(builder, R.color.cm_app_color);
    }

    public static boolean isShow() {
        if (builder == null) {
            return false;
        }
        return builder.isShowing();
    }


  /*  public static void getDialogMaterial(final Context context, String title, String content, View.OnClickListener sureListener, View.OnClickListener cancelListener,DialogInterface.OnDismissListener dismissListener) {
        final MLMaterialDialog mMaterialDialog = new MLMaterialDialog(context);
        mMaterialDialog.setTitle(title);
        mMaterialDialog.setMessage(content)
        .setPositiveButton("确定", sureListener)
                .setNegativeButton("取消", cancelListener).setCanceledOnTouchOutside(false)
                .setOnDismissListener(dismissListener).show();
    }*/


    public static Date strToDate(String style, String date) {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

}

package cn.ml.base.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.ml.base.MLApplication;
import cn.ml.base.MLBaseConstants;
import cn.ml.base.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

/**
 * 更新版本提示 下载
 *
 * @author Genng
 * @since 2013.10.28
 */
public class MLUpdateManager {

    private Context mContext;

    //是否强制升级
    private boolean mCoerce;

    //提示语
    private String updateMsg = "检查到更新版本，是否更新？";

    //返回的安装包url
    private String apkUrl = "";

    private AlertDialog noticeDialog;


    //	private Dialog downloadDialog;
     /* 下载包安装路径 */
    private static final String savePath = MLFolderUtils.getUpdate();

    private static final String saveFileName = savePath + "/update.apk";

    /* 进度条与通知ui刷新的handler和msg常量 */
//    private ProgressBar mProgress;

    private ProgressDialog dialog;


    private static final int DOWN_UPDATE = 1;

    private static final int DOWN_OVER = 2;


    private static String msg = "";

    private int progress;

    private Thread downLoadThread;

    private boolean interceptFlag = false;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    dialog.setProgress(progress);
                    break;
                case DOWN_OVER:
                    dialog.dismiss();
                    installApk();
                    break;
                default:
                    break;
            }
        }

    };

    public MLUpdateManager(Context context, String apkUrl, String updateMsg) {
        this.mContext = context;
        this.apkUrl = apkUrl;
        this.updateMsg = updateMsg;
        msg = updateMsg;
        this.mCoerce = false;
    }

    public MLUpdateManager(Context context, String apkUrl, String updateMsg, boolean coerce) {
        this.mContext = context;
        this.apkUrl = apkUrl;
        this.updateMsg = updateMsg;
        msg = updateMsg;
        this.mCoerce = coerce;
    }

    //外部接口让主Activity调用
    public void checkUpdateInfo() {

        showNoticeDialog();
    }

    //外部接口让主Activity调用
    public void checkUpdateInfo_2() {

        showNoticeDialog_2();
    }

    @SuppressLint("NewApi")
    private void showNoticeDialog() {
        AlertDialog.Builder builder = null;
        if (Build.VERSION.SDK_INT >= 11) {
            builder = new Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        } else {
            builder = new Builder(mContext);
        }

        builder.setTitle("提示");
        builder.setMessage(updateMsg);
        builder.setPositiveButton("是", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
            }
        });
        builder.setNegativeButton("否", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //强制升级
                if (mCoerce) {
                    MLAppManager.getAppManager().AppExit(mContext);
                } else {
                    dialog.dismiss();
                }
            }
        });
        noticeDialog = builder.create();
        noticeDialog.show();
        setDialogTitleColor(noticeDialog, R.color.cm_app_color);
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

    @SuppressLint("NewApi")
    private void showNoticeDialog_2() {
        AlertDialog.Builder builder = null;
        builder = new Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("提示");
        builder.setMessage(updateMsg);
        builder.setPositiveButton("是", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
            }
        });
        builder.setNegativeButton("否", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        noticeDialog = builder.create();
        noticeDialog.setCancelable(false);
        noticeDialog.show();
    }


    public void setMsg(String msg) {
        MLUpdateManager.msg = msg;
    }

    private void showDownloadDialog() {

//		AlertDialog.Builder builder = null;
//		View v = null;
//		final LayoutInflater inflater = LayoutInflater.from(mContext);
//		builder = new Builder(mContext);
//		v = inflater.inflate(R.layout.progress, null);
//		builder.setTitle("版本更新");
//		mProgress = (ProgressBar)v.findViewById(R.id.progress);
//		builder.setView(v);
//		builder.setMessage(msg);
//		builder.setNegativeButton("取消", new OnClickListener() {	
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				interceptFlag = true;
//			}
//		});
//		downloadDialog = builder.create();
//		downloadDialog.show();

        dialog = new ProgressDialog(mContext, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        dialog.setTitle("版本更新");
        dialog.setMessage(msg);
        dialog.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                // TODO Auto-generated method stub
                interceptFlag = true;
                System.out.println("---cancel");
            }
        });
        dialog.show();
        dialog.setCancelable(false);
        setDialogTitleColor(dialog, R.color.cm_app_color);
        downloadApk();
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    //更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        //下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!interceptFlag);//点击取消就停止下载.

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    /**
     * 下载apk
     *
     * @param url
     */

    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 安装apk
     *
     * @param url
     */
    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        //执行的数据类型   
//        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
//        mContext.startActivity(i);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(apkfile),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }
}

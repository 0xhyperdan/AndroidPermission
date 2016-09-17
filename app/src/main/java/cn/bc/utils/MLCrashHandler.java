package cn.bc.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.baichang.huishoufang.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.bc.base.APP;



/**
 * Created with IntelliJ IDEA.
 * User: michael
 * Date: 12-12-10
 * Time: 上午12:56
 * Description:
 */
public class MLCrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = MLCrashHandler.class.getSimpleName();

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static MLCrashHandler INSTANCE = new MLCrashHandler();
    //程序的Context对象
    private Context mContext;
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    //写入文件的信息
    private  String errorMsg;
    /**
     * 保证只有一个CrashHandler实例
     */
    private MLCrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static MLCrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        	//向服务端发送错误信息
       //	sendError();
      	//腾讯bugly
     	//CrashReport.postCatchedException(ex);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            //不加上不打印
            handleException(ex);

            //重新启动程序
        	Intent i = mContext.getPackageManager()
				      .getLaunchIntentForPackage(mContext.getPackageName());
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("data", "1");
				mContext.startActivity(i);
			      android.os.Process.killProcess(android.os.Process.myPid());

		  //退出程序
       /*    android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);*/



            //重新启动程序
         /*   Intent intent  = new Intent();
           intent.setClass(mContext, ZMMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());*/

    }



    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
       if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息
/*        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序数据出现异常,即将退出.请查看《汽配商城》/异常信息日志 目录下的日志信息。", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();*/
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        saveCrashInfo2File(ex);
        ex.printStackTrace();
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi;
            pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    File _crashLogDirFile = null;

    private File getCrashLogFolder() {
        if (_crashLogDirFile == null) {
            File SDCardRoot = Environment.getExternalStorageDirectory();
            // create a new file, specifying the path, and the filename
            // which we want to save the file as.
            _crashLogDirFile = new File(SDCardRoot, APP.getInstance().getString(R.string.app_name)+"/异常信息日志/");
            _crashLogDirFile.mkdirs();
        }
        return _crashLogDirFile;

    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Log.d(TAG, String.format("crash log dir is %s", getCrashLogFolder().getAbsolutePath()));
                File file = new File(getCrashLogFolder(), fileName);
                Log.d(TAG, String.format("carsh log file is %s", file.getAbsolutePath()));
                FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            errorMsg = sb.toString();
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }
}

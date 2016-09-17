package cn.ml.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public final class MLDeviceUtils {

	public static final int SCREEN_240P = 240;
	public static final int SCREEN_360P = 360;
	public static final int SCREEN_480P = 480;
	public static final int SCREEN_720P = 720;
	public static final int SCREEN_1080P = 1080;
	public static final int SCREEN_1280P = 1280;
	
	private MLDeviceUtils(){}
	
	/**
	 * 获取屏幕高度
	 */
	public static int getScreenHeight(Context context){
		DisplayMetrics dm = null;
		dm = context.getResources().getDisplayMetrics();
		if (dm == null) {
			return -1;
		}
		return dm.heightPixels;
	}
	
	/**
	 * 获取屏幕分辨率
	 */
	public static int getDeviceScreen(Context context){
		DisplayMetrics dm = null;
		dm = context.getResources().getDisplayMetrics();
		if (dm == null) {
			return -1;
		}
		int screenWidth = dm.widthPixels;
		if (screenWidth <= SCREEN_240P) {
			return SCREEN_240P;
		}else if (screenWidth > SCREEN_240P && screenWidth <= SCREEN_360P) {
			return SCREEN_360P;
		}else if (screenWidth > SCREEN_360P && screenWidth <= SCREEN_480P) {
			return SCREEN_480P;
		}else if (screenWidth > SCREEN_480P && screenWidth <= SCREEN_720P) {
			return SCREEN_720P;
		}else if (screenWidth > SCREEN_720P && screenWidth <= SCREEN_1080P) {
			return SCREEN_1080P;
		}else {
			return SCREEN_1280P;
		}
	}
	
	/**
	 * 获取网络类型
	 */
	public static String getNetworkType(Context context){
		NetworkInfo info = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if (info == null) {
			return "无网络";
		}
		if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			return "WIFI";
		}
		switch (info.getSubtype()) {
		case TelephonyManager.NETWORK_TYPE_UMTS:
		case TelephonyManager.NETWORK_TYPE_HSDPA:
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
			return "3G";
		case TelephonyManager.NETWORK_TYPE_GPRS:
		case TelephonyManager.NETWORK_TYPE_EDGE:
		case TelephonyManager.NETWORK_TYPE_CDMA:
			return "2G";
		case TelephonyManager.NETWORK_TYPE_LTE:
			return "4G";
		default:
			return "";
		}
	}
	
	/**
	 * 获取SIM卡运营商
	 */
	public static String getSimType(Context context){
		String IMSI = ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
		if (TextUtils.isEmpty(IMSI)) {
			return "无服务";
		}
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
			return "中国移动";
		}else if (IMSI.startsWith("46001")) {
			return "中国联通";
		}else if (IMSI.startsWith("46003")) {
			return "中国电信";
		}else {
			return "未知运营商";
		}
	}
	/** 检查是否有网络 */
	public static boolean isNetworkAvailable(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		if (info != null) {
			return info.isAvailable();
		}
		return false;
	}

	/** 检查是否是WIFI */
	public static boolean isWifi(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		if (info != null) {
			if (info.getType() == ConnectivityManager.TYPE_WIFI)
				return true;
		}
		return false;
	}

	/** 检查是否是移动网络 */
	public static boolean isMobile(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		if (info != null) {
			if (info.getType() == ConnectivityManager.TYPE_MOBILE)
				return true;
		}
		return false;
	}

	private static NetworkInfo getNetworkInfo(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo();
	}

	/** 检查SD卡是否存在 */
	public static boolean checkSdCard() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}
	/**
	 * 获取IEMI
	 */
	public static String getDeviceIEMI(Context context){
		return String.valueOf(((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
	}
	
	/**
	 * 获取设备型号
	 */
	public static String getDeviceModel(){
		return android.os.Build.MODEL;
	}
	
	/**
	 * 获取系统版本名
	 */
	public static String getOSVersionName(){
		return android.os.Build.VERSION.RELEASE;
	}
	
	/**
	 * 获取系统版本号
	 */
	public static int getOSVersionCode(){
		return android.os.Build.VERSION.SDK_INT;
	}
	
	
    /**
     * 获取版本号
     * @return
     */
	public static  String getVersionCode(Context context)
	   {
	           // 获取packagemanager的实例
	           PackageManager packageManager = context.getPackageManager();
	           // getPackageName()是你当前类的包名，0代表是获取版本信息
	           PackageInfo packInfo = null;
			try {
				packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	           String version = packInfo.versionCode+"";
	           return version;
	   }
	
	/**
     * 获取版本名称
     * @return
     */
	public static  String getVersionName(Context context)
	   {
	           // 获取packagemanager的实例
	           PackageManager packageManager = context.getPackageManager();
	           // getPackageName()是你当前类的包名，0代表是获取版本信息
	           PackageInfo packInfo = null;
			try {
				packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	           String version = packInfo.versionName+"";
	           return version;
	   }
    
	/**
	 * 获得屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context)
	{
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}


	/**
	 * 获得状态栏的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context)
	{

		int statusHeight = -1;
		try
		{
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;

	}

	
}

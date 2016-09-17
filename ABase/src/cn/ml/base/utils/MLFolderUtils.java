package cn.ml.base.utils;

import java.io.File;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import cn.ml.base.MLApplication;

/**
 * 磁盘储存地址
 * @author Marcello
 *
 */
public class MLFolderUtils {
	
	public static String getUpdate() {
		return getDiskCacheDir("update");
	}
	public static String getImage() {
		return getDiskCacheDir("image");
	}
	public static String getOther() {
		return getDiskCacheDir("other");
	}
		@SuppressLint("NewApi")
		public static String getDiskCacheDir(String folderName) {
			Context context = MLApplication.getInstance();
		    String cachePath = null;  
		    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
		            || !Environment.isExternalStorageRemovable()) {  
		        cachePath = context.getExternalCacheDir().getPath();  
		    } else {  
		        cachePath = context.getCacheDir().getPath();  
		    }  
		    return String.format("%s/%s",cachePath,folderName);
		}  
		
	/*	@SuppressLint("NewApi")
		public static String getDiskFileDir(String folderName) {
			Context context = MLApplication.getInstance();
		    String cachePath = null;  
		    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
		            || !Environment.isExternalStorageRemovable()) {  
		        cachePath = context.getExternalFilesDir(folderName).getAbsolutePath();
		    } else {  
		        cachePath = context.getFileStreamPath(folderName).getAbsolutePath();
		    }  
		    return String.format("%s/%s",cachePath,folderName);
		}  */
		
		@SuppressLint("NewApi")
		public static File getDiskFile(String folderName) {
			Context context = MLApplication.getInstance();
		    File cachePath = null;  
		    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
		            || !Environment.isExternalStorageRemovable()) {  
		        cachePath = context.getExternalFilesDir(folderName);
		    } else {  
		        cachePath = context.getFileStreamPath(folderName);
		    }  
		    return cachePath;
		}  
		
		
		/*		public static String getExternalUpdate() {
		File file = MLApplication.getInstance().getExternalCacheDir();
		String subPath = String.format("%s/update",file.getAbsolutePath());
		return subPath;
}
	public static String getUpdate() {
		File file = MLApplication.getInstance().getCacheDir();
		String subPath = String.format("%s/update",file.getAbsolutePath());
		return subPath;
}
	*/
}

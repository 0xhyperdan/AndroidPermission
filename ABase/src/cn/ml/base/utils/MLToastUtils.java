package cn.ml.base.utils;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;
import cn.ml.base.R;
import cn.ml.base.widget.MLTipsToast;

public class MLToastUtils {
	/**
	 * 普通Toast
	 * 
	 * @param context
	 * @param obj
	 */
	public static void showMessage(Context context, Object obj) {
		if(context==null||obj==null)return;
		if (obj instanceof String) {
			Toast.makeText(context, (CharSequence) obj, Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(context,
					ToolsUtil.getResourceString((Integer) obj),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 成功Toast
	 * @param contex
	 * @param obj
	 */
	public static void showMessageSuccess(Context contex, Object obj) {
		if (obj instanceof String) {
			showTipsToast(contex,R.drawable.tips_success, (CharSequence) obj);
		} else {
			showTipsToast(contex,R.drawable.tips_success,
					ToolsUtil.getResourceString((Integer) obj));
		}
	}

	/**
	 * 失败Toast
	 * @param contex
	 * @param obj
	 */
	public static void showMessageError(Context contex, Object obj) {
		if (obj instanceof String) {
			showTipsToast(contex,R.drawable.tips_error, (CharSequence) obj);
		} else {
			showTipsToast(contex,R.drawable.tips_error,
					ToolsUtil.getResourceString((Integer) obj));
		}
	}

	/**
	 * 警告Toast
	 * @param contex
	 * @param obj
	 */
	public static void showMessageWarning(Context contex, Object obj) {
		if (obj instanceof String) {
			showTipsToast(contex,R.drawable.tips_warning, (CharSequence) obj);
		} else {
			showTipsToast(contex,R.drawable.tips_warning,
					ToolsUtil.getResourceString((Integer) obj));
		}
	}

	/**
	 * 笑脸Toast
	 * @param contex
	 * @param obj
	 */
	public static void showMessageSmile(Context contex, Object obj) {
		if (obj instanceof String) {
			showTipsToast(contex,R.drawable.tips_smile, (CharSequence) obj);
		} else {
			showTipsToast(contex,R.drawable.tips_smile,
					ToolsUtil.getResourceString((Integer) obj));
		}
	}
	
	
	/**
	 * TipsToast
	 * 
	 * @param context
	 * @param obj
	 */
	private static MLTipsToast tipsToast;

	public static void showTipsToast(Context context, int iconResId,
			CharSequence text) {
		if (tipsToast != null) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				tipsToast.cancel();
			}
		} else {
			tipsToast = MLTipsToast.makeText(context, text,
					MLTipsToast.LENGTH_SHORT);
		}
		tipsToast.show();
		tipsToast.setIcon(iconResId);
		tipsToast.setText(text);
	}

}

package com.yisa.pray.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 显示相关的工具类
 * 
 * @author hubin
 * @email 7629654@qq.com
 * @date 2014-11-07
 */
public class ShowUtils {

	/** 将dp转换成px */
	public static int dip2px(Context context, float dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/** px转成为dp */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取屏幕宽高
	 * 
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getScreenSize(Context context) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(
				localDisplayMetrics);
		return localDisplayMetrics;
	}

	/**
	 * 显示Toast
	 * 
	 * @param message
	 */
	private static Toast toast;

	public static void showToast(Context mContext, String message) {
		showToast(mContext, message, Toast.LENGTH_LONG);
	}

	public static void showToast(Context mContext, String message, int time) {
		if (toast == null) {
			toast = Toast.makeText(mContext.getApplicationContext(), message, time);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

	public static void showToast(Context mContext, int resourceId) {
		showToast(mContext, resourceId, Toast.LENGTH_LONG);
	}

	public static void removeToast() {
		if (toast != null) {
			toast = null;
		}
	}

	public static void showToast(Context mContext, int resourceId, int time) {
		if (toast == null) {
			toast = Toast.makeText(mContext.getApplicationContext(), resourceId, time);
		} else {
			toast.setText(resourceId);
		}
		toast.show();
	}

	public static void LogE(String message) {
		Log.e("chenru--------->", message);
	}

	public static void LogD(String message) {
		Log.d("chenru--------->", message);
	}
}

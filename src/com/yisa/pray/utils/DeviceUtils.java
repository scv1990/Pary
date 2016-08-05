/**
 * 项目名称: 七七同城
 * 
 * 文件名称: DeviceUtils.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.utils;

import java.io.File;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

/**
 *
 * 类名称: DeviceUtils.java
 * 类描述: 系统设备工具
 * 创建人: hq
 * 创建时间: 2016年8月5日下午2:57:27
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class DeviceUtils {
	/**
	 * 获取设备MAC地址
	 * 需添加权限<uses-permission
	 * android:name="android.permission.ACCESS_WIFI_STATE"/>
	 */
	public static String getMacAddress(Context context) {
		String macAddress;
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		macAddress = info.getMacAddress();
		if (null == macAddress) {
			return "";
		}
		macAddress = macAddress.replace(":", "");
		return macAddress;
	}

	/**
	 * 获取设备厂商，如Xiaomi
	 */
	public static String getManufacturer() {
		String MANUFACTURER = Build.MANUFACTURER;
		return MANUFACTURER;
	}

	/**
	 * 获取设备型号，如MI2SC
	 */
	public static String getModel() {
		String model = Build.MODEL;
		if (model != null) {
			model = model.trim().replaceAll("\\s*", "");
		} else {
			model = "";
		}
		return model;
	}

	/**
	 * 获取设备SD卡是否可用
	 */
	public static boolean isSDCardEnable() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * 获取设备SD卡路径
	 * 一般是/storage/emulated/0/
	 */
	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	}
	
	/**
	 * @Title: getDeviceId 
	 * @Description: TODO(获取设备deviceid) 
	 * @param @param context
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public static String getDeviceId(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  
		return tm.getDeviceId();
	}
}

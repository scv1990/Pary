/**
 * 项目名称: 七七同城
 * 
 * 文件名称: MyApplication.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.base;

import android.app.Application;

/**
 *
 * 类名称: MyApplication.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年7月27日下午6:06:52
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class MyApplication extends Application {
	private static MyApplication instance = null;
	public static boolean isActive = true;
	
	public static MyApplication getInstance(){
		if(instance == null){
			instance = new MyApplication();
		}
		return instance;
	}

	public static boolean isActive() {
		return isActive;
	}

	public static void setActive(boolean isActive) {
		MyApplication.isActive = isActive;
	}

}

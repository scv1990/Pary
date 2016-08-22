/**
 * 项目名称: 七七同城
 * 
 * 文件名称: PropertyUtil.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.utils;

import java.util.Properties;

import com.yisa.pray.R;

import android.content.Context;

/**
 *
 * 类名称: PropertyUtil.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月22日下午1:32:53
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class PropertyUtil {
	private static PropertyUtil instance = null;
	
	public static PropertyUtil getInstance(){
		if(instance == null){
			instance = new PropertyUtil();
		}
		return instance;
	}
	
	public String readProperty(Context context, String key){
		Properties pr = new Properties();
		try {
			pr.load(context.getResources().openRawResource(R.raw.education));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (String)pr.get(key);
	}
	
	public Properties readProperty(Context context, int rawId){
		Properties pr = new Properties();
		try {
			pr.load(context.getResources().openRawResource(rawId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pr;
	}
}

/**
 * 项目名称: 七七同城
 * 
 * 文件名称: UIHelper.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.utils;

import com.yisa.pray.activity.BlogCategroyActivity;
import com.yisa.pray.activity.EditBlogActivity;
import com.yisa.pray.activity.HomeActivity;
import com.yisa.pray.activity.LoginActivity;
import com.yisa.pray.activity.RegisterActivity;

import android.content.Context;
import android.content.Intent;

/**
 *
 * 类名称: UIHelper.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年7月28日下午2:54:59
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class UIHelper {

	/**
	 * 调到Home launch
	 * 
	 * @param context
	 */
	public static void showLaunch(Context context) {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 如果intent不指定category，那么无论intent filter的内容是什么都应该是匹配的。
		intent.addCategory(Intent.CATEGORY_HOME);
		context.startActivity(intent);
	}
	
	/**
	 * 打开主界面
	 * @param context
	 */
	public static void showHomeActivity(Context context){
		Intent intent = new Intent(context, HomeActivity.class);
		context.startActivity(intent);
	}
	
	/**
	 * 打开登陆界面
	 * @param context
	 */
	public static void showLoginActivity(Context context){
		Intent intent = new Intent(context, LoginActivity.class);
		context.startActivity(intent);
	}
	/**
	 * @Title: showRegister 
	 * @Description: TODO(打开注册界面) 
	 * @param @param reqestCode    判断是从什么地方打开的
	 * @return void    返回类型 
	 * @throws
	 */
	public static void showRegister(Context context, int reqestCode){
		Intent intent = new Intent(context, RegisterActivity.class);
		context.startActivity(intent);
	}
	
	/**
	 * @Title: showRegister 
	 * @Description: TODO(打开注册界面) 
	 * @param @param reqestCode    判断是从什么地方打开的
	 * @return void    返回类型 
	 * @throws
	 */
	public static void showBlogEdit(Context context, int reqestCode){
		Intent intent = new Intent(context, EditBlogActivity.class);
		context.startActivity(intent);
	}
	
	public static void showBlogCategroy(Context context, int reqestCode){
		
	}
	
}

/**
 * 项目名称: 七七同城
 * 
 * 文件名称: UIHelper.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.utils;

import java.util.ArrayList;

import com.yisa.pray.activity.HomeActivity;
import com.yisa.pray.activity.LoginActivity;
import com.yisa.pray.activity.PerfectUserinfoActivity;
import com.yisa.pray.activity.RegisterActivity;
import com.yisa.pray.activity.ShowBigImageActivity;
import com.yisa.pray.blog.activity.BlogCategroyActivity;
import com.yisa.pray.blog.activity.EditBlogActivity;
import com.yisa.pray.blog.activity.ImageSelectActivity;
import com.yisa.pray.blog.activity.ThankPrayActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
	
	/**
	 * @Title: showPerfectUserinfoActicity 
	 * @Description: TODO(打开完善用户信息) 
	 * @param context
	 * @param userId   用户id
	 * @return void    返回类型 
	 * @throws
	 */
	public static void showPerfectUserinfoActicity(Context context){
		Intent intent = new Intent(context, PerfectUserinfoActivity.class);
		context.startActivity(intent);
	}
	
	/**
	 * 跳转本地图片选择
	 * 
	 * @param context
	 */
	public static void showSelectPhoto(Activity activity, Bundle bundle, int maxSize, int requestCode) {
		Intent intent = new Intent(activity.getApplicationContext(), ImageSelectActivity.class).putExtra(
				IntentKey.IMAGE_PATH_BUNDLE, bundle).putExtra(IntentKey.IMAGE_UPLOAD_MAXSIZE, maxSize);
		activity.startActivityForResult(intent, requestCode);
	}
	
	
	/**
	 * 跳转缩略图大图展示
	 * 
	 * @param mContext
	 *            搜索关键字
	 * @param images
	 *            展示图片链接数据
	 * @param index
	 *            当前点击图片数组下标
	 */
	public static void showBigImage(Context mContext, ArrayList<String> images, int index) {
		Intent intent = new Intent(mContext.getApplicationContext(), ShowBigImageActivity.class);
		intent.putStringArrayListExtra("KEY", images).putExtra("index", index);
		mContext.startActivity(intent);
	}
	
	/**
	 * @Title: showThankPray 
	 * @Description: TODO(打开感谢代祷界面) 
	 * @param @param context    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public static void showThankPray(Context context){
		Intent intent = new Intent(context, ThankPrayActivity.class);
		context.startActivity(intent);
	}
	
}

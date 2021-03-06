/**
 * 项目名称: 七七同城
 * 
 * 文件名称: UserUtils.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.yisa.pray.entity.UserInfo;

/**
 *
 * 类名称: UserUtils.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月11日下午4:43:42
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class UserUtils {
	private static UserUtils instance = null;
	
	public static UserUtils getInstance(){
		if(instance == null){
			return new UserUtils();
		}
		return instance;
	}
	
	/**
	 * @Title: getUser 
	 * @Description: TODO(获取用户信息) 
	 * @param @param context
	 * @param @return    设定文件 
	 * @return UserInfo    返回类型 
	 * @throws
	 */
	public UserInfo getUser(Context context){
		String userStr = PreferenceUtils.getPrefString(context, IntentKey.USERINFO, "");
		if(userStr == null || "".equals(userStr)){
			return null;
		}
		return new Gson().fromJson(userStr, UserInfo.class);
	}
	
}

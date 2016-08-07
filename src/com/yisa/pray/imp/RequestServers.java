/**
 * 项目名称: 七七同城
 * 
 * 文件名称: RequestServers.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.imp;
import com.yisa.pray.entity.ErrorMessage;
import com.yisa.pray.entity.UserInfo;
import com.yisa.pray.utils.UrlUtils;

import retrofit.Call;
import retrofit.http.*;

/**
 *
 * 类名称: RequestServers.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月5日下午5:51:43
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public interface RequestServers {
	
	/**
	 * 注册
	 * @param phone  电话
	 * @param userName  用户名
	 * @param password 密码
	 * @param code  邀请码
	 * @return
	 */
	@Multipart
	@POST(UrlUtils.REGISTER)
	Call<ErrorMessage> getString( @Part("phone") String phone,
						    @Part("username") String userName,
						    @Part("password") String password,
						    @Part("invitation_code") String code);
	@POST(UrlUtils.LOGIN)
	Call<String> login( @Query("username") String userName,
						@Query("password") String password,
						@Query("device") String device,
						@Query("device_model") String deviceModel,
						@Query("device_type") String deviceType);
	
	@POST(UrlUtils.LOGIN)
	Call<UserInfo> login( @Query("username") String userName,
						@Query("password") String password);
	
}

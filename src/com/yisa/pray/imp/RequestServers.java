/**
 * 项目名称: 七七同城
 * 
 * 文件名称: RequestServers.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.imp;
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
	@POST(UrlUtils.REGISTER)
	Call<String> getString( @Query("phone") String phone,
						    @Query("username") String userName,
						    @Query("password") String password,
						    @Query("code") String code);
	
}
/**
 * 项目名称: 七七同城
 * 
 * 文件名称: UserService.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.imp;

import com.yisa.pray.entity.OnlineCountEntity;
import com.yisa.pray.utils.UrlUtils;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 *
 * 类名称: UserService.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月12日上午11:09:38
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public interface UserService {
	@GET(UrlUtils.GET_ON_LINE_NUMBER)
	Call<OnlineCountEntity> getOnlineNum(@Header("X-Access-Token") String token);

}

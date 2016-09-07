/**
 * 项目名称: 七七同城
 * 
 * 文件名称: SimpleService.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.imp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 *
 * 类名称: SimpleService.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月25日下午3:14:09
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public interface SimpleService {
	@GET
	Call<String> getData(
			@Url String url,
			@Header("X-Access-Token") String token);

}

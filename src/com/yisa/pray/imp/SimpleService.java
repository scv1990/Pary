/**
 * 项目名称: 七七同城
 * 
 * 文件名称: SimpleService.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.imp;

import java.util.List;

import com.yisa.pray.entity.SimpleData;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

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
	@GET("/{path}")
	Call<List<SimpleData>> getData(
			@Path("path") String path,
			@Query("X-Access-Token") String token);

}

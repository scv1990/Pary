/**
 * 项目名称: 七七同城
 * 
 * 文件名称: NoticeService.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.notification.imp;

import java.util.List;

import com.yisa.pray.notification.entity.Notification;
import com.yisa.pray.utils.UrlUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 *
 * 类名称: NoticeService.java
 * 类描述:	定义通知的一些接口 
 * 创建人:  hq
 * 创建时间: 2016年8月29日下午2:58:02
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public interface NoticeService {
	/**
	 * @Title: getNotice 
	 * @Description: TODO(获取用户通知) 
	 * @param @param token  
	 * @param @param page 页码
	 * @param @param count 每页数量
	 * @return Call<List<Notification>>  
	 * @throws
	 */
	@GET(UrlUtils.GET_NOTICE)
	Call<List<Notification>> getNotice(
			@Header("X-Access-Token") String token,
			@Query("page") int page,
			@Query("per_page") int count
			);
	
}

/**
 * 项目名称: 七七同城
 * 
 * 文件名称: BlogCategroyService.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.imp;

import java.util.List;

import com.yisa.pray.entity.BlogCategroyEntity;
import com.yisa.pray.entity.BlogEntity;
import com.yisa.pray.utils.BlogUrlUtils;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 *
 * 类名称: BlogCategroyService.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月8日下午4:06:02
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public interface BlogService {
	/**
	 * @Title: getCategroy 
	 * @Description: TODO(获取帖子分类) 
	 * @param @return    设定文件 
	 * @return Call<List<BlogCategroyEntity>>    返回类型 
	 * @throws
	 */
	@GET(BlogUrlUtils.GET_BLOG_CATEGROY)
	Call<List<BlogCategroyEntity>> getCategroy();

	/**
	 * @Title: getBlogList 
	 * @Description: 获取帖子列表
	 * @param @param page 页码
	 * @param @param perPage 每页数量
	 * @param @param token Token
	 * @param @param cateId 分类
	 * @param @param regionId 区域
	 * @param @param sort 排序字段
	 * @param @param order 排列方式
	 * @param @return    设定文件 
	 * @return Call<BlogCategroyEntity[]>    返回类型 
	 * @throws
	 */
	@GET(BlogUrlUtils.GET_BLOG_LIST)
	Call<BlogEntity[]> getBlogList(
				@Query("page") int page,
				@Query("per_page") int perPage,
				@Header("X-Access-Token") String token,
				@Query("category_id") String cateId,
				@Query("region_id") String regionId,
				@Query("sort") String sort,
				@Query("order") String order
			);
}
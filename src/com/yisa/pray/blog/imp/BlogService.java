/**
 * 项目名称: 七七同城
 * 
 * 文件名称: BlogCategroyService.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.blog.imp;

import java.util.List;

import okhttp3.RequestBody;

import com.yisa.pray.blog.entity.BlogCategroyEntity;
import com.yisa.pray.blog.entity.BlogEntity;
import com.yisa.pray.blog.entity.PostImage;
import com.yisa.pray.blog.entity.RegionEntity;
import com.yisa.pray.blog.entity.ThankPrayEntity;
import com.yisa.pray.entity.OperationResult;
import com.yisa.pray.utils.BlogUrlUtils;
import com.yisa.pray.utils.PrayUrlUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
	 * @param page 页码
	 * @param perPage 每页数量
	 * @param token Token
	 * @param cateId 分类
	 * @param regionId 区域
	 * @param sort 排序字段
	 * @param order 排列方式
	 * @return    设定文件 
	 * @return Call<BlogCategroyEntity[]>    返回类型 
	 * @throws
	 */
	@GET(BlogUrlUtils.GET_BLOG_LIST)
	Call<BlogEntity[]> getBlogList(
				@Query("page") int page,
				@Query("per_page") int perPage,
				@Header("X-Access-Token") String token,
				@Query("category_id") Integer cateId,
				@Query("region_id") Integer regionId,
				@Query("sort") String sort,
				@Query("order") String order
			);
	
	@GET(BlogUrlUtils.GET_BLOG_LIST)
	Call<BlogEntity[]> getBlogList(
				@Query("page") int page,
				@Query("per_page") int perPage,
				@Header("X-Access-Token") String token,
				@Query("sort") String sort,
				@Query("order") String order
			);
	
	/**
	 * @Title: addBlog 
	 * @Description: TODO  发布帖子。添加 
	 * @param token
	 * @param regionId  地区id
	 * @param cateId  分类id
	 * @param title  标题
	 * @param content  内容
	 * @return    设定文件 
	 * @return Call<BlogEntity>    返回类型 
	 * @throws
	 */
	@POST(BlogUrlUtils.GET_BLOG_LIST)
	Call<BlogEntity> addBlog(@Header("X-Access-Token") String token,
			@Query("region_id") int regionId,
			@Query("category_id") int cateId,
			@Query("title") String title,
			@Query("content") String content
			);
	
	
	/**
	 * @Title: recivePray 
	 * @Description: TODO(领取代祷) 
	 * @param @param token
	 * @param @param id 帖子id
	 * @param @return    设定文件 
	 * @return Call<BlogEntity>    返回类型 
	 * @throws
	 */
	@PUT(BlogUrlUtils.RECIVE_PRAY)
	Call<BlogEntity> recivePray(
			@Header("X-Access-Token") String token,
			@Path("id") int id
			);
	
	/**
	 * @Title: thankPraySingle 
	 * @Description: TODO(单一的感谢代祷) 
	 * @param @param blogId
	 * @param @param id
	 * @param @param token
	 * @param @return    设定文件 
	 * @return Call<ThankPrayEntity>    返回类型 
	 * @throws
	 */
	@PUT(PrayUrlUtils.THANK_PRAY_SINGLE)
	Call<ThankPrayEntity> thankPraySingle(
			@Path("history_id") int id,
			@Header("X-Access-Token") String token
			);
	
	/**
	 * 
	 * @Title: thankPrayBatch 
	 * @Description: TODO(批量感谢代祷) 
	 * @param @param token
	 * @param @return    设定文件 
	 * @return Call<OperationResult>    返回类型 
	 * @throws
	 */
	@PUT(PrayUrlUtils.THANK_PRAY_BATCH)
	Call<OperationResult> thankPrayBatch(
			@Header("X-Access-Token") String token
			);
	/**
	 * @Title: getPrayList 
	 * @Description: TODO(获取感谢代祷列表) 
	 * @param @param id
	 * @param @param token
	 * @param @return    设定文件 
	 * @return Call<List<ThankPrayEntity>>    返回类型 
	 * @throws
	 */
	@GET(PrayUrlUtils.PRAY_LIST)
	Call<List<ThankPrayEntity>> getPrayList(
			@Header("X-Access-Token") String token,
			@Query("page") int page,
			@Query("per_page") int perPage
			);
	/**
	 * @Title: getRegion 
	 * @Description: TODO(获取地区) 
	 * @param @return    设定文件 
	 * @return Call<RegionEntity>    返回类型 
	 * @throws
	 */
	@GET(BlogUrlUtils.GET_BLOG_AREA)
	Call<List<RegionEntity>> getRegion();
	
	/**
	 * @Title: uploadImage 
	 * @Description: TODO(上传图片) 
	 * @param @param id
	 * @param @param file
	 * @param @param token
	 * @param @return    设定文件 
	 * @return Call<PostImage>    返回类型 
	 * @throws
	 */
	@Multipart
	@POST(BlogUrlUtils.UPLOAD_BLOG_IMAGE)
	Call<PostImage> uploadImage(
			@Path("id") int id,
			@Part("image\"; filename=\"avatar.jpg") RequestBody file,
			@Header("X-Access-Token") String token);
}

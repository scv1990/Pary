/**
 * 项目名称: 七七同城
 * 
 * 文件名称: BlogUrlUtils.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.utils;

/**
 *
 * 类名称: BlogUrlUtils.java
 * 类描述:  帖子相关的接口地址
 * 创建人:  hq
 * 创建时间: 2016年8月8日下午4:52:41
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class BlogUrlUtils {
	/**
	 * 帖子分类
	 */
	public static final String GET_BLOG_CATEGROY = "/api/v1/categories";
	
	
	public static final String GET_BLOG_LIST ="/api/v1/posts";
	
	/**
	 * 领取代祷
	 */
	public static final String RECIVE_PRAY = "/api/v1/posts/{id}/pray";
	
	/**
	 * 获取地区
	 */
	public static final String GET_BLOG_AREA = "/api/v1/regions";
	
	/**
	 * 上传帖子图片
	 */
	public static final String UPLOAD_BLOG_IMAGE = "/api/v1/posts/{id}/images";
	
	/**
	 * 感谢代祷
	 */
	public static final String THANK_PRAY_SINGLE = "/api/v1/posts/{id}/prays/{history_id}/thank";
	
	/**
	 * 感谢代祷列表
	 */
	public static final String THANK_PRAY_LIST = "/api/v1/posts/{id}/prays";
}

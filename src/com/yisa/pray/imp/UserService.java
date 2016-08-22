/**
 * 项目名称: 七七同城
 * 
 * 文件名称: UserService.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.imp;

import com.yisa.pray.entity.OnlineCountEntity;
import com.yisa.pray.entity.UserInfo;
import com.yisa.pray.utils.UrlUtils;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

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
	/**
	 * @Title: getOnlineNum 
	 * @Description: TODO(获取在线人数) 
	 * @param @param token
	 * @param @return    设定文件 
	 * @return Call<OnlineCountEntity>    返回类型 
	 * @throws
	 */
	@GET(UrlUtils.GET_ON_LINE_NUMBER)
	Call<OnlineCountEntity> getOnlineNum(@Header("X-Access-Token") String token);

	/**
	 * @Title: upateUserInfo 
	 * @Description: TODO(更新用户信息) 
	 * @param @return    设定文件 
	 * @return Call<UserInfo>    返回类型 
	 * @throws
	 */
	@PUT(UrlUtils.UPDATE_USER_INFO)
	Call<UserInfo> upateUserInfo(
			@Path("id") Integer id,
			@Query("name") String name,
			@Query("address") String address,
			@Query("gender") String gender,
			@Query("birth") String birth,
			@Query("education") int education,
			@Query("job") String job,
			@Query("church") String church,
			@Query("church_service") String churchService,
			@Query("rebirth") String rebirth,
			@Query("area") String area,
			@Query("period") String period,
			@Header("X-Access-Token") String token);
	
	@GET(UrlUtils.GET_USER_INFO)
	Call<UserInfo> getUserInfo(
			@Header("X-Access-Token") String token,
			@Path("id") int userId);
}

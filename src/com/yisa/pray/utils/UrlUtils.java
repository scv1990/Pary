package com.yisa.pray.utils;

public class UrlUtils {
	/**
	 * 服务器地址
	 */
	public static final String SERVER_ADDRESS = "http://jcalliance.org";
	/**
	 * 注册
	 */
	public static final String REGISTER = "/api/v1/accounts/register";

	/**
	 * 登陆
	 */
	public static final String LOGIN ="/api/v1/accounts/login";
	
	/**
	 * 获取验证码
	 */
	public static final String GET_VERIFI_CODE ="/api/v1/auth_codes";
	
	/**
	 * 获取在线人数
	 */
	public static final String GET_ON_LINE_NUMBER = "/api/v1/users/online_count";
	
	/**
	 * 更新用户信息
	 */
	public static final String UPDATE_USER_INFO = "/api/v1/users/{id}";
	/**
	 * 获取用户详细信息
	 */
	public static final String GET_USER_INFO = "/api/v1/users/{id}";
}

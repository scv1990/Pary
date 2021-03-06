package com.yisa.pray.utils;

public class ResponseCode {
	
	/**
	 * 服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）
	 */
	public static final int RESPONSE_CODE_201 = 201;
	
	/**
	 * [POST/PUT/PATCH]：用户新建或修改数据成功
	 */
	public static final int RESPONSE_CODE_200 = 200;
	/**
	 * [DELETE]：用户删除数据成功
	 */
	public static final int RESPONSE_CODE_204 = 204;
	/**
	 * [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
	 */
	public static final int RESPONSE_CODE_400 = 400;
	/**
	 * [*]：表示用户没有权限（令牌、用户名、密码错误）。
	 */
	public static final int RESPONSE_CODE_401 = 401;
	/**
	 * [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的
	 */
	public static final int RESPONSE_CODE_403 = 403;
	
	/**
	 * [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
	 */
	public static final int RESPONSE_CODE_404 = 404;
	/**
	 * [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）
	 */
	public static final int RESPONSE_CODE_406 = 406;
	/**
	 * 用户请求的资源被永久删除，且不会再得到的
	 */
	public static final int RESPONSE_CODE_410 = 410;
	/**
	 * [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
	 */
	public static final int RESPONSE_CODE_422 = 422;
	/**
	 * 服务器发生错误，用户将无法判断发出的请求是否成功。
	 */
	public static final int RESPONSE_CODE_500 = 500;

}

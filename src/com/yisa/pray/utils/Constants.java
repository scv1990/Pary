/**
 * 项目名称: 七七同城
 * 
 * 文件名称: Constants.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.utils;

/**
 *
 * 类名称: Constants.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年7月28日下午2:35:48
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class Constants {
	// handler message 赞message.what
	public static final int HANDLER_MESSAGE = 0x60;
	// show loading dialog
	public static final int SHOW_LOADING_DIALOG = 0x61;
	// dimiss loading dialog
	public static final int DIMISS_LOADING_DIALOG = 0x62;
	// 验证成功
	public static final int VERIFY_SUCCESS = 0x100;
	// 验证失败
	public static final int VERIFY_FAIL = 0x101;
	/**
	 * 设备类型
	 */
	public static final String DEVICE_TYPE = "android";
	
	/**
	 * 从登录页打开注册界面请求
	 */
	public static final int LOGIN_TO_REGISTER_REQ_CODE = 0x301;
	/**
	 * 从祷告墙到注册界面请求
	 */
	public static final int PRAY_WALL_TO_REGISTER_REQ_CODE = 0x401;
	/**
	 * 从编辑帖子打开选择blog分类的界面
	 */
	public static final int EDIT_TO_BLOG_CATEGROY_REQ_CODE = 0x501;
	
	/**
	 * 从更新用户信息界面到地区选择界面
	 */
	public static final int USER_INFO_TO_REGION_REQ_CODE = 0x601;
	
	/**
	 * 拍照
	 */
	public static final int UPLOAD_TAKE_PHOTO_REQ_CODE = 0x701;
	
	/**
	 * 从相册中选照片
	 */
	public static final int UPLOAD_PICK_PHOTO_REQ_CODE = 0x702;
	
	/**
	 * 剪裁照片
	 */
	public static final int UPLOAD_CUT_PHOTO_REQ_CODE = 0x703;
	
	/**
	 *  选择图片广播
	 */
	public static final String REFRESH_UPLOAD_GRIDVIEW_IMAGE = "refresh_upload_gridview_image";
	
	/**
	 * 上传图片最大张数
	 */
	public static final int IMAGE_SELECT_MAXSIZE = 9;
	
}

/**
 * 项目名称: 七七同城
 * 
 * 文件名称: PrayUrlUtils.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.utils;

/**
 *
 * 类名称: PrayUrlUtils.java
 * 类描述: 代祷模块url 
 * 创建人:  hq
 * 创建时间: 2016年9月5日下午2:16:08
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class PrayUrlUtils {
	
	private static final String ROOT_PATH = UrlUtils.API + UrlUtils.API_VERSION + UrlUtils.API_MODEL_PRAYS;
	/**
	 * 代祷列表（GET请求）
	 */
	public static final String PRAY_LIST = ROOT_PATH;
	
	/**
	 * 感谢代祷（单个）
	 */
	public static final String THANK_PRAY_SINGLE = ROOT_PATH + "/{history_id}/thank";
	
	/**
	 * 感谢代祷（批量）
	 */
	public static final String THANK_PRAY_BATCH = ROOT_PATH + "/batch_thank";
}

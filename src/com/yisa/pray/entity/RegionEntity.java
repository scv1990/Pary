/**
 * 项目名称: 七七同城
 * 
 * 文件名称: BlogAreaEntity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.entity;

import java.io.Serializable;

/**
 *
 * 类名称: BlogAreaEntity.java
 * 类描述:	区域
 * 创建人:  hq
 * 创建时间: 2016年8月19日上午10:34:30
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class RegionEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

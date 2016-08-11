/**
 * 项目名称: 七七同城
 * 
 * 文件名称: BlogCategroyEntity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.entity;

import java.io.Serializable;

/**
 *
 * 类名称: BlogCategroyEntity.java
 * 类描述:	 帖子分类
 * 创建人:  hq
 * 创建时间: 2016年8月8日下午4:07:00
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class BlogCategroyEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

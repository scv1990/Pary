/**
 * 项目名称: 七七同城
 * 
 * 文件名称: SimpleData.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.entity;

import java.io.Serializable;

/**
 *
 * 类名称: SimpleData.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月25日下午3:18:51
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class SimpleData implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}

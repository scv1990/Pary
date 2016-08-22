/**
 * 项目名称: 七七同城
 * 
 * 文件名称: EducationEntity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.entity;

import java.io.Serializable;

/**
 *
 * 类名称: EducationEntity.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月22日下午1:46:02
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class EducationEntity implements Serializable{
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

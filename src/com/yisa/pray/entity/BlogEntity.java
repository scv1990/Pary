/**
 * 项目名称: 七七同城
 * 
 * 文件名称: BlogEntity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.entity;

/**
 *
 * 类名称: BlogEntity.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月9日下午2:40:13
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class BlogEntity {
	private String id;
	
	private String category_id;
	
	private String category_name;
	
	private String region_id;
	
	private String region_name;
	
	private String title;
	
	private String content;
	
	private String pray_number;
	
	private String created_at;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPray_number() {
		return pray_number;
	}

	public void setPray_number(String pray_number) {
		this.pray_number = pray_number;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	
}

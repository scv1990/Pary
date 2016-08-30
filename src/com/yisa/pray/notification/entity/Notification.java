/**
 * 项目名称: 七七同城
 * 
 * 文件名称: Notification.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.notification.entity;

import java.io.Serializable;

/**
 *
 * 类名称: Notification.java
 * 类描述:	 消息通知实体类
 * 创建人:  hq
 * 创建时间: 2016年8月30日下午4:42:26
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class Notification implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String type;
	
	private String content;
	
	private String target_type;
	
	private String target_id;
	
	private String created_at;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTarget_type() {
		return target_type;
	}

	public void setTarget_type(String target_type) {
		this.target_type = target_type;
	}

	public String getTarget_id() {
		return target_id;
	}

	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

}

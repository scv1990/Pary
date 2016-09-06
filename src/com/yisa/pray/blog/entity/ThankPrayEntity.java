/**
 * 项目名称: 七七同城
 * 
 * 文件名称: ThankPrayEntity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.blog.entity;

/**
 *
 * 类名称: ThankPrayEntity.java
 * 类描述:	感谢代祷Gson解析类 
 * 创建人:  hq
 * 创建时间: 2016年9月2日下午3:49:44
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class ThankPrayEntity {
	private int id ;
	private int user_id;
	private String user_name;
	private String user_avator;
	private String creat_at;
	private boolean is_thanked;
	private boolean isChecked = true;
	/**
	 * 用于指明item中显示的是button还是checkbox
	 * true: 显示button
	 * false : 显示checkbox
	 */
	private boolean isHidden = true; 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_avator() {
		return user_avator;
	}
	public void setUser_avator(String user_avator) {
		this.user_avator = user_avator;
	}
	public String getCreat_at() {
		return creat_at;
	}
	public void setCreat_at(String creat_at) {
		this.creat_at = creat_at;
	}
	public boolean isIs_thanked() {
		return is_thanked;
	}
	public void setIs_thanked(boolean is_thanked) {
		this.is_thanked = is_thanked;
	}
	public boolean isHidden() {
		return isHidden;
	}
	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
}

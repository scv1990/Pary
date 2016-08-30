/**
 * 项目名称: 七七同城
 * 
 * 文件名称: NoticeAdapter.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.notification.adapter;

import java.util.List;

import com.yisa.pray.notification.entity.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 *
 * 类名称: NoticeAdapter.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月30日下午5:13:38
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class NoticeAdapter extends BaseAdapter {

	private List<Notification> mNoticeList;
	private LayoutInflater mInflater;
	
	public NoticeAdapter(Context context){
		
	}
	
	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

}

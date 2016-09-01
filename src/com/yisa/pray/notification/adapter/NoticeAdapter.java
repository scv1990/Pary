/**
 * 项目名称: 七七同城
 * 
 * 文件名称: NoticeAdapter.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.notification.adapter;

import java.util.List;

import com.lidroid.xutils.util.AdapterUtils;
import com.yisa.pray.R;
import com.yisa.pray.notification.entity.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 *
 * 类名称: NoticeAdapter.java
 * 类描述:	消息通知adapter 
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
		this.mInflater = LayoutInflater.from(context);
	}
	
	public List<Notification> getmNoticeList() {
		return mNoticeList;
	}

	public void setmNoticeList(List<Notification> mNoticeList) {
		this.mNoticeList = mNoticeList;
	}



	@Override
	public int getCount() {
		if(mNoticeList == null){
			return 0;
		}
		return mNoticeList.size();
	}

	@Override
	public Notification getItem(int position) {
		if(mNoticeList == null){
			return null;
		}
		return mNoticeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_notice, null);
		}
		TextView content = AdapterUtils.get(convertView, R.id.name);
		content.setText(getItem(position).getContent());
		return convertView;
	}

}

/**
 * 项目名称: 七七同城
 * 
 * 文件名称: BlogListAdapter.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.adapter;

import java.util.List;

import com.lidroid.xutils.util.AdapterUtils;
import com.yisa.pray.R;
import com.yisa.pray.entity.BlogEntity;
import com.yisa.pray.views.CircleImageView;
import com.yisa.pray.views.ExpandableTextView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 *
 * 类名称: BlogListAdapter.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月9日下午2:39:22
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class BlogListAdapter extends BaseAdapter {
	private Context mContext;
	private List<BlogEntity> data;
	private LayoutInflater mInflater;
	
	/**
	 * @param mContext
	 * @param data
	 * @param mInflater
	 */
	public BlogListAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
	}

	public List<BlogEntity> getData() {
		return data;
	}

	public void setData(List<BlogEntity> data) {
		this.data = data;
	}


	@Override
	public int getCount() {
		if(data == null){
			return 0;
		}
		return data.size();
	}

	@Override
	public BlogEntity getItem(int position) {
		if(data == null){
			return null;
		}
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_blog_main,  null);
		}
		BlogEntity blog = getItem(position);
		CircleImageView avatar = (CircleImageView) AdapterUtils.get(convertView, R.id.user_head_pic);
		TextView userName = (TextView) AdapterUtils.get(convertView, R.id.user_name);
		TextView addTime = (TextView) AdapterUtils.get(convertView, R.id.add_time);
		ExpandableTextView content = (ExpandableTextView) AdapterUtils.get(convertView, R.id.posts_content);
 		return convertView;
	}

}

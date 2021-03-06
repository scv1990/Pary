/**
 * 项目名称: 七七同城
 * 
 * 文件名称: RegionAdapter.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.blog.adapter;

import java.util.List;

import com.lidroid.xutils.util.AdapterUtils;
import com.yisa.pray.R;
import com.yisa.pray.blog.entity.BlogCategroyEntity;
import com.yisa.pray.blog.entity.RegionEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 *
 * 类名称: RegionAdapter.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月19日下午4:19:54
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class RegionAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private List<RegionEntity> data;
	
	/**
	 * @param mContext
	 */
	public RegionAdapter(Context mContext, List<RegionEntity> list) {
		super();
		this.mContext = mContext;
		this.mLayoutInflater = LayoutInflater.from(mContext);
		this.data = list;
	}

	
	@Override
	public int getCount() {
		if(data == null){
			return 0;
		}
		return data.size();
	}

	@Override
	public RegionEntity getItem(int position) {
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
			convertView = mLayoutInflater.inflate(R.layout.view_simple_item, null);
		}
		TextView name = (TextView) AdapterUtils.get(convertView, R.id.name);
		name.setText(getItem(position).getName());
		return convertView;
	}

}

/**
 * 项目名称: 七七同城
 * 
 * 文件名称: UserAttentionAdapter.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.user.adapter;

import java.util.List;

import com.lidroid.xutils.util.AdapterUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yisa.pray.R;
import com.yisa.pray.entity.UserInfo;
import com.yisa.pray.views.CircleImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 *
 * 类名称: UserAttentionAdapter.java
 * 类描述:	我的关注adapter 
 * 创建人:  hq
 * 创建时间: 2016年9月2日下午5:00:09
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class UserAttentionAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<UserInfo> data;
	
	public UserAttentionAdapter(Context context){
		this.mInflater = LayoutInflater.from(context);
	}
	
	public List<UserInfo> getData() {
		return data;
	}

	public void setData(List<UserInfo> data) {
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
	public UserInfo getItem(int position) {
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
			convertView = mInflater.inflate(R.layout.item_user_favorite, null);
		}
		UserInfo entity = getItem(position);
		TextView userName = AdapterUtils.get(convertView, R.id.username);
		TextView content = AdapterUtils.get(convertView, R.id.content);
		CircleImageView avator = AdapterUtils.get(convertView, R.id.avator);
		userName.setText(entity.getName());
		if(entity.getAvatar() != null && !"".equals(entity.getAvatar())){
			ImageLoader.getInstance().displayImage(entity.getAvatar(), avator);
		}
		return convertView;
	}


}

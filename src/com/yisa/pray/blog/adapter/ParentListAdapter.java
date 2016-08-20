package com.yisa.pray.blog.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yisa.pray.R;
import com.yisa.pray.blog.entity.ImageEntity;
import com.yisa.pray.views.MyImageView;

public class ParentListAdapter extends BaseAdapter {
	private List<ImageEntity> list;
	protected LayoutInflater mInflater;

	public ParentListAdapter(Context context, List<ImageEntity> list, ListView listView) {
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		ImageEntity mImageBean = list.get(position);
		String path = mImageBean.getImagePath();
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_image_select_popupwindow, null);
			viewHolder.mImageView = (MyImageView) convertView.findViewById(R.id.parent_image);
			viewHolder.mTextViewTitle = (TextView) convertView.findViewById(R.id.parent_title);
			viewHolder.mTextViewCounts = (TextView) convertView.findViewById(R.id.parent_count);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mImageView.setImageResource(R.drawable.upload_pic_default);
		viewHolder.mTextViewTitle.setText(mImageBean.getFolderName());
		viewHolder.mTextViewCounts.setText(Integer.toString(mImageBean.getImageCounts()));
		ImageLoader.getInstance().displayImage("file:///" + path, viewHolder.mImageView, DisplayImageOptions.createSimple());
		return convertView;
	}

	public static class ViewHolder {
		public MyImageView mImageView;
		public TextView mTextViewTitle;
		public TextView mTextViewCounts;
	}

}

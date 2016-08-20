package com.yisa.pray.blog.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lidroid.xutils.util.AdapterUtils;
import com.yisa.pray.R;
import com.yisa.pray.blog.listener.OnImageDelteListener;
import com.yisa.pray.utils.ImageLoaderUtil;
import com.yisa.pray.views.MyImageView;

public class ImageShowAdapter extends BaseAdapter {
	/**
	 * 用来存储图片的选中情况
	 */
	private List<String> mImageList;
	private Activity mContext;
	private OnImageDelteListener mOnImageDeleteListener;

	public void setOnImageDeleteListener(OnImageDelteListener mOnImageDeleteListener) {
		this.mOnImageDeleteListener = mOnImageDeleteListener;
	}

	public ImageShowAdapter(Activity context, List<String> list) {
		this.mImageList = list;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return mImageList.size();
	}

	@Override
	public Object getItem(int position) {
		return mImageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		String path = mImageList.get(position);
		if (convertView == null)
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shining_image_upload, null);
		if (position == parent.getChildCount()) {
			MyImageView mImageView = AdapterUtils.get(convertView, R.id.image_select);
			ImageView mDel = AdapterUtils.get(convertView, R.id.image_del);
			mDel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mOnImageDeleteListener.onDelete(position);
				}
			});
			ImageLoaderUtil.displayImageForGlide(mContext, "file:///" + path, mImageView);
		}
		return convertView;
	}

	public void updateListView(List<String> imageList) {
		mImageList = imageList;
		notifyDataSetChanged();
	}

}

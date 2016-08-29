package com.yisa.pray.blog.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yisa.pray.R;
import com.yisa.pray.utils.ImageLoaderUtil;
import com.yisa.pray.views.MyImageView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


/**
 * 
 * 类名称: ImageSelectAdapter.java
 * 类描述: 图片选择适配器
 * 创建人: chenru
 * 创建时间: 2015-5-20
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class ImageSelectAdapter extends BaseAdapter {
	private static HashMap<Integer, Boolean> mSelectMap = new HashMap<Integer, Boolean>();
	private List<String> mAllImgReslist;
	private Activity mContext;
	protected boolean mIsChange;
	private int mCount = 0;
	private int maxSize = 0;
	private List<String> mSelectedImglist;

	public ImageSelectAdapter(Activity context, List<String> list, int maxSize, List<String> selectItems) {
		this.mAllImgReslist = list;
		this.mContext = context;
		this.maxSize = maxSize;
		mSelectedImglist = selectItems;
		mCount = mSelectedImglist.size();
	}

	@Override
	public int getCount() {
		return mAllImgReslist.size();
	}

	@Override
	public Object getItem(int position) {
		return mAllImgReslist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		String path = mAllImgReslist.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_image_select_item, null);
			viewHolder.mImageView = (MyImageView) convertView.findViewById(R.id.iv_image_select);
			viewHolder.mCheckBox = (ImageView) convertView.findViewById(R.id.cb_image_select);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (null != mSelectedImglist && mSelectedImglist.contains(path)) {
			viewHolder.mCheckBox.setSelected(true);
		} else {
			viewHolder.mCheckBox.setSelected(false);
		}
		viewHolder.mCheckBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mIsChange = false;
				if (!viewHolder.mCheckBox.isSelected()) {
					viewHolder.mCheckBox.setSelected(true);
					if (!mSelectedImglist.contains(mAllImgReslist.get(position))) {
						if (mCount < maxSize) {
							mCount++;
							mIsChange = true;
							mSelectedImglist.add(mSelectedImglist.size(), mAllImgReslist.get(position));
						} else {
							viewHolder.mCheckBox.setSelected(false);
						}
					}

				} else {
					if (mSelectedImglist.contains(mAllImgReslist.get(position))) {
						viewHolder.mCheckBox.setSelected(false);
						mCount--;
						mIsChange = true;
						mSelectedImglist.remove(mAllImgReslist.get(position));
					}

				}

			}
		});
		ImageLoaderUtil.displayImageForGlide(mContext, "file:///" + path, viewHolder.mImageView);
		return convertView;
	}

	public List<String> getSelectImg() {
		mSelectedImglist.add("Constants remark click photo choose icon");
		return mSelectedImglist;
	}

	/**
	 * 获取选中的Item的position
	 * 
	 * @return
	 */
	public List<Integer> getSelectItems() {
		List<Integer> list = new ArrayList<Integer>();
		for (Iterator<Map.Entry<Integer, Boolean>> it = mSelectMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<Integer, Boolean> entry = it.next();
			if (entry.getValue()) {
				list.add(entry.getKey());
			}
		}

		return list;
	}

	public static class ViewHolder {
		public MyImageView mImageView;
		public ImageView mCheckBox;
	}

}

package com.yisa.pray.adapter;

import java.util.ArrayList;
import java.util.List;

import com.yisa.pray.utils.ImageLoaderUtil;
import com.yisa.pray.views.photoview.PhotoView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;


public class ImageBrowserAdapter extends PagerAdapter {

	private List<String> mPhotos = new ArrayList<String>();
	private Context mContext;

	public ImageBrowserAdapter(Context mContext,List<String> photos) {
		if (photos != null) {
			mPhotos = photos;
		}
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mPhotos.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public View instantiateItem(ViewGroup container, int position) {
		PhotoView photoView = new PhotoView(container.getContext());
		String photoStr = mPhotos.get(position);
		if (!(null == mPhotos.get(position) || "".equals(mPhotos.get(position)) || "null".equals(mPhotos.get(position)))) {
//			ImageLoader.getInstance().displayImage(photoStr, photoView,ImageLoaderUtil.mThumbLoaderOptions);
			ImageLoaderUtil.displayImageForGlideGif(mContext, photoStr, photoView);
		}
		container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		return photoView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}

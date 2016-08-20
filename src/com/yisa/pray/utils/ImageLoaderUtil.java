package com.yisa.pray.utils;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.yisa.pray.R;

public class ImageLoaderUtil {

	public static DisplayImageOptions mDetailIconLoaderOptions = new DisplayImageOptions.Builder()
			.bitmapConfig(Config.RGB_565).cacheInMemory(true).cacheOnDisk(true).displayer(new SimpleBitmapDisplayer())
			.build();

	public static DisplayImageOptions mCategoryLoaderOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
			.cacheOnDisk(true).resetViewBeforeLoading(true).displayer(new SimpleBitmapDisplayer()).build();;

	public static DisplayImageOptions mThumbLoaderOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
			.cacheOnDisk(true).resetViewBeforeLoading(true).displayer(new FadeInBitmapDisplayer(300))
			.bitmapConfig(Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();

	public static DisplayImageOptions mUserIconOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
			.cacheOnDisk(true).resetViewBeforeLoading(true).displayer(new SimpleBitmapDisplayer())
			.showImageForEmptyUri(R.drawable.default_user_head_pic).showImageOnFail(R.drawable.default_user_head_pic)
			.showImageOnLoading(R.drawable.default_user_head_pic).bitmapConfig(Config.RGB_565)
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();



	public static DisplayImageOptions mAlbumLoaderOptions = new DisplayImageOptions.Builder()
			.resetViewBeforeLoading(true).displayer(new FadeInBitmapDisplayer(300)).bitmapConfig(Config.RGB_565)
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();

	public static void displayImageForGlide(Context context, String url, ImageView imageView) {
		Glide.with(context).load(url).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
		// .placeholder(R.drawable.ic_default_loading_image)
				.crossFade().into(imageView);
	}

	public static void displayImageForGlideGif(Context context, String url, ImageView imageView) {
		Glide.with(context).load(url).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL)
		// .placeholder(R.drawable.ic_default_loading_image)
				.crossFade().into(imageView);
	}

}

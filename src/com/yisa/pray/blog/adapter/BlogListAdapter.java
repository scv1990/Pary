/**
 * 项目名称: 七七同城
 * 
 * 文件名称: BlogListAdapter.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.blog.adapter;

import java.util.ArrayList;
import java.util.List;








import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.google.gson.Gson;
import com.lidroid.xutils.util.AdapterUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yisa.pray.R;
import com.yisa.pray.blog.entity.BlogEntity;
import com.yisa.pray.blog.entity.PostImage;
import com.yisa.pray.blog.imp.BlogService;
import com.yisa.pray.converter.gson.GsonConverterFactory;
import com.yisa.pray.entity.ErrorMessage;
import com.yisa.pray.utils.ImageLoaderUtil;
import com.yisa.pray.utils.ResponseCode;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UIHelper;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.utils.UserUtils;
import com.yisa.pray.views.CircleImageView;
import com.yisa.pray.views.ExpandableTextView;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

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
	private ArrayList<BlogEntity> data;
	private LayoutInflater mInflater;
	private float screenWidth = 0;;
	
	/**
	 * @param mContext
	 * @param data
	 * @param mInflater
	 */
	public BlogListAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		screenWidth = ShowUtils.getScreenSize(mContext).widthPixels - ShowUtils.dip2px(mContext, 36);
		this.mInflater = LayoutInflater.from(mContext);
	}

	public ArrayList<BlogEntity> getData() {
		return data;
	}

	public void setData(ArrayList<BlogEntity> data) {
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
		final int pos = position;
		BlogEntity blog = getItem(position);
		showFacebookImage(convertView, blog);
		CircleImageView avatar = (CircleImageView) AdapterUtils.get(convertView, R.id.user_head_pic);
		if(blog.getUser_avatar() != null && !"".equals(blog.getUser_avatar())){
			ImageLoader.getInstance().displayImage(blog.getUser_avatar(), avatar);
		}
		TextView userName = (TextView) AdapterUtils.get(convertView, R.id.user_name);
		TextView addTime = (TextView) AdapterUtils.get(convertView, R.id.add_time);
		ExpandableTextView content = (ExpandableTextView) AdapterUtils.get(convertView, R.id.posts_content);
		TextView prayNum = (TextView) AdapterUtils.get(convertView, R.id.recive_pray);
		TextView comment = (TextView) AdapterUtils.get(convertView, R.id.comment);
		Button addAttention = (Button) AdapterUtils.get(convertView, R.id.attention);
		addAttention.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				
			}
			
		});
		if(blog.getUser_id() == UserUtils.getInstance().getUser(mContext).getId()){
			addAttention.setVisibility(View.GONE);
		}else {
			addAttention.setVisibility(View.VISIBLE);
		}
		userName.setText(blog.getUser_name());
		addTime.setText(blog.getCreated_at());
		content.setText(blog.getContent());
		comment.setText(String.format(mContext.getResources().getString(R.string.comment), 0));
		prayNum.setText(String.format(mContext.getResources().getString(R.string.recive_pray), blog.getPray_number()));
		prayNum.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				recivePary(pos);
			}
		});
 		return convertView;
	}
	
	public void recivePary(int position){
		final int pos = position;
		Retrofit ret = new Retrofit.Builder()
				.baseUrl(UrlUtils.SERVER_ADDRESS)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		Log.i("token", UserUtils.getInstance().getUser(mContext).getAuthentication_token());
		Log.i("id", getItem(position).getId());
		BlogService service = ret.create(BlogService.class);
		Call<BlogEntity> call = service.recivePray(
					UserUtils.getInstance().getUser(mContext).getAuthentication_token(),
					Integer.parseInt(getItem(position).getId()));
		call.enqueue(new Callback<BlogEntity>() {

			@Override
			public void onFailure(Call<BlogEntity> arg0, Throwable arg1) {
				ShowUtils.showToast(mContext, arg1.getMessage());
				
			}

			@Override
			public void onResponse(Call<BlogEntity> arg0, Response<BlogEntity> response) {
				switch (response.code()) {
					case ResponseCode.RESPONSE_CODE_200:
						BlogEntity blog = response.body();
						data.set(pos, blog);
						notifyDataSetChanged();
						break;
	
					default:
						try {
							ErrorMessage error = new Gson().fromJson(response.errorBody().string(), ErrorMessage.class);
							ShowUtils.showToast(mContext, error.getError());
						} catch (Exception e) {
							e.printStackTrace();
							ShowUtils.showToast(mContext, mContext.getResources().getString(R.string.recive_pray_faile_tips));
						}
					break;
				}
			}
		});
	}
	
	
	public void addAttention(int position){
		
	}
	/**
	 * 展示类似facebook图片展示
	 */
	private void showFacebookImage(View convertView, BlogEntity blogItem) {
		List<PostImage> images = blogItem.getPost_images();
		ImageView singleView = AdapterUtils.get(convertView, R.id.single_img);
		LinearLayout horViewById = AdapterUtils.get(convertView, R.id.bloglist_hor_img);
		LinearLayout verViewById = AdapterUtils.get(convertView, R.id.bloglist_ver_img);
		singleView.setVisibility(View.GONE);
		horViewById.setVisibility(View.GONE);
		verViewById.setVisibility(View.GONE);
		int length = images.size() > 5 ? 5 : images.size();
		float imageWidth = 400;
		float imageHeight = 360;
//		float imageWidth = images.get(0).getWidth();
//		float imageHeight = images.get(0).getHeight();
		if (length == 1) {
			singleView.setVisibility(View.VISIBLE);
			float realWidth = screenWidth;
			float realHeight = imageHeight * screenWidth / imageWidth;
			if (imageHeight > imageWidth && imageHeight / screenWidth > 2) {
				realWidth = screenWidth;
				realHeight = screenWidth;
				singleView.setScaleType(ScaleType.FIT_CENTER);
			} else {
				singleView.setScaleType(ScaleType.CENTER_CROP);
			}
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) realWidth, (int) realHeight);
			params.gravity = Gravity.CENTER;
			singleView.setLayoutParams(params);
			ImageLoaderUtil.displayImageForGlide(mContext, images.get(0).getImage(), singleView);
			singleView.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 0));
		} else if(length > 1){
			if (imageWidth > imageHeight) {
				verViewById.setVisibility(View.VISIBLE);
				verViewById.removeAllViews();
				verViewById.setLayoutParams(new LinearLayout.LayoutParams((int) screenWidth, (int) screenWidth));
				View horView = LayoutInflater.from(mContext).inflate(R.layout.view_horizontal_more_img, null);
				verViewById.addView(horView);
				ImageView image1 = AdapterUtils.get(horView, R.id.hor_one_first_img);
				ImageView image2 = AdapterUtils.get(horView, R.id.hor_one_second_img);
				ImageView image3 = AdapterUtils.get(horView, R.id.hor_two_first_img);
				ImageView image4 = AdapterUtils.get(horView, R.id.hor_two_second_img);
				ImageView image5 = AdapterUtils.get(horView, R.id.hor_two_third_img);
				LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams((int) screenWidth, (int) screenWidth / 2);
				LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams((int) screenWidth / 2,
						(int) screenWidth / 2);
				LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams((int) screenWidth / 3 - 8,
						(int) screenWidth / 2);
				lp1.setMargins(ShowUtils.dip2px(mContext, 10), 0, 0, 0);
				lp2.setMargins(ShowUtils.dip2px(mContext, 10), 0, 0, 0);
				lp3.setMargins(ShowUtils.dip2px(mContext, 10), 0, 0, 0);
				switch (length) {
				case 2:
					image1.setVisibility(View.VISIBLE);
					image2.setVisibility(View.GONE);
					image3.setVisibility(View.VISIBLE);
					image4.setVisibility(View.GONE);
					image5.setVisibility(View.GONE);
					image1.setLayoutParams(lp1);
					image3.setLayoutParams(lp1);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(0).getImage(), image1);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(1).getImage(), image3);
					image1.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 0));
					image3.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 1));
					break;
				case 3:
					image1.setVisibility(View.VISIBLE);
					image2.setVisibility(View.GONE);
					image3.setVisibility(View.VISIBLE);
					image4.setVisibility(View.VISIBLE);
					image5.setVisibility(View.GONE);
					image1.setLayoutParams(lp1);
					image3.setLayoutParams(lp2);
					image4.setLayoutParams(lp2);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(0).getImage(), image1);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(1).getImage(), image3);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(2).getImage(), image4);
					image1.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 0));
					image3.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 1));
					image4.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 2));
					break;
				case 4:
					image1.setVisibility(View.VISIBLE);
					image2.setVisibility(View.GONE);
					image3.setVisibility(View.VISIBLE);
					image4.setVisibility(View.VISIBLE);
					image5.setVisibility(View.VISIBLE);
					image1.setLayoutParams(lp1);
					image3.setLayoutParams(lp3);
					image4.setLayoutParams(lp3);
					image5.setLayoutParams(lp3);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(0).getImage(), image1);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(1).getImage(), image3);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(2).getImage(), image4);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(3).getImage(), image5);
					image1.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 0));
					image3.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 1));
					image4.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 2));
					image5.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 3));
					break;
				case 5:
					image1.setVisibility(View.VISIBLE);
					image2.setVisibility(View.VISIBLE);
					image3.setVisibility(View.VISIBLE);
					image4.setVisibility(View.VISIBLE);
					image5.setVisibility(View.VISIBLE);
					image1.setLayoutParams(lp2);
					image2.setLayoutParams(lp2);
					image3.setLayoutParams(lp3);
					image4.setLayoutParams(lp3);
					image5.setLayoutParams(lp3);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(0).getImage(), image1);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(1).getImage(), image2);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(2).getImage(), image3);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(3).getImage(), image4);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(4).getImage(), image5);
					image1.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 0));
					image2.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 1));
					image3.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 2));
					image4.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 3));
					image5.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 4));
					break;
				}
			} else {
				horViewById.setVisibility(View.VISIBLE);
				horViewById.removeAllViews();
				horViewById.setLayoutParams(new LinearLayout.LayoutParams((int) screenWidth, (int) screenWidth));
				View verView = LayoutInflater.from(mContext).inflate(R.layout.view_vertical_more_img, null);
				horViewById.addView(verView);
				ImageView image1 = AdapterUtils.get(verView, R.id.ver_one_first_img);
				ImageView image2 = AdapterUtils.get(verView, R.id.ver_one_second_img);
				ImageView image3 = AdapterUtils.get(verView, R.id.ver_two_first_img);
				ImageView image4 = AdapterUtils.get(verView, R.id.ver_two_second_img);
				ImageView image5 = AdapterUtils.get(verView, R.id.ver_two_third_img);
				LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams((int) screenWidth / 2, (int) screenWidth);
				LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams((int) screenWidth / 2,
						(int) screenWidth / 2);
				LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams((int) screenWidth / 2,
						(int) screenWidth / 3);
				lp1.setMargins(0, ShowUtils.dip2px(mContext, 3), 0, 0);
				lp2.setMargins(0, ShowUtils.dip2px(mContext, 3), 0, 0);
				lp3.setMargins(0, ShowUtils.dip2px(mContext, 3), 0, 0);
				switch (length) {
				case 2:
					image1.setVisibility(View.VISIBLE);
					image2.setVisibility(View.GONE);
					image3.setVisibility(View.VISIBLE);
					image4.setVisibility(View.GONE);
					image5.setVisibility(View.GONE);
					image1.setLayoutParams(lp1);
					image3.setLayoutParams(lp1);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(0).getImage(), image1);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(1).getImage(), image3);
					image1.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 0));
					image3.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 1));
					break;
				case 3:
					image1.setVisibility(View.VISIBLE);
					image2.setVisibility(View.GONE);
					image3.setVisibility(View.VISIBLE);
					image4.setVisibility(View.VISIBLE);
					image5.setVisibility(View.GONE);
					image1.setLayoutParams(lp1);
					image3.setLayoutParams(lp2);
					image4.setLayoutParams(lp2);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(0).getImage(), image1);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(1).getImage(), image3);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(2).getImage(), image4);
					image1.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 0));
					image3.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 1));
					image4.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 2));
					break;
				case 4:
					image1.setVisibility(View.VISIBLE);
					image2.setVisibility(View.GONE);
					image3.setVisibility(View.VISIBLE);
					image4.setVisibility(View.VISIBLE);
					image5.setVisibility(View.VISIBLE);
					image1.setLayoutParams(lp1);
					image3.setLayoutParams(lp3);
					image4.setLayoutParams(lp3);
					image5.setLayoutParams(lp3);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(0).getImage(), image1);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(1).getImage(), image3);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(2).getImage(), image4);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(3).getImage(), image5);
					image1.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 0));
					image3.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 1));
					image4.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 2));
					image5.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 3));
					break;
				case 5:
					image1.setVisibility(View.VISIBLE);
					image2.setVisibility(View.VISIBLE);
					image3.setVisibility(View.VISIBLE);
					image4.setVisibility(View.VISIBLE);
					image5.setVisibility(View.VISIBLE);
					image1.setLayoutParams(lp2);
					image2.setLayoutParams(lp2);
					image3.setLayoutParams(lp3);
					image4.setLayoutParams(lp3);
					image5.setLayoutParams(lp3);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(0).getImage(), image1);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(1).getImage(), image2);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(2).getImage(), image3);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(3).getImage(), image4);
					ImageLoaderUtil.displayImageForGlide(mContext, images.get(4).getImage(), image5);
					image1.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 0));
					image2.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 1));
					image3.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 2));
					image4.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 3));
					image5.setOnClickListener(new OnImageClickListener(getImageUrlList(blogItem), 4));
					break;
				}
			}
		}
	}
	
	/**
	 * @param position 将图片的url放到一个list里面
	 * @return
	 */
	public ArrayList<String> getImageUrlList(BlogEntity blogItem){
		ArrayList<String> imageUrlList= new ArrayList<String>();
		ArrayList<PostImage> imageList = blogItem.getPost_images();
		if(imageList == null || imageList.size() == 0){
			return imageUrlList;
		}
		
		for(PostImage image: imageList){
			imageUrlList.add(image.getImage());
		}
		return imageUrlList;
		
	}
	
	class OnImageClickListener implements OnClickListener {
		private ArrayList<String> images;
		private int index;

		OnImageClickListener(ArrayList<String> images, int index) {
			this.images = images;
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			UIHelper.showBigImage(mContext, images, index);
		}
	}

}

/**
 * 项目名称: 七七同城
 * 
 * 文件名称: BlogListAdapter.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.blog.adapter;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import com.google.gson.Gson;
import com.lidroid.xutils.util.AdapterUtils;
import com.yisa.pray.R;
import com.yisa.pray.blog.entity.BlogEntity;
import com.yisa.pray.blog.imp.BlogService;
import com.yisa.pray.entity.ErrorMessage;
import com.yisa.pray.utils.ResponseCode;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.utils.UserUtils;
import com.yisa.pray.views.CircleImageView;
import com.yisa.pray.views.ExpandableTextView;

import android.content.Context;
import android.util.Log;
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
		final int pos = position;
		BlogEntity blog = getItem(position);
		CircleImageView avatar = (CircleImageView) AdapterUtils.get(convertView, R.id.user_head_pic);
		TextView userName = (TextView) AdapterUtils.get(convertView, R.id.user_name);
		TextView addTime = (TextView) AdapterUtils.get(convertView, R.id.add_time);
		ExpandableTextView content = (ExpandableTextView) AdapterUtils.get(convertView, R.id.posts_content);
		TextView prayNum = (TextView) AdapterUtils.get(convertView, R.id.recive_pray);
		TextView comment = (TextView) AdapterUtils.get(convertView, R.id.comment);
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
			public void onResponse(Response<BlogEntity> response, Retrofit arg1) {
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
			
			@Override
			public void onFailure(Throwable arg0) {
				ShowUtils.showToast(mContext, arg0.getMessage());
			}
		});
	}

}

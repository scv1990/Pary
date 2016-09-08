/**
 * 项目名称: 七七同城
 * 
 * 文件名称: ThankPrayAdapter.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.blog.adapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.google.gson.Gson;
import com.lidroid.xutils.util.AdapterUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yisa.pray.R;
import com.yisa.pray.blog.entity.BlogCategroyEntity;
import com.yisa.pray.blog.entity.ThankPrayEntity;
import com.yisa.pray.blog.imp.BlogService;
import com.yisa.pray.converter.gson.GsonConverterFactory;
import com.yisa.pray.entity.ErrorMessage;
import com.yisa.pray.utils.ImageLoaderUtil;
import com.yisa.pray.utils.ResponseCode;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.utils.UserUtils;
import com.yisa.pray.views.CircleImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

/**
 *
 * 类名称: ThankPrayAdapter.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年9月2日下午3:49:25
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class ThankPrayAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<ThankPrayEntity> data;
	private Context mContext;
	private boolean isHidden = true;
	
	public ThankPrayAdapter(Context context){
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
	}
	
	public List<ThankPrayEntity> getData() {
		return data;
	}

	public void setData(List<ThankPrayEntity> data) {
		this.data = data;
	}
	
	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	@Override
	public int getCount() {
		if(data == null){
			return 0;
		}
		return data.size();
	}

	@Override
	public ThankPrayEntity getItem(int position) {
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
			convertView = mInflater.inflate(R.layout.item_thank_pray, null);
		}
		final int pos = position;
		ThankPrayEntity entity = getItem(position);
		TextView userName = AdapterUtils.get(convertView, R.id.username);
		TextView content = AdapterUtils.get(convertView, R.id.content);
		CircleImageView avator = AdapterUtils.get(convertView, R.id.avator);
		CheckBox checkbox = AdapterUtils.get(convertView, R.id.check_box);
		Button button = AdapterUtils.get(convertView, R.id.thank_pray_btn);
		userName.setText(entity.getUser_name());
		content.setText(String.format(mContext.getResources().getString(R.string.thank_pray_content), entity.getCreated_at()));
		if(entity.getUser_avator() != null && !"".equals(entity.getUser_avator())){
			ImageLoader.getInstance().displayImage(entity.getUser_avator(), avator);
		}
		
		if(isHidden){
			button.setVisibility(View.GONE);
		}else{
			if(entity.isIs_thanked()){
				button.setVisibility(View.GONE);
			}else{
				button.setVisibility(View.VISIBLE);
			}
		}
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				thankPray(pos);
			}
		});
//		if(isHidden){
//			checkbox.setVisibility(View.GONE);
//			button.setVisibility(View.VISIBLE);
//		}else{
//			button.setVisibility(View.GONE);
//			if(entity.isIs_thanked()){
//				checkbox.setVisibility(View.GONE);
//				checkbox.setChecked(false);
//			}else{
//				checkbox.setVisibility(View.VISIBLE);
//			}
//		}
		checkbox.setChecked(entity.isChecked());
		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				data.get(pos).setChecked(isChecked);
				notifyDataSetChanged();
			}
			
		});
		return convertView;
	}
	
	public void thankPray(int position){
		try {
			final int pos = position;
			ThankPrayEntity entity = data.get(position);
			
			Retrofit ret = new Retrofit.Builder()
					.baseUrl(UrlUtils.SERVER_ADDRESS)
					.addConverterFactory(GsonConverterFactory.create())
					.build();
			BlogService service = ret.create(BlogService.class);
			Call<ThankPrayEntity> call = service.thankPraySingle(entity.getId(), UserUtils.getInstance().getToken(mContext));
			call.enqueue(new Callback<ThankPrayEntity>() {
				
				@Override
				public void onResponse(Call<ThankPrayEntity> call, Response<ThankPrayEntity> response) {
					try {
						switch (response.code()) {
						    case ResponseCode.RESPONSE_CODE_200:
						    	data.get(pos).setIs_thanked(true);
						    	notifyDataSetChanged();
						    	break;
							case ResponseCode.RESPONSE_CODE_201:
								ThankPrayEntity pray = response.body();
								data.set(pos, pray);
								notifyDataSetChanged();
								break;
							default:
								ErrorMessage msg = new Gson().fromJson(response.errorBody().string(), ErrorMessage.class);
								ShowUtils.showToast(mContext, msg.getError());
								break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
				}
				
				@Override
				public void onFailure(Call<ThankPrayEntity> arg0, Throwable arg1) {
					ShowUtils.showToast(mContext, arg1.getMessage());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

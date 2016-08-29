/**
 * 项目名称: 七七同城
 * 
 * 文件名称: BlogCategroyActivity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.blog.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.blog.adapter.BlogCategroyAdapter;
import com.yisa.pray.blog.entity.BlogCategroyEntity;
import com.yisa.pray.blog.imp.BlogService;
import com.yisa.pray.converter.gson.GsonConverterFactory;
import com.yisa.pray.entity.ErrorMessage;
import com.yisa.pray.utils.IntentKey;
import com.yisa.pray.utils.ResponseCode;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.views.CustomHeadView;
import com.yisa.pray.views.LoadingDialog;

/**
 *
 * 类名称: BlogCategroyActivity.java
 * 类描述: 帖子分类界面
 * 创建人: hq
 * 创建时间: 2016年8月8日下午5:07:20
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class BlogCategroyActivity extends BaseActivity {
	private static final String TAG = "BlogCategroyActivity";
	private CustomHeadView mHeadView;
	private ListView mCateListview;
	private BlogCategroyAdapter mAdapter;
	private List<BlogCategroyEntity> mCategroyList;
	private LoadingDialog mLoading;

	@Override
	public void setRootLayout() {
		setContentView(R.layout.activity_blog_categroy);
	}

	@Override
	public void initView() {
		mLoading = new LoadingDialog(mContext);
		mCategroyList = new ArrayList<BlogCategroyEntity>();
		mHeadView = (CustomHeadView) getView(R.id.head_view);
		mHeadView.setLeftIconClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mCateListview = (ListView) getView(R.id.categroy_list);
		mCateListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.putExtra(IntentKey.BLOG_CATEGROY, mCategroyList.get(position));
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		getCategroy();
	}

	public void getCategroy() {
		mLoading.show();
		Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlUtils.SERVER_ADDRESS)
				.addConverterFactory(GsonConverterFactory.create()).build();
		BlogService service = retrofit.create(BlogService.class);
		Call<List<BlogCategroyEntity>> call = service.getCategroy();
		try {
			call.enqueue(new Callback<List<BlogCategroyEntity>>() {
				@Override
				public void onFailure(Call<List<BlogCategroyEntity>> arg0, Throwable arg1) {
					Log.i(TAG + "onFailure", arg1.getMessage());
				}

				@Override
				public void onResponse(Call<List<BlogCategroyEntity>> arg0, Response<List<BlogCategroyEntity>> response) {
					switch (response.code()) {
					case ResponseCode.RESPONSE_CODE_200:
						mCategroyList = response.body();
						if (mCategroyList != null && mCategroyList.size() > 0) {
							mAdapter = new BlogCategroyAdapter(mContext, mCategroyList);
							mCateListview.setAdapter(mAdapter);
							mAdapter.notifyDataSetChanged();
						}
						break;
					default:
						String message = "";
						ErrorMessage error = new ErrorMessage();
						;
						try {
							message = response.errorBody().string();
							Gson gson = new Gson();
							error = gson.fromJson(message, ErrorMessage.class);
						} catch (IOException e) {
							e.printStackTrace();
						}
						Log.i(TAG, message);
						ShowUtils.showToast(mContext, error.getError());
						break;
					}
					
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mLoading.dismiss();
		}
	}

}

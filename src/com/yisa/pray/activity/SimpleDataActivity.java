/**
 * 项目名称: 七七同城
 * 
 * 文件名称: PeriodActivity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.adapter.SimpleDataAdapter;
import com.yisa.pray.blog.adapter.BlogCategroyAdapter;
import com.yisa.pray.blog.entity.BlogCategroyEntity;
import com.yisa.pray.entity.ErrorMessage;
import com.yisa.pray.entity.Period;
import com.yisa.pray.entity.SimpleData;
import com.yisa.pray.imp.SimpleService;
import com.yisa.pray.utils.IntentKey;
import com.yisa.pray.utils.ResponseCode;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.utils.UserUtils;
import com.yisa.pray.views.CustomHeadView;
import com.yisa.pray.views.LoadingDialog;

/**
 *
 * 类名称: PeriodActivity.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月25日下午2:56:36
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class SimpleDataActivity extends BaseActivity{
	private SimpleDataAdapter mAdapter;
	private CustomHeadView mHeadView;
	private ListView mListview;
	private List<SimpleData> mList;
	private LoadingDialog mLoading;
	private String mUrl;
	
	@Override
	public void setRootLayout() {
		setContentView(R.layout.activity_simple_data);
	}

	@Override
	public void initView() {
		Intent intent = getIntent();
		String title = intent.getStringExtra(IntentKey.TITLE);
		mUrl = intent.getStringExtra(IntentKey.URL);
		mLoading = new LoadingDialog(mContext);
		mList = new ArrayList<SimpleData>();
		mHeadView = (CustomHeadView) getView(R.id.head_view);
		mHeadView.setTitle(title);
		mHeadView.setLeftIconClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mListview = (ListView) getView(R.id.list);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.putExtra(IntentKey.DATA, mList.get(position));
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		requestData();
	}
	
	
	public void requestData(){
		Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlUtils.SERVER_ADDRESS).addConverterFactory(GsonConverterFactory.create()).build();
		try {
			SimpleService service = retrofit.create(SimpleService.class);
			Call<List<SimpleData>> call = service.getData(mUrl, UserUtils.getInstance().getUser(mContext).getAuthentication_token());
			call.enqueue(new Callback<List<SimpleData>>() {
				
				@Override
				public void onResponse(Response<List<SimpleData>> response, Retrofit retrofit) {
					switch (response.code()) {
						case  ResponseCode.RESPONSE_CODE_200:
							List<SimpleData> data = response.body();
							if(mAdapter == null){
								mAdapter = new SimpleDataAdapter(mContext);
								mListview.setAdapter(mAdapter);
							}
							mAdapter.setData(data);
							mAdapter.notifyDataSetChanged();
							break;
						default:
							ErrorMessage error;
							try {
								Log.i("simple", response.errorBody().string());
								error = new Gson().fromJson(response.errorBody().string(), ErrorMessage.class);
								ShowUtils.showToast(mContext, error.getError());
							} catch (JsonSyntaxException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
							break;
					}
				}
				
				@Override
				public void onFailure(Throwable arg0) {
					
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

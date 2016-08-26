/**
 * 项目名称: 七七同城
 * 
 * 文件名称: RegionActivity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.activity;

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
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.blog.adapter.BlogCategroyAdapter;
import com.yisa.pray.blog.adapter.RegionAdapter;
import com.yisa.pray.blog.entity.BlogCategroyEntity;
import com.yisa.pray.blog.entity.RegionEntity;
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
 * 类名称: RegionActivity.java
 * 类描述:	获取区域列表
 * 创建人:  hq
 * 创建时间: 2016年8月19日下午4:19:35
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class RegionActivity extends BaseActivity {
	private static final String TAG = "RegionActivity";
	private CustomHeadView mHeadView;
	private ListView mListview;
	private RegionAdapter mAdapter;
	private List<RegionEntity> mRegionList;
	private LoadingDialog mLoading;
	
	@Override
	public void setRootLayout() {
		setContentView(R.layout.activity_region);
	}

	@Override
	public void initView() {
		mLoading = new LoadingDialog(mContext);
		mRegionList = new ArrayList<RegionEntity>();
		mHeadView = (CustomHeadView) getView(R.id.head_view);
		mHeadView.setLeftIconClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mListview = (ListView) getView(R.id.region_list);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				RegionEntity region = mRegionList.get(position);
				intent.putExtra(IntentKey.REGION, region);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		getRegion();
	}
	
	public void getRegion(){
		mLoading.show();
		Retrofit retrofit = new Retrofit.Builder()
							.baseUrl(UrlUtils.SERVER_ADDRESS)
							.addConverterFactory(GsonConverterFactory.create())
							.build();
		BlogService service = retrofit.create(BlogService.class);
		Call<List<RegionEntity>> call = service.getRegion();
		try {
			call.enqueue(new Callback<List<RegionEntity>>(){

				@Override
				public void onFailure(Call<List<RegionEntity>> arg0, Throwable arg1) {
					Log.i(TAG+"onFailure", arg1.getMessage());
					
				}

				@Override
				public void onResponse(Call<List<RegionEntity>> arg0, Response<List<RegionEntity>> response) {
					switch (response.code()) {
						case ResponseCode.RESPONSE_CODE_200:
							mRegionList = response.body();
							if(mRegionList != null && mRegionList.size() > 0){
								mAdapter = new RegionAdapter(mContext, mRegionList);
								mListview.setAdapter(mAdapter);
								mAdapter.notifyDataSetChanged();
							}
							break;
						default:
							String message = "";
							ErrorMessage error = new ErrorMessage();;
							try {
								message = response.errorBody().string();
								Gson gson =  new Gson();
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
		}finally{
			mLoading.dismiss();
		}
	}

}

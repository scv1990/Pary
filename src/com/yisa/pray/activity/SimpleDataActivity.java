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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.adapter.SimpleDataAdapter;
import com.yisa.pray.blog.adapter.BlogCategroyAdapter;
import com.yisa.pray.blog.entity.BlogCategroyEntity;
import com.yisa.pray.converter.scalars.ScalarsConverterFactory;
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
	private static final String TAG = "SimpleDataActivity";
	private SimpleDataAdapter mAdapter;
	private CustomHeadView mHeadView;
	private ListView mListview;
	private List<SimpleData> mList;
	private LoadingDialog mLoading;
	private String url;
	
	@Override
	public void setRootLayout() {
		setContentView(R.layout.activity_simple_data);
	}

	@Override
	public void initView() {
		Intent intent = getIntent();
		String title = intent.getStringExtra(IntentKey.TITLE);
		url = intent.getStringExtra(IntentKey.URL);
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
		mLoading.show();
		Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlUtils.SERVER_ADDRESS).addConverterFactory(ScalarsConverterFactory.create()).build();
		try {
			SimpleService service = retrofit.create(SimpleService.class);
			Log.i(TAG + "token",  UserUtils.getInstance().getUser(mContext).getAuthentication_token());
			Call<String> call = service.getData(UrlUtils.GET_PERIOD, UserUtils.getInstance().getUser(mContext).getAuthentication_token());
			call.enqueue(new Callback<String>() {
				@Override
				public void onFailure(Call<String> arg0, Throwable arg1) {
					ShowUtils.showToast(mContext, arg1.getMessage());
					
				}

				@Override
				public void onResponse(Call<String> arg0, Response<String> response) {
					switch (response.code()) {
						case  ResponseCode.RESPONSE_CODE_200:
							mList = new ArrayList<SimpleData>(); 
							String data = response.body();
							Log.i(TAG, data);
							Map<String, String> datas = new Gson().fromJson(data, new TypeToken<Map<String, String>>() {}.getType());  
							SimpleData simple = null;
							Iterator<Map.Entry<String,String>> it = datas.entrySet().iterator();
							while(it.hasNext()){
								simple = new SimpleData();
								Entry<String, String> entry = it.next();
								simple.setId(entry.getKey());
								simple.setName(entry.getValue());
								mList.add(simple);
							}
							if(mAdapter == null){
								mAdapter = new SimpleDataAdapter(mContext);
								mListview.setAdapter(mAdapter);
							}
							mAdapter.setData(mList);
							mAdapter.notifyDataSetChanged();
							break;
						case ResponseCode.RESPONSE_CODE_404 :
							ShowUtils.showToast(mContext, getResources().getString(R.string.internet_address_is_not_found));
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
			});
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			mLoading.dismiss();
		}
		
	}

}

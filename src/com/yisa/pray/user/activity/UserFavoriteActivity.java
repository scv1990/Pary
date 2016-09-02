/**
 * 项目名称: 七七同城
 * 
 * 文件名称: UserFavoriteActivity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.user.activity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.view.View;
import android.widget.ListView;

import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.converter.gson.GsonConverterFactory;
import com.yisa.pray.entity.UserInfo;
import com.yisa.pray.imp.UserService;
import com.yisa.pray.user.adapter.UserAttentionAdapter;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.utils.UserUtils;
import com.yisa.pray.views.CustomHeadView;
import com.yisa.pray.views.swipe.SwipyRefreshLayout;
import com.yisa.pray.views.swipe.SwipyRefreshLayout.OnRefreshListener;
import com.yisa.pray.views.swipe.SwipyRefreshLayoutDirection;

/**
 *
 * 类名称: UserFavoriteActivity.java
 * 类描述:	我的关注列表
 * 创建人:  hq
 * 创建时间: 2016年9月2日下午4:56:38
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class UserFavoriteActivity extends BaseActivity implements OnRefreshListener{
	private CustomHeadView mHeadView;
	private SwipyRefreshLayout mSwipy;
	private ListView mListView;
	private UserAttentionAdapter mAdapter;
	private int mPage = 1;
	private int mPageCount = 10;
	private List<UserInfo> mUserList;
	
	
	@Override
	public void setRootLayout() {
		setContentView(R.layout.activity_user_attention);
	}

	@Override
	public void initView() {
		mHeadView = (CustomHeadView) getView(R.id.head_view);
		mHeadView.setLeftIconClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mSwipy = (SwipyRefreshLayout) getView(R.id.swipy);
		mSwipy.setDirection(SwipyRefreshLayoutDirection.BOTH);
		mSwipy.setOnRefreshListener(this);
		mListView = (ListView) getView(R.id.notice_list);
		mUserList = new ArrayList<UserInfo>();
	}

	@Override
	public void onRefresh(SwipyRefreshLayoutDirection direction) {
		if(direction == SwipyRefreshLayoutDirection.TOP){
			mPage = 1;
			mUserList.removeAll(mUserList);
		}else if(direction == SwipyRefreshLayoutDirection.BOTTOM){
			mPage++;
		}
		getList();
	}
	
	public void getList(){
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(UrlUtils.SERVER_ADDRESS)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		
		UserService service = retrofit.create(UserService.class);
		
		Call<List<UserInfo>> call = service.getAttentionList(
						UserUtils.getInstance().getToken(mActivity), 
						mPage, 
						mPageCount);
		call.enqueue(new Callback<List<UserInfo>>() {
			
			@Override
			public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
				try {
					mUserList.addAll(response.body());
					if(mAdapter == null){
						mAdapter = new UserAttentionAdapter(mActivity);
						mListView.setAdapter(mAdapter);
					}
					mAdapter.setData(mUserList);
					mAdapter.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Call<List<UserInfo>> call, Throwable throwable) {
				ShowUtils.showToast(mActivity, throwable.getMessage());
			}
		});
	}

}

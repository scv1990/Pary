/**
 * 项目名称: 七七同城
 * 
 * 文件名称: ThankPrayActivity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.blog.activity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.blog.adapter.ThankPrayAdapter;
import com.yisa.pray.blog.entity.ThankPrayEntity;
import com.yisa.pray.blog.imp.BlogService;
import com.yisa.pray.converter.gson.GsonConverterFactory;
import com.yisa.pray.entity.UserInfo;
import com.yisa.pray.imp.UserService;
import com.yisa.pray.user.adapter.UserAttentionAdapter;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.utils.UserUtils;
import com.yisa.pray.views.CustomHeadView;
import com.yisa.pray.views.swipe.SwipyRefreshLayout;
import com.yisa.pray.views.swipe.SwipyRefreshLayoutDirection;
import com.yisa.pray.views.swipe.SwipyRefreshLayout.OnRefreshListener;

/**
 *
 * 类名称: ThankPrayActivity.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年9月2日下午3:49:03
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class ThankPrayActivity extends BaseActivity implements OnRefreshListener{
	private CustomHeadView mHeadView;
	private SwipyRefreshLayout mSwipy;
	private ListView mListView;
	private ThankPrayAdapter mAdapter;
	private Button mThankPrayBatchBtn;
	private int mPage = 1;
	private int mPageCount = 10;
	private List<ThankPrayEntity> mPrayList;
	
	
	@Override
	public void setRootLayout() {
		setContentView(R.layout.activity_thank_pray);

	}

	@Override
	public void initView() {
		mHeadView = (CustomHeadView) getView(R.id.head_view);
		mThankPrayBatchBtn = (Button) getView(R.id.thank_pray_batch_btn);
		mHeadView.setLeftIconClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mHeadView.setRightText(getResources().getString(R.string.thank_pray_batch), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isHidden = mAdapter.isHidden();
				mAdapter.setHidden(!isHidden);
				if(isHidden){
					mThankPrayBatchBtn.setVisibility(View.VISIBLE);
				}else{
					mThankPrayBatchBtn.setVisibility(View.GONE);
				}
				mAdapter.notifyDataSetChanged();
			}
		});
		
		mSwipy = (SwipyRefreshLayout) getView(R.id.swipy);
		mSwipy.setDirection(SwipyRefreshLayoutDirection.BOTH);
		mSwipy.setOnRefreshListener(this);
		mListView = (ListView) getView(R.id.notice_list);
		mPrayList = new ArrayList<ThankPrayEntity>();
	}

	@Override
	public void onRefresh(SwipyRefreshLayoutDirection direction) {
		if(direction == SwipyRefreshLayoutDirection.TOP){
			mPage = 1;
			mPrayList.removeAll(mPrayList);
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
		
		BlogService service = retrofit.create(BlogService.class);
		
		Call<List<ThankPrayEntity>> call = service.getPrayList(
						UserUtils.getInstance().getToken(mActivity), 
						mPage, 
						mPageCount);
		call.enqueue(new Callback<List<ThankPrayEntity>>() {
			
			@Override
			public void onResponse(Call<List<ThankPrayEntity>> call, Response<List<ThankPrayEntity>> response) {
				try {
					mPrayList.addAll(response.body());
					if(mAdapter == null){
						mAdapter = new ThankPrayAdapter(mActivity);
						mListView.setAdapter(mAdapter);
					}
					mAdapter.setData(mPrayList);
					mAdapter.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Call<List<ThankPrayEntity>> call, Throwable throwable) {
				ShowUtils.showToast(mActivity, throwable.getMessage());
			}
		});
	}
}
/**
 * 项目名称: 七七同城
 * 
 * 文件名称: MessageFragment.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.notification.fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lidroid.xutils.ui.BaseFragment;
import com.yisa.pray.R;
import com.yisa.pray.converter.gson.GsonConverterFactory;
import com.yisa.pray.notification.entity.Notification;
import com.yisa.pray.notification.imp.NoticeService;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.utils.UserUtils;
import com.yisa.pray.views.swipe.SwipyRefreshLayout;
import com.yisa.pray.views.swipe.SwipyRefreshLayout.OnRefreshListener;
import com.yisa.pray.views.swipe.SwipyRefreshLayoutDirection;

/**
 *
 * 类名称: MessageFragment.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月1日下午5:46:31
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class MessageFragment extends BaseFragment implements OnRefreshListener{
	private SwipyRefreshLayout mSwipy;
	private ListView mListView;
	
	private int mPage = 1;
	private int mPageCount = 10;
	private List<Notification> mNoticeList;
	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		return inflater.inflate(R.layout.fragment_notice, null);
	}

	@Override
	public void onInitView(View view, Bundle savedInstanceState) {
		mSwipy = (SwipyRefreshLayout) getView(R.id.swipy);
		mSwipy.setDirection(SwipyRefreshLayoutDirection.BOTH);
		mSwipy.setOnRefreshListener(this);
		mListView = (ListView) getView(R.id.notice_list);
		mNoticeList = new ArrayList<Notification>();
	}

	@Override
	public void onRefresh(SwipyRefreshLayoutDirection direction) {
		if(direction == SwipyRefreshLayoutDirection.TOP){
			mPage = 1;
			mNoticeList.removeAll(mNoticeList);
		}else if(direction == SwipyRefreshLayoutDirection.BOTTOM){
			mPage++;
		}
		
	}
	
	public void getNotice(){
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(UrlUtils.SERVER_ADDRESS)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		
		NoticeService service = retrofit.create(NoticeService.class);
		
		Call<List<Notification>> call = service.getNotice(
						UserUtils.getInstance().getToken(mActivity), 
						mPage, 
						mPageCount);
		call.enqueue(new Callback<List<Notification>>() {
			
			@Override
			public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
				try {
					mNoticeList.addAll(response.body());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Call<List<Notification>> call, Throwable throwable) {
				ShowUtils.showToast(mActivity, throwable.getMessage());
			}
		});
	}

}

/**
 * 项目名称: 七七同城
 * 
 * 文件名称: BlogMainFragment.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lidroid.xutils.ui.BaseFragment;
import com.yisa.pray.R;
import com.yisa.pray.activity.LoginActivity;
import com.yisa.pray.activity.RegisterActivity;
import com.yisa.pray.adapter.BlogListAdapter;
import com.yisa.pray.entity.BlogEntity;
import com.yisa.pray.entity.ErrorMessage;
import com.yisa.pray.entity.UserInfo;
import com.yisa.pray.imp.BlogService;
import com.yisa.pray.utils.Constants;
import com.yisa.pray.utils.IntentKey;
import com.yisa.pray.utils.PreferenceUtils;
import com.yisa.pray.utils.ResponseCode;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UIHelper;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.views.CustomHeadView;
import com.yisa.pray.views.swipe.SwipyRefreshLayout;
import com.yisa.pray.views.swipe.SwipyRefreshLayout.OnRefreshListener;
import com.yisa.pray.views.swipe.SwipyRefreshLayoutDirection;

/**
 *
 * 类名称: BlogMainFragment.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月1日下午4:56:27
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class BlogMainFragment extends BaseFragment implements OnRefreshListener{
	private static final int REQUEST_ADD_BLOG = 0x0001;
	private CustomHeadView mHeadView;
	private SwipyRefreshLayout mRefresh;
	private ListView mListView;
	private BlogListAdapter mAdapter;
	private List<BlogEntity> mBlogList;
	
	private int mPage = 0;
	private int mPerPage = 10;
	private String mRegionId = "1";
	private String mCateId = "1";
	private String mSort = "id";
	private String mOrder = "desc";
	private String mToken ="";
	private UserInfo mUserInfo;
	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		return inflater.inflate(R.layout.fragment_blog_main, null);
	}

	@Override
	public void onInitView(View view, Bundle savedInstanceState) {
		initHeadView();
		mRefresh = (SwipyRefreshLayout) getView(R.id.swipy);
		mRefresh.setDirection(SwipyRefreshLayoutDirection.BOTH);
		mRefresh.setOnRefreshListener(this);
		mListView = (ListView) getView(R.id.blog_list);
		mAdapter = new BlogListAdapter(mActivity);
		mListView.setAdapter(mAdapter);
		addListHead(); 
		getBlogList();
	}
	
	/**
	 * @Title: initHeadView 
	 * @Description: TODO(c初始化headview) 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void initHeadView(){
		mHeadView = (CustomHeadView) getView(R.id.head_view);
		String userStr = PreferenceUtils.getPrefString(mActivity, IntentKey.USERINFO, null);
		mHeadView.setLeftText(R.string.register, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mActivity, RegisterActivity.class);
				mActivity.startActivityForResult(intent, Constants.PRAY_WALL_TO_REGISTER_REQ_CODE);
			}
		});
		
		mHeadView.setRightText(R.string.login, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mActivity, LoginActivity.class);
				mActivity.startActivityForResult(intent, Constants.PRAY_WALL_TO_REGISTER_REQ_CODE);
			}
		});
		if(userStr == null || "".equals(userStr)){
			mHeadView.setRightTextVisibile(View.VISIBLE);
			mHeadView.setLeftTextVisibile(View.VISIBLE);
		}else{
			mUserInfo = new Gson().fromJson(userStr, UserInfo.class);
			mToken = mUserInfo.getAuthentication_token();
			mHeadView.setRightTextVisibile(View.GONE);
			mHeadView.setLeftTextVisibile(View.GONE);
		}
	}
	
	public void addListHead(){
		View layout = LayoutInflater.from(mActivity).inflate(R.layout.view_add_blog, null);
		TextView addBlogBtn = (TextView) layout.findViewById(R.id.add_blog);
		addBlogBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showBlogEdit(mActivity, REQUEST_ADD_BLOG);
			}
		});
		
		mListView.addHeaderView(layout);
	}
	
	public void getBlogList(){
		Retrofit retrofit = new Retrofit.Builder()
								.baseUrl(UrlUtils.SERVER_ADDRESS)
								.addConverterFactory(GsonConverterFactory.create())
								.build();
		BlogService service = retrofit.create(BlogService.class);
		Call<BlogEntity[]> call = service.getBlogList(  mPage, 
														mPerPage,  
														mToken, 
														mCateId, 
														mRegionId, 
														mSort, 
														mOrder);
		
		call.enqueue(new Callback<BlogEntity[]>() {
			@Override
			public void onResponse(Response<BlogEntity[]> response, Retrofit arg1) {
				mBlogList = new ArrayList<BlogEntity>();
				switch(response.code()){
					case ResponseCode.RESPONSE_CODE_200 :
						BlogEntity[] blogs = response.body();
						Collections.addAll(mBlogList, blogs);
						if(mBlogList == null || mBlogList.size() == 0){
							ShowUtils.showToast(mActivity, getResources().getString(R.string.no_blog_tips));
						}
						break;
					default :
						ErrorMessage error = new ErrorMessage();
						try {
							error = new Gson().fromJson(response.errorBody().string(), ErrorMessage.class);
							ShowUtils.showToast(mActivity, error.getError());
						} catch (Exception e) {
							e.printStackTrace();
						} 
						break;
				}
				mAdapter.setData(mBlogList);
				mAdapter.notifyDataSetChanged();
				mRefresh.setRefreshing(false);
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				ShowUtils.showToast(mActivity, arg0.getMessage());
			}
		});
				
	}

	@Override
	public void onRefresh(SwipyRefreshLayoutDirection direction) {
		if(direction == SwipyRefreshLayoutDirection.TOP){
			mPage = 0;
		}else{
			mPage ++;
		}
		getBlogList();
	}

}

/**
 * 项目名称: 七七同城
 * 
 * 文件名称: BlogMainFragment.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.blog.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.ui.BaseFragment;
import com.yisa.pray.R;
import com.yisa.pray.activity.LoginActivity;
import com.yisa.pray.activity.RegisterActivity;
import com.yisa.pray.blog.adapter.BlogListAdapter;
import com.yisa.pray.blog.entity.BlogEntity;
import com.yisa.pray.blog.imp.BlogService;
import com.yisa.pray.entity.ErrorMessage;
import com.yisa.pray.entity.OnlineCountEntity;
import com.yisa.pray.entity.UserInfo;
import com.yisa.pray.imp.UserService;
import com.yisa.pray.utils.Constants;
import com.yisa.pray.utils.IntentKey;
import com.yisa.pray.utils.PreferenceUtils;
import com.yisa.pray.utils.ResponseCode;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UIHelper;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.utils.UserUtils;
import com.yisa.pray.views.CustomHeadView;
import com.yisa.pray.views.swipe.SwipyRefreshLayout;
import com.yisa.pray.views.swipe.SwipyRefreshLayout.OnRefreshListener;
import com.yisa.pray.views.swipe.SwipyRefreshLayoutDirection;

/**
 *
 * 类名称: BlogMainFragment.java
 * 类描述:	 帖子列表页
 * 创建人:  hq
 * 创建时间: 2016年8月1日下午4:56:27
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class BlogMainFragment extends BaseFragment implements OnRefreshListener{
	private static final String TAG = "BlogMainFragment";
	private static final int REQUEST_ADD_BLOG = 0x0001;
	private CustomHeadView mHeadView;
	private SwipyRefreshLayout mRefresh;
	private ListView mListView;
	private BlogListAdapter mAdapter;
	private List<BlogEntity> mBlogList;
	private View mCreatPost;
	private TextView mZeroClockTxt;
	private TextView mThreeClockTxt;
	private TextView mSixClockTxt;
	private TextView mNineClockTxt;
	
	private Timer mTimer;
	private int mPage = 0;
	private int mPerPage = 10;
	private String mRegionId = "0";
	private String mCateId = "0";
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
		initToolBar();
		mRefresh = (SwipyRefreshLayout) getView(R.id.swipy);
		mRefresh.setDirection(SwipyRefreshLayoutDirection.BOTH);
		mRefresh.setOnRefreshListener(this);
		mListView = (ListView) getView(R.id.blog_list);
		addListHead(); 
		getBlogList();
		mTimer = new Timer();
		mTimer.schedule(new getOnlineNumTask(), 60*1000);
		Log.i(TAG,UserUtils.getInstance().getUser(mActivity).getAuthentication_token());
	}
	
	public void initToolBar(){
		mZeroClockTxt = (TextView) getView(R.id.twelve_clock);
		mThreeClockTxt = (TextView) getView(R.id.three_clock);
		mSixClockTxt = (TextView) getView(R.id.six_clock);
		mNineClockTxt = (TextView) getView(R.id.nine_clock);
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
		mCreatPost = LayoutInflater.from(mActivity).inflate(R.layout.view_add_blog, null);
		mCreatPost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showBlogEdit(mActivity, REQUEST_ADD_BLOG);
			}
		});
		
		mListView.addHeaderView(mCreatPost);
	}
	
	/**
	 * @Title: getBlogList 
	 * @Description: TODO(取帖子列表) 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void getBlogList(){
		Retrofit retrofit = new Retrofit.Builder()
								.baseUrl(UrlUtils.SERVER_ADDRESS)
								.addConverterFactory(GsonConverterFactory.create())
								.build();
		BlogService service = retrofit.create(BlogService.class);
		Call<BlogEntity[]> call = service.getBlogList(  mPage, 
														mPerPage,  
														mToken, 
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
				if(mAdapter == null){
					mAdapter = new BlogListAdapter(mActivity);
					mListView.setAdapter(mAdapter);
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
	/**
	 * 类名称: BlogMainFragment.java
	 * 类描述:	获取在线人数线程 
	 * 创建人:  hq
	 * 创建时间: 2016年8月12日下午2:08:29
	 * -------------------------修订历史------------
	 * 修改人:
	 * 修改时间:
	 * 修改备注:
	 */
	public class getOnlineNumTask extends TimerTask{
		Retrofit retrofit = new Retrofit.Builder()
								.baseUrl(UrlUtils.SERVER_ADDRESS)
								.addConverterFactory(GsonConverterFactory.create())
								.build();
		UserService service = retrofit.create(UserService.class);
		Call<OnlineCountEntity> call = service.getOnlineNum(
					UserUtils.getInstance().getUser(mActivity).getAuthentication_token());
		
		@Override
		public void run() {
			call.enqueue(new Callback<OnlineCountEntity>() {

				@Override
				public void onFailure(Throwable arg0) {
					ShowUtils.showToast(mActivity, arg0.getMessage());
				}

				@Override
				public void onResponse(Response<OnlineCountEntity> response, Retrofit ret) {
					try {
						switch (response.code()) {
						case ResponseCode.RESPONSE_CODE_200:
							OnlineCountEntity data = response.body();
							Log.i(TAG, data.getCount());
							setOnlineNum(data.getCount());
							break;
						default:
//							ErrorMessage error = new Gson().fromJson(response.errorBody().string(), ErrorMessage.class);
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
	}
	/**
	 * @Title: setOnlineNum 
	 * @Description: TODO(根据时间来设置显示现在人数的位置) 
	 * @param @param onlineNum    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void setOnlineNum(String onlineNum){
		int time = Calendar.getInstance().get(Calendar.HOUR);
		if (time < 3) {
			mZeroClockTxt.setText(onlineNum);
			mZeroClockTxt.setVisibility(View.VISIBLE);
			mThreeClockTxt.setVisibility(View.GONE);
			mSixClockTxt.setVisibility(View.GONE);
			mNineClockTxt.setVisibility(View.GONE);
		}else if(time >=3 && time <6){
			mThreeClockTxt.setText(onlineNum);
			mZeroClockTxt.setVisibility(View.GONE);
			mThreeClockTxt.setVisibility(View.VISIBLE);
			mSixClockTxt.setVisibility(View.GONE);
			mNineClockTxt.setVisibility(View.GONE);
		}else if(time >=6 && time <9){
			mSixClockTxt.setText(onlineNum);
			mZeroClockTxt.setVisibility(View.GONE);
			mThreeClockTxt.setVisibility(View.GONE);
			mSixClockTxt.setVisibility(View.VISIBLE);
			mNineClockTxt.setVisibility(View.GONE);
		}else if(time >=9){
			mNineClockTxt.setText(onlineNum);
			mZeroClockTxt.setVisibility(View.GONE);
			mThreeClockTxt.setVisibility(View.GONE);
			mSixClockTxt.setVisibility(View.GONE);
			mNineClockTxt.setVisibility(View.VISIBLE);
		}
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

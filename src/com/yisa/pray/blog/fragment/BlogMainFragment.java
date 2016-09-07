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
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.ui.BaseFragment;
import com.yisa.pray.R;
import com.yisa.pray.activity.LoginActivity;
import com.yisa.pray.activity.RegionActivity;
import com.yisa.pray.activity.RegisterActivity;
import com.yisa.pray.blog.activity.BlogCategroyActivity;
import com.yisa.pray.blog.activity.EditBlogActivity;
import com.yisa.pray.blog.adapter.BlogListAdapter;
import com.yisa.pray.blog.entity.BlogCategroyEntity;
import com.yisa.pray.blog.entity.BlogEntity;
import com.yisa.pray.blog.entity.RegionEntity;
import com.yisa.pray.blog.imp.BlogService;
import com.yisa.pray.converter.gson.GsonConverterFactory;
import com.yisa.pray.entity.ErrorMessage;
import com.yisa.pray.entity.OnlineCountEntity;
import com.yisa.pray.entity.UserInfo;
import com.yisa.pray.imp.UserService;
import com.yisa.pray.utils.Constants;
import com.yisa.pray.utils.IntentKey;
import com.yisa.pray.utils.PreferenceUtils;
import com.yisa.pray.utils.ResponseCode;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.utils.UserUtils;
import com.yisa.pray.views.CustomHeadView;
import com.yisa.pray.views.DrawableCenterTextView;
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
public class BlogMainFragment extends BaseFragment implements OnRefreshListener, OnClickListener{
	private static final String TAG = "BlogMainFragment";
	private CustomHeadView mHeadView;
	private SwipyRefreshLayout mRefresh;
	private ListView mListView;
	private BlogListAdapter mAdapter;
	private ArrayList<BlogEntity> mBlogList  = new ArrayList<BlogEntity>();
	private View mCreatPost;
	private TextView mZeroClockTxt;
	private TextView mThreeClockTxt;
	private TextView mSixClockTxt;
	private TextView mNineClockTxt;
	private DrawableCenterTextView mRegionTxt;
	private DrawableCenterTextView mCateTxt;
	private TextView mAllOnlineTxt;
	private TextView mRegionOnlineTxt;
	private Timer mTimer;
	private int mPage = 1;
	private int mPerPage = 10;
	private RegionEntity mRegion = null;
	private BlogCategroyEntity mCategroy = null;
	private Integer mRegionId = null;
	private Integer mCategroyId = null;
	private String mSort = "id";
	private String mOrder = "desc";
	private String mToken ="";
	private UserInfo mUserInfo;
	private boolean mHasLoadedOnce = false;
	
	public BlogMainFragment(String token){
		this.mToken = token;
	}
	
	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		setUserVisibleHint(true);
		Log.i(TAG , "BlogMainFragment inflaterView");
		return inflater.inflate(R.layout.fragment_blog_main, null);
	}

	@Override
	public void onInitView(View view, Bundle savedInstanceState) {
		Log.i(TAG , "BlogMainFragment init");
		initHeadView();
		initToolBar();
		mRefresh = (SwipyRefreshLayout) getView(R.id.blog_swipy);
		mRefresh.setDirection(SwipyRefreshLayoutDirection.BOTH);
		mRefresh.setOnRefreshListener(this);
		mListView = (ListView) getView(R.id.blog_list);
		addListHead(); 
//		getBlogList();
		mTimer = new Timer();
		mTimer.schedule(new getOnlineNumTask(), 0, 60*1000);
		mAdapter = new BlogListAdapter(mActivity);
		mListView.setAdapter(mAdapter);
		mAdapter.setData(mBlogList);
		mAdapter.notifyDataSetChanged();
	}
	
	public void initToolBar(){
		mRegionTxt = (DrawableCenterTextView) getView(R.id.area);
		mCateTxt = (DrawableCenterTextView) getView(R.id.categroy);
		mRegionTxt.setOnClickListener(this);
		mCateTxt.setOnClickListener(this);
		mZeroClockTxt = (TextView) getView(R.id.twelve_clock);
		mThreeClockTxt = (TextView) getView(R.id.three_clock);
		mSixClockTxt = (TextView) getView(R.id.six_clock);
		mNineClockTxt = (TextView) getView(R.id.nine_clock);
		mAllOnlineTxt = (TextView) getView(R.id.on_line_count);
		mRegionOnlineTxt = (TextView) getView(R.id.region_line_count);
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
		String userStr = PreferenceUtils.getPrefString(mActivity, IntentKey.USERINFO, null);
		if(userStr == null || "".equals(userStr)){
			mHeadView.setRightTextVisibile(View.VISIBLE);
			mHeadView.setLeftTextVisibile(View.VISIBLE);
		}else{
			mHeadView.setRightTextVisibile(View.GONE); 
			mHeadView.setLeftTextVisibile(View.GONE);
		}
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && !mHasLoadedOnce && mBlogList.size() == 0) {
            getBlogList();
            mHasLoadedOnce = true;
        }
		super.setUserVisibleHint(isVisibleToUser);
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
			case R.id.area:
				Intent intent = new Intent(mActivity, RegionActivity.class);
				mActivity.startActivityForResult(intent, Constants.PRAY_WALL_TO_REGION_REQ_CODE);
				break;
			case R.id.categroy:
				Intent intent2 = new Intent(mActivity, BlogCategroyActivity.class);
				mActivity.startActivityForResult(intent2, Constants.PRAY_WALL_TO_CATEGROY_REQ_CODE);
				break;
			default:
				break;
		}
	}
	
	public void addListHead(){
		mCreatPost = LayoutInflater.from(mActivity).inflate(R.layout.view_add_blog, null);
		mCreatPost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, EditBlogActivity.class);
				mActivity.startActivityForResult(intent, Constants.PRAY_WALL_TO_EDIT_BLOG_REQ_CODE);
//				UIHelper.showBlogEdit(mActivity, REQUEST_ADD_BLOG);
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
		Log.i(TAG, "getBlogList");
		Retrofit retrofit = new Retrofit.Builder()
								.baseUrl(UrlUtils.SERVER_ADDRESS)
								.addConverterFactory(GsonConverterFactory.create())
								.build();
		BlogService service = retrofit.create(BlogService.class);
		if(mRegion == null){
			mRegionId = null;
		}else{
			mRegionId = mRegion.getId();
			Log.i(TAG + "mRegionId", mRegionId + "");
		}
		if(mCategroy == null){
			mCategroyId = null;
		}else{
			mCategroyId = mCategroy.getId();
			Log.i(TAG + "mCategroyId", mCategroyId + "");
		}
		
		Call<BlogEntity[]> call = service.getBlogList(  mPage, 
														mPerPage,  
														mToken, 
														mCategroyId,
														mRegionId,
														mSort, 
														mOrder);
		Log.i(TAG + "mPage", mPage +"");
		Log.i(TAG + "mPerPage", mPerPage +"");
		Log.i(TAG + "mToken", mToken);
		Log.i(TAG + "mCategroyId", mCategroyId + "");
		Log.i(TAG + "mRegionId", mRegionId +"");
		Log.i(TAG + "mSort", mSort);
		Log.i(TAG + "mOrder", mOrder);
		
		call.enqueue(new Callback<BlogEntity[]>() {

			@Override
			public void onFailure(Call<BlogEntity[]> arg0, Throwable arg1) {
				ShowUtils.showToast(mActivity, arg1.getMessage());
				Log.i(TAG, arg1.getMessage());
			}

			@Override
			public void onResponse(Call<BlogEntity[]> arg0, Response<BlogEntity[]> response) {
				Log.i(TAG, response.code() + "");
				try {
					switch(response.code()){
					case ResponseCode.RESPONSE_CODE_200 :
						BlogEntity[] blogs = response.body();
						if(blogs == null || blogs.length == 0){
							ShowUtils.showToast(mActivity, getResources().getString(R.string.no_blog_tips));
						}else{
							Collections.addAll(mBlogList, blogs);
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
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					mRefresh.setRefreshing(false);
				}
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
			Log.i("TASK", "getOnlineNumTask");
			call.enqueue(new Callback<OnlineCountEntity>() {

				@Override
				public void onFailure(Call<OnlineCountEntity> arg0, Throwable arg1) {
					ShowUtils.showToast(mActivity, arg1.getMessage());
					
				}

				@Override
				public void onResponse(Call<OnlineCountEntity> arg0, Response<OnlineCountEntity> response) {
					try {
						switch (response.code()) {
							case ResponseCode.RESPONSE_CODE_200:
								OnlineCountEntity data = response.body();
								setOnlineNum(data.getCount());
								break;
							default:
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
		mAllOnlineTxt.setText(String.format(getResources().getString(R.string.all_online_count), onlineNum));
		mRegionOnlineTxt.setText(String.format(getResources().getString(R.string.regions_online_count), onlineNum));
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
			mBlogList = new ArrayList<BlogEntity>();
			mPage = 1;
		}else{
			mPage ++;
		}
		getBlogList();
	}
	
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if(resultCode == Activity.RESULT_OK){
//			switch (requestCode) {
//				case Constants.PRAY_WALL_TO_EDIT_BLOG_REQ_CODE:
//					onRefresh(SwipyRefreshLayoutDirection.TOP);
//					break;
//				case Constants.PRAY_WALL_TO_REGION_REQ_CODE:
//					mRegion = (RegionEntity) data.getSerializableExtra(IntentKey.REGION);
//					mRegionTxt.setText(mRegion.getName());
//					break;
//				case Constants.PRAY_WALL_TO_CATEGROY_REQ_CODE:
//					mCategroy = (BlogCategroyEntity) data.getSerializableExtra(IntentKey.BLOG_CATEGROY);
//					mCateTxt.setText(mCategroy.getName());
//					break;
//				default:
//					break;
//			}
//		}
//	}
	
	public void setRegion(RegionEntity region){
		mRegion = region;
		mRegionTxt.setText(mRegion.getName());
		onRefresh(SwipyRefreshLayoutDirection.TOP);
	}
	
	public void setCategroy(BlogCategroyEntity cate){
		mCategroy = cate;
		mCateTxt.setText(mCategroy.getName());
		onRefresh(SwipyRefreshLayoutDirection.TOP);
	}
	
	@Override
	public void onDestroy() {
		Log.i(TAG, "ondestroy");
		mTimer.cancel();
		super.onDestroy();
	}
	
	@Override
	public void onPause() {
		Log.i(TAG, "onPause");
		mTimer.cancel();
		super.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

}

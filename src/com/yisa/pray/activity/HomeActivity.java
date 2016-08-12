/**
 * 项目名称: 七七同城
 * 
 * 文件名称: HomeActivity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.activity;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.adapter.HomePageAdaper;
import com.yisa.pray.fragment.BlogMainFragment;
import com.yisa.pray.fragment.MessageFragment;
import com.yisa.pray.fragment.UserCenterFragment;
import com.yisa.pray.views.CustomHeadView;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

/**
 *
 * 类名称: HomeActivity.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月1日下午5:47:16
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class HomeActivity extends BaseActivity implements OnPageChangeListener{
	private TextView mBlogTxt;
	private TextView mNotiTxt;
	private TextView mUserCenterTxt;
	private ViewPager mViewPager;
	private Fragment mBlogFragment;
	private Fragment mNotiFragment;
	private Fragment mUserCenterFragment;
	private HomePageAdaper mAdapter;
	private List<Fragment> mFragmentList;
	private FragmentManager mFm;
	@Override
	public void setRootLayout() {
		setContentView(R.layout.activity_home);

	}

	@Override
	public void initView() {
		mBlogTxt = (TextView) getView(R.id.pray_wall_title);
		mNotiTxt = (TextView) getView(R.id.notification_title);
		mUserCenterTxt = (TextView) getView(R.id.user_center_title);
		mViewPager = (ViewPager) getView(R.id.view_page);
		mFragmentList = new ArrayList<Fragment>();
		
		mBlogFragment = new BlogMainFragment();
		mNotiFragment = new MessageFragment();
		mUserCenterFragment = new UserCenterFragment();
		
		mFragmentList.add(mBlogFragment);
		mFragmentList.add(mNotiFragment);
		mFragmentList.add(mUserCenterFragment);
		mFm = this.getSupportFragmentManager();
		mAdapter = new HomePageAdaper(mFm ,mFragmentList);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(0);
		mBlogTxt.setPressed(true);
		mViewPager.setOnPageChangeListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		switch (position) {
		case 0:
			mBlogTxt.setPressed(true);
			mNotiTxt.setPressed(false);
			mUserCenterTxt.setPressed(false);
			break;
		case 1:
			mBlogTxt.setPressed(false);
			mNotiTxt.setPressed(true);
			mUserCenterTxt.setPressed(false);
			break;
		case 2:
			mBlogTxt.setPressed(false);
			mNotiTxt.setPressed(false);
			mUserCenterTxt.setPressed(true);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent Intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, responseCode, Intent);
	}

}

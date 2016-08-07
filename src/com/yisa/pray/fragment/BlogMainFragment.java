/**
 * 项目名称: 七七同城
 * 
 * 文件名称: BlogMainFragment.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ui.BaseFragment;
import com.yisa.pray.R;
import com.yisa.pray.activity.LoginActivity;
import com.yisa.pray.activity.RegisterActivity;
import com.yisa.pray.utils.Constants;
import com.yisa.pray.views.CustomHeadView;

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
public class BlogMainFragment extends BaseFragment {
	private CustomHeadView mHeadView;
	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		return inflater.inflate(R.layout.fragment_blog_main, null);
	}

	@Override
	public void onInitView(View view, Bundle savedInstanceState) {
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
	}

}

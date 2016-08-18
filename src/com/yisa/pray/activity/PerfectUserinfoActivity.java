/**
 * 项目名称: 七七同城
 * 
 * 文件名称: PerfectUserinfoActivity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.activity;

import android.view.View;
import android.widget.EditText;

import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.views.CustomHeadView;

/**
 *
 * 类名称: PerfectUserinfoActivity.java
 * 类描述:	 完善用户信息
 * 创建人:  hq
 * 创建时间: 2016年8月17日下午5:59:47
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class PerfectUserinfoActivity extends BaseActivity {
	private CustomHeadView mHeadView;
	private EditText mUserName;
	private EditText mAge;
	private EditText mTel;
	private EditText mEducation;
	private EditText mVocation;
	private EditText mChurch;
	private EditText mChurchService;
	private EditText mArea;
	private EditText mPeriod;
	private EditText mRebirth;
	private EditText mAddress;
	
	@Override
	public void setRootLayout() {
		setContentView(R.layout.activity_perfected_userinfo);
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
		mUserName = (EditText) getView(R.id.name);
		mAge = (EditText) getView(R.id.tel);
		mTel = (EditText) getView(R.id.age);
		mEducation = (EditText) getView(R.id.education);
		mVocation = (EditText) getView(R.id.vocation);
		mChurch = (EditText) getView(R.id.church);
		mChurchService = (EditText) getView(R.id.church_service);
		mArea = (EditText) getView(R.id.pray_area);
		mPeriod = (EditText) getView(R.id.pray_period);
		mRebirth = (EditText) getView(R.id.rebirth);
		mAddress = (EditText) getView(R.id.address);
	}

	public void commitData(){
		
	}
}

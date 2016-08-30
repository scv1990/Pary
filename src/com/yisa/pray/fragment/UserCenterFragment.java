/**
 * 项目名称: 七七同城
 * 
 * 文件名称: UserCenterFragment.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ui.BaseFragment;
import com.yisa.pray.R;
import com.yisa.pray.utils.UIHelper;

/**
 *
 * 类名称: UserCenterFragment.java
 * 类描述:
 * 创建人: hq
 * 创建时间: 2016年8月1日下午5:45:54
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class UserCenterFragment extends BaseFragment implements OnClickListener {

	private RelativeLayout mThanksPrayLayout;
	private RelativeLayout mUserInfoLayout;
	private RelativeLayout mAttentionLayout;
	private RelativeLayout mSettingLayout;
	private RelativeLayout mInviteCodeLayout;

	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		return inflater.inflate(R.layout.fragment_user_center, null);
	}

	@Override
	public void onInitView(View view, Bundle savedInstanceState) {
		mThanksPrayLayout = (RelativeLayout) getView(R.id.thanks_pray);
		mUserInfoLayout = (RelativeLayout) getView(R.id.perfect_user_info);
		mAttentionLayout = (RelativeLayout) getView(R.id.my_attention);
		mSettingLayout = (RelativeLayout) getView(R.id.setting);
		mInviteCodeLayout = (RelativeLayout) getView(R.id.invited_code);
		mThanksPrayLayout.setOnClickListener(this);
		mUserInfoLayout.setOnClickListener(this);
		mAttentionLayout.setOnClickListener(this);
		mSettingLayout.setOnClickListener(this);
		mInviteCodeLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.thanks_pray:

			break;
		case R.id.perfect_user_info:
			UIHelper.showPerfectUserinfoActicity(mActivity);
			break;
		case R.id.my_attention:

			break;
		case R.id.setting:

			break;
		case R.id.invited_code:

			break;
		default:
			break;
		}
	}

}

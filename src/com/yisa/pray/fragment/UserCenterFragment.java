/**
 * 项目名称: 七七同城
 * 
 * 文件名称: UserCenterFragment.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.fragment;

import android.os.Bundle;
import android.provider.Contacts.Intents.UI;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ui.BaseFragment;
import com.yisa.pray.R;
import com.yisa.pray.utils.UIHelper;
import com.yisa.pray.utils.UserUtils;

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
	private TextView mUserName;
	private TextView mUserLevel;
	private TextView mUserScore;

	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		return inflater.inflate(R.layout.fragment_user_center, null);
	}

	@Override
	public void onInitView(View view, Bundle savedInstanceState) {
		Log.i("UserCenterFragment" , "UserCenterFragment init");
		
		mUserName = (TextView) getView(R.id.user_center_name);
		mUserLevel = (TextView) getView(R.id.user_level);
		mUserScore = (TextView) getView(R.id.user_point);
		Log.i("user_center", UserUtils.getInstance().getUserName(mActivity));
		String username = UserUtils.getInstance().getUserName(mActivity);
		mUserName.setText(username);
		mUserLevel.setText(String.format(getResources().getString(R.string.user_level_format),UserUtils.getInstance().getLevel(mActivity)));
		mUserScore.setText(String.format(getResources().getString(R.string.user_point_format), UserUtils.getInstance().getScore(mActivity)));
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
			UIHelper.showThankPray(mActivity);
			break;
		case R.id.perfect_user_info:
			UIHelper.showPerfectUserinfoActicity(mActivity);
			break;
		case R.id.my_attention:
			UIHelper.showAttention(mActivity);
			break;
		case R.id.setting:
			UIHelper.showSettingActivity(mActivity);
			break;
		case R.id.invited_code:
			UIHelper.showInviteCodeActivity(mActivity);
			break;
		default:
			break;
		}
	}

}

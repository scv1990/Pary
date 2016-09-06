/**
 * 项目名称: 七七同城
 * 
 * 文件名称: InviteCodeActivity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.user.activity;

import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.utils.UserUtils;
import com.yisa.pray.views.CustomHeadView;

/**
 *
 * 类名称: InviteCodeActivity.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年9月6日下午4:25:12
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class InviteCodeActivity extends BaseActivity{
	private CustomHeadView mHeadView;
	private TextView mCode;
	@Override
	public void setRootLayout() {
		setContentView(R.layout.activity_invite_code);
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
		mCode = (TextView) getView(R.id.invite_code);
		mCode.setText(UserUtils.getInstance().getUser(mContext).getInvitation_code());
	}

}

/**
 * 项目名称: 七七同城
 * 
 * 文件名称: SettingActivity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.user.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.manager.ActivityManager;
import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.activity.LoginActivity;
import com.yisa.pray.utils.IntentKey;
import com.yisa.pray.utils.PreferenceUtils;
import com.yisa.pray.utils.UIHelper;
import com.yisa.pray.utils.UserUtils;
import com.yisa.pray.views.CustomHeadView;

/**
 *
 * 类名称: SettingActivity.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年9月6日下午4:25:59
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class SettingActivity extends BaseActivity{
	private CustomHeadView mHeadView;
	private Button mQuitBtn;
	@Override
	public void setRootLayout() {
		setContentView(R.layout.activity_setting);
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
		mQuitBtn = (Button) getView(R.id.quit);
		mQuitBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PreferenceUtils.setPrefString(mContext, IntentKey.USERINFO, "");
				ActivityManager.getInstance().finishAllActivity();;
				UIHelper.showLoginActivity(mContext);
			}
		});
	}
}

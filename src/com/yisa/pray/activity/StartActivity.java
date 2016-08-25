/**
 * 项目名称: 七七同城
 * 
 * 文件名称: StartActivity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.activity;

import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.entity.UserInfo;
import com.yisa.pray.utils.UIHelper;
import com.yisa.pray.utils.UserUtils;

/**
 *
 * 类名称: StartActivity.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月12日下午5:37:12
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class StartActivity extends BaseActivity {

	@Override
	public void setRootLayout() {

	}

	@Override
	public void initView() {
		UserInfo user = UserUtils.getInstance().getUser(mContext);
		if(user == null || user.getStatus() == 0 
				|| user.getRegion_id() ==  0 || "".equals(user.getRegion_name()) 
				|| user.getPeriod_id() == 0 || user.getPeriod_text() == null || "".equals(user.getPeriod_text())){
			UIHelper.showLoginActivity(mContext);
		}else{
			UIHelper.showHomeActivity(mContext);
		}
		finish();
	}

}

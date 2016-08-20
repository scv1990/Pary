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
		if(user == null || user.getStatus() == 0 || user.getArea() == null || "".equals(user.getArea()) || user.getPeriod() == 0){
			UIHelper.showLoginActivity(mContext);
		}else{
			UIHelper.showHomeActivity(mContext);
		}
		finish();
	}

}

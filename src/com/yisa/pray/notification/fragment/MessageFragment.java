/**
 * 项目名称: 七七同城
 * 
 * 文件名称: MessageFragment.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.notification.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ui.BaseFragment;
import com.yisa.pray.R;

/**
 *
 * 类名称: MessageFragment.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月1日下午5:46:31
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class MessageFragment extends BaseFragment {

	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		return inflater.inflate(R.layout.fragment_notice, null);
	}

	@Override
	public void onInitView(View view, Bundle savedInstanceState) {

	}

}

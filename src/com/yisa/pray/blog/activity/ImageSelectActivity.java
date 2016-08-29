package com.yisa.pray.blog.activity;

import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.blog.fragment.ImageSelectFragment;

/**
 * 
 * 类名称: ImageSelectActivity.java
 * 类描述: 图片选择
 * 创建人: chenru
 * 创建时间: 2015-5-20
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class ImageSelectActivity extends BaseActivity {
	private ImageSelectFragment mFragment;

	@Override
	public void setRootLayout() {
		// TODO Auto-generated method stub
		if (mFragment == null) {
			mFragment = new ImageSelectFragment();
		}
		initFragment(mFragment);
	}

	@Override
	public void initView() {
		setLock(false);
	}
}

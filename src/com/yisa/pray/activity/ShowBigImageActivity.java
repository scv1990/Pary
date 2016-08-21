package com.yisa.pray.activity;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.adapter.ImageBrowserAdapter;
import com.yisa.pray.views.PhotoTextView;
import com.yisa.pray.views.ScrollViewPager;

/**
 * 
 * @author chenru
 * 
 */
public class ShowBigImageActivity extends BaseActivity implements OnPageChangeListener {

	List<String> banners = new ArrayList<String>();
	ImageBrowserAdapter bannerAdapter;
	private ScrollViewPager mSvpPager;
	private PhotoTextView mPtvPage;
	private int mPosition;
	private int mTotal;

	@Override
	public void setRootLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_big_image);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		banners = getIntent().getStringArrayListExtra("KEY");
		mPosition = getIntent().getIntExtra("index", 0);
		mTotal = banners.size();
		initBanner();
	}

	/**
	 * 初始化多图控件
	 */
	private void initBanner() {
		mSvpPager = getView(R.id.imagebrowser_svp_pager);
		mPtvPage = getView(R.id.imagebrowser_ptv_page);
		mSvpPager.setOnPageChangeListener(this);
		mPtvPage.setText((mPosition % mTotal) + 1 + "/" + mTotal);
		bannerAdapter = new ImageBrowserAdapter(mContext, banners);
		mSvpPager.setAdapter(bannerAdapter);
		mSvpPager.setCurrentItem(mPosition, false);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		mPosition = arg0;
		mPtvPage.setText((mPosition % mTotal) + 1 + "/" + mTotal);
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

}

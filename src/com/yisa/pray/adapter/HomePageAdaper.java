package com.yisa.pray.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomePageAdaper extends FragmentPagerAdapter {
	List<Fragment> pagers = new ArrayList<Fragment>();
	public HomePageAdaper(FragmentManager fm, List<Fragment> pagers) {
		super(fm);
		this.pagers = pagers;
	}

	@Override
	public int getCount() {
		if (pagers == null) {
			return 0;
		}
		return pagers.size();
	}

	@Override
	public Fragment getItem(int location) {
		return pagers.get(location);
	}

}

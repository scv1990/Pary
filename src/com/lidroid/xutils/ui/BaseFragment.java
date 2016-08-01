package com.lidroid.xutils.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements OnClickListener {
	public Activity mActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		registerBroadcast();
		registerManager();
		return inflaterView(inflater, container, savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	protected abstract View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle);

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		onInitView(view, savedInstanceState);
	}

	public abstract void onInitView(View view, Bundle savedInstanceState);

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		unRegisterBroadcast();
		unRegisterManager();
		super.onDestroy();
	}

	public void registerBroadcast() {
	}

	public void unRegisterBroadcast() {
	}
	
	public void registerManager() {
	}

	public void unRegisterManager() {
	}

	public void widgetClick(View v) {
	}

	@Override
	public void onClick(View v) {
		widgetClick(v);
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int id) {
		return (T) getActivity().findViewById(id);
	}
}

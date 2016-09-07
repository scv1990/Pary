package com.lidroid.xutils.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.lidroid.xutils.manager.ActivityManager;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.util.SystemTool;
import com.yisa.pray.base.MyApplication;
import com.yisa.pray.utils.Constants;
import com.yisa.pray.utils.PreferenceUtils;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.views.LoadingDialog;

public abstract class BaseActivity extends FragmentActivity implements OnClickListener {

	private Toast toast;
	public BaseActivity mActivity;
	public Context mContext;
	private ScreenActionReceiver screenReceiver;
	private static int VERIFY = 0x99;
	private boolean isLock = true;
	private LoadingDialog mDefaultLoadingDialog;
	private Handler mDefaultHandler = new Handler() {
		public void handleMessage(Message msg) {
			defaultHandlerMessage(msg);
		}
	};

	public Handler getDefaultHandler() {
		return mDefaultHandler;
	}

	public void defaultHandlerMessage(Message msg) {
		if(msg.what==Constants.SHOW_LOADING_DIALOG){
			showLoadingDialog();
		}else if(msg.what == Constants.DIMISS_LOADING_DIALOG){
			dimissLoadingDialog();
		}else if(msg.what == Constants.HANDLER_MESSAGE){
			showToast(msg.obj.toString());
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mActivity = this;
		mContext = this;
		mDefaultLoadingDialog = new LoadingDialog(mActivity);
		ActivityManager.getInstance().addActivity(this);
		setRootLayout();
		initView();
		if (isLock && PreferenceUtils.getPrefBoolean(mActivity, PreferenceUtils.GESTURE_STATE, false)) {
			screenReceiver = new ScreenActionReceiver();
			screenReceiver.registerScreenActionReceiver(mActivity);
		}
		registerBroadcast();
		registerManager();
	}
	public void showLoadingDialog(){
		if(!mDefaultLoadingDialog.isShowing()){
			mDefaultLoadingDialog.show();
		}
	}
	public void dimissLoadingDialog(){
			mDefaultLoadingDialog.dismiss();
	}
	public abstract void setRootLayout();

	public abstract void initView();

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if (screenReceiver != null) {
			screenReceiver.unRegisterScreenActionReceiver(mActivity);
		}
		unRegisterBroadcast();
		unRegisterManager();
		ActivityManager.getInstance().finishActivity(this);
		ShowUtils.removeToast();
		super.onDestroy();
	}

	public void registerBroadcast() {
	}

	public void unRegisterBroadcast() {
	}

	public void widgetClick(View v) {
	}

	public void registerManager() {
	}

	public void unRegisterManager() {
	}

	@Override
	public void onClick(View v) {
		widgetClick(v);
	}

	/**
	 * 用Fragment替换视图
	 * 
	 * @param targetFragment
	 *            用来替换的Fragment
	 */
	public void initFragment(BaseFragment targetFragment) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(android.R.id.content, targetFragment, targetFragment.getClass().getName());
		transaction.commit();
	}

	/**
	 * 用Fragment替换视图
	 * 
	 * @param resView
	 *            将要被替换掉的视图
	 * @param targetFragment
	 *            用来替换的Fragment
	 */
	public void changeFragment(int resView, BaseFragment targetFragment) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(resView, targetFragment, targetFragment.getClass().getName());
		transaction.commit();
	}

	public void showToast(String message) {
		showToast(message, Toast.LENGTH_LONG);
	}

	public void showToast(String message, int time) {
		if (toast == null) {
			toast = Toast.makeText(getApplicationContext(), message, time);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

	public void showToast(int resourceId) {
		showToast(resourceId, Toast.LENGTH_LONG);
	}

	public void showToast(int resourceId, int time) {
		if (toast == null) {
			toast = Toast.makeText(getApplicationContext(), resourceId, time);
		} else {
			toast.setText(resourceId);
		}
		toast.show();
	}

	public void showLogD(String str) {
		LogUtils.d(str);
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int id) {
		return (T) findViewById(id);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (!SystemTool.isAppOnForeground(mActivity)) {
			MyApplication.getInstance();
			MyApplication.isActive = false;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MyApplication.getInstance();
		// 应用存在与后台并且要设置手势密码
		ShowUtils.LogE("activity--->:" + getClass().getName() + "--->" + MyApplication.isActive);
		MyApplication.getInstance();
		if (isLock && !MyApplication.isActive
				&& PreferenceUtils.getPrefBoolean(mActivity, PreferenceUtils.GESTURE_STATE, false)) {
//			UIHelper.showGestureVerify(this, VERIFY);
		}
		JPushInterface.onResume(mActivity);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(mActivity);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == VERIFY) {
		}
	}

	public class ScreenActionReceiver extends BroadcastReceiver {

		private boolean isRegisterReceiver = false;

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_SCREEN_OFF)) {
//				UIHelper.showGestureVerify(BaseActivity.this, VERIFY);
			}
		}

		public void registerScreenActionReceiver(Context mContext) {
			if (!isRegisterReceiver) {
				isRegisterReceiver = true;
				IntentFilter filter = new IntentFilter();
				filter.addAction(Intent.ACTION_SCREEN_OFF);
				filter.addAction(Intent.ACTION_SCREEN_ON);
				mContext.registerReceiver(ScreenActionReceiver.this, filter);
			}
		}

		public void unRegisterScreenActionReceiver(Context mContext) {
			if (isRegisterReceiver) {
				isRegisterReceiver = false;
				mContext.unregisterReceiver(ScreenActionReceiver.this);
			}
		}
	}

	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}
}

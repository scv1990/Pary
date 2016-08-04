package com.yisa.pray.activity;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.views.LoadingDialog;
/**
 * 注册界面
 * @author Administrator
 *
 */
public class RegisterActivity extends BaseActivity implements OnClickListener{
	private static final String TAG = "RegisterActivity";
	private EditText mUserName;
	private EditText mTel;
	private EditText mPwd;
	private EditText mPwdRepeat;
	private EditText mInviteCode;
	private Button mCommitBtn;
	private LoadingDialog mLoading;

	@Override
	public void setRootLayout() {
		setContentView(R.layout.activity_register);

	}

	@Override
	public void initView() {
		mLoading = new LoadingDialog(mContext);
		mUserName = (EditText) getView(R.id.reg_username);
		mTel = (EditText) getView(R.id.reg_tel);
		mPwd = (EditText) getView(R.id.reg_pwd);
		mPwdRepeat = (EditText) getView(R.id.reg_pwd_repeat);
		mInviteCode = (EditText) getView(R.id.reg_invite_code);
		mCommitBtn = (Button) getView(R.id.reg_commit_btn);
		mCommitBtn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.reg_commit_btn :
			checkParam();
			break;
		default : 
			break;
		}
	}
	
	public void checkParam(){
		String userName = mUserName.getText().toString();
		if(userName == null || "".equals(userName)){
			ShowUtils.showToast(mContext, getString(R.string.reg_nickname_hint));
			return;
		}
		String tel = mTel.getText().toString();
		if(tel == null || "".equals(tel)){
			ShowUtils.showToast(mContext, getString(R.string.reg_tel_hint));
			return;
		}
		String pwd = mPwd.getText().toString();
		if(pwd == null || "".equals(pwd)){
			ShowUtils.showToast(mContext, getString(R.string.reg_pwd_hint));
			return;
		}
		String pwdRepeat = mPwdRepeat.getText().toString();
		if(pwdRepeat == null || "".equals(pwdRepeat)){
			ShowUtils.showToast(mContext, getString(R.string.reg_pwd_repeat_hint));
			return;
		}
		if(!pwd.equals(pwdRepeat)){
			ShowUtils.showToast(mContext, getString(R.string.reg_pwd_different));
			return;
		}
		String inviteCode = mInviteCode.getText().toString();
		if(inviteCode == null || "".equals(inviteCode)){
			ShowUtils.showToast(mContext, getString(R.string.reg_invite_code_hint));
			return;
		}
		RequestParams param = new RequestParams(); 
		param.addBodyParameter("phone", tel);
		param.addBodyParameter("code", inviteCode);
		param.addBodyParameter("password", pwd);
		param.addBodyParameter("username", userName);
		requestData(param);
	}
	
	public void requestData(RequestParams param){
		mLoading.show();
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, UrlUtils.REGISTER, param, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				Log.i(TAG, responseInfo.result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Log.i(TAG + "onFailure", msg);
				ShowUtils.showToast(mContext, msg);
			}

			@Override
			public void onFinish() {
				mLoading.dismiss();
			}
		});
	}

}

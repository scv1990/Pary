/**
 * 项目名称: 七七同城
 * 
 * 文件名称: LoginActivity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.activity;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.converter.gson.GsonConverterFactory;
import com.yisa.pray.entity.ErrorMessage;
import com.yisa.pray.entity.UserInfo;
import com.yisa.pray.imp.RequestServers;
import com.yisa.pray.utils.Constants;
import com.yisa.pray.utils.PreferenceUtils;
import com.yisa.pray.utils.ResponseCode;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UIHelper;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.views.LoadingDialog;

/**
 *
 * 类名称: LoginActivity.java
 * 类描述:	登陆界面 
 * 创建人:  hq
 * 创建时间: 2016年8月3日下午2:18:00
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class LoginActivity extends BaseActivity implements OnClickListener{

	private static final String TAG = "LoginActivity";
	private EditText mUserName ;
	private EditText mPwd;
	private Button mLoginBtn;
	private TextView mRegisterTxt;
	private LoadingDialog mLoading;
	@Override
	public void setRootLayout() {
		setContentView(R.layout.activity_login);
	}

	@Override
	public void initView() {
		mLoading = new LoadingDialog(mContext);
		mUserName = (EditText) getView(R.id.login_name);
		mPwd = (EditText) getView(R.id.login_pwd);
		mLoginBtn = (Button) getView(R.id.login_btn);
		mRegisterTxt = (TextView) getView(R.id.reg_txt);
		mLoginBtn.setOnClickListener(this);
		mRegisterTxt.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			 checkParam();
			break;
		case R.id.reg_txt :
			UIHelper.showRegister(mContext, Constants.LOGIN_TO_REGISTER_REQ_CODE);
		   break;
		default:
			break;
		}
	}
	
	public void checkParam(){
		String userName = mUserName.getText().toString();
		if(userName == null || "".equals(userName)){
			ShowUtils.showToast(mContext, getString(R.string.reg_nickname_hint));
			return;
		}
		String pwd = mPwd.getText().toString();
		if(pwd == null || "".equals(pwd)){
			ShowUtils.showToast(mContext, getResources().getString(R.string.reg_pwd_hint));
			return;
		}
		
		try {
			Retrofit retrofit = new Retrofit.Builder()
				    .baseUrl(UrlUtils.SERVER_ADDRESS)
				    .addConverterFactory(GsonConverterFactory.create())
				    .build();
			RequestServers service = retrofit.create(RequestServers.class);
			
			Call<UserInfo> call = service.login(userName, pwd);
			call.enqueue(new Callback<UserInfo>(){

				@Override
				public void onFailure(Call<UserInfo> arg0, Throwable arg1) {
					Log.i(TAG+"onFailure", arg1.getMessage());
					
				}

				@Override
				public void onResponse(Call<UserInfo> arg0, Response<UserInfo> response) {
					String message = "";
					switch (response.code()) {
					case ResponseCode.RESPONSE_CODE_201:
						UserInfo user = response.body();
						message = new Gson().toJson(response.body());
						Log.i(TAG + "201", message);
						PreferenceUtils.setPrefString(mContext, "userinfo", message);
						if(user.getRegion_id()== 0 || "".equals(user.getRegion_name()) || user.getPeriod_id() == 0 ){
							UIHelper.showPerfectUserinfoActicity(mContext);
						}else{
							if(user.getStatus() == 0)
							{
								ShowUtils.showToast(mContext, getResources().getString(R.string.user_status_0));
							}else{
								UIHelper.showHomeActivity(mContext);
								finish();
							}
						}
						break;
					default:
						ErrorMessage error = new ErrorMessage();;
						try {
							message = response.errorBody().string();
							Gson gson =  new Gson();
							error = gson.fromJson(message, ErrorMessage.class);
						} catch (IOException e) {
							e.printStackTrace();
						}
						Log.i(TAG, message);
						ShowUtils.showToast(mContext, error.getError());
						break;
					}
					
				}
			});
		} catch (Exception e) {
			Log.i(TAG +"Exception", e.getMessage());
		}
	}
	
	public void requestData(RequestParams param){
		mLoading.show();
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, UrlUtils.LOGIN, param, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				Log.i(TAG, responseInfo.result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				ShowUtils.showToast(mContext, msg);
			}

			@Override
			public void onFinish() {
				mLoading.dismiss();
			}
		});
	}
}

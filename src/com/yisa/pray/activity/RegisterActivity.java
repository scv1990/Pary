package com.yisa.pray.activity;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.Gson;
import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.entity.ErrorMessage;
import com.yisa.pray.entity.OperationResult;
import com.yisa.pray.imp.RegisterService;
import com.yisa.pray.imp.RequestServers;
import com.yisa.pray.utils.ResponseCode;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.views.CustomHeadView;
import com.yisa.pray.views.LoadingDialog;
/**
 * 注册界面
 * @author Administrator
 *
 */
public class RegisterActivity extends BaseActivity implements OnClickListener{
	private static final String TAG = "RegisterActivity";
	private static final int CONTINUE = 0x0001;
	private static final int STOP = 0x0002;
	private CustomHeadView mHeadView;
	private EditText mUserName;
	private EditText mTel;
	private EditText mPwd;
	private EditText mPwdRepeat;
	private EditText mInviteCode;
	private Button mVerifiBtn;
	private EditText mVerifiTxt;
	private Button mCommitBtn;
	private LoadingDialog mLoading;
	private Timer timer; // 定时器
	
	// 定时器handler，更新按钮倒计时秒数
	@SuppressLint("ResourceAsColor")
	@SuppressWarnings("deprecation")
	Handler captTimeHandle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == CONTINUE) {
				int num = (Integer) msg.obj;
				mVerifiBtn.setText(String.format(getString(R.string.reg_verification_code_btn), num));
			} else if (msg.what == STOP) {
				mVerifiBtn.setBackgroundColor(R.color.head_bg);
				mVerifiBtn.setText(R.string.reg_verification_code_btn_hint);
				mVerifiBtn.setClickable(true);
			}
		}
	};
	
	
	@Override
	public void setRootLayout() {
		setContentView(R.layout.activity_register);

	}

	@Override
	public void initView() {
		mLoading = new LoadingDialog(mContext);
		timer = new Timer();
		mHeadView = (CustomHeadView) getView(R.id.head_view);
		mHeadView.setLeftIconClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mUserName = (EditText) getView(R.id.reg_username);
		mTel = (EditText) getView(R.id.reg_tel);
		mPwd = (EditText) getView(R.id.reg_pwd);
		mPwdRepeat = (EditText) getView(R.id.reg_pwd_repeat);
		mInviteCode = (EditText) getView(R.id.reg_invite_code);
		mCommitBtn = (Button) getView(R.id.reg_commit_btn);
		mVerifiBtn = (Button) getView(R.id.get_code_btn);
		mVerifiBtn.setOnClickListener(this);
		mVerifiTxt = (EditText) getView(R.id.reg_verification_code);
		mCommitBtn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.reg_commit_btn :
			checkParam();
			break;
		case R.id.get_code_btn :
			getVerifiCode();
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
		
		try {
			Retrofit retrofit = new Retrofit.Builder()
				    .baseUrl(UrlUtils.SERVER_ADDRESS)
				    .addConverterFactory(GsonConverterFactory.create())
				    .build();
			
			 
			RequestServers service = retrofit.create(RequestServers.class);
			
			Call<ErrorMessage> call = service.getString(tel, userName, pwd, inviteCode);
			mLoading.show();
			call.enqueue(new Callback<ErrorMessage>(){

				@Override
				public void onFailure(Throwable arg0) {
					Log.i(TAG+"onFailure", arg0.getMessage());
				}

				@Override
				public void onResponse(retrofit.Response<ErrorMessage> response, Retrofit retrofit) {
					switch (response.code()) {
					case ResponseCode.RESPONSE_CODE_201:
						setResult(RESULT_OK);
						Log.i(TAG +"201", response.message());
						finish();
						break;
					case ResponseCode.RESPONSE_CODE_422:
						String message = "";
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
					default:
						break;
					}
				}
			});
		} catch (Exception e) {
			Log.i(TAG +"Exception", e.getMessage());
		}finally {
			mLoading.dismiss();
		}
		
	}
	
	@SuppressLint("ResourceAsColor")
	public void getVerifiCode(){
		String tel = mTel.getText().toString();
		if(tel == null || "".equals(tel)){
			ShowUtils.showToast(mContext, getString(R.string.reg_tel_hint));
			return;
		}
		mVerifiBtn.setClickable(false);
		mVerifiBtn.setBackgroundColor(android.R.color.darker_gray);
		try {
			Retrofit retrofit = new Retrofit.Builder()
				    .baseUrl(UrlUtils.SERVER_ADDRESS)
				    .addConverterFactory(GsonConverterFactory.create())
				    .build();
			
			 
			RegisterService service = retrofit.create(RegisterService.class);
			
			Call<OperationResult> call = service.getCode(tel);
			mLoading.show();
			call.enqueue(new Callback<OperationResult>(){

				@Override
				public void onFailure(Throwable arg0) {
					Log.i(TAG+"onFailure", arg0.getMessage());
				}

				@Override
				public void onResponse(retrofit.Response<OperationResult> response, Retrofit retrofit) {
					switch (response.code()) {
					case ResponseCode.RESPONSE_CODE_201:
						setResult(RESULT_OK);
						Log.i(TAG +"201", response.message());
						break;
					case ResponseCode.RESPONSE_CODE_422:
						String message = "";
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
					default:
						break;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
		}
	}
	
	/**
	 * @ClassName: GetCaptchaTimer
	 * @Description: TODO(设置一分钟才能点一次验证码)
	 * @author hq
	 * @date 2015年3月2日 下午4:09:39
	 */
	class GetCaptchaTimer extends TimerTask {
		int num = 59;

		@Override
		public void run() {
			Message msg = new Message();
			if (num > 0) {
				num--;
				msg.what = CONTINUE;
				msg.obj = num;
				captTimeHandle.sendMessage(msg);
			} else {
				// 改变按钮背景色，并且设置为不可点
				msg.what = STOP;
				captTimeHandle.sendMessage(msg);
				this.cancel();
			}
		}

	}

}

/**
 * 项目名称: 七七同城
 * 
 * 文件名称: PerfectUserinfoActivity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.activity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.blog.entity.RegionEntity;
import com.yisa.pray.converter.gson.GsonConverterFactory;
import com.yisa.pray.entity.EducationEntity;
import com.yisa.pray.entity.ErrorMessage;
import com.yisa.pray.entity.Period;
import com.yisa.pray.entity.SimpleData;
import com.yisa.pray.entity.UserInfo;
import com.yisa.pray.imp.UserService;
import com.yisa.pray.utils.Constants;
import com.yisa.pray.utils.IntentKey;
import com.yisa.pray.utils.PreferenceUtils;
import com.yisa.pray.utils.PropertyUtil;
import com.yisa.pray.utils.ResponseCode;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.utils.UserUtils;
import com.yisa.pray.views.CustomHeadView;

/**
 *
 * 类名称: PerfectUserinfoActivity.java
 * 类描述:	 完善用户信息
 * 创建人:  hq
 * 创建时间: 2016年8月17日下午5:59:47
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class PerfectUserinfoActivity extends BaseActivity implements OnClickListener{
	private static final String TAG = "PerfectUserinfoActivity";
	private CustomHeadView mHeadView;
	private EditText mUserName;
	private EditText mAge;
	private EditText mTel;
	private EditText mEducation;
	private EditText mVocation;
	private EditText mChurch;
	private EditText mChurchService;
	private EditText mArea;
	private EditText mPeriod;
	private EditText mRebirth;
	private EditText mAddress;
	private Button mCommit;
	private RadioGroup mRadioGroup;
	private RadioButton mMaleRadio;
	private RadioButton mFemaleRadio;
	private UserInfo mUserInfo;
	private String token;
	private EducationEntity mEdu;
	private RegionEntity mRegion;
	private Period mPeriodEn;
	@Override
	public void setRootLayout() {
		setContentView(R.layout.activity_perfected_userinfo);
	}

	@Override
	public void initView() {
		mHeadView = (CustomHeadView) getView(R.id.head_view);
		mHeadView.setLeftIconClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mUserName = (EditText) getView(R.id.name);
		mTel = (EditText) getView(R.id.tel);
		mAge = (EditText) getView(R.id.age);
		mEducation = (EditText) getView(R.id.education);
		mVocation = (EditText) getView(R.id.vocation);
		mChurch = (EditText) getView(R.id.church);
		mChurchService = (EditText) getView(R.id.church_service);
		mArea = (EditText) getView(R.id.pray_area);
		mArea.setOnClickListener(this);
		mPeriod = (EditText) getView(R.id.pray_period);
		mPeriod.setOnClickListener(this);
		mRebirth = (EditText) getView(R.id.rebirth);
		mAddress = (EditText) getView(R.id.address);
		mRadioGroup = (RadioGroup) getView(R.id.sex_radio_group);
		mMaleRadio = (RadioButton) getView(R.id.male_radio);
		mFemaleRadio = (RadioButton) getView(R.id.female_radio);
		mCommit = (Button) getView(R.id.commit);
		mEducation.setOnClickListener(this);
		mArea.setOnClickListener(this);
		mCommit.setOnClickListener(this);
		token = UserUtils.getInstance().getUser(mContext).getAuthentication_token();
		getUserinfo();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent intent = new Intent();
		switch (v.getId()) {
			case R.id.commit:
				commitData();
				break;
			case R.id.pray_area:
				intent.setClass(mContext, RegionActivity.class);
				startActivityForResult(intent, Constants.USER_INFO_TO_REGION_REQ_CODE);
				break;
			case R.id.pray_period:
				intent.putExtra(IntentKey.TITLE, getResources().getString(R.string.perfect_period_label));
				intent.putExtra(IntentKey.API_VERSION, UrlUtils.API_VERSION);
				intent.putExtra(IntentKey.API_MODEL, UrlUtils.API_MODEL_USER);
				intent.putExtra(IntentKey.API_FUNCTION, "periods");
				intent.setClass(mContext, SimpleDataActivity.class);
				startActivityForResult(intent, Constants.USER_INFO_TO_PERIOD_REQ_CODE);
				break;
			case R.id.education:
				intent.setClass(mContext, EducationActicity.class);
				startActivityForResult(intent, Constants.USER_INFO_TO_EDUCATION_REQ_CODE);
				break;
			default:
				break;
		}
	}
	
	public void getUserinfo(){
		Retrofit retrofit = 
				new Retrofit.Builder()
					.baseUrl(UrlUtils.SERVER_ADDRESS)
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		UserService service = retrofit.create(UserService.class);
		UserInfo user = UserUtils.getInstance().getUser(mContext);
		Call<UserInfo> call = service.getUserInfo(user.getAuthentication_token(), user.getId());
		call.enqueue(new Callback<UserInfo>() {

			@Override
			public void onFailure(Call<UserInfo> arg0, Throwable arg1) {
				ShowUtils.showToast(mContext, arg1.getMessage());
				
			}

			@Override
			public void onResponse(Call<UserInfo> arg0, Response<UserInfo> response) {
				try {
					switch (response.code()) {
					case ResponseCode.RESPONSE_CODE_200:
						mUserInfo = response.body();
						mUserInfo.setAuthentication_token(token);
						Log.i(TAG + "SUERINFO--=", new Gson().toJson(mUserInfo));
						PreferenceUtils.setPrefString(mContext, "userinfo", new Gson().toJson(mUserInfo));
						mEdu = new EducationEntity();
						mEdu.setId(mUserInfo.getId());
						initUserInfo();
						break;

					default:
						ErrorMessage error = new Gson().fromJson(response.errorBody().string(), ErrorMessage.class);
						ShowUtils.showToast(mContext, error.getError());
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	/**
	 * @Title: initUserInfo 
	 * @Description: TODO(更新界面用户信息) 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void initUserInfo(){
		mUserName.setText(mUserInfo.getName());
		mAge.setText(mUserInfo.getBirth());
		mTel.setText(mUserInfo.getPhone());
		mEducation.setText(PropertyUtil.getInstance().readProperty(mContext, mUserInfo.getEducation() +""));
		mVocation.setText(mUserInfo.getJob());
		mChurch.setText(mUserInfo.getChurch());
		mChurchService.setText(mUserInfo.getChurch_service());
		mArea.setText(mUserInfo.getRegion_name());
		mPeriod.setText(mUserInfo.getPeriod_text());
		mRebirth.setText(mUserInfo.getRebirth());
		mAddress.setText(mUserInfo.getAddress());
		String gender = mUserInfo.getGender();
		if(gender.equals(getResources().getString(R.string.female))){
			mMaleRadio.setChecked(false);
			mFemaleRadio.setChecked(true);
		}else{
			mMaleRadio.setChecked(true);
			mFemaleRadio.setChecked(false);
		}
	}
	/**
	 * @Title: commitData 
	 * @Description: TODO(提交更新用户信息) 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void commitData(){
		Retrofit retrofit = 
				new Retrofit.Builder()
					.baseUrl(UrlUtils.SERVER_ADDRESS)
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		UserService service = retrofit.create(UserService.class);
		Call<UserInfo> call = service.upateUserInfo(
						mUserInfo.getId(), 
						mUserName.getText().toString(), 
						mAddress.getText().toString(), 
						((RadioButton)getView(mRadioGroup.getCheckedRadioButtonId())).getText().toString(), 
						mAge.getText().toString(), 
						mEdu.getId(), 
						mVocation.getText().toString(), 
						mChurch.getText().toString(), 
						mChurchService.getText().toString(), 
						mRebirth.getText().toString(), 
						mUserInfo.getRegion_id(), 
						mUserInfo.getPeriod_id(), 
						token);
		Log.i(TAG, mAge.getText().toString());
		call.enqueue(new Callback<UserInfo>() {
			@Override
			public void onFailure(Call<UserInfo> arg0, Throwable arg1) {
				ShowUtils.showToast(mContext, arg1.getMessage());
				
			}

			@Override
			public void onResponse(Call<UserInfo> arg0, Response<UserInfo> response) {
				int code = response.code();
				try {
					switch (code) {
					case ResponseCode.RESPONSE_CODE_200:
						mUserInfo = response.body();
						finish();
						break;

					default:
						ErrorMessage error = new Gson().fromJson(response.errorBody().string(), ErrorMessage.class);
						ShowUtils.showToast(mContext, error.getError());
						break;
					}
				} catch (Exception e) {
				}
				
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		super.onActivityResult(requestCode, responseCode, intent);
		
		if(responseCode == RESULT_OK){
			switch (requestCode) {
				case Constants.USER_INFO_TO_REGION_REQ_CODE:
					mRegion = (RegionEntity)intent.getSerializableExtra(IntentKey.REGION);
					mUserInfo.setRegion_id(mRegion.getId());
					mUserInfo.setRegion_name(mRegion.getName());
					mArea.setText(mRegion.getName());
					break;
				case Constants.USER_INFO_TO_EDUCATION_REQ_CODE:
					mEdu = (EducationEntity)intent.getSerializableExtra(IntentKey.EDUCATION);
					mEducation.setText(mEdu.getName());
					break;
				case Constants.USER_INFO_TO_PERIOD_REQ_CODE:
					SimpleData data = (SimpleData)intent.getSerializableExtra(IntentKey.DATA);
					SimpleData father = new Period();
					father.setId(data.getId());
					father.setName(data.getName());
					mUserInfo.setPeriod_id(Integer.parseInt(data.getId()));
					mUserInfo.setPeriod_text(data.getName());
					mPeriod.setText(mUserInfo.getPeriod_text());
					break;
					
				default:
					break;
			}
		}
		
	}
}

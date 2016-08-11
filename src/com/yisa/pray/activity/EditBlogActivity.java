/**
 * 项目名称: 七七同城
 * 
 * 文件名称: EditBlogActivity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.activity;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.entity.BlogCategroyEntity;
import com.yisa.pray.entity.BlogEntity;
import com.yisa.pray.entity.ErrorMessage;
import com.yisa.pray.entity.UserInfo;
import com.yisa.pray.imp.BlogService;
import com.yisa.pray.utils.Constants;
import com.yisa.pray.utils.IntentKey;
import com.yisa.pray.utils.ResponseCode;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.utils.UserUtils;
import com.yisa.pray.views.CustomHeadView;
import com.yisa.pray.views.LoadingDialog;

/**
 *
 * 类名称: EditBlogActivity.java
 * 类描述:	编辑帖子页面 
 * 创建人:  hq
 * 创建时间: 2016年8月8日下午3:53:25
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class EditBlogActivity extends BaseActivity implements OnClickListener{
	private static final String TAG = "EditBlogActivity";
	private CustomHeadView mHeadView;
	private TextView mCategroyTxt;
	private EditText mContent;
	private BlogCategroyEntity mCategroy;
	private Button mPublishBtn;
	private LoadingDialog mLoading;
	@Override
	public void setRootLayout() {
		setContentView(R.layout.activity_edit_blog);
	}

	@Override
	public void initView() {
		mLoading = new LoadingDialog(mContext);
		mHeadView = (CustomHeadView) getView(R.id.head_view);
		mHeadView.setLeftIconClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mCategroyTxt = (TextView) getView(R.id.categroy);
		mCategroyTxt.setOnClickListener(this);
		mContent = (EditText) getView(R.id.content);
		mPublishBtn = (Button) getView(R.id.publish);
		mPublishBtn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.categroy:
			Intent intent = new Intent(mContext, BlogCategroyActivity.class);
			startActivityForResult(intent, Constants.EDIT_TO_BLOG_CATEGROY_REQ_CODE);
			break;
		case R.id.publish:
			checkParam();
			break;
		default:
			break;
		}
	}
	
	public void checkParam(){
		String content = mContent.getText().toString();
		if(content == null || "".equals(content)){
			ShowUtils.showToast(mContext, getResources().getString(R.string.add_blog_hint));
			return ;
		}
		
		if(mCategroy == null || mCategroy.getId() == null || "".equals(mCategroy.getId())){
			ShowUtils.showToast(mContext, getResources().getString(R.string.blog_categroy_chose_tips));
			return ;
		}
		
		publish(content);
	}
	
	public void publish(String content){
		mLoading.show();
		try {
			Retrofit retrofit = new Retrofit.Builder()
								.baseUrl(UrlUtils.SERVER_ADDRESS)
								.addConverterFactory(GsonConverterFactory.create())
								.build();
			UserInfo user = UserUtils.getInstance().getUser(mContext);
			BlogService service = retrofit.create(BlogService.class);
			Call<BlogEntity> call = service.addBlog(
					user.getAuthentication_token(), 
					1, 
					Integer.parseInt(mCategroy.getId()), "", content);
			Log.i(TAG, mCategroy.getId());
			call.enqueue(new Callback<BlogEntity>() {
				
				@Override
				public void onResponse(Response<BlogEntity> response, Retrofit arg1) {
					int code = response.code();
					String message = "";
					try {
						switch(code){
						case ResponseCode.RESPONSE_CODE_201 :
							BlogEntity blog = response.body();
							Intent intent = new Intent(mContext, HomeActivity.class);
							intent.putExtra(IntentKey.BLOG, blog);
							setResult(RESULT_OK, intent);
							finish();
							break;
						default :
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
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
				@Override
				public void onFailure(Throwable arg0) {
					ShowUtils.showToast(mContext, arg0.getMessage());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			mLoading.dismiss();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, responseCode, intent);
		if(responseCode == RESULT_OK){
			switch (requestCode) {
			case Constants.EDIT_TO_BLOG_CATEGROY_REQ_CODE:
				mCategroy = (BlogCategroyEntity) intent.getSerializableExtra(IntentKey.BLOG_CATEGROY);
				mCategroyTxt.setText(mCategroy.getName());
				break;
			default:
				break;
			}
		}
	}

}

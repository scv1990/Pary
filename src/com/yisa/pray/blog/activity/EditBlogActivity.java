/**
 * 项目名称: 七七同城
 * 
 * 文件名称: EditBlogActivity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.blog.activity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Multipart;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.ui.BaseActivity;
import com.lidroid.xutils.util.SystemTool;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;
import com.yisa.pray.R;
import com.yisa.pray.activity.HomeActivity;
import com.yisa.pray.blog.adapter.ImageShowAdapter;
import com.yisa.pray.blog.entity.BlogCategroyEntity;
import com.yisa.pray.blog.entity.BlogEntity;
import com.yisa.pray.blog.entity.PostImage;
import com.yisa.pray.blog.imp.BlogService;
import com.yisa.pray.blog.listener.OnImageDelteListener;
import com.yisa.pray.entity.ErrorMessage;
import com.yisa.pray.entity.UserInfo;
import com.yisa.pray.popupwindow.SelectUserPicPopupwindow;
import com.yisa.pray.utils.Constants;
import com.yisa.pray.utils.FileTool;
import com.yisa.pray.utils.IntentKey;
import com.yisa.pray.utils.ResponseCode;
import com.yisa.pray.utils.ShowUtils;
import com.yisa.pray.utils.UIHelper;
import com.yisa.pray.utils.UrlUtils;
import com.yisa.pray.utils.UserUtils;
import com.yisa.pray.views.CustomHeadView;
import com.yisa.pray.views.LoadingDialog;
import com.yisa.pray.views.NoScrollGridView;
/**
 *
 * 类名称: EditBlogActivity.java
 * 类描述:	编辑帖子页面 
 * 创建人:  hq
 * 创建时间: 2016年8月8日下午3:53:25
 * -------------------------修订历史------------
 * 修改人:u
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
	private ImageView mAddImage;
	private SelectUserPicPopupwindow menuWindow;
	private NoScrollGridView mImageGridView;
	
	private List<String> mSelectList = new ArrayList<String>();
	private Uri mPhotoPath;
	private ImageShowAdapter mImageAdapter;
	
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
		mImageGridView = (NoScrollGridView) findViewById(R.id.upload_gridview);
		
		
		mAddImage = (ImageView) getView(R.id.upload_pic);
		mAddImage.setOnClickListener(this);
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
		case R.id.upload_pic :
			SystemTool.hideKeyBoard(this);
			if (menuWindow == null) {
				menuWindow = new SelectUserPicPopupwindow(mActivity, itemsOnClick);
			}
			menuWindow.showAtLocation(findViewById(R.id.edit_blog), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,
					0);
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
					Integer.parseInt(mCategroy.getId()), mCategroy.getName(), content);
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
							if(mSelectList != null && mSelectList.size() > 0){
								uploadImage(blog.getId());
							}
							
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
	
	public void uploadImage(String articalId){
		if(mSelectList == null || mSelectList.size() == 0){
			return;
		}
		
		RequestBody requestFile;
		Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlUtils.SERVER_ADDRESS).addConverterFactory(GsonConverterFactory.create()).build();
		BlogService service = retrofit.create(BlogService.class);
		Call<PostImage> call;
		String token = UserUtils.getInstance().getUser(mContext).getAuthentication_token();
		int id = Integer.parseInt(articalId);
		for(String path: mSelectList){
			requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), new File(path));
			call = service.uploadImage(id, requestFile, token);
			call.enqueue(new Callback<PostImage>() {
				@Override
				public void onFailure(Throwable arg0) {
					ShowUtils.showToast(mContext, arg0.getMessage());
				}

				@Override
				public void onResponse(Response<PostImage> response, Retrofit retrofit) {
					switch (response.code()) {
					case ResponseCode.RESPONSE_CODE_201:
						
						break;
					default:
						ShowUtils.showToast(mContext, getResources().getString(R.string.upload_image_falie));
						break;
					}
				}
			});
		}
	} 
	
	
	// 选取照片的popuwonder的button监听事件
	private OnClickListener itemsOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			menuWindow.dismiss();
			switch (v.getId()) {
			case R.id.takePhotoBtn:
				if (mSelectList.size() < Constants.IMAGE_SELECT_MAXSIZE) {
					goCamera();
				} else {
					showToast(R.string.image_max_warn);
				}
				
				break;
			case R.id.pickPhotoBtn:
				
				if (mSelectList.size() < Constants.IMAGE_SELECT_MAXSIZE) {
					Bundle bundle = new Bundle();
					bundle.putStringArrayList(IntentKey.IMAGE_PATH_LIST, (ArrayList<String>) mSelectList);
					UIHelper.showSelectPhoto(EditBlogActivity.this, bundle, Constants.IMAGE_SELECT_MAXSIZE,
							Constants.UPLOAD_PICK_PHOTO_REQ_CODE);
				} else {
					showToast(R.string.image_max_warn);
				}
				
				break;
			default:
				break;
			}
		}

	};
	
	private void goCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		mPhotoPath = Uri.fromFile(FileTool.createTempFile(getNowTimeWithNoSeparator(), ".jpg"));
		intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoPath);
		startActivityForResult(intent, Constants.UPLOAD_TAKE_PHOTO_REQ_CODE);
	}
	public String getNowTimeWithNoSeparator() {
		return new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
	}
	
		
	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent data) {
		super.onActivityResult(requestCode, responseCode, data);
		if(responseCode == RESULT_OK){
			switch (requestCode) {
			case Constants.EDIT_TO_BLOG_CATEGROY_REQ_CODE:
				mCategroy = (BlogCategroyEntity) data.getSerializableExtra(IntentKey.BLOG_CATEGROY);
				mCategroyTxt.setText(mCategroy.getName());
				break;
			case Constants.UPLOAD_TAKE_PHOTO_REQ_CODE:
				mSelectList.add(mSelectList.size(), mPhotoPath.getPath());
				 refreshImageListView();
				break;

			case  Constants.UPLOAD_PICK_PHOTO_REQ_CODE:
				mSelectList = data.getStringArrayListExtra(IntentKey.IMAGE_PATH_LIST);
				 refreshImageListView();
				break;
			default:
				break;
			}
		}
	}
	
	public void refreshImageListView(){
		if (mImageAdapter == null) {
			mImageAdapter = new ImageShowAdapter(EditBlogActivity.this, mSelectList);
			mImageAdapter.setOnImageDeleteListener(new OnImageDelteListener() {
				@Override
				public void onDelete(int count) {
					mSelectList.remove(count);
					mImageAdapter.updateListView(mSelectList);
				}
			});
			mImageGridView.setAdapter(mImageAdapter);
		} else {
			mImageAdapter.updateListView(mSelectList);
		}
	}

}

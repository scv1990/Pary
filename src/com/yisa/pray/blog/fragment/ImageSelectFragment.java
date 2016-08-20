package com.yisa.pray.blog.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.lidroid.xutils.ui.BaseFragment;
import com.yisa.pray.R;
import com.yisa.pray.blog.adapter.ImageSelectAdapter;
import com.yisa.pray.blog.adapter.ParentListAdapter;
import com.yisa.pray.blog.entity.ImageEntity;
import com.yisa.pray.utils.Constants;
import com.yisa.pray.utils.IntentKey;
import com.yisa.pray.views.CustomHeadView;

public class ImageSelectFragment extends BaseFragment {

	private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
	private Activity mActivity;
	private GridView mGridView;
	private List<String> list;
	private String DEFAULT_PARENT_NAME = "Camera";
	private String PARENT_NAME = "";
	private int lAST_POSITION = -1;
	private final int GRIDVIEW_REFRESH = 0;
	private final int NO_SDCARD = 2;
	protected ImageSelectAdapter adapter = null;
	private final static int SCAN_OK = 1;
	private PopupWindow mPopupWindow;
	private ParentListAdapter mPopupWindowAdapter;
	private ListView listView;
	private ImageView mShareImageView;
	protected boolean isRefresh = false;
	private ArrayList<String> mSelectedList;
	private int maxSize = 6;
	private int titleRightPixel = 25;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SCAN_OK:
				// 关闭进度条
				// mLoadHandler.sendEmptyMessage(getString(R.string.));
				list = mGruopMap.get(DEFAULT_PARENT_NAME);
				mHeadView.setTitle(DEFAULT_PARENT_NAME);
				if (null != list && list.size() > 0) {
					adapter = new ImageSelectAdapter(mActivity, list, maxSize, mSelectedList);
					mGridView.setAdapter(adapter);
				}
				break;
			case GRIDVIEW_REFRESH:
				mHeadView.setTitle(PARENT_NAME);
				if (null != list && list.size() > 0) {
					adapter = new ImageSelectAdapter(mActivity, list, maxSize, mSelectedList);
					mGridView.setAdapter(adapter);
				}
				break;
			case NO_SDCARD:
				getActivity().finish();
				break;
			}
		}
	};
	private CustomHeadView mHeadView;

	@Override
	public void onAttach(Activity activity) {
		mActivity = activity;
		Intent intent = getActivity().getIntent();
		if (null != intent) {
			Bundle bundle = intent.getBundleExtra(IntentKey.IMAGE_PATH_BUNDLE);
			if (null != bundle) {
				mSelectedList = bundle.getStringArrayList(IntentKey.IMAGE_PATH_LIST);
			}
			maxSize = intent.getExtras().getInt(IntentKey.IMAGE_UPLOAD_MAXSIZE);
		}
		super.onAttach(activity);
	}

	protected void initPopupWindow() {
		View view = null;
		if (mPopupWindow == null) {
			view = getActivity().getLayoutInflater().inflate(R.layout.fragment_image_select_popupwindow, null);
			mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.blog_background));
			mPopupWindow.setTouchable(true);
			mPopupWindow.setFocusable(true);
			mPopupWindow.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					// TODO Auto-generated method stub
					mShareImageView.setVisibility(View.GONE);
					mHeadView.setTitleRightDrawable(getResources().getDrawable(R.drawable.arrow_select_image_bottom),
							titleRightPixel);
				}
			});
		}
		if (view != null) {
			listView = (ListView) view.findViewById(R.id.image_select_popupwindow_listview);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					if (lAST_POSITION != position) {
						PARENT_NAME = subGroupOfImage(mGruopMap).get(position).getFolderName();
						list = mGruopMap.get(PARENT_NAME);
						lAST_POSITION = position;
						mHandler.sendEmptyMessage(GRIDVIEW_REFRESH);
					}
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					}

				}
			});
		}
		getImages();
	}

	/**
	 * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
	 */
	private void getImages() {
		// 显示进度条
		new Thread(new Runnable() {
			@Override
			public void run() {
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = mActivity.getContentResolver();
				// 只查询jpeg和png的图片
				Cursor mCursor = mContentResolver.query(mImageUri, null, MediaStore.Images.Media.MIME_TYPE + "=? or "
						+ MediaStore.Images.Media.MIME_TYPE + "=?", new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_MODIFIED);
				while (mCursor.moveToNext()) {
					// 获取图片的路径
					String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
					// 获取该图片的父路径名
					String parentName = new File(path).getParentFile().getName();
					// 根据父路径名将图片放入到mGruopMap中
					if (!mGruopMap.containsKey(parentName)) {
						List<String> chileList = new ArrayList<String>();
						chileList.add(path);
						mGruopMap.put(parentName, chileList);
					} else {
						mGruopMap.get(parentName).add(path);
					}
				}
				mCursor.close();
				mCursor = null;
				// 通知Handler扫描图片完成
				mHandler.sendEmptyMessage(SCAN_OK);

			}
		}).start();

	}

	/**
	 * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中 所以需要遍历HashMap将数据组装成List
	 * 
	 * @param mGruopMap
	 * @return
	 */
	private List<ImageEntity> subGroupOfImage(HashMap<String, List<String>> mGruopMap) {
		if (mGruopMap.size() == 0) {
			return null;
		}
		List<ImageEntity> list = new ArrayList<ImageEntity>();

		Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, List<String>> entry = it.next();
			ImageEntity mImageEntity = new ImageEntity();
			String key = entry.getKey();
			List<String> value = entry.getValue();

			mImageEntity.setFolderName(key);
			mImageEntity.setImageCounts(value.size());
			mImageEntity.setImagePath(value.get(0));// 获取该组的第一张图片

			list.add(mImageEntity);
		}

		return list;

	}

	@Override
	public void onDestroy() {
		if (null != list) {
			list.clear();
			list = null;
		}
		if (null != mGruopMap) {
			mGruopMap.clear();
			mGruopMap = null;
		}
		if (null != mSelectedList) {
			mSelectedList.clear();
			mSelectedList = null;
		}
		super.onDestroy();
	}

	public void onPicChooseResult() {
		Intent intent = new Intent(Constants.REFRESH_UPLOAD_GRIDVIEW_IMAGE);
		intent.putStringArrayListExtra(IntentKey.IMAGE_PATH_LIST, (ArrayList<String>) adapter.getSelectImg());
		getActivity().sendBroadcast(intent);
		getActivity().finish();
	}

	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_image_select, container, false);
		return view;
	}

	@Override
	public void onInitView(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mGridView = (GridView) view.findViewById(R.id.shining_image_select_gridview);
		mShareImageView = (ImageView) view.findViewById(R.id.shining_shade);
		mHeadView = (CustomHeadView) view.findViewById(R.id.head_view);
		mHeadView.setTitleRightDrawable(getResources().getDrawable(R.drawable.arrow_select_image_bottom), titleRightPixel);
		mHeadView.setRightText(R.string.confirm, new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().setResult(Activity.RESULT_OK,
						new Intent().putStringArrayListExtra(IntentKey.IMAGE_PATH_LIST, mSelectedList));
				getActivity().finish();
			}
		});
		mHeadView.setTitleOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				if (mPopupWindowAdapter == null) {
					mPopupWindowAdapter = new ParentListAdapter(mActivity, subGroupOfImage(mGruopMap), listView);
					listView.setAdapter(mPopupWindowAdapter);
				}
				if (!mPopupWindow.isShowing()) {
					mPopupWindow.showAsDropDown(v);
					mShareImageView.setVisibility(View.VISIBLE);
					mHeadView.setTitleRightDrawable(getResources().getDrawable(R.drawable.arrow_select_image_top), titleRightPixel);
				} else {
					mPopupWindow.dismiss();
				}

			}
		});
		initPopupWindow();
	}
}

/**
 * 项目名称: 七七同城
 * 
 * 文件名称: EducationActicity.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.lidroid.xutils.ui.BaseActivity;
import com.yisa.pray.R;
import com.yisa.pray.adapter.EducationAdapter;
import com.yisa.pray.blog.adapter.BlogCategroyAdapter;
import com.yisa.pray.blog.entity.BlogCategroyEntity;
import com.yisa.pray.entity.EducationEntity;
import com.yisa.pray.utils.IntentKey;
import com.yisa.pray.utils.PropertyUtil;
import com.yisa.pray.views.CustomHeadView;
import com.yisa.pray.views.LoadingDialog;

/**
 *
 * 类名称: EducationActicity.java
 * 类描述:	选择学历界面 
 * 创建人:  hq
 * 创建时间: 2016年8月22日下午1:53:29
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class EducationActicity extends BaseActivity {
	private static final String TAG = "EducationActicity";
	private CustomHeadView mHeadView;
	private ListView mListview;
	private EducationAdapter mAdapter;
	private List<EducationEntity> mList;
	private LoadingDialog mLoading;
	
	@Override
	public void setRootLayout() {
		setContentView(R.layout.activity_education);
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
		
		mListview = (ListView) getView(R.id.list);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.putExtra(IntentKey.EDUCATION, mList.get(position));
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		getEducationList();
		mAdapter = new EducationAdapter(mContext, mList);
		mListview.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	}
	
	public void getEducationList(){
		Properties properties = PropertyUtil.getInstance().readProperty(mContext, R.raw.education);
		Iterator<Entry<Object, Object>> it = properties.entrySet().iterator();
		mList = new ArrayList<EducationEntity>();
		Entry<Object, Object> entry = null;  
		EducationEntity edu = null;
		while (it.hasNext()) {
			entry = it.next();
			edu = new EducationEntity();
			edu.setId(Integer.parseInt((String)entry.getKey()));
			edu.setName((String) entry.getValue());
			mList.add(edu);
		}
	}

}

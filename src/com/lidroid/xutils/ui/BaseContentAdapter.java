package com.lidroid.xutils.ui;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Adapter基类
 * 
 * @author _Lidynast
 * @date 2014/11/10
 * @param <T>
 */
public abstract class BaseContentAdapter<T> extends BaseAdapter {

	protected Context mContext;
	protected List<T> dataList;
	protected LayoutInflater mInflater;

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
		this.notifyDataSetChanged();
	}

	public BaseContentAdapter(Context context, List<T> list) {
		mContext = context;
		dataList = list;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return null == dataList ? 0 : dataList.size();
	}

	@Override
	public T getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getConvertView(position, convertView, parent);
	}

	public abstract View getConvertView(int position, View convertView, ViewGroup parent);

}

package com.lidroid.xutils.util;

import android.util.SparseArray;
import android.view.View;

/**
 * ViewHolder简洁模式
 * 
 * @author _Lidynast
 * @date 2014/11/10
 * 
 */
public class AdapterUtils {

	/**
	 * @param view
	 * @param id
	 * @return
	 * @description 使用说明 在adapter的getView(int position, View convertView,
	 *              ViewGroup parent)方法中<br>
	 *              TextView txt=AdapterUtil.get(convertView, R.id.txt);<br>
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}
}
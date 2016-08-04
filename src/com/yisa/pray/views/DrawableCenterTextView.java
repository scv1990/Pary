/**
 * 项目名称: 七七同城
 * 
 * 文件名称: DrawableCenterTextView.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 *
 * 类名称: DrawableCenterTextView.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月4日下午3:11:14
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class DrawableCenterTextView extends TextView {

	/**
	 * @param context
	 */
	public DrawableCenterTextView(Context context) {
		super(context);
	}
	
	public DrawableCenterTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public DrawableCenterTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Drawable[] drawables = getCompoundDrawables();
		if(drawables != null){
			Drawable leftDraw = drawables[0];
			if(leftDraw != null){
				float textWidth = getPaint().measureText(getText().toString());
				int drawPadding = getCompoundDrawablePadding();
				int drawWidth = 0;
				drawWidth = leftDraw.getIntrinsicWidth();
				float bodyWidth = textWidth + drawPadding + drawWidth;
				canvas.translate((getWidth() - bodyWidth) / 2, 0);
			}
		}
		super.onDraw(canvas);
		
	}

}

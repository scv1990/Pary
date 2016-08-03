package com.yisa.pray.views;


import com.yisa.pray.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @Description
 * @author cr E-mail: 42888525@qq.com
 * @date 创建时间：2015年3月27日 上午10:24:16
 **/
public class CustomHeadView extends RelativeLayout {
	private TextView mTitleTextView;
	private TextView mRightTextView;
	private ImageView mLeftImageView;
	private ImageView mRightImageView;
	private ImageView mSecondRightImageView;

	public CustomHeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View mHeadView = LayoutInflater.from(context).inflate(R.layout.view_header, this, true);
		mTitleTextView = (TextView) mHeadView.findViewById(R.id.header_title);
		mRightTextView = (TextView) mHeadView.findViewById(R.id.header_right_text);
		mLeftImageView = (ImageView) mHeadView.findViewById(R.id.header_back);
		mRightImageView = (ImageView) mHeadView.findViewById(R.id.header_right_icon);
		mSecondRightImageView = (ImageView) mHeadView.findViewById(R.id.header_second_right_icon);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HeaderView);
		String title = a.getString(R.styleable.HeaderView_headTitle);
//		if(!isInEditMode()){
			mTitleTextView.setText(title);
//		}
		a.recycle();
	}

	public void setSecondRightIcon(int drawableId, OnClickListener onRightIconClickListener) {
		mSecondRightImageView.setImageResource(drawableId);
		if (onRightIconClickListener != null) {
			setSecondRightIconClickListener(onRightIconClickListener);
		}
	}

	public void setRightIcon(int drawableId, OnClickListener onRightIconClickListener) {
		mRightImageView.setImageResource(drawableId);
		if (onRightIconClickListener != null) {
			setRightIconClickListener(onRightIconClickListener);
		}
	}

	public void setRightText(int resourceId, OnClickListener onRightTextClickListener) {
		mRightTextView.setText(resourceId);
		if (onRightTextClickListener != null) {
			setRightTextClickListener(onRightTextClickListener);
		}
	}

	public void setRightText(String text, OnClickListener onRightTextClickListener) {
		mRightTextView.setText(text);
		if (onRightTextClickListener != null) {
			setRightTextClickListener(onRightTextClickListener);
		}
	}

	public void setLeftIconClickListener(OnClickListener onLeftIconClickListener) {
		mLeftImageView.setVisibility(View.VISIBLE);
		mLeftImageView.setOnClickListener(onLeftIconClickListener);
	}

	public void setLeftIconClickListener(int imageResource, OnClickListener onLeftIconClickListener) {
		mLeftImageView.setVisibility(View.VISIBLE);
		mLeftImageView.setImageResource(imageResource);
		mLeftImageView.setOnClickListener(onLeftIconClickListener);
	}

	private void setRightIconClickListener(OnClickListener onRightIconClickListener) {
		mRightImageView.setVisibility(View.VISIBLE);
		mRightTextView.setVisibility(View.GONE);
		mRightImageView.setOnClickListener(onRightIconClickListener);
	}

	private void setSecondRightIconClickListener(OnClickListener onRightIconClickListener) {
		mSecondRightImageView.setVisibility(View.VISIBLE);
		mSecondRightImageView.setOnClickListener(onRightIconClickListener);
	}

	private void setRightTextClickListener(OnClickListener onRightTextClickListener) {
		mRightImageView.setVisibility(View.GONE);
		mRightTextView.setVisibility(View.VISIBLE);
		mRightTextView.setOnClickListener(onRightTextClickListener);
	}

	public void setTitle(String title) {
		mTitleTextView.setText(title);
	}

	public void setTitle(int resId) {
		mTitleTextView.setText(resId);
	}

	public TextView getRightTextView() {
		return mRightTextView;
	}

	public void setRightTextView(TextView mRightTextView) {
		this.mRightTextView = mRightTextView;
	}

	public void setTitleOnClickListener(OnClickListener onClickListener) {
		mTitleTextView.setOnClickListener(onClickListener);
	}

	public void setTitleRightDrawable(Drawable right, int pixel) {
		right.setBounds(0, 0, pixel, pixel);
		mTitleTextView.setCompoundDrawables(null, null, right, null);
		mTitleTextView.setCompoundDrawablePadding(3);
	}

	public void setRightTextColor(int id) {
		mRightTextView.setTextColor(getResources().getColor(id));
	}

	public void setRightTextBG(int resID) {
		mRightTextView.setBackgroundResource(resID);
	}
	
	public void setRightTextVisibile(int visibility) {
		mRightTextView.setVisibility(visibility);
	}

	public void setRightDrawable(int resourceId) {
		if (mRightImageView != null) {
			mRightImageView.setImageResource(resourceId);
		}
	}
}

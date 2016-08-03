package com.yisa.pray.views;

import java.util.Timer;
import java.util.TimerTask;



import com.yisa.pray.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 加载中Dialog
 * 
 * @author cr
 * 
 * 
 * @Createtime 2014-5-10 上午9:14:34
 * 
 */
public class LoadingDialog extends AlertDialog {

	private TextView tips_loading_msg;
	ImageView image;

	private final int CANCEL_TIME = 5000;
	private String message = null;
	Context context;
	private Timer timer;
	private TimerTask timeTask;
	private Animation hyperspaceJumpAnimation;

	public LoadingDialog(Context context) {
		super(context);
		message = context.getString(R.string.loading);
		this.context = context;
	}

	public LoadingDialog(Context context, String message) {
		super(context);
		this.message = message;
		this.setCancelable(false);
		this.context = context;
	}

	public LoadingDialog(Context context, int theme, String message) {
		super(context, theme);
		this.message = message;
		this.setCancelable(false);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_tips_loading);
		tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
		image = (ImageView) findViewById(R.id.image);

		// 加载动画
		hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_loading);
		// 使用ImageView显示动画

		tips_loading_msg.setText(this.message);
		timer = new Timer();
		timeTask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setCancelable(true);
			}
		};
	}

	public void setText(String message) {
		this.message = message;
		if (tips_loading_msg != null) {
			tips_loading_msg.setText(this.message);
		}
	}

	public void setText(int resId) {

		setText(getContext().getResources().getString(resId));
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		image.startAnimation(hyperspaceJumpAnimation);
		setCancelable(false);
		startTime();
	}

	private void startTime() {
		if (timer != null) {
			timeTask = new TimerTask() {
				@Override
				public void run() {
					setCancelable(true);
				}
			};
			timer.schedule(timeTask, CANCEL_TIME);
		}
	}

	@Override
	public void dismiss() {
		if (isShowing()) {
			super.dismiss();
			timeTask.cancel();
		}
	}
}

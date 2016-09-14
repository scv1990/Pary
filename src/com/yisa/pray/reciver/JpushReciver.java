/**
 * 项目名称: 七七同城
 * 
 * 文件名称: JpushReciver.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.reciver;

import cn.jpush.android.api.JPushInterface;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 *
 * 类名称: JpushReciver.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年9月12日下午5:22:44
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */
public class JpushReciver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("JpushReciver", "onReceive");
		Bundle bundle = intent.getExtras();
		if(JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())){
			System.out.println("register_id" + bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID));
		}
		
	}

}

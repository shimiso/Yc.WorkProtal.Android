package com.yuecheng.workprotal.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * 
 * 这个类的作用:做一些系统的后台任务
 * 
 * @author juanqiang
 */
public class BaseService extends Service {
	protected Context context = BaseService.this;

	@Override
	public IBinder onBind(Intent arg0) {

		return null;
	}
}

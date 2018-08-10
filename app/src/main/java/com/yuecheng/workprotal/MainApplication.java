package com.yuecheng.workprotal;

import android.app.Activity;
import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.util.LinkedList;
import java.util.List;

public class MainApplication extends Application {
    private static MainApplication app;
    public List<Activity> activityList = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initXunfei();
    }


    public static MainApplication getApplication() {
        return app;
    }

    /**
     * 添加Activity到容器中.
     *
     * @param activity
     * @author 史明松
     * @update 2014年6月26日 上午10:55:40
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
    /**
     * 遍历所有Activity并finish.
     *
     * @author 史明松
     * @update 2014年6月26日 上午10:55:28
     */
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void initXunfei() {
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5b582f08");
    }
}

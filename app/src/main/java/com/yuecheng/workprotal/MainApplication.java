package com.yuecheng.workprotal;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

public class MainApplication extends Application {
    private static MainApplication app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initXunfei();
    }


    public static MainApplication getApplication() {
        return app;
    }

    private void initXunfei() {
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=58ac2f10");
    }
}

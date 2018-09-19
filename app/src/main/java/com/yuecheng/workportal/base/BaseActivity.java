package com.yuecheng.workportal.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yuecheng.workportal.MainApplication;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.utils.AndroidUtil;
import com.yuecheng.workportal.utils.SharePreferenceUtil;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


/**
 * Activity基类
 */
public class BaseActivity extends AppCompatActivity {
    /**
     * 共享存储工具类
     **/
    protected SharePreferenceUtil spUtil;
    /**
     * Application
     **/
    protected MainApplication mainApplication;
    /**
     * Android工具类
     **/
    protected AndroidUtil androidUtil;

    private boolean isFullFullscreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spUtil = new SharePreferenceUtil(this);
        mainApplication = (MainApplication) getApplication();
        this.androidUtil = AndroidUtil.init(this, spUtil, mainApplication);
        mainApplication.addActivity(this);
        //设置状态栏透明
        setTranslucentStatus(true);
        //通知栏默认为系统颜色
        setWindow(R.color.primary_dark);
    }

    /**
     * 初始化窗口
     *
     * @param barTintResource 通知栏颜色
     */
    protected void setWindow(int barTintResource) {
        //如果已经设置全屏了就无需再初始化窗口
        if (isFullFullscreen) {
            return;
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // 激活状态栏设置
        tintManager.setStatusBarTintEnabled(true);
        // 激活导航栏设置
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setStatusBarTintResource(barTintResource);//通知栏所需颜色
        tintManager.setNavigationBarTintColor(android.R.color.transparent);//导航条颜色置为透明
    }

    /**
     * 全屏
     */
    protected void setFullFullscreen() {
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        isFullFullscreen = true;
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    protected void connectRongIM(String token) {
        /**
         * IMKit SDK调用第二步,建立与服务器的连接
         */
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            /**
             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
             */
            @Override
            public void onTokenIncorrect() {
                Log.d("LoginActivity", "--onTokenIncorrect");
            }

            /**
             * 连接融云成功
             * @param userid 当前 token
             */
            @Override
            public void onSuccess(String userid) {
                Log.d("LoginActivity", "--onSuccess" + userid);
                spUtil.setCurrentUserName(userid);
            }
            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             *                  http://www.rongcloud.cn/docs/android.html#常见错误码
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d("LoginActivity", "--onError" + errorCode);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        //友盟页面统计：开始计时
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //友盟页面统计：结束计时
//        MobclickAgent.onPause(this);
    }
}

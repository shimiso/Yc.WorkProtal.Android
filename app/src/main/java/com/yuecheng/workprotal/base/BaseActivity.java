package com.yuecheng.workprotal.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yuecheng.workprotal.MainApplication;
import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.utils.AndroidUtil;
import com.yuecheng.workprotal.utils.SharePreferenceUtil;


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
        setWindow(R.color.primary);
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

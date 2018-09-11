package com.yuecheng.workprotal.module.mycenter.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.yuecheng.workprotal.MainActivity;
import com.yuecheng.workprotal.MainApplication;
import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.base.BaseActivity;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 */
public class SettingsActivity extends BaseActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ButterKnife.bind(this);
        context = this;
    }

    @OnClick({R.id.modify_password,R.id.message_notification,R.id.language_settings,R.id.index_settings,
            R.id.fangyan_settings,R.id.exit_btn,R.id.back_iv})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.modify_password://修改密码
                startActivity(new Intent(context,ModifyPasswordActivity.class));
                break;
            case R.id.message_notification://消息提醒
                break;
            case R.id.language_settings://语言设置
                showLanguageDialog();
                break;
            case R.id.fangyan_settings://方言设置
                showFangYanDialog();
                break;
            case R.id.index_settings://首页设置
                break;
            case R.id.exit_btn://退出
                MainApplication.getApplication().exit();
                startActivity(new Intent(context,LoginActivity.class));
                finish();
                break;
            case R.id.back_iv://返回
                finish();
                break;
        }
    }

    /**
     * 设置语种
     */
    private void showLanguageDialog() {
        LanguageSettingsDialog centerDialog = new LanguageSettingsDialog(context);
        centerDialog.setClicklistener(new LanguageSettingsDialog.ClickListenerInterface() {

            @Override
            public void onOneClick() {
                setChineseLanguage();
            }

            @Override
            public void onTwoClick() {
                setEnglishLanguage();
            }
        });
    }

    /**
     * 设置方言
     */
    private void showFangYanDialog() {
        FangYanSettingsDialog centerDialog = new FangYanSettingsDialog(context);
        centerDialog.setClicklistener(new FangYanSettingsDialog.ClickListenerInterface() {

            @Override
            public void onOneClick() {
            }

            @Override
            public void onTwoClick() {
            }

            @Override
            public void onThreeClick() {
            }
        });
    }
    /**
     * 切换英文
     */
    private void setEnglishLanguage() {
        // 获得res资源对象
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        // 获得屏幕参数：主要是分辨率，像素等。
        DisplayMetrics dm = resources.getDisplayMetrics();
        // 英语
        config.locale = Locale.US;
        resources.updateConfiguration(config, dm);
        //模拟重启页面
        MainApplication.getApplication().toIndex();
    }

    /**
     * 切换英文
     */
    private void setChineseLanguage() {

        // 获得res资源对象
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        // 获得屏幕参数：主要是分辨率，像素等。
        DisplayMetrics dm = resources.getDisplayMetrics();
        // 英语
        config.locale = Locale.CHINA;
        resources.updateConfiguration(config, dm);
        //模拟重启页面
        MainApplication.getApplication().toIndex();
    }
}

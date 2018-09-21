package com.yuecheng.workportal.module.mycenter.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.TextUnderstander;
import com.yuecheng.workportal.MainActivity;
import com.yuecheng.workportal.LoginActivity;
import com.yuecheng.workportal.MainApplication;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.module.robot.view.VoiceActivity;

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
            R.id.fangyan_settings,R.id.exit_btn,R.id.back_iv,R.id.title_voice})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.modify_password://修改密码
                startActivity(new Intent(context,ModifyPasswordActivity.class));
                break;
            case R.id.message_notification://消息提醒
                Intent magintent = new Intent(context, MsgNoticeActivity.class);
                startActivity(magintent);
                break;
            case R.id.language_settings://语言设置
                showLanguageDialog();
                break;
            case R.id.fangyan_settings://方言设置
                showFangYanDialog();
                break;
            case R.id.index_settings://首页设置
                break;
            case R.id.title_voice://跳转到语音页面
                Intent intent = new Intent(context, VoiceActivity.class);
                startActivity(intent);
                //使其由下向上弹出
                overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
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
                setFangYan("mandarin");
                centerDialog.dismissDialog();
            }

            @Override
            public void onTwoClick() {
                setFangYan("cantonese");
                centerDialog.dismissDialog();
            }

            @Override
            public void onThreeClick() {
                setFangYan("lmz");
                centerDialog.dismissDialog();
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
    //设置方言
    private void setFangYan(String fangyan){
        Locale curLocale = context.getResources().getConfiguration().locale;
        SpeechRecognizer recognizer = SpeechRecognizer.createRecognizer(SettingsActivity.this, null);
        TextUnderstander textUnderstander = TextUnderstander.createTextUnderstander(SettingsActivity.this, null);
        //通过Locale的equals方法，判断出当前语言环境
        if (curLocale.equals(Locale.SIMPLIFIED_CHINESE)) {
            //中文
            recognizer.setParameter(SpeechConstant.ACCENT, fangyan);
            textUnderstander.setParameter(SpeechConstant.ACCENT, fangyan);
        }else{
            //英文状态下没有方言设置
            recognizer.setParameter(SpeechConstant.ACCENT, null);
            textUnderstander.setParameter(SpeechConstant.ACCENT, null);
        }
    }
}

package com.yuecheng.workportal.module.mycenter.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.TextUnderstander;
import com.yuecheng.workportal.MainApplication;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.bean.MessageEvent;
import com.yuecheng.workportal.module.robot.view.VoiceActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 */
public class SettingsActivity extends BaseActivity {
    Context context;
    @BindView(R.id.home_page)
    TextView homePage;
    private HomePageSettingsDialog homePageSettingsDialog;
    @BindView(R.id.version_name)
    TextView version_name;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ButterKnife.bind(this);
        context = this;

        init();
    }

    private void init() {
        int HomeSetting = spUtil.getInt("HomeSetting", 0);
        homePageSettingsDialog = new HomePageSettingsDialog(context,HomeSetting);
        switch (HomeSetting) {
            case 0:
                homePage.setText(getString(R.string.message));
                break;
            case 1:
                homePage.setText(getString(R.string.task));
                break;
            case 2:
                homePage.setText(getString(R.string.workbench));
                break;
            case 3:
                homePage.setText(getString(R.string.address_books));
                break;
            case 4:
                homePage.setText(getString(R.string.my));
                break;
        }

        version_name.setText("V" + androidUtil.getApkVersionName());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
       if (messageEvent.type == MessageEvent.HOME_PAGE_TEXT) {
            homePage.setText(messageEvent.json);//展示选中首页
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.modify_password, R.id.message_notification, R.id.language_settings, R.id.index_settings,
            R.id.exit_btn, R.id.back_iv, R.id.title_voice})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.modify_password://修改密码
                startActivity(new Intent(context, ModifyPasswordActivity.class));
                break;
            case R.id.message_notification://消息提醒
                Intent magintent = new Intent(context, MsgNoticeActivity.class);
                startActivity(magintent);
                break;
            case R.id.language_settings://语言设置
                startActivity(new Intent(this,MultilingualActivity.class));
                break;
            case R.id.index_settings://首页设置
                showHomePageDialog();
                break;
            case R.id.title_voice://跳转到语音页面
                Intent intent = new Intent(context, VoiceActivity.class);
                startActivity(intent);
                //使其由下向上弹出
                overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
                break;
            case R.id.exit_btn://退出
                MainApplication.getApplication().relogin();
                break;
            case R.id.back_iv://返回
                finish();
                break;
        }
    }

    //设置展示首页
    private void showHomePageDialog() {
        homePageSettingsDialog.showDialog();
        homePageSettingsDialog.setClicklistener(new HomePageSettingsDialog.ClickListenerInterface() {
            @Override
            public void onMessageClick() {
                spUtil.setInt("HomeSetting", 0);
                homePageSettingsDialog.dismissDialog();
                EventBus.getDefault().post(new MessageEvent(MessageEvent.HOME_PAGE_TEXT, getString(R.string.message)));
            }

            @Override
            public void onTaskClick() {
                spUtil.setInt("HomeSetting", 1);
                homePageSettingsDialog.dismissDialog();
                EventBus.getDefault().post(new MessageEvent(MessageEvent.HOME_PAGE_TEXT, getString(R.string.task)));
            }

            @Override
            public void onWorkbenchClick() {
                spUtil.setInt("HomeSetting", 2);
                homePageSettingsDialog.dismissDialog();
                EventBus.getDefault().post(new MessageEvent(MessageEvent.HOME_PAGE_TEXT, getString(R.string.workbench)));
            }

            @Override
            public void onAddressClick() {
                spUtil.setInt("HomeSetting", 3);
                homePageSettingsDialog.dismissDialog();
                EventBus.getDefault().post(new MessageEvent(MessageEvent.HOME_PAGE_TEXT, getString(R.string.address_books)));
            }

            @Override
            public void onMyClick() {
                spUtil.setInt("HomeSetting", 4);
                homePageSettingsDialog.dismissDialog();
                EventBus.getDefault().post(new MessageEvent(MessageEvent.HOME_PAGE_TEXT, getString(R.string.my)));
            }
        });
    }
}

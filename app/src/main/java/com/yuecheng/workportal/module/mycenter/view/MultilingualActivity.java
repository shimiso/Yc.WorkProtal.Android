package com.yuecheng.workportal.module.mycenter.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.TextUnderstander;
import com.nineoldandroids.animation.ObjectAnimator;
import com.yuecheng.workportal.MainApplication;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;

import java.text.BreakIterator;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultilingualActivity extends BaseActivity {

    @BindView(R.id.chinese_img)
    ImageView chineseImg;
    @BindView(R.id.english_img)
    ImageView englishImg;
    @BindView(R.id.fanyan)
    TextView fanyan;
    @BindView(R.id.set_fanyan)
    ImageView setFanyan;
    @BindView(R.id.fangyan_ll)
    LinearLayout fangyanLl;
    @BindView(R.id.fangyan_settings)
    RelativeLayout fangyanSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_center_multilingual);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        String language = spUtil.getStrings("Language", "中文");
        String dialect = spUtil.getStrings("FangYan", "普通话");
        if (language.equals("中文")) {
            englishImg.setVisibility(View.GONE);
            chineseImg.setVisibility(View.VISIBLE);
            fangyanSettings.setVisibility(View.VISIBLE);
        } else {
            chineseImg.setVisibility(View.GONE);
            englishImg.setVisibility(View.VISIBLE);
            fangyanSettings.setVisibility(View.GONE);
        }

        switch (dialect){
            case "普通话":
                fanyan.setText(getString(R.string.mandarin));
                break;
            case "粤语":
                fanyan.setText(getString(R.string.cantonese));
                break;
            case "四川话":
                fanyan.setText(getString(R.string.lmz));
                break;
        }
    }

    @OnClick({R.id.back_iv, R.id.chinese, R.id.english, R.id.fangyan_settings, R.id.complete, R.id.mandarin, R.id.cantonese, R.id.lmz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.chinese: //中文
                chineseImg.setVisibility(View.VISIBLE);
                englishImg.setVisibility(View.GONE);
                fangyanSettings.setVisibility(View.VISIBLE);
                break;
            case R.id.english: //英文
                chineseImg.setVisibility(View.GONE);
                englishImg.setVisibility(View.VISIBLE);
                fangyanSettings.setVisibility(View.GONE);
                if (fangyanLl.getVisibility() == View.VISIBLE) {
                    fangyanLl.setVisibility(View.GONE);
                    ObjectAnimator.ofFloat(setFanyan, "rotation", -270, 0).start();
                }
                break;
            case R.id.complete://完成
                spUtil.setString("Language", chineseImg.getVisibility() == View.VISIBLE ? "中文" : "English");

                if(fanyan.getText().toString().equals(getString(R.string.mandarin))){
                    setFangYan("mandarin");
                    spUtil.setString("FangYan", "普通话");
                } else if(fanyan.getText().toString().equals(getString(R.string.cantonese))){
                    setFangYan("cantonese");
                    spUtil.setString("FangYan", "粤语");
                } else if(fanyan.getText().toString().equals(getString(R.string.lmz))){
                    setFangYan("lmz");
                    spUtil.setString("FangYan","四川话");
                }
                if (chineseImg.getVisibility() == View.VISIBLE) {
                    setChineseLanguage();//切换中文
                } else {
                    setEnglishLanguage();//切换英文
                }
                finish();
                break;
            case R.id.fangyan_settings://设置方言
                if (fangyanLl.getVisibility() == View.VISIBLE) {
                    fangyanLl.setVisibility(View.GONE);
                    ObjectAnimator.ofFloat(setFanyan, "rotation", -270, 0).start();
                } else {
                    fangyanLl.setVisibility(View.VISIBLE);
                    ObjectAnimator.ofFloat(setFanyan, "rotation", 0, 90).start();
                }
                break;
            case R.id.mandarin: //普通话
                showDialectView(getString(R.string.mandarin));
                break;
            case R.id.cantonese: //粤语
                showDialectView(getString(R.string.cantonese));
                break;
            case R.id.lmz: //四川话
                showDialectView(getString(R.string.lmz));
                break;
        }
    }

    //显示方言控件
    private void showDialectView(String string) {
        fangyanLl.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(setFanyan, "rotation", -270, 0).start();
        fanyan.setText(string);
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
        startActivity(new Intent( MainApplication.getApplication(), SettingsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 切换中文
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
        startActivity(new Intent( MainApplication.getApplication(), SettingsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    //设置方言
    private void setFangYan(String fangyan) {
        Locale curLocale = getResources().getConfiguration().locale;
        SpeechRecognizer recognizer = SpeechRecognizer.createRecognizer(this, null);
        TextUnderstander textUnderstander = TextUnderstander.createTextUnderstander(this, null);
        //通过Locale的equals方法，判断出当前语言环境
        if (curLocale.equals(Locale.SIMPLIFIED_CHINESE)) {
            //中文
            recognizer.setParameter(SpeechConstant.ACCENT, fangyan);
            textUnderstander.setParameter(SpeechConstant.ACCENT, fangyan);
        } else {
            //英文状态下没有方言设置
            recognizer.setParameter(SpeechConstant.ACCENT, null);
            textUnderstander.setParameter(SpeechConstant.ACCENT, null);
        }
    }

}

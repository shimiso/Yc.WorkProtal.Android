package com.yuecheng.workportal.module.mycenter.view;

import android.os.Bundle;
import android.view.View;

import com.suke.widget.SwitchButton;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 消息通知
 */
public class MsgNoticeActivity extends BaseActivity {

    @BindView(R.id.message_button)
    SwitchButton messageButton;
    @BindView(R.id.voice_button)
    SwitchButton voiceButton;
    @BindView(R.id.vibration_button)
    SwitchButton vibrationButton;
    @BindView(R.id.synergy_button)
    SwitchButton synergyButton;
    @BindView(R.id.meeting_button)
    SwitchButton meetingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_center_notice);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        //消息通知
        messageButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    ToastUtil.info(MsgNoticeActivity.this,"开");
                }else{
                    ToastUtil.info(MsgNoticeActivity.this,"关");
                }
            }
        });
        //声音提示
        voiceButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    ToastUtil.info(MsgNoticeActivity.this,"开");
                }else{
                    ToastUtil.info(MsgNoticeActivity.this,"关");
                }
            }
        });
        //震动提示
        vibrationButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    ToastUtil.info(MsgNoticeActivity.this,"开");
                }else{
                    ToastUtil.info(MsgNoticeActivity.this,"关");
                }
            }
        });
        //协同通知
        synergyButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    ToastUtil.info(MsgNoticeActivity.this,"开");
                }else{
                    ToastUtil.info(MsgNoticeActivity.this,"关");
                }
            }
        });
        //会议通知
        meetingButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    ToastUtil.info(MsgNoticeActivity.this,"开");
                }else{
                    ToastUtil.info(MsgNoticeActivity.this,"关");
                }
            }
        });
    }

    @OnClick({R.id.back_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;

        }
    }
}

package com.yuecheng.workportal.module.mycenter.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.module.robot.view.VoiceActivity;
import com.yuecheng.workportal.widget.XEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.opinion_msg_et)
    XEditText opinionMsgEt;
    @BindView(R.id.opinion_phone_et)
    XEditText opinionPhoneEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_center_feedback);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_iv, R.id.title_voice, R.id.submit_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_voice:
                Intent intent = new Intent(this, VoiceActivity.class);
                startActivity(intent);
                //使其由下向上弹出
                overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
                break;
            case R.id.submit_button:
                break;
        }
    }

}

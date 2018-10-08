package com.yuecheng.workportal.module.mycenter.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.module.robot.view.VoiceActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HRActivity extends BaseActivity {

    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.join_company)
    TextView joinCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_center_hr);
        ButterKnife.bind(this);
        joinCompany.setText(getString(R.string.join_company_one) + "100" + getString(R.string.join_company_two));
    }

    @OnClick({R.id.back_iv, R.id.title_voice,R.id.wdzl, R.id.xzcx, R.id.htcx, R.id.kqcx, R.id.jqcx})
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
            case R.id.wdzl:
                break;
            case R.id.xzcx:
                break;
            case R.id.htcx:
                break;
            case R.id.kqcx:
                break;
            case R.id.jqcx:
                break;
        }
    }


}

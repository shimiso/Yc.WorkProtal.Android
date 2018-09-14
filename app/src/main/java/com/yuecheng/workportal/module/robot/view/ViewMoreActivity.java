package com.yuecheng.workportal.module.robot.view;

import android.os.Bundle;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewMoreActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.robot_view_more);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}

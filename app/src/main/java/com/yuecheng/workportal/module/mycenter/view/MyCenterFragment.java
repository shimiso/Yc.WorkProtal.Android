package com.yuecheng.workportal.module.mycenter.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuecheng.workportal.MainActivity;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseFragment;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by huochangsheng on 2018/7/25.
 */

public class MyCenterFragment extends BaseFragment {

    Unbinder unbinder;

    public static MyCenterFragment newInstance() {
        Bundle args = new Bundle();
        MyCenterFragment fragment = new MyCenterFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_center_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_hr, R.id.my_login_web, R.id.my_directions, R.id.my_feedback, R.id.my_setting,R.id.my_information_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_information_rl://个人详情
                startActivity(new Intent(getContext(),MyDetailActivity.class));
                break;
            case R.id.my_hr: //HR自助
                showHRDialog();
                break;
            case R.id.my_login_web: //登录网页版
                break;
            case R.id.my_directions://使用说明
                break;
            case R.id.my_feedback://意见反馈
                startActivity(new Intent(getContext(),FeedbackActivity.class));
                break;
            case R.id.my_setting: //设置
                startActivity(new Intent(getContext(),SettingsActivity.class));
                break;
        }
    }
    /**
     * HR自助
     */
    private void showHRDialog() {
        MessageKnowDialog centerDialog = new MessageKnowDialog(getContext());
        centerDialog.setClicklistener(new MessageKnowDialog.ClickListenerInterface() {

            @Override
            public void onOneClick() {
                centerDialog.dismissDialog();
            }

        });
    }}

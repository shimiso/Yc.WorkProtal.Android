package com.yuecheng.workprotal.module.mycenter.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuecheng.workprotal.MainActivity;
import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.base.BaseFragment;
import com.yuecheng.workprotal.module.mycenter.CenterSettingDialog;

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
//
//        view.findViewById(R.id.exit_btn).setOnClickListener(view1 -> {
//            MainApplication.getApplication().exit();
//            startActivity(new Intent(getActivity(),LoginActivity.class));
//            getActivity().finish();
//        });
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 设置中英文切换
     */
    private void showBottomDialog() {
        CenterSettingDialog centerDialog = new CenterSettingDialog(getContext());
        centerDialog.setClicklistener(new CenterSettingDialog.ClickListenerInterface() {

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

    private void setChineseLanguage() {
        /**
         * 切换英文
         */
        // 获得res资源对象
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        // 获得屏幕参数：主要是分辨率，像素等。
        DisplayMetrics dm = resources.getDisplayMetrics();
        // 英语
        config.locale = Locale.CHINA;
        resources.updateConfiguration(config, dm);
        //模拟重启页面
        getActivity().finish();
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    private void setEnglishLanguage() {
        /**
         * 切换英文
         */
        // 获得res资源对象
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        // 获得屏幕参数：主要是分辨率，像素等。
        DisplayMetrics dm = resources.getDisplayMetrics();
        // 英语
        config.locale = Locale.US;
        resources.updateConfiguration(config, dm);
        //模拟重启页面
        getActivity().finish();
        startActivity(new Intent(getContext(), MainActivity.class));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_hr, R.id.my_login_web, R.id.my_directions, R.id.my_feedback, R.id.my_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_hr:
                break;
            case R.id.my_login_web:
                break;
            case R.id.my_directions:
                break;
            case R.id.my_feedback:
                break;
            case R.id.my_setting:
                break;
        }
    }
}

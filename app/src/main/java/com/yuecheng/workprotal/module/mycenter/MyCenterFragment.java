package com.yuecheng.workprotal.module.mycenter;

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
import com.yuecheng.workprotal.module.PasswordLoginActivity;

import java.util.Locale;

/**
 * Created by huochangsheng on 2018/7/25.
 */

public class MyCenterFragment extends BaseFragment {

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
        view.findViewById(R.id.my_workplans).setOnClickListener(v -> showBottomDialog()
                //startActivity(new Intent(getActivity(),OpenH5Activity.class))
                );
        view.findViewById(R.id.exit_btn).setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(),PasswordLoginActivity.class));
            getActivity().finish();
        });
        return view;
    }

    /**
     * 展示
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


}

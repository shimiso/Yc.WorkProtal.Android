package com.yuecheng.workprotal.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.base.BaseFragment;
import com.yuecheng.workprotal.module.mycenter.MyCenterFragment;

/**
 * Created by huochangsheng on 2018/8/23.
 */

public class MyRobotFragment extends BaseFragment {
    public static MyRobotFragment newInstance() {
        Bundle args = new Bundle();
        MyRobotFragment fragment = new MyRobotFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_center_fragment, container, false);
        return view;
    }
}

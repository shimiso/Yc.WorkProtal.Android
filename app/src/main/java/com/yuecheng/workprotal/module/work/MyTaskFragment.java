package com.yuecheng.workprotal.module.work;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.base.BaseFragment;

/**
 * Created by huochangsheng on 2018/7/25.
 */

public class MyTaskFragment extends BaseFragment {


    public static MyTaskFragment newInstance() {
        Bundle args = new Bundle();
        MyTaskFragment fragment = new MyTaskFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_fragment, container, false);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
    }
}

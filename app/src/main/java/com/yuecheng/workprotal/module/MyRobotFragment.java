package com.yuecheng.workprotal.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by huochangsheng on 2018/8/23.
 */

public class MyRobotFragment extends BaseFragment {
    Unbinder unbinder;

    public static MyRobotFragment newInstance() {
        Bundle args = new Bundle();
        MyRobotFragment fragment = new MyRobotFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workbench_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.dbgz, R.id.fqbd, R.id.xt, R.id.fqxt, R.id.xjhy, R.id.cxhy, R.id.sjap, R.id.dhys, R.id.qyyx, R.id.itfw, R.id.wdsc, R.id.qyzd, R.id.wdzx, R.id.xw, R.id.gg, R.id.dc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dbgz:
                Toast.makeText(getContext(),"dbgz",Toast.LENGTH_SHORT).show();
                break;
            case R.id.fqbd:
                Toast.makeText(getContext(),"dbgz",Toast.LENGTH_SHORT).show();
                break;
            case R.id.xt:
                Toast.makeText(getContext(),"dbgz",Toast.LENGTH_SHORT).show();
                break;
            case R.id.fqxt:
                Toast.makeText(getContext(),"dbgz",Toast.LENGTH_SHORT).show();
                break;
            case R.id.xjhy:
                Toast.makeText(getContext(),"xjhy",Toast.LENGTH_SHORT).show();
                break;
            case R.id.cxhy:
                Toast.makeText(getContext(),"cxhy",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sjap:
                Toast.makeText(getContext(),"sjap",Toast.LENGTH_SHORT).show();
                break;
            case R.id.dhys:
                Toast.makeText(getContext(),"dhys",Toast.LENGTH_SHORT).show();
                break;
            case R.id.qyyx:
                Toast.makeText(getContext(),"qyyx",Toast.LENGTH_SHORT).show();
                break;
            case R.id.itfw:
                Toast.makeText(getContext(),"itfw",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wdsc:
                Toast.makeText(getContext(),"wdsc",Toast.LENGTH_SHORT).show();
                break;
            case R.id.qyzd:
                Toast.makeText(getContext(),"qyzd",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wdzx:
                Toast.makeText(getContext(),"wdzx",Toast.LENGTH_SHORT).show();
                break;
            case R.id.xw:
                Toast.makeText(getContext(),"xw",Toast.LENGTH_SHORT).show();
                break;
            case R.id.gg:
                Toast.makeText(getContext(),"gg",Toast.LENGTH_SHORT).show();
                break;
            case R.id.dc:
                Toast.makeText(getContext(),"dc",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

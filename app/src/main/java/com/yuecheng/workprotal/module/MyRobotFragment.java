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

    @OnClick({R.id.hthq, R.id.fksq, R.id.ywqs, R.id.fyssx, R.id.xjsq, R.id.jbsq, R.id.dbsx, R.id.dhys, R.id.qyyx, R.id.itfw, R.id.qyzd, R.id.wdzx, R.id.xw, R.id.gg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hthq:
                Toast.makeText(getContext(),"hthq ",Toast.LENGTH_LONG).show();
                break;
            case R.id.fksq:
                Toast.makeText(getContext()," fksq",Toast.LENGTH_LONG).show();
                break;
            case R.id.ywqs:
                Toast.makeText(getContext()," ywqs",Toast.LENGTH_LONG).show();
                break;
            case R.id.fyssx:
                Toast.makeText(getContext(),"fyssx ",Toast.LENGTH_LONG).show();
                break;
            case R.id.xjsq:
                Toast.makeText(getContext(),"xjsq ",Toast.LENGTH_LONG).show();
                break;
            case R.id.jbsq:
                Toast.makeText(getContext()," jbsq",Toast.LENGTH_LONG).show();
                break;
            case R.id.dbsx:
                Toast.makeText(getContext(),"dbsx ",Toast.LENGTH_LONG).show();
                break;
            case R.id.dhys:
                Toast.makeText(getContext()," dhys",Toast.LENGTH_LONG).show();
                break;
            case R.id.qyyx:
                Toast.makeText(getContext(),"qyyx ",Toast.LENGTH_LONG).show();
                break;
            case R.id.itfw:
                Toast.makeText(getContext(),"itfw ",Toast.LENGTH_LONG).show();
                break;
            case R.id.qyzd:
                Toast.makeText(getContext()," qyzd",Toast.LENGTH_LONG).show();
                break;
            case R.id.wdzx:
                Toast.makeText(getContext()," wdzx",Toast.LENGTH_LONG).show();
                break;
            case R.id.xw:
                Toast.makeText(getContext()," xw",Toast.LENGTH_LONG).show();
                break;
            case R.id.gg:
                Toast.makeText(getContext(),"gg ",Toast.LENGTH_LONG).show();
                break;
        }
    }
}

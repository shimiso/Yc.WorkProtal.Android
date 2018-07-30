package com.yuecheng.workprotal.module.mycenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.module.myandroid.OpenH5Activity;

/**
 * Created by huochangsheng on 2018/7/25.
 */

public class MyCenterFragment extends Fragment {

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
        view.findViewById(R.id.my_workplans).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),OpenH5Activity.class));
            }
        });
        return view;
    }
}

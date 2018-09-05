package com.yuecheng.workprotal.module.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuecheng.workprotal.R;


/**
 * Created by huochangsheng on 2018/8/30.
 */

public class MyTabStructureFragment extends Fragment {

    private View view;
    public static MyTabStructureFragment newInstance() {
        Bundle args = new Bundle();
        MyTabStructureFragment fragment = new MyTabStructureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contacts_tab_structure, container, false);

        return view;
    }
}

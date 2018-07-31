package com.yuecheng.workprotal.module.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuecheng.workprotal.R;

/**
 * Created by huochangsheng on 2018/7/25.
 */

public class MyContactsFragment extends Fragment {

    public static MyContactsFragment newInstance() {
        Bundle args = new Bundle();
        MyContactsFragment fragment = new MyContactsFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_contacts_fragment, container, false);
        return view;
    }
}

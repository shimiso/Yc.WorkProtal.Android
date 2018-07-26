package com.yuecheng.workprotal.message;

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

public class MyMessageFragment extends Fragment {

    public static MyMessageFragment newInstance() {
        Bundle args = new Bundle();
        MyMessageFragment fragment = new MyMessageFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_message_fragment, container, false);

        return view;
    }
}

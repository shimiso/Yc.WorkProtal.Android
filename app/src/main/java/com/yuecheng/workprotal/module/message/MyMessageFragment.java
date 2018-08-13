package com.yuecheng.workprotal.module.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.base.BaseFragment;
import com.yuecheng.workprotal.module.robot.view.VoiceActivity;

/**
 * Created by huochangsheng on 2018/7/25.
 */

public class MyMessageFragment extends BaseFragment {

    public static MyMessageFragment newInstance() {
        Bundle args = new Bundle();
        MyMessageFragment fragment = new MyMessageFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment, container, false);
        view.findViewById(R.id.my_assistant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VoiceActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}

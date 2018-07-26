package com.yuecheng.workprotal.mywork;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.mywork.editor.EditorApplicationActivity;

/**
 * Created by huochangsheng on 2018/7/25.
 */

public class MyWorkFragment extends Fragment {

    public static MyWorkFragment newInstance() {
        Bundle args = new Bundle();
        MyWorkFragment fragment = new MyWorkFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_work_fragment, container, false);

        TextView work_tv = (TextView) view.findViewById(R.id.work_tv);
        work_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditorApplicationActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}

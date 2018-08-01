package com.yuecheng.workprotal.module.mywork;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.module.mywork.editor.FunctionBlockAdapter;
import com.yuecheng.workprotal.module.mywork.editor.FunctionItem;
import com.yuecheng.workprotal.module.mywork.editor.SFUtils;
import com.yuecheng.workprotal.module.mywork.editor.SpaceItemDecoration;

import java.util.List;

/**
 * Created by huochangsheng on 2018/7/25.
 */

public class MyWorkFragment extends Fragment {

    private View view;
    private SFUtils sfUtils;
    private List<FunctionItem> selData;
    private RecyclerView recycler_view;

    public static MyWorkFragment newInstance() {
        Bundle args = new Bundle();
        MyWorkFragment fragment = new MyWorkFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.work_fragment, container, false);

        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.addItemDecoration(new SpaceItemDecoration(4, dip2px(getContext(), 10)));


        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        sfUtils = new SFUtils(getContext());
        selData = sfUtils.getSelectFunctionItem();

        selData.add(new FunctionItem("更多",true,"more",""));

        FunctionBlockAdapter blockAdapter = new FunctionBlockAdapter(getContext(), 1,selData);
        recycler_view.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recycler_view.setAdapter(blockAdapter);

    }

    public  int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

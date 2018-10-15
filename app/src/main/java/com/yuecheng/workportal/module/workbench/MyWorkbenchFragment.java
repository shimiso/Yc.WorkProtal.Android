package com.yuecheng.workportal.module.workbench;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseFragment;
import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.module.robot.OpenH5Activity;
import com.yuecheng.workportal.module.work.editor.FunctionItem;
import com.yuecheng.workportal.module.work.editor.SpaceItemDecoration;
import com.yuecheng.workportal.module.workbench.adapter.WorkbenchAdapter;
import com.yuecheng.workportal.module.workbench.bean.WorkbenchBean;
import com.yuecheng.workportal.module.workbench.presenter.WorkbenchPresenter;
import com.yuecheng.workportal.utils.LoadViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by huochangsheng on 2018/8/23.
 */

public class MyWorkbenchFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.recyclerViewAll)
    RecyclerView recyclerViewAll;
    private WorkbenchPresenter workbenchPresenter;
    private WorkbenchAdapter workbenchAdapter;
    private List<WorkbenchBean.PermsBean> permsList = new ArrayList<>();
    private LoadViewUtil viewUtil;

    public static MyWorkbenchFragment newInstance() {
        Bundle args = new Bundle();
        MyWorkbenchFragment fragment = new MyWorkbenchFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workbench_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        viewUtil = LoadViewUtil.init(view, getContext());
        workbenchPresenter = new WorkbenchPresenter(getContext());


        GridLayoutManager gridManager = new GridLayoutManager(getContext(), 4);
        gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                return permsList.get(position).isTitle ? 4 : 1;
            }
        });
        workbenchAdapter = new WorkbenchAdapter(getContext());
        recyclerViewAll.setLayoutManager(gridManager);
        recyclerViewAll.setAdapter(workbenchAdapter);
        loadData();
        return view;
    }

    /**
     * 加载数据
     */
    private void loadData() {
        //如果没有网络就直接返回
        if (!androidUtil.hasInternetConnected()) {
            viewUtil.stopLoading();
            viewUtil.showLoadingErrorView(LoadViewUtil.LOADING_NONET_VIEW, () -> {
                viewUtil.startLoading();
                loadData();
            });
            return;
        }
        getWorkBenchData();
    }
    //获取工作台数据
    private void getWorkBenchData() {
        workbenchPresenter.getGetStaffPerms("", new CommonPostView<List<WorkbenchBean>>() {

            @Override
            public void postSuccess(ResultInfo<List<WorkbenchBean>> resultInfo) {
                List<WorkbenchBean> result = resultInfo.getResult();
                workbenchAdapter.onRefresh(permsList);
                for (int i = 0; i < result.size(); i++) {
                    WorkbenchBean.PermsBean permsBean = new WorkbenchBean.PermsBean(result.get(i).getSectionName(), true);
                    permsList.add(permsBean);
                    permsList.addAll(result.get(i).getPerms());
                }
                viewUtil.stopLoading();
            }

            @Override
            public void postError(String errorMsg) {
                viewUtil.stopLoading();
                viewUtil.showLoadingErrorView(LoadViewUtil.LOADING_ERROR_VIEW, () -> {
                    viewUtil.startLoading();
                    loadData();
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

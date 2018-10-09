package com.yuecheng.workportal.module.contacts.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.module.contacts.adapter.OrganizationAdapter;
import com.yuecheng.workportal.module.contacts.adapter.SelectGroupAdapter;
import com.yuecheng.workportal.module.contacts.bean.OrganizationBean;
import com.yuecheng.workportal.module.contacts.presenter.ContactsPresenter;
import com.yuecheng.workportal.utils.LoadViewUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectGroupActivity extends BaseActivity {

    @BindView(R.id.my_select_group_rl)
    RecyclerView mySelectGroupRl;
    private List<OrganizationBean.OrgsBean> orgs;
    private SelectGroupAdapter selectGroupAdapter;
    private int myselfTopOrgId;
    private LoadViewUtil viewUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_select_group);
        ButterKnife.bind(this);
        viewUtil = LoadViewUtil.init(getWindow().getDecorView(), this);
        Intent intent = getIntent();
        if (intent != null) {
            myselfTopOrgId = intent.getIntExtra("myselfTopOrgId",-1);
            orgs = (List<OrganizationBean.OrgsBean>) intent.getSerializableExtra("orgs");
        }

        //设置RecyclerView管理器
        mySelectGroupRl.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        selectGroupAdapter = new SelectGroupAdapter(this,myselfTopOrgId);
        //设置添加或删除item时的动画，这里使用默认动画
        mySelectGroupRl.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        mySelectGroupRl.setAdapter(selectGroupAdapter);

        loadData();//加载数据
        selectGroupAdapter.setOnRecyclerViewItemClickLintemet(position -> {
            int orgId = orgs.get(position).getOrgId();
            intent.putExtra("orgid",orgId);
            setResult(RESULT_OK, intent);
            finish();
        });

    }

    /**
     * 加载数据
     */
    protected void loadData() {
        //如果没有网络就直接返回
        if (!androidUtil.hasInternetConnected()) {
            viewUtil.stopLoading();
            viewUtil.showLoadingErrorView(LoadViewUtil.LOADING_NONET_VIEW, () -> {
                viewUtil.startLoading();
                loadData();
            });
            return;
        }
        viewUtil.startLoading();
        if(orgs!=null){
            selectGroupAdapter.onRefresh(orgs);
            viewUtil.stopLoading();
        }else{
            viewUtil.stopLoading();
            viewUtil.showLoadingErrorView(LoadViewUtil.LOADING_EMPTY_VIEW, () -> {
                viewUtil.startLoading();
                loadData();
            });
        }
    }
    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}

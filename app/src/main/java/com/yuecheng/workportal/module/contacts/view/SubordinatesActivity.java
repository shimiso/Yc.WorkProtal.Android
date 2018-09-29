package com.yuecheng.workportal.module.contacts.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.module.contacts.adapter.OrganizationAdapter;
import com.yuecheng.workportal.module.contacts.adapter.SubordinatesAdapter;
import com.yuecheng.workportal.module.contacts.bean.PersonnelDetailsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubordinatesActivity extends BaseActivity {

    @BindView(R.id.my_subordinates_recyclerView)
    RecyclerView mySubordinatesRecyclerView;
    private SubordinatesAdapter subordinatesAdapter;
    private List<PersonnelDetailsBean.SubordinatesBean> subordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_subordinates);
        ButterKnife.bind(this);

        Intent intent  = getIntent();
        if(intent!=null){
            subordinates = (List<PersonnelDetailsBean.SubordinatesBean>) intent.getSerializableExtra("subordinates");

        }
        //设置RecyclerView管理器
        mySubordinatesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        subordinatesAdapter = new SubordinatesAdapter(this);
        //设置添加或删除item时的动画，这里使用默认动画
        mySubordinatesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        mySubordinatesRecyclerView.setAdapter(subordinatesAdapter);

        subordinatesAdapter.onRefresh(subordinates);
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}

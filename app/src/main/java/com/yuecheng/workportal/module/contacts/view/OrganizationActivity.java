package com.yuecheng.workportal.module.contacts.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.module.contacts.adapter.OrganizationAdapter;
import com.yuecheng.workportal.module.contacts.bean.ChildInstitutionsBean;
import com.yuecheng.workportal.module.contacts.bean.OrganizationBean;
import com.yuecheng.workportal.module.contacts.presenter.ContactsPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrganizationActivity extends BaseActivity {

    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.my_linearlayout)
    LinearLayout myLinearlayout;
    @BindView(R.id.my_contacter_recyclerView)
    RecyclerView myContacterRecyclerView;
    @BindView(R.id.orgname)
    TextView orgname_tv;
    @BindView(R.id.hor_scrollview)
    HorizontalScrollView horScrollview;
    private int orgid;//点击的子组织机构ID
    private String orgname;//点击的子组织机构ID
    private OrganizationAdapter organizationAdapter;
    private List<OrganizationBean.OrgsBean.StaffsBean> staffsList;
    private List<OrganizationBean.OrgsBean.SubOrgsBean> subOrgsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_organization);
        ButterKnife.bind(this);

        //去除到头使阴影
        horScrollview.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //水平方向的水平滚动条是否显示
        horScrollview.setHorizontalScrollBarEnabled(false);
        Intent intent = getIntent();
        orgid = intent.getIntExtra("orgid", -1);
        orgname = intent.getStringExtra("orgname");
        orgname_tv.setText(orgname);
        //设置RecyclerView管理器
        myContacterRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        organizationAdapter = new OrganizationAdapter(this);
        //设置添加或删除item时的动画，这里使用默认动画
        myContacterRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        myContacterRecyclerView.setAdapter(organizationAdapter);


        initData(orgid);
        initEvent();
    }

    //点击事件
    private void initEvent() {
        organizationAdapter.setOnRecyclerViewItemClickLintemet(position -> {
            if (subOrgsList != null && position < subOrgsList.size()) {
                initData(subOrgsList.get(position).getOrgId());

            } else {
                if (subOrgsList != null && position >= subOrgsList.size()) {
                    //条目点击
                    Intent intent = new Intent(OrganizationActivity.this, InformationActivity.class);
                    intent.putExtra("StaffId", staffsList.get(position - subOrgsList.size()).getStaffId());
                    intent.putExtra("name", staffsList.get(position - subOrgsList.size()).getName());
                    startActivity(intent);
                }
            }
        });
    }

    //获取数据
    private void initData(int orgid) {
        ContactsPresenter Presenter = new ContactsPresenter(OrganizationActivity.this);
        Presenter.getAddressOrgQuery(orgid, new CommonPostView<ChildInstitutionsBean>() {
            @Override
            public void postSuccess(ResultInfo<ChildInstitutionsBean> resultInfo) {

                ChildInstitutionsBean result1 = resultInfo.getResult();
                String[] split2 = result1.getDeepOrgNames().split(">");
                titleName.setText(split2[split2.length - 1]);
                ChildInstitutionsBean.OrgsBean result = result1.getOrgs().get(0);
                subOrgsList = result.getSubOrgs();
                staffsList = result.getStaffs();
                organizationAdapter.onRefresh(staffsList, subOrgsList);

                TextView textView = new TextView(OrganizationActivity.this);
                String[] split = result1.getDeepOrgNames().split(">");
                textView.setText(" > " + split[split.length - 1]);
                myLinearlayout.addView(textView);
                for (int i = 0; i < myLinearlayout.getChildCount(); i++) {
                    if (i == myLinearlayout.getChildCount() - 1) {
                        TextView childAt = (TextView) myLinearlayout.getChildAt(i);
                        childAt.setTextColor(Color.parseColor("#282828"));
                    } else {
                        TextView childAt = (TextView) myLinearlayout.getChildAt(i);
                        childAt.setTextColor(Color.parseColor("#3189f4"));
                    }
                }
                textView.setOnClickListener(v -> {
                    for (int i = myLinearlayout.getChildCount() - 1; i >= split.length - 1; i--) {
                        myLinearlayout.removeViewAt(i);
                    }

                    for (int i = 0; i < myLinearlayout.getChildCount(); i++) {
                        if (i == myLinearlayout.getChildCount() - 1) {
                            TextView childAt = (TextView) myLinearlayout.getChildAt(i);
                            childAt.setTextColor(Color.parseColor("#282828"));
                        } else {
                            TextView childAt = (TextView) myLinearlayout.getChildAt(i);
                            childAt.setTextColor(Color.parseColor("#3189f4"));
                        }
                    }
                    String[] split1 = result1.getDeepOrgIds().split(">");
                    ContactsPresenter Presenter = new ContactsPresenter(OrganizationActivity.this);
                    Presenter.getAddressOrgQuery(Integer.parseInt(split1[split1.length - 1]), new CommonPostView<ChildInstitutionsBean>() {
                        @Override
                        public void postSuccess(ResultInfo<ChildInstitutionsBean> resultInfo) {
                            ChildInstitutionsBean result1 = resultInfo.getResult();
                            ChildInstitutionsBean.OrgsBean result = result1.getOrgs().get(0);
                            subOrgsList = result.getSubOrgs();
                            staffsList = result.getStaffs();
                            organizationAdapter.onRefresh(staffsList, subOrgsList);
                        }

                        @Override
                        public void postError(String errorMsg) {
                            Toast.makeText(OrganizationActivity.this, "服务器发生未知异常", Toast.LENGTH_SHORT).show();
                        }
                    });

                });
            }

            @Override
            public void postError(String errorMsg) {
                Toast.makeText(OrganizationActivity.this, "服务器发生未知异常", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick({R.id.back_iv, R.id.share, R.id.orgname})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
            case R.id.orgname:
                finish();
                break;
            case R.id.share://跳转到语音页面
                break;
        }
    }
}

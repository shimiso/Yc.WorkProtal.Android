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
import com.yuecheng.workportal.utils.LoadViewUtil;

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
    protected LoadViewUtil viewUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_organization);
        ButterKnife.bind(this);
        viewUtil = LoadViewUtil.init(getWindow().getDecorView(), this);
        init();
        loadData();
        initEvent();
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
        initData(orgid);
    }
    private void init() {
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
                    intent.putExtra("Guid", staffsList.get(position - subOrgsList.size()).getGuid());
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
                viewUtil.stopLoading();
                //设置title名字
                ChildInstitutionsBean result1 = resultInfo.getResult();
                String[] split2 = result1.getDeepOrgNames().split(">");
                titleName.setText(split2[split2.length - 1]);
                //刷新适配器
                ChildInstitutionsBean.OrgsBean result = result1.getOrgs().get(0);
                subOrgsList = result.getSubOrgs();
                staffsList = result.getStaffs();
                organizationAdapter.onRefresh(staffsList, subOrgsList);

                //更新组织路径
                String[] split = result1.getDeepOrgNames().split(">");
                myLinearlayout.removeAllViews();
                for(int index=1;index<split.length;index++){
                    TextView textView = new TextView(OrganizationActivity.this);
                    textView.setText(" > " + split[index]);
                    myLinearlayout.addView(textView);
                    //路劲导航条点击事件
                    textView.setOnClickListener(v -> {
                        int Vindex=myLinearlayout.indexOfChild(v);//获取当前点击view的下标
                        for (int i = myLinearlayout.getChildCount() - 1; i > Vindex; i--) {
                            myLinearlayout.removeViewAt(i);
                        }

                        for (int i = 0; i < myLinearlayout.getChildCount(); i++) {
                            if (i == myLinearlayout.getChildCount() - 1) {
                                TextView childAt = (TextView) myLinearlayout.getChildAt(i);
                                childAt.setTextColor(Color.parseColor("#282828"));
                                titleName.setText(split[Vindex + 1]);//设置标题
                            } else {
                                TextView childAt = (TextView) myLinearlayout.getChildAt(i);
                                childAt.setTextColor(Color.parseColor("#3189f4"));
                            }
                        }
                        String[] split1 = result1.getDeepOrgIds().split(">");
                        ContactsPresenter Presenter = new ContactsPresenter(OrganizationActivity.this);
                        Presenter.getAddressOrgQuery(Integer.parseInt(split1[Vindex+1]), new CommonPostView<ChildInstitutionsBean>() {
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

                for (int i = 0; i < myLinearlayout.getChildCount(); i++) {
                    if (i == myLinearlayout.getChildCount() - 1) {
                        TextView childAt = (TextView) myLinearlayout.getChildAt(i);
                        childAt.setTextColor(Color.parseColor("#282828"));
                    } else {
                        TextView childAt = (TextView) myLinearlayout.getChildAt(i);
                        childAt.setTextColor(Color.parseColor("#3189f4"));
                    }
                }

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

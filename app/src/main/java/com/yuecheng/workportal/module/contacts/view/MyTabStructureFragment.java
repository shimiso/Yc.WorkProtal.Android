package com.yuecheng.workportal.module.contacts.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuecheng.workportal.base.BaseFragment;
import com.yuecheng.workportal.bean.MessageEvent;
import com.yuecheng.workportal.module.contacts.bean.OrganizationBean;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.module.contacts.presenter.ContactsPresenter;
import com.yuecheng.workportal.module.contacts.bean.ChildInstitutionsBean;
import com.yuecheng.workportal.module.contacts.bean.OrganizationBean;
import com.yuecheng.workportal.module.contacts.adapter.OrganizationAdapter;
import com.yuecheng.workportal.utils.LoadViewUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by huochangsheng on 2018/8/30.
 */

public class MyTabStructureFragment extends BaseFragment {

    @BindView(R.id.my_contacter_recyclerView)
    RecyclerView myContacterRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.my_linearlayout)
    LinearLayout myLinearlayout;
    private View view;
    private OrganizationAdapter organizationAdapter;
    private List<OrganizationBean.OrgsBean.StaffsBean> staffsList;
    private List<OrganizationBean.OrgsBean.SubOrgsBean> subOrgsList;
    private List<OrganizationBean.OrgsBean> orgs;
    private String selectname;
    private LoadViewUtil viewUtil;
    private int orgid = -1;
    private OrganizationBean result;

    public static MyTabStructureFragment newInstance() {
        Bundle args = new Bundle();
        MyTabStructureFragment fragment = new MyTabStructureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contacts_tab_structure, container, false);
        unbinder = ButterKnife.bind(this, view);
        viewUtil = LoadViewUtil.init(view, getContext());

        init();
        initEvent();
        loadData();
        return view;
    }

    private void init() {
        //设置RecyclerView管理器
        myContacterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        organizationAdapter = new OrganizationAdapter(getContext());
        //设置添加或删除item时的动画，这里使用默认动画
        myContacterRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        myContacterRecyclerView.setAdapter(organizationAdapter);
    }

    private void initEvent() {
        //条目点击事件
        organizationAdapter.setOnRecyclerViewItemClickLintemet(position -> {
            if (subOrgsList != null && position < subOrgsList.size()) {
                Intent intent = new Intent(getContext(), OrganizationActivity.class);
                intent.putExtra("orgid",subOrgsList.get(position).getOrgId());
                intent.putExtra("orgname",selectname);
                startActivity(intent);


            } else {
                if (subOrgsList != null && position >= subOrgsList.size()) {
                    //条目点击
                    Intent intent = new Intent(getContext(), InformationActivity.class);
                    intent.putExtra("StaffId", staffsList.get(position - subOrgsList.size()).getStaffId());
                    intent.putExtra("name", staffsList.get(position - subOrgsList.size()).getName());
                    startActivity(intent);
                }
            }
        });
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
        getData();
    }
    //获取组织架构数据
    public void getData() {
        ContactsPresenter contactsPresenter = new ContactsPresenter(getActivity());
        //参数一是登录人员的id
        contactsPresenter.getAddressTopOrgQuery(new CommonPostView<OrganizationBean>(){

            @Override
            public void postSuccess(ResultInfo<OrganizationBean> resultInfo) {
                if (resultInfo.isSuccess()) {
                    result = resultInfo.getResult();
                    if(orgid==-1){
                        orgid = result.getMyselfTopOrgId();
                    }
                    orgs = result.getOrgs();
                    if(orgs == null) return;
                    OrganizationBean.OrgsBean orgsBean = null;
                    for(int i=0;i<orgs.size();i++){
                        if(orgid == orgs.get(i).getOrgId()){
                            orgsBean = orgs.get(i);
                        }

                    }
                    if(orgsBean==null){
                        myLinearlayout.removeAllViews();
                        viewUtil.stopLoading();
                        viewUtil.showLoadingErrorView(LoadViewUtil.LOADING_EMPTY_VIEW, () -> {
                            viewUtil.startLoading();
                            loadData();
                        });
                        return;
                    }else{
                        viewUtil.clearErrorView();
                    }
                    selectname=orgsBean.getOrgName(); //获取当前机构name
                    //根目录下人员
                    staffsList = orgsBean.getStaffs();
                    //根目录下部门
                    subOrgsList = orgsBean.getSubOrgs();

                    myLinearlayout.removeAllViews();
                    TextView textView = new TextView(getContext());
                    textView.setText(orgsBean.getOrgName());
                    textView.setTextColor(Color.parseColor("#282828"));
                    myLinearlayout.addView(textView);
                    if (staffsList != null && staffsList.size() > 0||
                            subOrgsList != null && subOrgsList.size() > 0   ) {
                        organizationAdapter.onRefresh(staffsList, subOrgsList);
                    }
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

    @OnClick(R.id.switch_img_iv)
    public void onViewClicked() { //点击跳转事件

        Intent intent = new Intent(getContext(), SelectGroupActivity.class);
        intent.putExtra("orgs", (Serializable) orgs);//传入当前的组织
        intent.putExtra("myselfTopOrgId", orgid);//传入当前的组织
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null) return;
        int selectorgid = data.getExtras().getInt("orgid");
        orgid = selectorgid; //将选中的orgid赋值
        viewUtil.startLoading();
        loadData();
        EventBus.getDefault().post(new MessageEvent(MessageEvent.REFRESH_STAFF, (Integer)orgid));
    }
}

package com.yuecheng.workprotal.module.contacts.view;

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

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.bean.ResultInfo;
import com.yuecheng.workprotal.common.CommonPostView;
import com.yuecheng.workprotal.module.contacts.presenter.ContactsPresenter;
import com.yuecheng.workprotal.module.contacts.bean.ChildInstitutionsBean;
import com.yuecheng.workprotal.module.contacts.bean.OrganizationBean;
import com.yuecheng.workprotal.module.contacts.adapter.OrganizationAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by huochangsheng on 2018/8/30.
 */

public class MyTabStructureFragment extends Fragment implements CommonPostView<OrganizationBean> {

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
        ContactsPresenter contactsPresenter = new ContactsPresenter(getActivity());
        //参数一是登录人员的id
        contactsPresenter.getAddressTopOrgQuery(11, this);

        //设置RecyclerView管理器
        myContacterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        organizationAdapter = new OrganizationAdapter(getContext());
        //设置添加或删除item时的动画，这里使用默认动画
        myContacterRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        myContacterRecyclerView.setAdapter(organizationAdapter);

        //条目点击事件
        organizationAdapter.setOnRecyclerViewItemClickLintemet(position -> {
            if (subOrgsList != null && position < subOrgsList.size()) {
                ContactsPresenter Presenter = new ContactsPresenter(getActivity());
                Presenter.getAddressOrgQuery(subOrgsList.get(position).getOrgId(), new CommonPostView<ChildInstitutionsBean>() {
                    @Override
                    public void postSuccess(ResultInfo<ChildInstitutionsBean> resultInfo) {

                        ChildInstitutionsBean result1 = resultInfo.getResult();
                        ChildInstitutionsBean.OrgsBean result = result1.getOrgs().get(0);
                        subOrgsList = result.getSubOrgs();
                        staffsList = result.getStaffs();
                        organizationAdapter.onRefresh(staffsList, subOrgsList);

                        TextView textView = new TextView(getContext());
                        String[] split = result1.getDeepOrgNames().split(">");
                        textView.setText(" > " + split[split.length - 1]);
                        myLinearlayout.addView(textView);
                        textView.setOnClickListener(v -> {
                            for(int i=myLinearlayout.getChildCount()-1;i>=split.length;i--){

                                if(i == myLinearlayout.getChildCount()-1){
                                    TextView childAt = (TextView)myLinearlayout.getChildAt(i);
                                    childAt.setTextColor(Color.parseColor("#000000"));
                                }else{
                                    TextView childAt = (TextView)myLinearlayout.getChildAt(i);
                                    childAt.setTextColor(Color.parseColor("#3189f4"));
                                }

                                myLinearlayout.removeViewAt(i);
                            }

                            Toast.makeText(getContext(),result1.getDeepOrgNames(),Toast.LENGTH_SHORT).show();
                            String[] split1 = result1.getDeepOrgIds().split(">");
                            ContactsPresenter Presenter = new ContactsPresenter(getActivity());
                            Presenter.getAddressOrgQuery(Integer.parseInt(split1[split1.length-1]), new CommonPostView<ChildInstitutionsBean>() {
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
                                    Toast.makeText(getContext(),"服务器发生未知异常",Toast.LENGTH_SHORT).show();
                                }
                            });

                        });
                    }

                    @Override
                    public void postError(String errorMsg) {
                        Toast.makeText(getContext(),"服务器发生未知异常",Toast.LENGTH_SHORT).show();
                    }
                });
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


        return view;
    }

    @Override
    public void postSuccess(ResultInfo<OrganizationBean> resultInfo) {
        if (resultInfo.isSuccess()) {
            OrganizationBean result = resultInfo.getResult();
            orgs = result.getOrgs();
            OrganizationBean.OrgsBean orgsBean = orgs.get(0);
            //根目录下人员
            staffsList = orgsBean.getStaffs();
            //根目录下部门
            subOrgsList = orgsBean.getSubOrgs();

            TextView textView = new TextView(getContext());
            textView.setText(orgsBean.getOrgName());
            myLinearlayout.addView(textView);
            organizationAdapter.onRefresh(staffsList, subOrgsList);
            textView.setOnClickListener(v -> {
                myLinearlayout.removeAllViews();
                ContactsPresenter contactsPresenter = new ContactsPresenter(getActivity());
                //参数一是登录人员的id
                contactsPresenter.getAddressTopOrgQuery(11, this);
            });
        }
    }

    @Override
    public void postError(String errorMsg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.switch_img_iv)
    public void onViewClicked() { //点击跳转事件

        Intent intent = new Intent(getContext(), SelectGroupActivity.class);
        intent.putExtra("selectname",orgs.get(0).getOrgName());//传入当前的组织
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null) return;
        String selectname = data.getExtras().getString("selectname");
        switch (selectname) {
            case "yuecheng":
                RefreshInstitutions("乐成集团");
                break;
            case "bcis":
                RefreshInstitutions("BCIS");
                break;
            case "laonian":
                RefreshInstitutions("恭和苑");
                break;
        }
    }

    private void RefreshInstitutions(String string) {
        for(int i=0;i<orgs.size();i++){
            OrganizationBean.OrgsBean orgsBean = orgs.get(i);
            if(orgsBean.getOrgName().equals(string)){
               //根目录下人员
               staffsList = orgsBean.getStaffs();
               //根目录下部门
               subOrgsList = orgsBean.getSubOrgs();
               myLinearlayout.removeAllViews();
               TextView textView = new TextView(getContext());
               textView.setText(orgsBean.getOrgName());
               myLinearlayout.addView(textView);
               organizationAdapter.onRefresh(staffsList, subOrgsList);
           }
        }
    }
}

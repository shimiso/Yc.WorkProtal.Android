package com.yuecheng.workportal.module.workbench;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseFragment;
import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.module.robot.OpenH5Activity;
import com.yuecheng.workportal.module.workbench.bean.WorkbenchBean;
import com.yuecheng.workportal.module.workbench.presenter.WorkbenchPresenter;
import com.yuecheng.workportal.utils.LogUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by huochangsheng on 2018/8/23.
 */

public class MyWorkbenchFragment extends BaseFragment {
    Unbinder unbinder;
    private WorkbenchPresenter workbenchPresenter;

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

        workbenchPresenter = new WorkbenchPresenter(getContext());
        getWorkBenchData();
        return view;
    }

    //获取工作台数据
    private void getWorkBenchData() {
        workbenchPresenter.getGetStaffPerms("", new CommonPostView<List<WorkbenchBean>>() {

            @Override
            public void postSuccess(ResultInfo<List<WorkbenchBean>> resultInfo) {
                List<WorkbenchBean> result = resultInfo.getResult();
                for(int i=0;i<result.size();i++){
                    String sectionName = result.get(i).getSectionName();//分类
                    List<WorkbenchBean.PermsBean> perms = result.get(i).getPerms();//详细
                    for(int j=0;j<perms.size();j++){
                        String enPermName = perms.get(j).getEnPermName();//英文名
                        String permName = perms.get(j).getPermName();//中文名
                        String iconUrl = perms.get(j).getIconUrl();//图标
                        String linkAddress = perms.get(j).getLinkAddress();//链接路径
                    }
                }
            }

            @Override
            public void postError(String errorMsg) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.dbgz, R.id.fqbd, R.id.xt, R.id.fqxt, R.id.xjhy, R.id.cxhy, R.id.sjap, R.id.dhys, R.id.qyyx, R.id.itfw, R.id.wdsc, R.id.qyzd, R.id.wdzx, R.id.xw, R.id.gg, R.id.dc, R.id.oms})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dbgz:
                Toast.makeText(getContext(),"dbgz",Toast.LENGTH_SHORT).show();
                break;
            case R.id.fqbd:
                Toast.makeText(getContext(),"dbgz",Toast.LENGTH_SHORT).show();
                break;
            case R.id.xt:
                Toast.makeText(getContext(),"dbgz",Toast.LENGTH_SHORT).show();
                break;
            case R.id.fqxt:
                Toast.makeText(getContext(),"dbgz",Toast.LENGTH_SHORT).show();
                break;
            case R.id.xjhy:
                Toast.makeText(getContext(),"xjhy",Toast.LENGTH_SHORT).show();
                break;
            case R.id.cxhy:
                Toast.makeText(getContext(),"cxhy",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sjap:
                Toast.makeText(getContext(),"sjap",Toast.LENGTH_SHORT).show();
                break;
            case R.id.dhys:
                Toast.makeText(getContext(),"dhys",Toast.LENGTH_SHORT).show();
                break;
            case R.id.qyyx:
                Toast.makeText(getContext(),"qyyx",Toast.LENGTH_SHORT).show();
                break;
            case R.id.itfw:
                Toast.makeText(getContext(),"itfw",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wdsc:
                Toast.makeText(getContext(),"wdsc",Toast.LENGTH_SHORT).show();
                break;
            case R.id.qyzd:
                Toast.makeText(getContext(),"qyzd",Toast.LENGTH_SHORT).show();
                break;
            case R.id.wdzx:
                Toast.makeText(getContext(),"wdzx",Toast.LENGTH_SHORT).show();
                break;
            case R.id.xw:
                Toast.makeText(getContext(),"xw",Toast.LENGTH_SHORT).show();
                break;
            case R.id.gg:
                Toast.makeText(getContext(),"gg",Toast.LENGTH_SHORT).show();
                break;
            case R.id.dc:
                Toast.makeText(getContext(),"dc",Toast.LENGTH_SHORT).show();
                break;
            case R.id.oms:
                String url = "http://omstest.gongheyuan.com/portal/#/CarePlan";
                Intent intent1 = new Intent(getContext(), OpenH5Activity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                intent1.putExtra("url",url);
//                    intent1.putExtra("name",name);
                getContext().startActivity(intent1);
                break;
        }
    }
}

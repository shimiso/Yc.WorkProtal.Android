package com.yuecheng.workportal.module.contacts.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luck.picture.lib.entity.LocalMedia;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.module.contacts.bean.PersonnelDetailsBean;
import com.yuecheng.workportal.module.contacts.presenter.ContactsPresenter;
import com.yuecheng.workportal.utils.LoadViewUtil;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;


public class InformationActivity extends BaseActivity implements CommonPostView<PersonnelDetailsBean> {

    @BindView(R.id.my_work_number_tv)
    TextView myWorkNumberTv;
    @BindView(R.id.my_phone_tv)
    TextView myPhoneTv;
    @BindView(R.id.my_landline_tv)
    TextView myLandlineTv;
    @BindView(R.id.my_email_tv)
    TextView myEmailTv;
    @BindView(R.id.my_jobs_tv)
    TextView myJobsTv;
    @BindView(R.id.my_deputy_tv)
    TextView myDeputyTv;
    @BindView(R.id.my_mmediate_superior_tv)
    TextView myMmediateSuperiorTv;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.head)
    RelativeLayout head;
    @BindView(R.id.contact_ren_name)
    TextView contactRenName;
    @BindView(R.id.contact_ren_jobs)
    TextView contactRenJobs;
    Context context;
    @BindView(R.id.my_subordinates_tv)
    TextView mySubordinatesTv;
    @BindView(R.id.isboy)
    ImageView isboy;
    @BindView(R.id.my_directory_ll)
    LinearLayout myDirectoryLl;
    @BindView(R.id.hor_scrollview)
    HorizontalScrollView horScrollview;
    @BindView(R.id.hor_scrollview_tv)
    HorizontalScrollView horScrollviewTv;
    @BindView(R.id.hor_scrollview_jobs)
    HorizontalScrollView horScrollviewJobs;

    private List<LocalMedia> selectList = new ArrayList<>();
    PersonnelDetailsBean personnelDetailsBean;
    //    private LoadingDialog loadingDialog;
    protected LoadViewUtil viewUtil;
    private PersonnelDetailsBean.DirectSupervisorBean directSupervisor;//直属上级
    private String guid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_information);
        viewUtil = LoadViewUtil.init(getWindow().getDecorView(), this);
        ButterKnife.bind(this);
        context = this;
        //去除到头使阴影
        horScrollview.setOverScrollMode(View.OVER_SCROLL_NEVER);
        horScrollviewTv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        horScrollviewJobs.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //水平方向的水平滚动条是否显示
        horScrollview.setHorizontalScrollBarEnabled(false);
        horScrollviewTv.setHorizontalScrollBarEnabled(false);
        horScrollviewJobs.setHorizontalScrollBarEnabled(false);
        Intent intent = getIntent();
        guid = intent.getStringExtra("Guid");
        String name = intent.getStringExtra("name");
        titleName.setText(name);
        if (guid != null) {
            loadData();
        }

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
        ContactsPresenter contactsPresenter = new ContactsPresenter(this);
        contactsPresenter.getContactInformation(guid, this);
    }


    @OnClick({R.id.back_iv, R.id.my_phone_tv, R.id.my_landline_tv, R.id.my_email_tv, R.id.to_chat, R.id.share, R.id.my_mmediate_superior_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.my_phone_tv: //电话
            case R.id.my_landline_tv:
                myCall(view);
                break;
            case R.id.my_email_tv://邮件
                Intent data = new Intent(Intent.ACTION_SENDTO);
                data.setData(Uri.parse("mailto:" + personnelDetailsBean.getEmail()));
                context.startActivity(data);
                break;
            case R.id.share://分享
                if(personnelDetailsBean==null)return;
                Bitmap mBitmap = mainApplication.getViewBitmap(this,personnelDetailsBean.getName(),//姓名
                        personnelDetailsBean.getPositionName(),//岗位
                        personnelDetailsBean.getMobilePhone(),//手机
                        personnelDetailsBean.getTelephone(),//座机
                        personnelDetailsBean.getEmail());//邮件
                mainApplication.myShare(this, mBitmap);//参数暂时为activity,后续待添加分享内容
                break;
            case R.id.to_chat://IM聊天
                if (RongIM.getInstance() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("targetName", personnelDetailsBean.getName());
                    bundle.putString("targetIcon", "http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png");
                    /**
                     * 启动单聊界面。
                     * @param context      应用上下文。
                     * @param targetUserId 要与之聊天的用户 Id。
                     * @param title        聊天的标题，开发者需要在聊天界面通过 intent.getData().getQueryParameter("title")
                     *                     获取该值, 再手动设置为聊天界面的标题。
                     */
                    RongIM.getInstance().startConversation(context, Conversation.ConversationType.PRIVATE, personnelDetailsBean.getGuid() + "", "与" + personnelDetailsBean.getName() + "对话", bundle);
                }
                break;
            case R.id.my_mmediate_superior_tv://直属上级
                titleName.setText(directSupervisor.getName());
                guid = directSupervisor.getGuid();
                if (guid != null) {
                    loadData();
                }
                break;
        }
    }


    //打电话
    private void myCall(View view) {
        String number;
        if (view.getId() == R.id.my_phone_tv) {
            number = myPhoneTv.getText().toString();
        } else {
            number = myLandlineTv.getText().toString();
        }
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void postSuccess(ResultInfo<PersonnelDetailsBean> resultInfo) {
        if (resultInfo.isSuccess()) {
            personnelDetailsBean = resultInfo.getResult();
            initView();
            initEvent();
            viewUtil.stopLoading();
        }
    }

    private void initEvent() {
        //下级员工
        List<PersonnelDetailsBean.SubordinatesBean> subordinates = personnelDetailsBean.getSubordinates();
        if (subordinates == null || subordinates.size() <= 0) {
            mySubordinatesTv.setText("无");
            mySubordinatesTv.setTextColor(Color.parseColor("#595959"));
            mySubordinatesTv.setClickable(false);
        } else if (subordinates.size() == 1) {
            mySubordinatesTv.setClickable(true);
            mySubordinatesTv.setText(subordinates.get(0).getName());
            mySubordinatesTv.setTextColor(Color.parseColor("#509FFF"));
            mySubordinatesTv.setOnClickListener(v -> {
                titleName.setText(subordinates.get(0).getName());
                guid = subordinates.get(0).getGuid();
                if (guid != null) {
                    loadData();
                }
            });
        } else if (subordinates.size() > 1) {
            mySubordinatesTv.setClickable(true);
            mySubordinatesTv.setText(subordinates.size() + "人>");
            mySubordinatesTv.setTextColor(Color.parseColor("#509FFF"));
            mySubordinatesTv.setOnClickListener(v -> {
                Intent intent = new Intent(this, SubordinatesActivity.class);
                intent.putExtra("subordinates", (Serializable) subordinates);
                startActivity(intent);
            });
        }

    }

    private void initView() {
        isboy.setImageResource(personnelDetailsBean.getGender() == 1 ? R.mipmap.boy : R.mipmap.girl);//性别
        contactRenName.setText(personnelDetailsBean.getName());//name
        myWorkNumberTv.setText(personnelDetailsBean.getCode());//工号
        myPhoneTv.setText(personnelDetailsBean.getMobilePhone());//手机号
        myLandlineTv.setText(personnelDetailsBean.getTelephone());//座机
        myEmailTv.setText(personnelDetailsBean.getEmail());//邮件
        myJobsTv.setText(personnelDetailsBean.getPositionName()+"("+personnelDetailsBean.getOrgCode()+")");//岗位
        contactRenJobs.setText(personnelDetailsBean.getPositionName());//岗位
//        myDirectoryTv.setText(personnelDetailsBean.getOrganizationName()); //组织路径
        myDeputyTv.setText(personnelDetailsBean.getPartTimeJobs().isEmpty() ? "无" : personnelDetailsBean.getPartTimeJobs());//副岗
        //直属上级
        directSupervisor = personnelDetailsBean.getDirectSupervisor();
        myMmediateSuperiorTv.setText(directSupervisor != null ? directSupervisor.getName() : "");//直属上级
        //设置颜色
        myMmediateSuperiorTv.setTextColor(Color.parseColor("#509FFF"));
        myPhoneTv.setTextColor(Color.parseColor("#509FFF"));
        myLandlineTv.setTextColor(Color.parseColor("#509FFF"));
        myEmailTv.setTextColor(Color.parseColor("#509FFF"));

        String[] split = personnelDetailsBean.getOrganizationName().split(">");
        myDirectoryLl.removeAllViews();
        for (int index = 0; index < split.length; index++) {
            TextView textView = new TextView(InformationActivity.this);
            textView.setText(index == 0 ? split[index] : " > " + split[index]);
            textView.setTextColor(Color.parseColor("#3189f4"));
            myDirectoryLl.addView(textView);

            textView.setOnClickListener(v -> {
                int Vindex = myDirectoryLl.indexOfChild(v);//获取当前点击view的下标
                String[] split1 = personnelDetailsBean.getDeepOrgIds().split(">");
                Intent intent = new Intent(this, OrganizationActivity.class);
                intent.putExtra("orgid", Integer.valueOf(split1[Vindex]));
                intent.putExtra("orgname", split[0]);
                startActivity(intent);
            });
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
}

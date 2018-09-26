package com.yuecheng.workportal.module.contacts.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luck.picture.lib.entity.LocalMedia;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.module.contacts.bean.PersonnelDetailsBean;
import com.yuecheng.workportal.module.contacts.presenter.ContactsPresenter;
import com.yuecheng.workportal.utils.ToastUtil;
import com.yuecheng.workportal.widget.LoadingDialog;

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
    @BindView(R.id.my_directory_tv)
    TextView myDirectoryTv;
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

    private List<LocalMedia> selectList = new ArrayList<>();
    PersonnelDetailsBean personnelDetailsBean;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_information);
        ButterKnife.bind(this);
        context = this;
        Intent intent = getIntent();
        String code = intent.getStringExtra("Code");
        String name = intent.getStringExtra("name");
        titleName.setText(name);
        loadingDialog = LoadingDialog.createDialog(this);
        loadingDialog.setMessage("请求网络中...");
        loadingDialog.show();
        if (code != null) {
            ContactsPresenter contactsPresenter = new ContactsPresenter(this);
            contactsPresenter.getContactInformation(code, this);
        }

    }



    @OnClick({R.id.back_iv, R.id.my_phone_tv, R.id.my_landline_tv, R.id.my_email_tv, R.id.to_chat, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.my_phone_tv: //电话
            case R.id.my_landline_tv:
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
                break;
            case R.id.my_email_tv://邮件

                break;
            case R.id.share://分享
                ShareDialog shareDialog = new ShareDialog(this);
                shareDialog.setClicklistener(new ShareDialog.ClickListenerInterface() {

                    @Override
                    public void onWXClick() {
                        shareDialog.dismissDialog();
                    }

                    @Override
                    public void onPYQClick() {

                    }

                    @Override
                    public void onQQClick() {

                    }
                });
                break;
            case R.id.to_chat://IM聊天
                if (RongIM.getInstance() != null){
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
                    RongIM.getInstance().startConversation(context, Conversation.ConversationType.PRIVATE, personnelDetailsBean.getCode()+"", "与"+personnelDetailsBean.getName()+"对话",bundle);
                }
                break;

        }
    }

    @Override
    public void postSuccess(ResultInfo<PersonnelDetailsBean> resultInfo) {
        if (resultInfo.isSuccess()) {
            personnelDetailsBean = resultInfo.getResult();
            //直属上级
            PersonnelDetailsBean.DirectSupervisorBean directSupervisor = personnelDetailsBean.getDirectSupervisor();

            contactRenName.setText(personnelDetailsBean.getName());//name
            myWorkNumberTv.setText(personnelDetailsBean.getCode());//工号
            myPhoneTv.setText(personnelDetailsBean.getMobilePhone());//手机号
            myLandlineTv.setText(personnelDetailsBean.getTelephone());//座机
            myEmailTv.setText(personnelDetailsBean.getEmail());//邮件
            myJobsTv.setText(personnelDetailsBean.getPositionName());//岗位
            contactRenJobs.setText(personnelDetailsBean.getPositionName());//岗位
            myDirectoryTv.setText(personnelDetailsBean.getOrganizationName()); //组织路径
            myDeputyTv.setText("");
            if(directSupervisor!=null){
                myMmediateSuperiorTv.setText(directSupervisor.getName());//直属上级
            }
            myPhoneTv.setTextColor(Color.parseColor("#509FFF"));
            myLandlineTv.setTextColor(Color.parseColor("#509FFF"));
            myEmailTv.setTextColor(Color.parseColor("#509FFF"));

            loadingDialog.dismiss();
        }
    }

    @Override
    public void postError(String errorMsg) {
        ToastUtil.error(InformationActivity.this,errorMsg);
        loadingDialog.dismiss();
    }
}

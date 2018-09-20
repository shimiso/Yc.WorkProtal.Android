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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_information);
        ButterKnife.bind(this);
        context = this;
        Intent intent = getIntent();
        int staffId = intent.getIntExtra("StaffId", -1);
        String name = intent.getStringExtra("name");
        titleName.setText(name);
        if (staffId != -1) {
            ContactsPresenter contactsPresenter = new ContactsPresenter(this);
            contactsPresenter.getContactInformation(staffId + "", this);
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
                if (RongIM.getInstance() != null)
                /**
                 * 启动单聊界面。
                 * @param context      应用上下文。
                 * @param targetUserId 要与之聊天的用户 Id。
                 * @param title        聊天的标题，开发者需要在聊天界面通过 intent.getData().getQueryParameter("title")
                 *                     获取该值, 再手动设置为聊天界面的标题。
                 */
                RongIM.getInstance().startPrivateChat(context, personnelDetailsBean.getId()+"", "与"+personnelDetailsBean.getName()+"对话");
                break;

        }
    }

    @Override
    public void postSuccess(ResultInfo<PersonnelDetailsBean> resultInfo) {
        if (resultInfo.isSuccess()) {
            personnelDetailsBean = resultInfo.getResult();
            contactRenName.setText(personnelDetailsBean.getName());
            myWorkNumberTv.setText(personnelDetailsBean.getCode());
            myPhoneTv.setText(personnelDetailsBean.getMobilePhone());
            myLandlineTv.setText(personnelDetailsBean.getTelephone());
            myEmailTv.setText(personnelDetailsBean.getEmail());
            myJobsTv.setText(personnelDetailsBean.getPositionName());
            contactRenJobs.setText(personnelDetailsBean.getPositionName());
            myDirectoryTv.setText(personnelDetailsBean.getOrganizationName());
            myDeputyTv.setText("");
//            myMmediateSuperiorTv.setText(personnelDetailsBean.getDirectSupervisor());
            myPhoneTv.setTextColor(Color.parseColor("#509FFF"));
            myLandlineTv.setTextColor(Color.parseColor("#509FFF"));
            myEmailTv.setTextColor(Color.parseColor("#509FFF"));
        }
    }

    @Override
    public void postError(String errorMsg) {

    }
}

package com.yuecheng.workprotal.module.contacts.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luck.picture.lib.entity.LocalMedia;
import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.base.BaseActivity;
import com.yuecheng.workprotal.bean.ResultInfo;
import com.yuecheng.workprotal.common.CommonPostView;
import com.yuecheng.workprotal.module.contacts.presenter.ContactsPresenter;
import com.yuecheng.workprotal.module.contacts.bean.PersonnelDetailsBean;
import com.yuecheng.workprotal.module.mycenter.view.LanguageSettingsDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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

    private List<LocalMedia> selectList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_information);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        int staffId = intent.getIntExtra("StaffId", -1);
        String name = intent.getStringExtra("name");
        titleName.setText(name);
        if (staffId != -1) {
            ContactsPresenter contactsPresenter = new ContactsPresenter(this);
            contactsPresenter.getContactInformation(staffId + "", this);
        }

    }



    @OnClick({R.id.back_iv, R.id.my_phone_tv, R.id.my_landline_tv, R.id.my_email_tv, R.id.my_share_img, R.id.share})
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
            case R.id.my_share_img://IM聊天

                break;

        }
    }

    @Override
    public void postSuccess(ResultInfo<PersonnelDetailsBean> resultInfo) {
        if (resultInfo.isSuccess()) {
            PersonnelDetailsBean result = resultInfo.getResult();
            contactRenName.setText(result.getName());
            myWorkNumberTv.setText(result.getCode());
            myPhoneTv.setText(result.getMobilePhone());
            myLandlineTv.setText(result.getTelephone());
            myEmailTv.setText(result.getEmail());
            myJobsTv.setText(result.getPositionName());
            contactRenJobs.setText(result.getPositionName());
            myDirectoryTv.setText(result.getOrganizationName());
            myDeputyTv.setText("");
            myMmediateSuperiorTv.setText(result.getDirectSupervisor());
            myPhoneTv.setTextColor(Color.parseColor("#509FFF"));
            myLandlineTv.setTextColor(Color.parseColor("#509FFF"));
            myEmailTv.setTextColor(Color.parseColor("#509FFF"));
        }
    }

    @Override
    public void postError(String errorMsg) {

    }
}

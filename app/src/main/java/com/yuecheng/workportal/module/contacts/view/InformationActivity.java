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
import android.widget.Toast;

import com.luck.picture.lib.entity.LocalMedia;
import com.tencent.connect.share.QQShare;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.module.contacts.bean.PersonnelDetailsBean;
import com.yuecheng.workportal.module.contacts.presenter.ContactsPresenter;
import com.yuecheng.workportal.utils.ToastUtil;
import com.yuecheng.workportal.widget.LoadingDialog;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.utils.SystemUtils;
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
    @BindView(R.id.my_subordinates_tv)
    TextView mySubordinatesTv;

    private List<LocalMedia> selectList = new ArrayList<>();
    PersonnelDetailsBean personnelDetailsBean;
    private LoadingDialog loadingDialog;
    private PersonnelDetailsBean.DirectSupervisorBean directSupervisor;//直属上级

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_information);
        ButterKnife.bind(this);
        context = this;
        Intent intent = getIntent();
        String guid = intent.getStringExtra("Guid");
        String name = intent.getStringExtra("name");
        titleName.setText(name);
        getInformationData(guid);

    }

    //获取网络数据
    private void getInformationData(String guid) {
        loadingDialog = LoadingDialog.createDialog(this);
        loadingDialog.setMessage("请求网络中...");
        loadingDialog.show();
        if (guid != null) {
            ContactsPresenter contactsPresenter = new ContactsPresenter(this);
            contactsPresenter.getContactInformation(guid, this);
        }
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

                break;
            case R.id.share://分享
                myShare();
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
                getInformationData(directSupervisor.getGuid());
                break;
        }
    }

    //分享
    private void myShare() {
        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.setClicklistener(new ShareDialog.ClickListenerInterface() {

            @Override
            public void onWXClick() {
                new ShareAction(InformationActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN.toSnsPlatform().mPlatform)//传入平台
                        .withText("hello")//分享内容
                        .setCallback(shareListener)//回调监听器
                        .share();
                shareDialog.dismissDialog();
            }

            @Override
            public void onPYQClick() {
                new ShareAction(InformationActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform().mPlatform)//传入平台
                        .withText("hello")//分享内容
                        .setCallback(shareListener)//回调监听器
                        .share();
                shareDialog.dismissDialog();
            }

            @Override
            public void onQQClick() {
                UMImage imagelocal = new UMImage(InformationActivity.this, R.mipmap.dbgz);
                imagelocal.setThumb(new UMImage(InformationActivity.this, R.mipmap.dbgz));
                new ShareAction(InformationActivity.this)
                        .withMedia(imagelocal )
                        .setPlatform(SHARE_MEDIA.QQ.toSnsPlatform().mPlatform)
                        .setCallback(shareListener).share();
                shareDialog.dismissDialog();
            }
        });
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

            contactRenName.setText(personnelDetailsBean.getName());//name
            myWorkNumberTv.setText(personnelDetailsBean.getCode());//工号
            myPhoneTv.setText(personnelDetailsBean.getMobilePhone());//手机号
            myLandlineTv.setText(personnelDetailsBean.getTelephone());//座机
            myEmailTv.setText(personnelDetailsBean.getEmail());//邮件
            myJobsTv.setText(personnelDetailsBean.getPositionName());//岗位
            contactRenJobs.setText(personnelDetailsBean.getPositionName());//岗位
            myDirectoryTv.setText(personnelDetailsBean.getOrganizationName()); //组织路径
            if(personnelDetailsBean.getPartTimeJobs().isEmpty()){
                myDeputyTv.setText("无");//副岗
            }else{
                myDeputyTv.setText(personnelDetailsBean.getPartTimeJobs());//副岗
            }

            //直属上级
            directSupervisor = personnelDetailsBean.getDirectSupervisor();
            if (directSupervisor != null) {
                myMmediateSuperiorTv.setText(directSupervisor.getName());//直属上级
            } else {
                myMmediateSuperiorTv.setText("");//直属上级
            }
            //下级员工
            List<PersonnelDetailsBean.SubordinatesBean> subordinates = personnelDetailsBean.getSubordinates();
            if (subordinates == null || subordinates.size() <= 0) {
                mySubordinatesTv.setText("无");
            }else if(subordinates.size() == 1){
                mySubordinatesTv.setText(subordinates.get(0).getName());
                mySubordinatesTv.setTextColor(Color.parseColor("#509FFF"));
                mySubordinatesTv.setOnClickListener(v -> {
                    titleName.setText(subordinates.get(0).getName());
                    getInformationData(subordinates.get(0).getGuid());
                });
            }else if(subordinates.size() > 1){
                mySubordinatesTv.setText(subordinates.size()+"人>");
                mySubordinatesTv.setTextColor(Color.parseColor("#509FFF"));
                mySubordinatesTv.setOnClickListener(v -> {
                    Intent intent  = new Intent(this,SubordinatesActivity.class);
                    intent.putExtra("subordinates", (Serializable) subordinates);
                    startActivity(intent);
                });
            }
            myMmediateSuperiorTv.setTextColor(Color.parseColor("#509FFF"));
            myPhoneTv.setTextColor(Color.parseColor("#509FFF"));
            myLandlineTv.setTextColor(Color.parseColor("#509FFF"));
            myEmailTv.setTextColor(Color.parseColor("#509FFF"));

            loadingDialog.dismiss();
        }
    }

    @Override
    public void postError(String errorMsg) {
        ToastUtil.error(InformationActivity.this, errorMsg);
        loadingDialog.dismiss();
    }

    //分享回调
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(InformationActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(InformationActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(InformationActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };

    //QQ分享使用
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}

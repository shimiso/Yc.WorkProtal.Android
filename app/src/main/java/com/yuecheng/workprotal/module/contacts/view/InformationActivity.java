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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.base.BaseActivity;
import com.yuecheng.workprotal.bean.ResultInfo;
import com.yuecheng.workprotal.common.CommonPostView;
import com.yuecheng.workprotal.module.contacts.presenter.ContactsPresenter;
import com.yuecheng.workprotal.module.contacts.quicksearch.Bean.PersonnelDetailsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class InformationActivity extends BaseActivity implements CommonPostView<PersonnelDetailsBean> {

    @BindView(R.id.my_portrait_img)
    ImageView my_portrait_img;
    @BindView(R.id.my_name_tv)
    TextView myNameTv;
    @BindView(R.id.my_gen_tv)
    TextView myGenTv;
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
    @BindView(R.id.my_portrait)
    RelativeLayout myPortrait;
    @BindView(R.id.my_name)
    RelativeLayout myName;
    @BindView(R.id.my_gen)
    RelativeLayout myGen;
    @BindView(R.id.share)
    ImageView share;
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
        if (staffId != -1) {
            ContactsPresenter contactsPresenter = new ContactsPresenter(this);
            contactsPresenter.getContactInformation(staffId + "", this);
        }
        if (name != null) {
            titleName.setText(name);
            head.setVisibility(View.VISIBLE);
            share.setVisibility(View.VISIBLE);
            myPortrait.setVisibility(View.GONE);
            myName.setVisibility(View.GONE);
            myGen.setVisibility(View.GONE);
        } else {
            titleName.setText("个人信息");
            head.setVisibility(View.GONE);
            share.setVisibility(View.GONE);
            myPortrait.setVisibility(View.VISIBLE);
            myName.setVisibility(View.VISIBLE);
            myGen.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    selectList = PictureSelector.obtainMultipleResult(data);
                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.ic_camera)
                            .error(R.drawable.ic_camera)
                            .diskCacheStrategy(DiskCacheStrategy.ALL);
                    LocalMedia media = selectList.get(0);
                    Glide.with(this)
                            .load(media.getPath())
                            .apply(options)
                            .into(my_portrait_img);
                    PictureFileUtils.deleteCacheDirFile(this);
                    break;
            }
        }
    }

    @OnClick({R.id.back_iv, R.id.my_portrait, R.id.my_phone_tv, R.id.my_landline_tv, R.id.my_email_tv})
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
            case R.id.my_portrait:
                // 进入相册 以下是例子：用不到的api可以不写
                PictureSelector.create(InformationActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                        .maxSelectNum(1)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                        .enableCrop(false)// 是否裁剪
                        .compress(false)// 是否压缩
                        .isGif(true)// 是否显示gif图片
                        .openClickSound(true)// 是否开启点击声音
                        .selectionMedia(selectList)// 是否传入已选图片
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                break;
        }
    }

    @Override
    public void postSuccess(ResultInfo<PersonnelDetailsBean> resultInfo) {
        if (resultInfo.getResult().isSuccess()) {
            PersonnelDetailsBean.ResultBean result = resultInfo.getResult().getResult();
            myNameTv.setText(result.getName());
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
            if (result.getGender() == 1) {
                myGenTv.setText("男");
            } else {
                myGenTv.setText("女");
            }
            myPhoneTv.setTextColor(Color.parseColor("#509FFF"));
            myLandlineTv.setTextColor(Color.parseColor("#509FFF"));
            myEmailTv.setTextColor(Color.parseColor("#509FFF"));
        }
    }

    @Override
    public void postError(String errorMsg) {

    }
}

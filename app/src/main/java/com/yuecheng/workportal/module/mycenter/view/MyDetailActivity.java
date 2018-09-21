package com.yuecheng.workportal.module.mycenter.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.module.contacts.bean.PersonnelDetailsBean;
import com.yuecheng.workportal.module.contacts.presenter.ContactsPresenter;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.module.contacts.bean.PersonnelDetailsBean;
import com.yuecheng.workportal.module.contacts.presenter.ContactsPresenter;
import com.yuecheng.workportal.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人详情
 */
public class MyDetailActivity extends BaseActivity implements CommonPostView<PersonnelDetailsBean> {
    Context context;
    @BindView(R.id.my_name)
    TextView myName;
    @BindView(R.id.my_gen)
    TextView myGen;
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
    private List<LocalMedia> selectList = new ArrayList<>();
    @BindView(R.id.user_head)
    CircleImageView user_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_center_detail);
        ButterKnife.bind(this);
        context = this;
        ContactsPresenter contactsPresenter = new ContactsPresenter(this);
        contactsPresenter.getContactInformation("", this);
    }

    @OnClick({R.id.back_iv, R.id.set_user_head})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_user_head://设置头像
                // 进入相册 以下是例子：用不到的api可以不写
                PictureSelector.create(MyDetailActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(R.style.picture_my_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                        .maxSelectNum(5)// 最大图片选择数量
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
            case R.id.back_iv://返回
                finish();
                break;
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
                    Glide.with(context)
                            .load(selectList.get(0).getPath())
                            .into(user_head);
                    PictureFileUtils.deleteCacheDirFile(context);
                    break;
            }
        }
    }

    @Override
    public void postSuccess(ResultInfo<PersonnelDetailsBean> resultInfo) {
        if (resultInfo.isSuccess()) {
            PersonnelDetailsBean result = resultInfo.getResult();
            myName.setText(result.getName());
            myWorkNumberTv.setText(result.getCode());
            myPhoneTv.setText(result.getMobilePhone());
            myLandlineTv.setText(result.getTelephone());
            myEmailTv.setText(result.getEmail());
            myJobsTv.setText(result.getPositionName());
            myDirectoryTv.setText(result.getOrganizationName());
            myDeputyTv.setText("");
            if(result.getGender()==1){
                myGen.setText("男");
            }else{
                myGen.setText("女");
            }

        }
    }

    @Override
    public void postError(String errorMsg) {
        ToastUtil.info(MyDetailActivity.this,errorMsg);
    }
}

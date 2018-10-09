package com.yuecheng.workportal.module.robot.handler;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.contacts.bean.ContactBean;
import com.yuecheng.workportal.module.contacts.bean.PersonnelDetailsBean;
import com.yuecheng.workportal.module.contacts.presenter.ContactsPresenter;
import com.yuecheng.workportal.module.contacts.view.InformationActivity;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.MainApplication;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.robot.action.CallAction;
import com.yuecheng.workportal.module.robot.bean.SemanticResult;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;


/**
 * 电话处理类
 */

public class ContactsHandler extends IntentHandler {

    private ContactsPresenter contactsPresenter;
    private List<ContactBean.StaffsBean> staffs;
    private String intent;

    public ContactsHandler(SemanticResult result) {
        super(result);
    }

    @Override
    public String getFormatContent() {
        if(result.semantic != null){
            JSONArray list = result.semantic.optJSONArray("slots");
            intent = result.semantic.optString("intent");
            if(list != null){
                for(int index = 0; index < list.length(); index++) {
                    JSONObject item = list.optJSONObject(index);
                    String name = item.optString("name","");
                    String namevalue = item.optString("value","");
                    switch (intent){
                        case instruction.TEXT://发短信
                            getPersonnelData(namevalue);
                            break;
                        case instruction.CALL://打电话
                            getPersonnelData(namevalue);
                            break;
                        case instruction.FIND://查找联系人
                            getPersonnelData(namevalue);
                            break;
                        case instruction.OPEN://打开主页
                            getPersonnelData(namevalue);
                            break;
                        default:
                            MainPresenter.responseAnswer(R.string.grammar_error+"");
                            break;
                    }
                }
            }
        }
        return "";
    }

    //获取人员
    private void getPersonnelData(String namevalue) {
        contactsPresenter = new ContactsPresenter(MainApplication.getApplication());
        contactsPresenter.getContact(namevalue,-1, new CommonPostView<ContactBean>() {
            @Override
            public void postSuccess(ResultInfo<ContactBean> resultInfo) {
                if(resultInfo.isSuccess()){
                    ContactBean result = resultInfo.getResult();
                    staffs = result.getStaffs();
                    if(staffs == null || staffs.size() <= 0){
                        MainPresenter.responseAnswer("没有找到当前人员");
                        return;
                    }
                    if(intent.equals(instruction.FIND) ||
                            (intent.equals(instruction.OPEN) &&  staffs.size()>1)||
                            intent.equals(instruction.CALL)||
                            intent.equals(instruction.TEXT)){
                        for(int i = 0; i< staffs.size(); i++){
                            ContactBean.StaffsBean staffsBean = staffs.get(i);
                            getInformactionData(staffsBean);//获取人员详情
                        }
                    }else if(intent.equals(instruction.OPEN) && staffs.size() == 1){ //打开主页
                        Intent detailsIntent = new Intent(MainApplication.getApplication(), InformationActivity.class);
                        detailsIntent.putExtra("Guid",staffs.get(0).getGuid());
                        detailsIntent.putExtra("name",staffs.get(0).getName());
                        detailsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MainApplication.getApplication().startActivity(detailsIntent);
                    }

                }
            }

            @Override
            public void postError(String errorMsg) {
                ToastUtil.error(MainApplication.getApplication(),errorMsg);
            }
        });
    }

    //获取人员详情
    private void getInformactionData(ContactBean.StaffsBean staffsBean) {
        contactsPresenter.getContactInformation(staffsBean.getGuid(), new CommonPostView<PersonnelDetailsBean>() {
            @Override
            public void postSuccess(ResultInfo<PersonnelDetailsBean> resultInfo) {
                if (resultInfo.isSuccess()) {
                    PersonnelDetailsBean personnelDetailsBean = resultInfo.getResult();
                    //打电话
                    if(intent.equals(instruction.CALL)  && staffs.size() == 1){
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + personnelDetailsBean.getMobilePhone()));
                        if (ActivityCompat.checkSelfPermission( MainApplication.getApplication(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MainApplication.getApplication().startActivity(intent);
                        return;
                    }else if(intent.equals(instruction.TEXT) && staffs.size() == 1 && RongIM.getInstance() != null){//发送消息
                        Bundle bundle = new Bundle();
                        bundle.putString("targetName", personnelDetailsBean.getName());
                        bundle.putString("targetIcon", "http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png");
                        RongIM.getInstance().startPrivateChat(MainApplication.getApplication(), personnelDetailsBean.getGuid()+"", "与"+personnelDetailsBean.getName()+"对话");
                        return;
                    }
                    MainPresenter.personDetailsMessage(personnelDetailsBean.getName(),personnelDetailsBean);
                }
            }

            @Override
            public void postError(String errorMsg) {
                ToastUtil.error(MainApplication.getApplication(),errorMsg);
            }
        });
    }
}

package com.yuecheng.workportal.module.robot.handler;

import android.text.TextUtils;

import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.contacts.bean.ContactBean;
import com.yuecheng.workportal.module.contacts.bean.PersonnelDetailsBean;
import com.yuecheng.workportal.module.contacts.presenter.ContactsPresenter;
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

import java.util.List;


/**
 * 电话处理类
 */

public class ContactsHandler extends IntentHandler {

    private ContactsPresenter contactsPresenter;

    public ContactsHandler(SemanticResult result) {
        super(result);
    }

    @Override
    public String getFormatContent() {
        if(result.semantic != null){
            JSONArray list = result.semantic.optJSONArray("slots");
            String intent = result.semantic.optString("intent");
            if(list != null){
                for(int index = 0; index < list.length(); index++) {
                    JSONObject item = list.optJSONObject(index);
                    String name = item.optString("name","");
                    String namevalue = item.optString("value","");
                    switch (intent){
                        case instruction.TEXT://发短信
                            MainPresenter.responseAnswer("跳转到短信页面发送短信！");
                            break;
                        case instruction.CALL://打电话
                            CallAction callAction = new CallAction(name, namevalue, MainApplication.getApplication());//目前可根据名字或电话号码拨打电话
                            callAction.start();
                            break;
                        case instruction.FIND://查找联系人
                            contactsPresenter = new ContactsPresenter(MainApplication.getApplication());
                            contactsPresenter.getContact(namevalue, new CommonPostView<ContactBean>() {
                                @Override
                                public void postSuccess(ResultInfo<ContactBean> resultInfo) {
                                    if(resultInfo.isSuccess()){
                                        ContactBean result = resultInfo.getResult();
                                        List<ContactBean.StaffsBean> staffs = result.getStaffs();
                                        if(staffs == null || staffs.size() <= 0){
                                            MainPresenter.responseAnswer("没有找到当前人员");
                                            return;
                                        }
                                        for(int i=0;i<staffs.size();i++){
                                            ContactBean.StaffsBean staffsBean = staffs.get(i);
                                            getInformactionData(staffsBean);//获取人员详情
                                        }
                                    }
                                }

                                @Override
                                public void postError(String errorMsg) {
                                    ToastUtil.error(MainApplication.getApplication(),errorMsg);
                                }
                            });
                            break;
                        case instruction.OPEN://打开主页
                           // MainPresenter.responseAnswer("跳转到通讯录查找联系人！");
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

    //获取人员详情
    private void getInformactionData(ContactBean.StaffsBean staffsBean) {
        contactsPresenter.getContactInformation(staffsBean.getGuid(), new CommonPostView<PersonnelDetailsBean>() {
            @Override
            public void postSuccess(ResultInfo<PersonnelDetailsBean> resultInfo) {
                if (resultInfo.isSuccess()) {
                    PersonnelDetailsBean personnelDetailsBean = resultInfo.getResult();
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

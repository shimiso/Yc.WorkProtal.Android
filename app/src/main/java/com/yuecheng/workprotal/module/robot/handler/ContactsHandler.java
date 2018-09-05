package com.yuecheng.workprotal.module.robot.handler;

import android.text.TextUtils;

import com.yuecheng.workprotal.MainApplication;
import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.common.instruction;
import com.yuecheng.workprotal.module.robot.action.CallAction;
import com.yuecheng.workprotal.module.robot.bean.SemanticResult;
import com.yuecheng.workprotal.module.robot.presenter.MainPresenter;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * 电话处理类
 */

public class ContactsHandler extends IntentHandler {

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
                    String code = item.optString("value","");//判断value值是手机号码还是姓名（name值可有可无）
                    switch (intent){
                        case instruction.TEXT://发短信
                            MainPresenter.responseAnswer("跳转到短信页面发送短信！");
                            break;
                        case instruction.CALL://打电话
                            CallAction callAction = new CallAction(name, code, MainApplication.getApplication());//目前可根据名字或电话号码拨打电话
                            callAction.start();
                            break;
                        case instruction.FIND://查找联系人
                            MainPresenter.responseAnswer("跳转到通讯录查找联系人！");
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
}

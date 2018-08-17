package com.yuecheng.workprotal.module.robot.handler;

import com.yuecheng.workprotal.module.robot.bean.SemanticResult;
import com.yuecheng.workprotal.module.robot.presenter.MainPresenter;


/**
 * 休假处理类
 */

public class LeaveHandler extends IntentHandler {

    public LeaveHandler(SemanticResult result) {
        super(result);
    }

    @Override
    public String getFormatContent() {
        if (result.semantic != null) {
//            JSONArray list = result.semantic.optJSONArray("slots");
            String intent = result.semantic.optString("intent");
            switch(intent) {
                case "take_leave"://请假
                    MainPresenter.responseAnswer("跳转页面到请假！");
                    break;
                case "my_leave"://我的请假
                    MainPresenter.responseAnswer("跳转页面到我的请假！");
                    break;
                default:
                    MainPresenter.responseAnswer("语义错误！");
                    break;
            }
        }
        return "";
    }
}

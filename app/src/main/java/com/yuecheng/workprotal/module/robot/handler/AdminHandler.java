package com.yuecheng.workprotal.module.robot.handler;

import com.yuecheng.workprotal.module.robot.bean.SemanticResult;
import com.yuecheng.workprotal.module.robot.presenter.MainPresenter;


/**
 * 行政处理类
 */

public class AdminHandler extends IntentHandler {

    public AdminHandler(SemanticResult result) {
        super(result);
    }

    @Override
    public String getFormatContent() {
        if (result.semantic != null) {
//            JSONArray list = result.semantic.optJSONArray("slots");
            String intent = result.semantic.optString("intent");
            switch(intent) {
                case "stamp_application"://申请盖章
                    MainPresenter.responseAnswer("跳转页面到申请盖章!");
                    break;
                case "name_card"://申请名片
                    MainPresenter.responseAnswer("跳转页面到申请名片!");
                    break;
                default:
                    MainPresenter.responseAnswer("语义错误！");
                    break;
            }
        }
        return "";
    }
}

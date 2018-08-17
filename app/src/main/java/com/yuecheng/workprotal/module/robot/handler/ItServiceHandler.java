package com.yuecheng.workprotal.module.robot.handler;

import com.yuecheng.workprotal.module.robot.bean.SemanticResult;
import com.yuecheng.workprotal.module.robot.presenter.MainPresenter;


/**
 * IT服务处理类
 */

public class ItServiceHandler extends IntentHandler {

    public ItServiceHandler(SemanticResult result) {
        super(result);
    }

    @Override
    public String getFormatContent() {
        if (result.semantic != null) {
//            JSONArray list = result.semantic.optJSONArray("slots");
            String intent = result.semantic.optString("intent");
            switch(intent) {
                case "it_service"://IT服务
                    MainPresenter.responseAnswer("跳转页面到IT服务！");
                    break;
                default:
                    MainPresenter.responseAnswer("语义错误！");
                    break;
            }
        }
        return "";
    }
}

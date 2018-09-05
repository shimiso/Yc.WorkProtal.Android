package com.yuecheng.workprotal.module.robot.handler;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.common.instruction;
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
                case instruction.IT_SERVICE://IT服务
                    MainPresenter.responseAnswer("跳转页面到IT服务！");
                    break;
                default:
                    MainPresenter.responseAnswer(R.string.grammar_error+"");
                    break;
            }
        }
        return "";
    }
}

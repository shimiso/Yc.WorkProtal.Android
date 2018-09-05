package com.yuecheng.workprotal.module.robot.handler;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.common.instruction;
import com.yuecheng.workprotal.module.robot.bean.SemanticResult;
import com.yuecheng.workprotal.module.robot.presenter.MainPresenter;


/**
 * 待办处理类
 */

public class ToDoHandler extends IntentHandler {

    public ToDoHandler(SemanticResult result) {
        super(result);
    }

    @Override
    public String getFormatContent() {
        if (result.semantic != null) {
//            JSONArray list = result.semantic.optJSONArray("slots");
            String intent = result.semantic.optString("intent");
            switch(intent) {
                case instruction.MY_TO_DO_LIST://查待办
                    MainPresenter.responseAnswer("跳转页面到待办！");
                    break;
                default:
                    MainPresenter.responseAnswer(R.string.grammar_error+"");
                    break;
            }
        }
        return "";
    }
}

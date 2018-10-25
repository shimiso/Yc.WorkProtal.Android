package com.yuecheng.workportal.module.robot.handler;

import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.robot.bean.SemanticResult;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.utils.AndroidUtil;


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
                    MainPresenter.responseAnswer(AndroidUtil.getString(R.string.grammar_error));
                    break;
            }
        }
        return "";
    }
}

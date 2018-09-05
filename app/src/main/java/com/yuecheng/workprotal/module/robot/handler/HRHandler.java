package com.yuecheng.workprotal.module.robot.handler;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.common.instruction;
import com.yuecheng.workprotal.module.robot.bean.SemanticResult;
import com.yuecheng.workprotal.module.robot.presenter.MainPresenter;


/**
 * HR处理类
 */

public class HRHandler extends IntentHandler {

    public HRHandler(SemanticResult result) {
        super(result);
    }

    @Override
    public String getFormatContent() {
        if (result.semantic != null) {
//            JSONArray list = result.semantic.optJSONArray("slots");
            String intent = result.semantic.optString("intent");
            switch(intent) {
                case instruction.MY_SALARY://我的工资
                    MainPresenter.responseAnswer("跳转页面到我的工资！");
                    break;
                case instruction.MY_EMPLOYMENT_CONTRACT://劳动合同
                    MainPresenter.responseAnswer("跳转页面到劳动合同！");
                    break;
                case instruction.APPLY_OVERTIME://申情加班
                    MainPresenter.responseAnswer("跳转页面到申请加班！");
                    break;
                default:
                    MainPresenter.responseAnswer(R.string.grammar_error+"");
                    break;
            }
        }
        return "";
    }
}

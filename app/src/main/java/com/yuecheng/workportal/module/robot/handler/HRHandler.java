package com.yuecheng.workportal.module.robot.handler;

import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.robot.bean.SemanticResult;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;


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

package com.yuecheng.workportal.module.robot.handler;

import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.robot.bean.SemanticResult;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.utils.AndroidUtil;


/**
 * 财务处理类
 */

public class FinanceHandler extends IntentHandler {

    public FinanceHandler(SemanticResult result) {
        super(result);
    }

    @Override
    public String getFormatContent() {
        if (result.semantic != null) {
//            JSONArray list = result.semantic.optJSONArray("slots");
            String intent = result.semantic.optString("intent");
            switch(intent) {
                case instruction.REIMBURSE://申请付款/报销
                    MainPresenter.responseAnswer("跳转页面到申请付款/报销！");
                    break;
                case instruction.CHECK_PR://查付款/报销
                    MainPresenter.responseAnswer("跳转页面到查付款/报销！");
                    break;
                case instruction.BUSINESS_APPLICATION://业务请示
                    MainPresenter.responseAnswer("跳转页面到业务请示！");
                    break;
                case instruction.CHECK_ON_BUSINESS_APPLICATION://查业务请示
                    MainPresenter.responseAnswer("跳转页面到查业务请示！");
                    break;
                case instruction.BUDGET_ADJUSTMENT://申请借款
                    MainPresenter.responseAnswer("跳转页面到申请借款！");
                    break;
                case instruction.CHECK_ON_BUDGET_ADJUSTMENT://查借款/查非预算事项
                    MainPresenter.responseAnswer("跳转页面到查非预算事项！");
                    break;
                default:
                    MainPresenter.responseAnswer(AndroidUtil.getString(R.string.grammar_error));
                    break;
            }
        }
        return "";
    }
}

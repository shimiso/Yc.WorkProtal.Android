package com.yuecheng.workprotal.module.robot.handler;

import com.yuecheng.workprotal.module.robot.bean.SemanticResult;
import com.yuecheng.workprotal.module.robot.presenter.MainPresenter;


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
                case "reimburse"://申请付款/报销
                    MainPresenter.responseAnswer("跳转页面到申请付款/报销！");
                    break;
                case "check_pr"://查付款/报销
                    MainPresenter.responseAnswer("跳转页面到查付款/报销！");
                    break;
                case "business_application"://业务请示
                    MainPresenter.responseAnswer("跳转页面到业务请示！");
                    break;
                case "check_on_business_application"://查业务请示
                    MainPresenter.responseAnswer("跳转页面到查业务请示！");
                    break;
                case "budget_adjustment"://申请借款
                    MainPresenter.responseAnswer("跳转页面到申请借款！");
                    break;
                case "check_on_budget_adjustment"://查借款/查非预算事项
                    MainPresenter.responseAnswer("跳转页面到查非预算事项！");
                    break;
                default:
                    MainPresenter.responseAnswer("语义错误！");
                    break;
            }
        }
        return "";
    }
}

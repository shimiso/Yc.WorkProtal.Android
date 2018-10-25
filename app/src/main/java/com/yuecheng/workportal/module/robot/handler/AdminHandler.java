package com.yuecheng.workportal.module.robot.handler;


import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.robot.bean.SemanticResult;
import com.yuecheng.workportal.utils.AndroidUtil;


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
                case instruction.STAMP_APPLICATION://申请盖章
                    MainPresenter.responseAnswer("跳转页面到申请盖章!");

                    break;
                case instruction.NAME_CARD://申请名片
                    MainPresenter.responseAnswer("跳转页面到申请名片!");
                    break;
                default:
                    MainPresenter.responseAnswer(AndroidUtil.getString(R.string.grammar_error));
                    break;
            }
        }
        return "";
    }
}

package com.yuecheng.workportal.module.robot.handler;

import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.robot.bean.SemanticResult;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.utils.AndroidUtil;


/**
 * 休假处理类
 */

public class LeaveHandler extends IntentHandler {

    public LeaveHandler(SemanticResult result) {
        super(result);
    }

    @Override
    public String getFormatContent() {
        if (result.semantic != null) {
//            JSONArray list = result.semantic.optJSONArray("slots");
            String intent = result.semantic.optString("intent");
            switch(intent) {
                case instruction.TAKE_LEAVE://请假
                    MainPresenter.responseAnswer("跳转页面到请假！");
                    break;
                case instruction.MY_LEAVE://我的请假
                    MainPresenter.responseAnswer("跳转页面到我的请假！");
                    break;
                default:
                    MainPresenter.responseAnswer(AndroidUtil.getString(R.string.grammar_error));
                    break;
            }
        }
        return "";
    }
}

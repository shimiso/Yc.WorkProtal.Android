package com.yuecheng.workportal.module.robot.handler;

import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.robot.bean.SemanticResult;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.utils.AndroidUtil;


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
                    MainPresenter.responseAnswer(AndroidUtil.getString(R.string.grammar_error));
                    break;
            }
        }
        return "";
    }
}

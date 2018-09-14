package com.yuecheng.workportal.module.robot.handler;

import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.robot.bean.SemanticResult;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;



/**
 * 会议处理类
 */

public class MeetingHandler extends IntentHandler {

    public MeetingHandler(SemanticResult result) {
        super(result);
    }

    @Override
    public String getFormatContent() {
        if (result.semantic != null) {
//            JSONArray list = result.semantic.optJSONArray("slots");
            String intent = result.semantic.optString("intent");
            switch(intent) {
                case instruction.BOOK_MEETING://订会议室
                    MainPresenter.responseAnswer("跳转页面到订会议室！");
                    break;
                case instruction.CANCEL_MEETING://取消会议室
                    MainPresenter.responseAnswer("跳转页面到取消会议室！");
                    break;
                case instruction.MY_MEETING://我的会议
                    MainPresenter.responseAnswer("跳转页面到我的会议！");
                    break;
                default:
                    MainPresenter.responseAnswer(R.string.grammar_error+"");
                    break;
            }
        }
        return "";
    }
}

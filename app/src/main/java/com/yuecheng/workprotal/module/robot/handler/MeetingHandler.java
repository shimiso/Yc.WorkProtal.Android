package com.yuecheng.workprotal.module.robot.handler;

import com.yuecheng.workprotal.module.robot.bean.SemanticResult;
import com.yuecheng.workprotal.module.robot.presenter.MainPresenter;



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
                case "book_meeting"://订会议室
                    MainPresenter.responseAnswer("跳转页面到订会议室！");
                    break;
                case "cancel_meeting"://取消会议室
                    MainPresenter.responseAnswer("跳转页面到取消会议室！");
                    break;
                case "my_meeting"://我的会议
                    MainPresenter.responseAnswer("跳转页面到我的会议！");
                    break;
                default:
                    MainPresenter.responseAnswer("语义错误！");
                    break;
            }
        }
        return "";
    }
}

package com.yuecheng.workprotal.module.robot.handler;

import com.yuecheng.workprotal.module.robot.bean.SemanticResult;
import com.yuecheng.workprotal.module.robot.presenter.MainPresenter;


/**
 * 新闻公告处理类
 */

public class NoticeHandler extends IntentHandler {

    public NoticeHandler(SemanticResult result) {
        super(result);
    }

    @Override
    public String getFormatContent() {
        if (result.semantic != null) {
//            JSONArray list = result.semantic.optJSONArray("slots");
            String intent = result.semantic.optString("intent");
            switch(intent) {
                case "search_news"://查找新闻公告
                    MainPresenter.responseAnswer("跳转页面到查找新闻公告！");
                    break;
                default:
                    MainPresenter.responseAnswer("语义错误！");
                    break;
            }
        }
        return "";
    }
}

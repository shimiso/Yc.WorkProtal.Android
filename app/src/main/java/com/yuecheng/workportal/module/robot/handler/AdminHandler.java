package com.yuecheng.workportal.module.robot.handler;

import android.content.Intent;

import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.MainApplication;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.robot.OpenH5Activity;
import com.yuecheng.workportal.module.robot.bean.SemanticResult;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;


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
                    String url = "http://192.168.0.150:8684/";
                    Intent intent1 = new Intent(MainApplication.getApplication(), OpenH5Activity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    intent1.putExtra("url",url);
//                    intent1.putExtra("name",name);
                    MainApplication.getApplication().startActivity(intent1);
                    break;
                case instruction.NAME_CARD://申请名片
                    MainPresenter.responseAnswer("跳转页面到申请名片!");
                    break;
                default:
                    MainPresenter.responseAnswer(R.string.grammar_error+"");
                    break;
            }
        }
        return "";
    }
}

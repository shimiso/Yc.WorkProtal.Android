package com.yuecheng.workportal.module.robot.handler;

import android.content.Intent;

import com.yuecheng.workportal.MainApplication;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.robot.OpenH5Activity;
import com.yuecheng.workportal.module.robot.bean.SemanticResult;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;

/**
 * Created by huochangsheng on 2018/9/14.
 */

public class CarePlanHandler extends IntentHandler {

    public CarePlanHandler(SemanticResult result) {
        super(result);
    }

    @Override
    public String getFormatContent() {
        if (result.semantic != null) {
//            JSONArray list = result.semantic.optJSONArray("slots");
            String intent = result.semantic.optString("intent");
            switch(intent) {
                case instruction.ZHAOHU_PLAN://照护计划
                    MainPresenter.responseAnswer("跳转页面到照护计划!");
                    String url = "http://192.168.0.150:8684";
                    Intent intent1 = new Intent(MainApplication.getApplication(), OpenH5Activity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    intent1.putExtra("url",url);
//                    intent1.putExtra("name",name);
                    MainApplication.getApplication().startActivity(intent1);
                    break;
                case instruction.VITAL_SIGNS_ENTER://生命体征录入
                    //{"gaoya":100}


                default:
                    MainPresenter.responseAnswer(R.string.grammar_error+"");
                    break;
            }
        }
        return "";
    }
}

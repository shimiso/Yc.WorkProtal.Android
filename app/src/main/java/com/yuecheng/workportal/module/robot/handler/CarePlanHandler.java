package com.yuecheng.workportal.module.robot.handler;

import android.content.Intent;

import com.yuecheng.workportal.MainApplication;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.bean.MessageEvent;
import com.yuecheng.workportal.common.JsApi;
import com.yuecheng.workportal.common.instruction;
import com.yuecheng.workportal.module.robot.OpenH5Activity;
import com.yuecheng.workportal.module.robot.bean.SemanticResult;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            JSONArray list = result.semantic.optJSONArray("slots");
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
                    JSONObject object=new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    if(list!=null && list.length()>0){
                        for(int index=0;index<list.length();index+=2){
                            JSONObject item_name = list.optJSONObject(index);
                            JSONObject item_value = list.optJSONObject(index+1);
                            String name = item_name.optString("normValue");
                            String value = item_value.optString("normValue");
                            try {
                                switch (name){
                                    case "高压":case "高雅":case "高呀":
                                        object.put("bloodPressureGao", value);
                                        break;
                                    case "低压":case "第二":case "低呀":
                                        object.put("bloodPressureTi", value);
                                        break;
                                    case "脉搏":
                                        object.put("pulse", value);
                                        break;
                                    case "体温":case "提问":case "请问":
                                        object.put("pressure", value);
                                        break;
                                    case "血糖":case "血糖值":case "学堂":
                                        object.put("bloodSugar", value);
                                        break;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        try {

                            EventBus.getDefault().post(new MessageEvent(MessageEvent.VITAL_SIGNS_JSON,jsonArray.put(object).toString()));

                        }catch(Exception e){
                            e.printStackTrace();
                            LogUtils.e("JsApi",e);
                        }
                    }
                    //
                default:
                    MainPresenter.responseAnswer(R.string.grammar_error+"");
                    break;
            }
        }
        return "";
    }
}

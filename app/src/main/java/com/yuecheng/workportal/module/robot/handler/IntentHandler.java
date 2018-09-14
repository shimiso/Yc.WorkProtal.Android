package com.yuecheng.workportal.module.robot.handler;


import com.yuecheng.workportal.module.robot.bean.SemanticResult;

/**
 * 语义结果处理抽象类
 */

public abstract class IntentHandler{
    public static final String NEWLINE = "<br/>";
    public static final String NEWLINE_NO_HTML = "\n";
    public static final String CONTROL_TIP = "你可以通过语音控制暂停，播放，上一首，下一首哦";
    public static int controlTipReqCount = 0;

    protected SemanticResult result;

    public IntentHandler( SemanticResult result){
        this.result = result;
    }

    public abstract String getFormatContent();

}

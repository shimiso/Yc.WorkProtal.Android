package com.yuecheng.workprotal.module.robot.handler;

import android.text.TextUtils;

import com.yuecheng.workprotal.module.robot.bean.SemanticResult;


/**
 * 识别失败是执行；拒识（rc = 4）结果处理
 */

public class HintHandler extends IntentHandler {
    private final StringBuilder defaultAnswer;

    public HintHandler(SemanticResult result) {
        super(result);
        defaultAnswer = new StringBuilder();
        defaultAnswer.append("你好，我不懂你的意思");
        defaultAnswer.append(IntentHandler.NEWLINE_NO_HTML);
        defaultAnswer.append(IntentHandler.NEWLINE_NO_HTML);
        defaultAnswer.append("在后台添加更多技能让我变得更强大吧 :D");
    }

    @Override
    public String getFormatContent() {
        if(TextUtils.isEmpty(result.answer)) {
            return defaultAnswer.toString();
        } else {
            return result.answer;
        }
    }
}

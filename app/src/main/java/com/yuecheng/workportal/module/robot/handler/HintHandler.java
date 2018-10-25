package com.yuecheng.workportal.module.robot.handler;

import android.text.TextUtils;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.module.robot.bean.SemanticResult;
import com.yuecheng.workportal.utils.AndroidUtil;
import com.yuecheng.workportal.utils.StringUtils;


/**
 * 识别失败是执行；拒识（rc = 4）结果处理
 */

public class HintHandler extends IntentHandler {
    private final StringBuilder defaultAnswer;

    public HintHandler(SemanticResult result) {
        super(result);
        defaultAnswer = new StringBuilder();
        defaultAnswer.append(AndroidUtil.getString(R.string.wrong_instruction));
//        defaultAnswer.append(IntentHandler.NEWLINE_NO_HTML);
//        defaultAnswer.append(IntentHandler.NEWLINE_NO_HTML);
//        defaultAnswer.append("在后台添加更多技能让我变得更强大吧 :D");
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

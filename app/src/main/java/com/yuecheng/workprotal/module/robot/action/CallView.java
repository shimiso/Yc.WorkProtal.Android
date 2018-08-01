package com.yuecheng.workprotal.module.robot.action;

import android.content.Context;
import android.content.Intent;


public class CallView {


    private Context context;
    public CallView(Context context) {
        this.context = context;
    }

    public void start() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL_BUTTON);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

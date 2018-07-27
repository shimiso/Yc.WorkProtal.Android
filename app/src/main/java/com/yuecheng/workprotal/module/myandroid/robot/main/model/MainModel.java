package com.yuecheng.workprotal.module.myandroid.robot.main.model;


import android.content.Context;

import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.yuecheng.workprotal.module.myandroid.robot.manager.XunfeiManager;

public class MainModel implements IMainModel {
    private XunfeiManager xunfeiManager;

    public MainModel() {
        xunfeiManager = XunfeiManager.getInstance();
    }

    @Override
    public void recognizeVoice(Context context, RecognizerDialogListener recognizerDialogListener) {
        xunfeiManager.voiceDictation(context, recognizerDialogListener);
    }

    @Override
    public void understandText(Context context, String text, TextUnderstanderListener textUnderstanderListener) {
        xunfeiManager.semanticComprehension(context, text, textUnderstanderListener);
    }

    @Override
    public void synthesizeVoice(Context context, String text, SynthesizerListener synthesizerListener) {
        xunfeiManager.voiceSynthesis(context, text, synthesizerListener);
    }
}

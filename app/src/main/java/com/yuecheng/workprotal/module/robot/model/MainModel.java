package com.yuecheng.workprotal.module.robot.model;


import android.content.Context;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.TextUnderstanderListener;
import com.yuecheng.workprotal.module.robot.manager.XunfeiManager;

public class MainModel implements IMainModel {
    private XunfeiManager xunfeiManager;

    public MainModel() {
        xunfeiManager = XunfeiManager.getInstance();
    }

    @Override
    public void startVoiceRobot(Context context, RecognizerListener recognizerListener) {
        xunfeiManager.startVoiceDictation(context, recognizerListener);
    }
    @Override
    public void stopVoiceRobot() {
        xunfeiManager.stopVoiceRobot();
    }
    @Override
    public void cancelVoiceRobot() {
        xunfeiManager.cancelVoiceRobot();
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

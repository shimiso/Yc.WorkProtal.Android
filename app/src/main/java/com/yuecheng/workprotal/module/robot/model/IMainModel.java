package com.yuecheng.workprotal.module.robot.model;

import android.content.Context;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.TextUnderstanderListener;


public interface IMainModel {
    void startVoiceRobot(Context context, RecognizerListener recognizerListener);

    void stopVoiceRobot();

    void cancelVoiceRobot();

    void understandText(Context context, String text, TextUnderstanderListener textUnderstanderListener);

    void synthesizeVoice(Context context, String text, SynthesizerListener synthesizerListener);
}

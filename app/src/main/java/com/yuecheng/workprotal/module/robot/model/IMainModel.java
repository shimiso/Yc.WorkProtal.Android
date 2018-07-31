package com.yuecheng.workprotal.module.robot.model;

import android.content.Context;

import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.ui.RecognizerDialogListener;


public interface IMainModel {
    void recognizeVoice(Context context, RecognizerDialogListener recognizerDialogListener);

    void understandText(Context context, String text, TextUnderstanderListener textUnderstanderListener);

    void synthesizeVoice(Context context, String text, SynthesizerListener synthesizerListener);
}

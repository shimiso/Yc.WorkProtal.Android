package com.yuecheng.workprotal.module.myandroid.robot.main.presenter;


public interface IMainPresenter {
    void onVoiceTypeSelect(int position);

    void onVoiceSpeedChanged(int progress);

    void onVoiceSpeedChangeCompletely(int progress);

    void startVoiceRobot();

}

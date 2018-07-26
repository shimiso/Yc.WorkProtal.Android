package com.yuecheng.workprotal.myandroid.robot.main.view;



import com.yuecheng.workprotal.myandroid.robot.bean.TalkBean;

import java.util.List;

public interface IMainView {
    void updateVoiceType(String type);

    void updateVoiceSpeed(String speed);

    void updateVersion(String version);

    void updateList(List<TalkBean> talkBeanList);
}

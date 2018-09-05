package com.yuecheng.workprotal.module.robot.view;



import com.yuecheng.workprotal.module.robot.bean.TalkBean;

import java.util.List;

public interface IMainView {

    //更新对话列表
    void updateList(List<TalkBean> talkBeanList);

    //设置音量
    void setVoiceChanged(int ic_volume_id);

    //销毁提示
    void destroyTipView();
}

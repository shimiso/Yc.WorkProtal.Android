package com.yuecheng.workprotal.module.myandroid.robot.bean;


import java.io.Serializable;

public class SemanticBean implements Serializable {
    private SlotsBean slots;

    public SlotsBean getSlots() {
        return slots;
    }

    public void setSlots(SlotsBean slots) {
        this.slots = slots;
    }
}

package com.yuecheng.workportal.module.mycenter.bean;

import java.io.Serializable;

/**
 * Created by huochangsheng on 2018/10/16.
 */

public class ClockInBean implements Serializable {

    private String time; //yyyy-MM-dd HH:mm
    private String address; //打卡地址
    private boolean isrange; //是否在范围
    private String note; //备注

    public ClockInBean() {
    }

    public ClockInBean(String time, String address, boolean isrange, String note) {
        this.time = time;
        this.address = address;
        this.isrange = isrange;
        this.note = note;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIsrange(boolean isrange) {
        this.isrange = isrange;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

    public boolean isIsrange() {
        return isrange;
    }

    public String getNote() {
        return note;
    }
}

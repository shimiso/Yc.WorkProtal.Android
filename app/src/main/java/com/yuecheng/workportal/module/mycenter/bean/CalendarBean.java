package com.yuecheng.workportal.module.mycenter.bean;

/**
 * Created by huochangsheng on 2018/10/10.
 */

public class CalendarBean {

    private String title; //标题
    private String dtstart; //开始时间
    private String dtend; //结束时间
    private String description; //详细
    private String location; //位置

    public CalendarBean(String title, String dtstart, String dtend, String description, String location) {
        this.title = title;
        this.dtstart = dtstart;
        this.dtend = dtend;
        this.description = description;
        this.location = location;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDtstart(String dtstart) {
        this.dtstart = dtstart;
    }

    public void setDtend(String dtend) {
        this.dtend = dtend;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public String getDtstart() {
        return dtstart;
    }

    public String getDtend() {
        return dtend;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }
}

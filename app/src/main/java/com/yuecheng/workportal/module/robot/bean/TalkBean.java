package com.yuecheng.workportal.module.robot.bean;


import com.yuecheng.workportal.module.contacts.bean.PersonnelDetailsBean;

import java.io.Serializable;
import java.util.Objects;

public class TalkBean implements Serializable {
    private String text;
    private String jsontext;
    private PersonnelDetailsBean personnelDetailsBean;
    private long time;
    private int type;

    public TalkBean(String text, String jsontext,long time, int type) {
        this.text = text;
        this.jsontext = jsontext;
        this.time = time;
        this.type = type;
    }

    public TalkBean(String text,PersonnelDetailsBean personnelDetailsBean, long time, int type) {
        this.text = text;
        this.personnelDetailsBean = personnelDetailsBean;
        this.time = time;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setJsontext(String jsontext) {
        this.jsontext = jsontext;
    }

    public String getJsontext() {
        return jsontext;
    }
    public void setPersonnelDetailsBean(PersonnelDetailsBean personnelDetailsBean) {
        this.personnelDetailsBean = personnelDetailsBean;
    }

    public PersonnelDetailsBean getPersonnelDetailsBean() {
        return personnelDetailsBean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TalkBean talkBean = (TalkBean) o;
        return time == talkBean.time &&
                type == talkBean.type &&
                Objects.equals(text, talkBean.text) &&
                Objects.equals(jsontext, talkBean.jsontext) &&
                Objects.equals(personnelDetailsBean, talkBean.personnelDetailsBean);
    }

    @Override
    public int hashCode() {

        return Objects.hash(text, jsontext, personnelDetailsBean, time, type);
    }
}

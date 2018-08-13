package com.yuecheng.workprotal.module.robot.bean;


import java.io.Serializable;

public class TalkBean implements Serializable {
    private String text;
    private String jsontext;
    private long time;
    private int type;

    public TalkBean(String text, String jsontext,long time, int type) {
        this.text = text;
        this.jsontext = jsontext;
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

}

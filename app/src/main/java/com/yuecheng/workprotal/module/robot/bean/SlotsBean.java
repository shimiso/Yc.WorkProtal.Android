package com.yuecheng.workprotal.module.robot.bean;


import java.io.Serializable;

public class SlotsBean implements Serializable {
    private String name;
    private String url;
    private String code;
    private String artist;
    private String song;
    private String sightspot;
    private LocationBean location;
    private DateBean datetime;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSightspot() {
        return sightspot;
    }

    public void setSightspot(String sightspot) {
        this.sightspot = sightspot;
    }

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public DateBean getDatetime() {
        return datetime;
    }

    public void setDatetime(DateBean datetime) {
        this.datetime = datetime;
    }
}

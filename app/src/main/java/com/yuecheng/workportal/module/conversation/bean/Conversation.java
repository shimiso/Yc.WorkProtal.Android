package com.yuecheng.workportal.module.conversation.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 描述: 会话
 * 作者: Shims
 */
@Entity(nameInDb = "conversation")
public class Conversation {
    @Transient
    public static final Integer ROBOT_ASSISTANT = 0;//机器人助理
    @Transient
    public static final Integer PRIVATE_CHAT = 1;//单聊

    //@Id：通过这个注解标记的字段必须是Long类型的，这个字段在数据库中表示它就是主键，并且它默认就是自增的
    @Id(autoincrement = true)
    private Long id;

    private String title;

    private String content;
    //发送者ID
    private String sendId;
    //发送者名字
    private String sendName;
    //发送者头像
    private String icon;
    //发送时间
    private Long sendTime;
    //接收时间
    private Long receivedTime;

    private Integer type;

    @Generated(hash = 187026784)
    public Conversation(Long id, String title, String content, String sendId,
            String sendName, String icon, Long sendTime, Long receivedTime,
            Integer type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.sendId = sendId;
        this.sendName = sendName;
        this.icon = icon;
        this.sendTime = sendTime;
        this.receivedTime = receivedTime;
        this.type = type;
    }

    @Generated(hash = 1893991898)
    public Conversation() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendId() {
        return this.sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getSendName() {
        return this.sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public Long getReceivedTime() {
        return this.receivedTime;
    }

    public void setReceivedTime(Long receivedTime) {
        this.receivedTime = receivedTime;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}

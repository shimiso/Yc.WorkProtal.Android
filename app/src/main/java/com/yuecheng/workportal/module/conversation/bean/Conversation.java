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

    private int messageId;

    private String title;

    private String content;
    //目标ID
    private String targetId;
    //目标名字
    private String targetName;
    //目标头像
    private String icon;

    //发送者id
    private String senderUserId;
    //发送者姓名
    private Long senderName;
    //发送时间
    private Long sentTime;
    //接收时间
    private Long receivedTime;

    private Integer type;

    @Generated(hash = 394068825)
    public Conversation(Long id, int messageId, String title, String content,
            String targetId, String targetName, String icon, String senderUserId,
            Long senderName, Long sentTime, Long receivedTime, Integer type) {
        this.id = id;
        this.messageId = messageId;
        this.title = title;
        this.content = content;
        this.targetId = targetId;
        this.targetName = targetName;
        this.icon = icon;
        this.senderUserId = senderUserId;
        this.senderName = senderName;
        this.sentTime = sentTime;
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

    public int getMessageId() {
        return this.messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
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

    public String getTargetId() {
        return this.targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return this.targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSenderUserId() {
        return this.senderUserId;
    }

    public void setSenderUserId(String senderUserId) {
        this.senderUserId = senderUserId;
    }

    public Long getSenderName() {
        return this.senderName;
    }

    public void setSenderName(Long senderName) {
        this.senderName = senderName;
    }

    public Long getSentTime() {
        return this.sentTime;
    }

    public void setSentTime(Long sentTime) {
        this.sentTime = sentTime;
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

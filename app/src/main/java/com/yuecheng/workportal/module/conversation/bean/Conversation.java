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
    private String targetIcon;

    //发送者id
    private String senderUserId;
    //发送者姓名
    private String senderName;
    //目发送者头像
    private String senderIcon;

    //发送时间
    private Long sentTime;
    //接收时间
    private Long receivedTime;

    //消息类型
    private Integer type;

    @Generated(hash = 447431449)
    public Conversation(Long id, int messageId, String title, String content,
            String targetId, String targetName, String targetIcon,
            String senderUserId, String senderName, String senderIcon,
            Long sentTime, Long receivedTime, Integer type) {
        this.id = id;
        this.messageId = messageId;
        this.title = title;
        this.content = content;
        this.targetId = targetId;
        this.targetName = targetName;
        this.targetIcon = targetIcon;
        this.senderUserId = senderUserId;
        this.senderName = senderName;
        this.senderIcon = senderIcon;
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

    public String getTargetIcon() {
        return this.targetIcon;
    }

    public void setTargetIcon(String targetIcon) {
        this.targetIcon = targetIcon;
    }

    public String getSenderUserId() {
        return this.senderUserId;
    }

    public void setSenderUserId(String senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getSenderName() {
        return this.senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderIcon() {
        return this.senderIcon;
    }

    public void setSenderIcon(String senderIcon) {
        this.senderIcon = senderIcon;
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

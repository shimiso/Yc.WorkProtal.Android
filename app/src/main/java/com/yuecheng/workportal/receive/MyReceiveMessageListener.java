package com.yuecheng.workportal.receive;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.yuecheng.workportal.MainActivity;
import com.yuecheng.workportal.module.conversation.ConversationPresenter;
import com.yuecheng.workportal.module.conversation.bean.Conversation;
import com.yuecheng.workportal.utils.AndroidUtil;

import org.greenrobot.eventbus.EventBus;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.FileMessage;
import io.rong.message.ImageMessage;
import io.rong.message.LocationMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

public class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {
    private Context context;
    ConversationPresenter conversationPresenter;
    public MyReceiveMessageListener(Context context){
        this.context = context;
        this.conversationPresenter = new ConversationPresenter(context);
    }
    /**
     * 收到消息的处理。
     *
     * @param message 收到的消息实体。
     * @param left    剩余未拉取消息数目。
     * @return 收到消息是否处理完成，true 表示自己处理铃声和后台通知，false 走融云默认处理方式。
     */
    @Override
    public boolean onReceived(Message message, int left) {
        MessageContent messageContent = message.getContent();
        Conversation conversation = new Conversation();

        if (messageContent instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message.getContent();
            conversation.setContent(textMessage.getContent());
            sendNotifyEvent(message,conversation);
        }else if(messageContent instanceof VoiceMessage){
            VoiceMessage voiceMessage = (VoiceMessage) message.getContent();
            conversation.setContent(message.getSenderUserId()+"发来一个语音信息");
            sendNotifyEvent(message,conversation);
        }else if(messageContent instanceof ImageMessage){
            ImageMessage imageMessage = (ImageMessage) message.getContent();
            conversation.setContent(message.getSenderUserId()+"发来一张图片信息");
            sendNotifyEvent(message,conversation);
        }else if(messageContent instanceof LocationMessage){
            LocationMessage locationMessage = (LocationMessage) message.getContent();
            conversation.setContent(message.getSenderUserId()+"发来定位信息");
            sendNotifyEvent(message,conversation);
        }else if(messageContent instanceof FileMessage){
            FileMessage fileMessage = (FileMessage) message.getContent();
            conversation.setContent(message.getSenderUserId()+"发来一个文件信息");
            sendNotifyEvent(message,conversation);
        }

        return false;
    }

    private void  sendNotifyEvent(Message message,Conversation conversation){
        conversation.setTitle("与" + message.getSenderUserId() + "会话");
        conversation.setType(Conversation.PRIVATE_CHAT);
        conversation.setReceivedTime(message.getReceivedTime());
        conversation.setSendId(message.getSenderUserId());
        conversation.setSendTime(message.getSentTime());

        conversationPresenter.saveConversation(conversation);
        Intent subjectIntent = new Intent(context, MainActivity.class);
        subjectIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent subjectPendingIntent = PendingIntent.getActivity(context, 0, subjectIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AndroidUtil.showNotification(context, 001, conversation.getSendId() + "发来消息", conversation.getContent(), subjectPendingIntent);
        EventBus.getDefault().post(conversation);
    }

}
package com.yuecheng.workportal.receive;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.yuecheng.workportal.MainApplication;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.bean.LoginUser;
import com.yuecheng.workportal.module.conversation.ConversationPresenter;
import com.yuecheng.workportal.module.conversation.bean.Conversation;
import com.yuecheng.workportal.utils.AndroidUtil;
import com.yuecheng.workportal.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

public class MySendMessageListener implements RongIM.OnSendMessageListener {
    public static final String TAG = "MySendMessageListener";
    Context context;
    ConversationPresenter conversationPresenter;
    AndroidUtil androidUtil;
    LoginUser loginUser;

    public MySendMessageListener(Context context) {
        this.context = context;
        this.androidUtil = AndroidUtil.init(context);
        this.conversationPresenter = new ConversationPresenter(context);
    }

    /**
     * 消息发送前监听器处理接口（是否发送成功可以从 SentStatus 属性获取）。
     *
     * @param message 发送的消息实例。
     * @return 处理后的消息实例。
     */
    @Override
    public Message onSend(Message message) {
        //开发者根据自己需求自行处理逻辑
        MessageContent messageContent = message.getContent();
        loginUser = MainApplication.getApplication().getLoginUser();
        Uri uri = Uri.parse(StringUtils.doEmpty(loginUser.getUserIcon()));
        messageContent.setUserInfo(new UserInfo(loginUser.getCode(),loginUser.getName(),uri));
        return message;
    }

    /**
     * 消息在 UI 展示后执行/自己的消息发出后执行,无论成功或失败。
     *
     * @param message              消息实例。
     * @param sentMessageErrorCode 发送消息失败的状态码，消息发送成功 SentMessageErrorCode 为 null。
     * @return true 表示走自己的处理方式，false 走融云默认处理方式。
     */
    @Override
    public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {

        if (message.getSentStatus() == Message.SentStatus.FAILED) {
            if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_CHATROOM) {
                //不在聊天室
            } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_DISCUSSION) {
                //不在讨论组
            } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_GROUP) {
                //不在群组
            } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.REJECTED_BY_BLACKLIST) {
                //你在他的黑名单中
            }
        }

        MessageContent messageContent = message.getContent();

        Conversation conversation = new Conversation();
        if (messageContent instanceof TextMessage) {//文本消息
            TextMessage textMessage = (TextMessage) messageContent;
            Log.d(TAG, "onSent-TextMessage:" + textMessage.getContent());

            conversation.setContent(textMessage.getContent());
            sendEvent(message, conversation);
        } else if (messageContent instanceof ImageMessage) {//图片消息
            ImageMessage imageMessage = (ImageMessage) messageContent;
            Log.d(TAG, "onSent-ImageMessage:" + imageMessage.getRemoteUri());

            conversation.setContent("[" + androidUtil.getString(R.string.picture) + "]");
            sendEvent(message, conversation);

        } else if (messageContent instanceof VoiceMessage) {//语音消息
            VoiceMessage voiceMessage = (VoiceMessage) messageContent;
            Log.d(TAG, "onSent-voiceMessage:" + voiceMessage.getUri().toString());

            conversation.setContent("[" + androidUtil.getString(R.string.voice) + "]");
            sendEvent(message, conversation);
        } else if (messageContent instanceof RichContentMessage) {//图文消息
            RichContentMessage richContentMessage = (RichContentMessage) messageContent;
            Log.d(TAG, "onSent-RichContentMessage:" + richContentMessage.getContent());
        } else {
            Log.d(TAG, "onSent-其他消息，自己来判断处理");
        }

        return false;
    }

    private void sendEvent(Message message, Conversation conversation) {
        conversation.setType(Conversation.PRIVATE_CHAT);
        conversation.setSenderName(loginUser.getName());
        conversation.setSenderIcon(StringUtils.doEmpty(loginUser.getUserIcon()));
        conversation.setSenderUserId(message.getSenderUserId());
        conversation.setSentTime(message.getSentTime());

        conversation.setTargetId(message.getTargetId());

        conversationPresenter.updateConversation(conversation);
        EventBus.getDefault().post(conversation);
    }
}
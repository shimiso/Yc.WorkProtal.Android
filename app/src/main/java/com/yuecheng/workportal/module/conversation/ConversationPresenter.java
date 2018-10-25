package com.yuecheng.workportal.module.conversation;

import android.content.Context;

import com.yuecheng.workportal.MainApplication;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.common.CommonResultView;
import com.yuecheng.workportal.db.DaoManager;
import com.yuecheng.workportal.greendao.ConversationDao;
import com.yuecheng.workportal.module.conversation.bean.Conversation;

import java.util.List;

/**
 * 描述:
 * 作者: Shims
 */
public class ConversationPresenter {
    private Context context;
    ConversationDao conversationDao;
    public ConversationPresenter(Context context) {
        this.context = context;
        this.conversationDao =  DaoManager.getInstance(context).getConversationDao();
    }

    public void getConversationList(final CommonResultView<List<Conversation>> commonResultView) {
        List<Conversation> list = null;
        try {
            list = conversationDao.queryBuilder().orderAsc(ConversationDao.Properties.Type,ConversationDao.Properties.ReceivedTime).list();
            //获取与登录人员相关的聊天列表
            String guid = MainApplication.getApplication().getLoginUser().getGuid();
            for(int i=list.size()-1;i>=0;i--){
                String senderUserId = list.get(i).getSenderUserId();
                if (senderUserId != null && !senderUserId.equals(guid)){
                    list.remove(i);
                }
            }
            commonResultView.success(list);
        }catch (Exception e){
            e.printStackTrace();
            commonResultView.error(context.getString(R.string.server_net_error));
        }
    }

    public void saveConversation(Conversation temp){
        List<Conversation> list;
        try {
            list = conversationDao.queryBuilder()
                    .where(ConversationDao.Properties.TargetId.eq(temp.getTargetId()))
                    .build().list();
            if(list!=null){
                conversationDao.deleteInTx(list);
            }
            conversationDao.save(temp);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void updateConversation(Conversation temp){
        List<Conversation> list;
        try {
            list = conversationDao.queryBuilder()
                    .where(ConversationDao.Properties.TargetId.eq(temp.getTargetId()))
                    .build().list();
            if(list!=null&&list.size()>0){
                conversationDao.deleteInTx(list);
            }
            conversationDao.save(temp);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void remove(Conversation conversation) {
        if(conversation!=null){
            conversationDao.delete(conversation);
        }
    }
}

package com.yuecheng.workportal.module.conversation;

import android.content.Context;

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
            commonResultView.success(list);
        }catch (Exception e){
            e.printStackTrace();
            commonResultView.error("服务器发生未知异常");
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
                temp.setTargetName(list.get(0).getTargetName());
                temp.setTargetIcon(list.get(0).getTargetIcon());
                temp.setTitle(list.get(0).getTitle());
                conversationDao.deleteInTx(list);
            }
            conversationDao.save(temp);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

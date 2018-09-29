package com.yuecheng.workportal.module.contacts.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.module.contacts.bean.PersonnelDetailsBean;
import com.yuecheng.workportal.module.contacts.view.InformationActivity;
import com.yuecheng.workportal.module.robot.view.VoiceActivity;
import com.yuecheng.workportal.widget.CircleTextImage;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by huochangsheng on 2018/9/28.
 */

public class SubordinatesAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context context;
    private List<PersonnelDetailsBean.SubordinatesBean> list;

    public SubordinatesAdapter(Context context) {
        this.context = context;
    }

    public void onRefresh(List<PersonnelDetailsBean.SubordinatesBean> list){
        this.list = list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subordinates_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.cti.setText4CircleImage(list.get(position).getName().subSequence(0, 1).toString());//头像
            viewHolder.isboy.setImageResource(R.mipmap.boy);//性别
            viewHolder.tv_contact_name.setText(list.get(position).getName());//name
//            viewHolder.tv_contact_department.setText();//岗位
            viewHolder.to_chat.setOnClickListener(v -> {//跳转IM
                if (RongIM.getInstance() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("targetName", list.get(position).getName());
                    bundle.putString("targetIcon", "http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png");
                    /**
                     * 启动单聊界面。
                     * @param context      应用上下文。
                     * @param targetUserId 要与之聊天的用户 Id。
                     * @param title        聊天的标题，开发者需要在聊天界面通过 intent.getData().getQueryParameter("title")
                     *                     获取该值, 再手动设置为聊天界面的标题。
                     */
                    RongIM.getInstance().startConversation(context, Conversation.ConversationType.PRIVATE, list.get(position).getGuid() + "", "与" + list.get(position).getName() + "对话", bundle);
                }
            });
            holder.itemView.setOnClickListener(v -> {
                //条目点击
                Intent intent = new Intent(context, InformationActivity.class);
                intent.putExtra("Guid",list.get(position).getGuid());
                intent.putExtra("name",list.get(position).getName());
                context.startActivity(intent);
            });
        }

    }

    @Override
    public int getItemCount() {
        if(list!=null && list.size()>0){
            return list.size();
        }
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_contact_name;
        private final TextView tv_contact_department;
        private final ImageView to_chat;
        private final CircleTextImage cti;
        private final ImageView isboy;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_contact_name = (TextView)itemView.findViewById(R.id.tv_contact_name);
            tv_contact_department = (TextView)itemView.findViewById(R.id.tv_contact_department);
            to_chat = (ImageView)itemView.findViewById(R.id.to_chat);
            cti = (CircleTextImage)itemView.findViewById(R.id.cti);
            isboy = (ImageView)itemView.findViewById(R.id.isboy);
        }
    }
}

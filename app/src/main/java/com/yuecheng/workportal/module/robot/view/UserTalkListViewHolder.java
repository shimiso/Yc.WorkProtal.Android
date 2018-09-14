package com.yuecheng.workportal.module.robot.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yuecheng.workportal.R;


public class UserTalkListViewHolder extends RecyclerView.ViewHolder {
    public TextView mTvUserTalk;

    public UserTalkListViewHolder(View itemView) {
        super(itemView);
        mTvUserTalk = (TextView) itemView.findViewById(R.id.tv_user_talk);
    }
}

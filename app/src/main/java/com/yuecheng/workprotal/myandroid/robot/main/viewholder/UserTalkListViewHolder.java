package com.yuecheng.workprotal.myandroid.robot.main.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yuecheng.workprotal.R;


public class UserTalkListViewHolder extends RecyclerView.ViewHolder {
    public TextView mTvUserTalk;

    public UserTalkListViewHolder(View itemView) {
        super(itemView);
        mTvUserTalk = (TextView) itemView.findViewById(R.id.tv_user_talk);
    }
}

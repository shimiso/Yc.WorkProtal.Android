package com.yuecheng.workprotal.module.robot.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yuecheng.workprotal.R;


public class RobotTalkListViewHolder extends RecyclerView.ViewHolder {
    public TextView mTvRobotTalk;

    public RobotTalkListViewHolder(View itemView) {
        super(itemView);
        mTvRobotTalk = (TextView) itemView.findViewById(R.id.tv_robot_talk);
    }
}

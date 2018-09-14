package com.yuecheng.workportal.module.robot.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuecheng.workportal.R;


public class RobotTalkListViewHolder extends RecyclerView.ViewHolder {
    public TextView mTvRobotTalk;
    public ImageView mRobotTalkImg;

    public RobotTalkListViewHolder(View itemView) {
        super(itemView);
        mTvRobotTalk = (TextView) itemView.findViewById(R.id.tv_robot_talk);
        mRobotTalkImg = (ImageView) itemView.findViewById(R.id.robot_talk_img);
    }
}

package com.yuecheng.workportal.module.robot.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yuecheng.workportal.widget.BridgeWebView;
import com.yuecheng.workportal.widget.BridgeWebView;
import com.yuecheng.workportal.R;


public class RobotWebListViewHolder extends RecyclerView.ViewHolder {
    public BridgeWebView mTvRobotWeb;

    public RobotWebListViewHolder(View itemView) {
        super(itemView);
        mTvRobotWeb = (BridgeWebView) itemView.findViewById(R.id.robot_weview);
    }
}

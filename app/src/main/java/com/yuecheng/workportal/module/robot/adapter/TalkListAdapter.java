package com.yuecheng.workportal.module.robot.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;


import com.yuecheng.workportal.R;
import com.yuecheng.workportal.module.robot.JavaSctiptMethods;
import com.yuecheng.workportal.module.robot.OpenH5Activity;
import com.yuecheng.workportal.module.robot.bean.TalkBean;
import com.yuecheng.workportal.module.robot.view.JsonActivity;
import com.yuecheng.workportal.module.robot.view.RobotTalkListViewHolder;
import com.yuecheng.workportal.module.robot.view.RobotWebListViewHolder;
import com.yuecheng.workportal.module.robot.view.UserTalkListViewHolder;

import java.util.List;

public class TalkListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_ROBOT_CHAT = 1;
    public static final int VIEW_TYPE_ROBOT_WEB = 2;
    public static final int VIEW_TYPE_USER = 3;
    private List<TalkBean> mTalkBeanList;
    private Context context;

    public TalkListAdapter(List<TalkBean> mTalkBeanList, Context context) {
        this.mTalkBeanList = mTalkBeanList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ROBOT_CHAT:
                View robotChatLayout = View.inflate(context, R.layout.robot_chat, null);
                return new RobotTalkListViewHolder(robotChatLayout);
            case VIEW_TYPE_ROBOT_WEB:
                View robotWebLayout = View.inflate(context, R.layout.robot_web, null);
                return new RobotWebListViewHolder(robotWebLayout);
            case VIEW_TYPE_USER:
                View userLayout = View.inflate(context, R.layout.robot_user_layout, null);
                return new UserTalkListViewHolder(userLayout);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mTalkBeanList == null ? 0 : mTalkBeanList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null) return;
        String talkText = "";
        final TalkBean talkBean = mTalkBeanList.get(position);
        talkText = talkBean.getText();
        if (TextUtils.isEmpty(talkText)) return;

        if (holder instanceof RobotTalkListViewHolder) {
            RobotTalkListViewHolder robotHolder = (RobotTalkListViewHolder) holder;
            robotHolder.mTvRobotTalk.setText(talkText);
            robotHolder.mRobotTalkImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, JsonActivity.class);
                    intent.putExtra("json",talkBean.getJsontext());
                    context.startActivity(intent);
                    Log.d("tag", talkBean.getJsontext());
                }
            });
        } else if (holder instanceof UserTalkListViewHolder) {
            UserTalkListViewHolder userHolder = (UserTalkListViewHolder) holder;
            userHolder.mTvUserTalk.setText(talkText);
        } else if (holder instanceof RobotWebListViewHolder) {
            RobotWebListViewHolder webHolder = (RobotWebListViewHolder) holder;
            webHolder.mTvRobotWeb.addBridgeInterface(new JavaSctiptMethods((Activity) context, webHolder.mTvRobotWeb));//设置js和android通信桥梁方法
            webHolder.mTvRobotWeb.loadUrl(talkText);//动态获取需要打开的链接
            webHolder.mTvRobotWeb.setWebViewClient(new WebViewClient());
            webHolder.mTvRobotWeb.setWebChromeClient(new WebChromeClient());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mTalkBeanList != null) {
            TalkBean talkBean = mTalkBeanList.get(position);
            return talkBean.getType();
        }
        return super.getItemViewType(position);
    }
}
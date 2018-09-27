package com.yuecheng.workportal.module.robot.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;


import com.yuecheng.workportal.R;
import com.yuecheng.workportal.module.contacts.bean.PersonnelDetailsBean;
import com.yuecheng.workportal.module.robot.JavaSctiptMethods;
import com.yuecheng.workportal.module.robot.OpenH5Activity;
import com.yuecheng.workportal.module.robot.bean.TalkBean;
import com.yuecheng.workportal.module.robot.view.JsonActivity;
import com.yuecheng.workportal.module.robot.view.PeopleDetailTalkListViewHolder;
import com.yuecheng.workportal.module.robot.view.RobotTalkListViewHolder;
import com.yuecheng.workportal.module.robot.view.RobotWebListViewHolder;
import com.yuecheng.workportal.module.robot.view.UserTalkListViewHolder;

import java.util.List;

import io.rong.imkit.RongIM;

public class TalkListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    public static final int VIEW_TYPE_ROBOT_CHAT = 1;
    public static final int VIEW_TYPE_ROBOT_WEB = 2;
    public static final int VIEW_TYPE_USER = 3;
    public static final int VIEW_TYPE_PEOPLE = 4;
    private List<TalkBean> mTalkBeanList;
    private Context context;
    private PeopleDetailTalkListViewHolder peopleHolder;
    private PersonnelDetailsBean personnelDetailsBean;

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
            case VIEW_TYPE_PEOPLE:
                View peopleLayout = View.inflate(context, R.layout.robot_people_detail, null);
                return new PeopleDetailTalkListViewHolder(peopleLayout);
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
        } else if (holder instanceof PeopleDetailTalkListViewHolder) {
            personnelDetailsBean = talkBean.getPersonnelDetailsBean();
            peopleHolder = (PeopleDetailTalkListViewHolder) holder;
            String[] split = personnelDetailsBean.getOrganizationName().split(">");

            peopleHolder.cti.setText4CircleImage(personnelDetailsBean.getName().subSequence(0, 1).toString());//设置头像显示文字
            peopleHolder.nameTv.setText(personnelDetailsBean.getName());//设置name
            peopleHolder.departmentTv.setText(split[split.length-1]);//设置部门
            peopleHolder.jobsTv.setText(personnelDetailsBean.getPositionName());//设置岗位
            peopleHolder.landlineTv.setText(personnelDetailsBean.getTelephone());//设置座机
            peopleHolder.phoneTv.setText(personnelDetailsBean.getMobilePhone());//设置手机号
            //设置电话号码下划线以及字体颜色
            peopleHolder.landlineTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
            peopleHolder.phoneTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
            peopleHolder.landlineTv.setTextColor(Color.parseColor("#008CFF"));
            peopleHolder.phoneTv.setTextColor(Color.parseColor("#008CFF"));


            peopleHolder.send_mail_ll.setOnClickListener(this);//邮件点击事件
            peopleHolder.send_msg_ll.setOnClickListener(this);//发送消息的点击事件
            peopleHolder.phoneTv.setOnClickListener(this);//手机号
            peopleHolder.landlineTv.setOnClickListener(this);//座机
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.landline_tv:
            case R.id.phone_tv: //打电话
                String number;
                if (v.getId() == R.id.phone_tv) {
                    number = peopleHolder.phoneTv.getText().toString();
                } else {
                    number = peopleHolder.landlineTv.getText().toString();
                }
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case R.id.send_msg_ll://发送消息
                if (RongIM.getInstance() != null){
                    RongIM.getInstance().startPrivateChat(context, personnelDetailsBean.getGuid()+"", "与"+personnelDetailsBean.getName()+"对话");
                }
                break;
            case R.id.send_mail_ll: //发送邮件
                Intent data=new Intent(Intent.ACTION_SENDTO);
                data.setData(Uri.parse("mailto:"+personnelDetailsBean.getEmail()));
                context.startActivity(data);
                break;
        }
    }
}
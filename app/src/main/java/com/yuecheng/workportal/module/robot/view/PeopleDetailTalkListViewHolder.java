package com.yuecheng.workportal.module.robot.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.widget.CircleTextImage;


public class PeopleDetailTalkListViewHolder extends RecyclerView.ViewHolder {
    public CircleTextImage cti;
    public TextView nameTv;
    public TextView departmentTv;
    public TextView jobsTv;
    public TextView landlineTv;
    public TextView phoneTv;
    public LinearLayout send_mail_ll;
    public LinearLayout send_msg_ll;
    public RelativeLayout details_rl;


    public PeopleDetailTalkListViewHolder(View itemView) {
        super(itemView);
        details_rl = (RelativeLayout) itemView.findViewById(R.id.details_rl);
        cti = (CircleTextImage) itemView.findViewById(R.id.cti);
        nameTv = (TextView) itemView.findViewById(R.id.name_tv);
        departmentTv = (TextView) itemView.findViewById(R.id.department_tv);
        jobsTv = (TextView) itemView.findViewById(R.id.jobs_tv);
        landlineTv = (TextView) itemView.findViewById(R.id.landline_tv);
        phoneTv = (TextView) itemView.findViewById(R.id.phone_tv);
        send_mail_ll = (LinearLayout) itemView.findViewById(R.id.send_mail_ll);
        send_msg_ll = (LinearLayout) itemView.findViewById(R.id.send_msg_ll);
    }
}

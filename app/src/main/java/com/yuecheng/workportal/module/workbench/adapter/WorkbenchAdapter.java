package com.yuecheng.workportal.module.workbench.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuecheng.workportal.BuildConfig;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.module.mycenter.view.SignInActivity;
import com.yuecheng.workportal.module.robot.OpenH5Activity;
import com.yuecheng.workportal.module.workbench.bean.WorkbenchBean;

import java.util.List;


/**
 * Created by huochangsheng on 2018/9/29.
 */

public class WorkbenchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<WorkbenchBean.PermsBean> list;
    private final int TITLE_NAME = 0;
    private final int LIST_DATA = 1;

    public WorkbenchAdapter(Context context) {
        this.context = context;
    }

    public void onRefresh(List<WorkbenchBean.PermsBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TITLE_NAME) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_bench_title_item, parent, false);
            return new TitleViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_bench_body_item, parent, false);
            return new BodyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (TITLE_NAME == getItemViewType(position)) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.work_title_name.setText(list.get(position).getName());
            titleViewHolder.fenggexian.setVisibility(position > 0 ? View.VISIBLE : View.GONE);
        } else {
            BodyViewHolder bodyViewHolder = (BodyViewHolder) holder;
            String iconUrl1 = list.get(position).getIconUrl();
            Glide.with(context)
                    .load(BuildConfig.HTTP_HOST + "img/" + iconUrl1)
                    .into(bodyViewHolder.body_img);

            bodyViewHolder.body_img_tv.setText(list.get(position).getPermName());
            bodyViewHolder.itemView.setOnClickListener(v -> {//点击跳转到相应页面
                if(list.get(position).getPermName().equals("打卡")){
                    context.startActivity(new Intent(context, SignInActivity.class));
                }
                String iconUrl = list.get(position).getLinkAddress();
                if (iconUrl==null||iconUrl.isEmpty()) return;
                Intent intent = new Intent(context, OpenH5Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url", iconUrl);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).isTitle ? TITLE_NAME : LIST_DATA;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {

        private final TextView work_title_name;
        private final View fenggexian;

        public TitleViewHolder(View itemView) {
            super(itemView);
            work_title_name = (TextView) itemView.findViewById(R.id.work_title_name);
            fenggexian = (View) itemView.findViewById(R.id.fenggexian);
        }
    }

    public class BodyViewHolder extends RecyclerView.ViewHolder {

        private final TextView body_img_tv;
        private final ImageView body_img;

        public BodyViewHolder(View itemView) {
            super(itemView);
            body_img_tv = (TextView) itemView.findViewById(R.id.body_img_tv);
            body_img = (ImageView) itemView.findViewById(R.id.body_img);
        }
    }
}

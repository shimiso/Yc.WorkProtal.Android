package com.yuecheng.workportal.module.contacts.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.module.contacts.bean.OrganizationBean;
import com.yuecheng.workportal.module.contacts.bean.OrganizationBean.OrgsBean.StaffsBean;
import com.yuecheng.workportal.module.contacts.bean.OrganizationBean.OrgsBean.SubOrgsBean;
import com.yuecheng.workportal.widget.CircleTextImage;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by huochangsheng on 2018/9/6.
 */

public class SelectGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OrganizationBean.OrgsBean> list;
    private Context context;
    private int selectOrgid;
    public View.OnClickListener onClickListener;


    //定义一个接口点击事件
    public interface OnRecyclerViewItemClickLintemet{
        void onItemClick(int position);
    }
    //定义接口对象         单击事件
    private OrganizationAdapter.OnRecyclerViewItemClickLintemet listener;
    //定义接口方法         单击事件
    public void setOnRecyclerViewItemClickLintemet(OrganizationAdapter.OnRecyclerViewItemClickLintemet listener){
        this.listener=listener;
    };

    public SelectGroupAdapter(Context context,int selectOrgid) {
        this.context = context;
        this.selectOrgid = selectOrgid;
    }


    /**
     * 刷新数据
     *
     * @param list 组织
     */
    public void onRefresh(List<OrganizationBean.OrgsBean> list) {
        if (null != this.list) {
            this.list.clear();
        }
        this.list = list;
        this.notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_select_group_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ViewHolder){
            ViewHolder viewHolder = (ViewHolder) holder;
            if(list!=null && position < list.size()){
                viewHolder.group_name.setText(list.get(position).getOrgName()); //设置组织name
                if(list.get(position).getOrgId() == selectOrgid){
                    viewHolder.select_group_img.setVisibility(View.VISIBLE);
                }else{
                    viewHolder.select_group_img.setVisibility(View.GONE);
                }
            }
            holder.itemView.setOnClickListener(v -> {
                listener.onItemClick(position);
            });
        }
    }

    @Override
    public int getItemCount() {
       return list==null?0:list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView group_name;
        private final ImageView select_group_img;

        public ViewHolder(View itemView) {
            super(itemView);
            group_name = (TextView)itemView.findViewById(R.id.group_name);
            select_group_img = (ImageView)itemView.findViewById(R.id.select_group_img);
        }
    }
}

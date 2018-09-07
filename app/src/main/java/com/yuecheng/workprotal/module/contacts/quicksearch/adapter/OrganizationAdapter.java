package com.yuecheng.workprotal.module.contacts.quicksearch.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.bean.ResultInfo;
import com.yuecheng.workprotal.common.CommonPostView;
import com.yuecheng.workprotal.module.contacts.presenter.ContactsPresenter;
import com.yuecheng.workprotal.module.contacts.quicksearch.Bean.ChildInstitutionsBean;
import com.yuecheng.workprotal.module.contacts.quicksearch.Bean.OrganizationBean.ResultBean.OrgsBean.StaffsBean;
import com.yuecheng.workprotal.module.contacts.quicksearch.Bean.OrganizationBean.ResultBean.OrgsBean.SubOrgsBean;
import com.yuecheng.workprotal.module.contacts.view.InformationActivity;
import com.yuecheng.workprotal.widget.CircleTextImage;

import java.util.List;

/**
 * Created by huochangsheng on 2018/9/6.
 */

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.ViewHolder> {
    private List<StaffsBean> staffsList;   //人员
    private List<SubOrgsBean> subOrgsList; //部门
    private Context context;

    //定义一个接口点击事件
    public interface OnRecyclerViewItemClickLintemet{
        void onItemClick(int position);
    }
    //定义接口对象         单击事件
    private OnRecyclerViewItemClickLintemet listener;
    //定义接口方法         单击事件
    public void setOnRecyclerViewItemClickLintemet(OnRecyclerViewItemClickLintemet listener){

        this.listener=listener;
    };

    public OrganizationAdapter( Context context) {
        this.context = context;
    }

    /**
     * 刷新数据
     *
     * @param staffsList 人员
     * @param subOrgsList 部门
     */
    public void onRefresh(List<StaffsBean> staffsList,List<SubOrgsBean> subOrgsList) {
        if (null != this.staffsList) {
            this.staffsList.clear();
        }
        if (null != this.subOrgsList) {
            this.subOrgsList.clear();
        }
        this.staffsList = staffsList;
        this.subOrgsList = subOrgsList;
        this.notifyDataSetChanged();
    }
    @Override
    public OrganizationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_structure_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrganizationAdapter.ViewHolder holder, final int position) {
        if(subOrgsList!=null && position < subOrgsList.size()){
            holder.contacts_item_img.setVisibility(View.VISIBLE);
            holder.contacts_item_cti.setVisibility(View.INVISIBLE);
            holder.contacts_departments_personnel.setText(subOrgsList.get(position).getOrgName());
            holder.contacts_number_jobs.setText(subOrgsList.get(position).getOrgStaffCount()+"人");
        }else if(staffsList != null &&  position >= subOrgsList.size()){
            holder.contacts_item_img.setVisibility(View.INVISIBLE);
            holder.contacts_item_cti.setVisibility(View.VISIBLE);
            holder.contacts_item_cti.setText4CircleImage(staffsList.get(position-subOrgsList.size()).getName().subSequence(0, 1).toString());
            holder.right_arrow_img.setVisibility(View.GONE);
            holder.contacts_departments_personnel.setText(staffsList.get(position-subOrgsList.size()).getName());
            holder.contacts_number_jobs.setText(staffsList.get(position-subOrgsList.size()).getPositionName());
        }
        if(position-subOrgsList.size()==-1 && staffsList.size()!=0){
            holder.fenggexian.setVisibility(View.VISIBLE);
        }else{
            holder.fenggexian.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(position);
        });

    }

    @Override
    public int getItemCount() {
        if(staffsList==null && subOrgsList==null){
            return 0;
        }else if(staffsList!=null && subOrgsList==null){
            return staffsList.size();
        }else if(staffsList==null && subOrgsList!=null){
            return subOrgsList.size();
        }else{
            return subOrgsList.size()+staffsList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView contacts_departments_personnel;
        private final TextView contacts_number_jobs;
        private final ImageView right_arrow_img;
        private final CircleTextImage contacts_item_cti;
        private final ImageView contacts_item_img;
        private final View fenggexian;

        public ViewHolder(View itemView) {
            super(itemView);
            contacts_departments_personnel = (TextView)itemView.findViewById(R.id.contacts_departments_personnel);
            contacts_number_jobs = (TextView)itemView.findViewById(R.id.contacts_number_jobs);
            right_arrow_img = (ImageView)itemView.findViewById(R.id.right_arrow_img);
            contacts_item_cti = (CircleTextImage)itemView.findViewById(R.id.contacts_item_cti);
            contacts_item_img = (ImageView)itemView.findViewById(R.id.contacts_item_img);
            fenggexian = (View)itemView.findViewById(R.id.fenggexian);
        }
    }
}

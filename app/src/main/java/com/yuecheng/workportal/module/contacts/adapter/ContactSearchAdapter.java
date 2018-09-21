package com.yuecheng.workportal.module.contacts.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.module.contacts.bean.ContactBean;
import com.yuecheng.workportal.module.contacts.view.InformationActivity;
import com.yuecheng.workportal.widget.CircleTextImage;

import java.util.List;


public class ContactSearchAdapter extends BaseAdapter {
    private Context context;
    private List<ContactBean.StaffsBean> list;

    public ContactSearchAdapter(Context context, List<ContactBean.StaffsBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.contact_item_contact, null);
        }
        ViewHolder holder = ViewHolder.getHolder(convertView);
        //设置数据
        ContactBean.StaffsBean contactBean = list.get(position);
        holder.tv_contact_name.setText(contactBean.getName());
        holder.cti.setText4CircleImage(contactBean.getName().subSequence(0, 1).toString());
        holder.tv_contact_department.setText(contactBean.getPositionName());
        if (position > 0) {
            holder.tv_first_alphabet.setVisibility(View.GONE);
        } else {
            holder.tv_first_alphabet.setVisibility(View.VISIBLE);
            holder.tv_first_alphabet.setText("找到"+list.size()+"个联系人");
        }

        //条目点击
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //条目点击
                Intent intent = new Intent(context, InformationActivity.class);
                intent.putExtra("StaffId",contactBean.getStaffId());
                intent.putExtra("name",contactBean.getName());
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    static class ViewHolder {
        TextView tv_contact_name;
        TextView tv_first_alphabet;
        TextView tv_contact_department;
        CircleTextImage cti;

        public ViewHolder(View convertView) {
            tv_contact_name = (TextView) convertView.findViewById(R.id.tv_contact_name);
            tv_first_alphabet = (TextView) convertView.findViewById(R.id.tv_first_alphabet);
            cti = (CircleTextImage) convertView.findViewById(R.id.cti);
            tv_contact_department = (TextView) convertView.findViewById(R.id.tv_contact_department);
        }

        public static ViewHolder getHolder(View convertView) {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}

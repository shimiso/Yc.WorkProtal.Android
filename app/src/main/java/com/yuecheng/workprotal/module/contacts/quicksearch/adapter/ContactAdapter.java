package com.yuecheng.workprotal.module.contacts.quicksearch.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.module.contacts.quicksearch.Bean.ContactBean;
import com.yuecheng.workprotal.module.contacts.quicksearch.Bean.PinYinStyle;
import com.yuecheng.workprotal.widget.CircleTextImage;

import java.util.ArrayList;


public class ContactAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ContactBean.ResultBean.StaffsBean> list;
    public PinYinStyle sortToken;

    public ContactAdapter(Context context, ArrayList<ContactBean.ResultBean.StaffsBean> list) {
        super();
        this.context = context;
        this.list = list;
        sortToken = new PinYinStyle();
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
        ContactBean.ResultBean.StaffsBean contactBean = list.get(position);
        holder.tv_contact_name.setText(contactBean.getName());
        holder.cti.setText4CircleImage(contactBean.getName().subSequence(0, 1).toString());
        holder.tv_contact_department.setText("中医推拿师职位");
        String currentAlphabet = contactBean.getPinyin().charAt(0) + "";
        if (position > 0) {
            String lastAlphabet = list.get(position - 1).getPinyin().charAt(0) + "";
            //获取上一个item的首字母

            if (currentAlphabet.equals(lastAlphabet)) {
                //首字母相同，需要隐藏当前item的字母的TextView
                holder.tv_first_alphabet.setVisibility(View.GONE);
            } else {
                //不相同就要显示当前的首字母
                holder.tv_first_alphabet.setVisibility(View.VISIBLE);
                holder.tv_first_alphabet.setText(currentAlphabet);
            }
        } else {
            holder.tv_first_alphabet.setVisibility(View.VISIBLE);
            holder.tv_first_alphabet.setText(currentAlphabet);
        }

        //条目点击
        holder.tv_contact_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("tag", "条目点击！！");
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

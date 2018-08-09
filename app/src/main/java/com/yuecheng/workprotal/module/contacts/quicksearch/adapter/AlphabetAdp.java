package com.yuecheng.workprotal.module.contacts.quicksearch.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuecheng.workprotal.R;

import java.util.List;


/**
 * Created by ytx on 2016/11/15.
 */
public class AlphabetAdp extends BaseAdapter {
    private Context context;
    List<String> mList;

    public AlphabetAdp(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList!=null?mList.size():0;
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.contact_layout_alphabet,null);
            holder.tv_alphabet = (TextView) convertView.findViewById(R.id.tv_alphabet);
            convertView.setTag(holder);
        }else {
           holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_alphabet.setText(mList.get(position));
        return convertView;
    }
    class ViewHolder{
        TextView tv_alphabet;
    }
}

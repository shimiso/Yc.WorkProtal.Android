package com.yuecheng.workportal.widget;

import com.yuecheng.workportal.module.contacts.bean.ContactBean.StaffsBean;

import java.util.Comparator;


/**
 * 用来对ListView中的数据根据A-Z进行排序，前面两个if判断主要是将不是以汉字开头的数据放在后面
 */
public class PinyinComparator implements Comparator<StaffsBean> {

    public int compare(StaffsBean o1, StaffsBean o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o1.getPinyin().substring(0,1).equals("@")
                || o2.getPinyin().substring(0,1).equals("#")) {
            return -1;
        } else if (o1.getPinyin().substring(0,1).equals("#")
                || o2.getPinyin().substring(0,1).equals("@")) {
            return 1;
        } else {
            return o1.getPinyin().substring(0,1).compareTo(o2.getPinyin().substring(0,1));
        }
    }
}

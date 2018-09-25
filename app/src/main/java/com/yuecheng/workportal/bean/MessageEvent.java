package com.yuecheng.workportal.bean;

import org.json.JSONArray;

public  class MessageEvent{
    public int type = 0;

    /**打开招呼执行ROBOT语音对话框**/
    public static final Integer SMTZLU_VOCE_DIALOG = 0;
    public boolean isShow = false;
    public MessageEvent(int type,boolean isShow) {
        this.type = type;
        this.isShow = isShow;
    }
    /**返回给H5的生命体征json**/
    public static final Integer VITAL_SIGNS_JSON = 1;
    public String json = null;
    public MessageEvent(int type,String json) {
        this.type = type;
        this.json = json;
    }
    /**修改展示的语言**/
    public static final Integer SET_LANGUAGE_TEXT = 2;
    /**默认首页**/
    public static final Integer HOME_PAGE_TEXT = 3;




}
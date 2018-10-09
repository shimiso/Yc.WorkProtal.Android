package com.yuecheng.workportal.bean;

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
    /**刷新通讯录人员列表**/
    public static final Integer REFRESH_STAFF = 2;
    public int id;
    public MessageEvent(int type,int id) {
        this.type = type;
        this.id = id;
    }
    /**默认首页**/
    public static final Integer HOME_PAGE_TEXT = 3;




}
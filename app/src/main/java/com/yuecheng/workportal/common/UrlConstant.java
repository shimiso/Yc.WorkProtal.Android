package com.yuecheng.workportal.common;

import com.yuecheng.workportal.BuildConfig;

/**
 * 网络请求的常量
 */
public interface UrlConstant {
    //服务器地址
    String HTTP_HOST = BuildConfig.HTTP_HOST;

    String  GET_NEW_VERSION = HTTP_HOST + "";
    String  ADDRESSSTAFFQUEY = HTTP_HOST + "api/services/app/Staff/AddressStaffQuery";//通讯录整体人员
    String  STAFFBASICINFOGET = HTTP_HOST + "api/services/app/Staff/StaffBasicInfoGet";//获取人员详情
    String  ADDRESSTOPORGQUERY = HTTP_HOST + "api/services/app/Staff/AddressTopOrgQuery";//组织机构顶级
    String  ADDRESSORGQUERY = HTTP_HOST + "api/services/app/Staff/AddressOrgQuery";//组织机构子机构



    String SSO_LOGIN = "http://192.168.0.141:5000/connect/token";
    String SSO_IDENTITY = "http://192.168.0.141:5005/api/Identity";
}

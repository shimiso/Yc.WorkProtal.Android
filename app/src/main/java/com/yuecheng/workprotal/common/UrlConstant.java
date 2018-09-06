package com.yuecheng.workprotal.common;

import com.yuecheng.workprotal.BuildConfig;

/**
 * 网络请求的常量
 */
public interface UrlConstant {
    //服务器地址
    String HTTP_HOST = BuildConfig.HTTP_HOST;

    String  GET_NEW_VERSION = HTTP_HOST + "";
    String  ADDRESSSTAFFQUEY = HTTP_HOST + "api/services/app/Staff/AddressStaffQuery";
    String  STAFFBASICINFOGET = HTTP_HOST + "api/services/app/Staff/StaffBasicInfoGet";
}

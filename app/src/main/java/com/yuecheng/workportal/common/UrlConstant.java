package com.yuecheng.workportal.common;

import com.yuecheng.workportal.BuildConfig;

/**
 * 网络请求的常量
 */
public interface UrlConstant {
    //服务器地址
    String HTTP_HOST = BuildConfig.HTTP_HOST;

    String  GET_NEW_VERSION = HTTP_HOST + "";
    String  ADDRESSSTAFFQUEY = HTTP_HOST + "AddressBook/GetAddressStaffQuery";//通讯录整体人员
    String  GET_STAFF_PERMS = HTTP_HOST + "Identity/GetStaffPerms";//获取用户工作台
    String  ADDRESSTOPORGQUERY = HTTP_HOST + "AddressBook/GetAddressTopOrgQuery";//组织机构顶级
    String  ADDRESSORGQUERY = HTTP_HOST + "AddressBook/GetAddressOrgQuery";//组织机构子机构
    String  CLOCK_IN = HTTP_HOST + "api/attendance/in";//考情打卡


    String  UPLOAD = "http://192.168.0.141:5007/api/file/upload";//上传头像
    String SSO_LOGIN = "http://192.168.0.141:5000/connect/token";
    String SSO_IDENTITY = HTTP_HOST + "Identity/GetBasicInfo";//获取人员详情

    //H5 URL
    String  CARE_PLAN = "http://omstest.gongheyuan.com/portal/#/AddUser";//照护计划
    String  MY_PROFILE = "http://omstest.gongheyuan.com/portal/#/MyProfile";//我的资料
    String  SALARY_QUERY_HR = "http://omstest.gongheyuan.com/portal/#/SalaryQueryHR";//薪资查询
    String  CONTRACT = "http://omstest.gongheyuan.com/portal/#/Contract";//合同查询
    String  HOLIDAY_QUERY = "http://omstest.gongheyuan.com/portal/#/HolidayQuery";//假期查询
    String  FLEXIBLE_POOL = "http://omstest.gongheyuan.com/portal/#/FlexiblePool";//Flexible Pool
    String  OVER_TIME = "http://omstest.gongheyuan.com/portal/#/OverTime";//加班记录
}

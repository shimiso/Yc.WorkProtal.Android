package com.yuecheng.workportal.common;

/**
 *
 * 语音指令
 * @author 霍长生
 */

public interface instruction {

     //通讯录
     String FIND = "find_contacts"; //查找
     String CALL = "call_contacts"; //打电话
     String TEXT = "text_contacts"; //发短信
     //会议
     String BOOK_MEETING = "book_meeting"; //订会议室
     String CANCEL_MEETING = "cancel_meeting"; //取消会议室
     String MY_MEETING = "my_meeting"; //我的会议
     //新闻公告
     String SEARCH_NEWS = "search_news"; //查找新闻公告
     //待办
     String MY_TO_DO_LIST = "my_to_do_list"; //查待办
     //IT服务
     String IT_SERVICE = "it_service"; //IT服务
     //休假
     String TAKE_LEAVE = "take_leave"; //请假
     String MY_LEAVE = "my_leave"; //我的请假
     //HR
     String MY_SALARY = "my_salary"; //我的工资
     String MY_EMPLOYMENT_CONTRACT = "my_employment_contract"; //我的劳动合同
     String APPLY_OVERTIME = "apply_overtime"; //申请加班
     //行政
     String STAMP_APPLICATION = "stamp_application"; //申请盖章
     String NAME_CARD = "name_card"; //申请名片
     //财务
     String REIMBURSE = "reimburse"; //申请付款
     String CHECK_PR = "check_pr"; //查付款
     String BUSINESS_APPLICATION = "business_application"; //业务请示
     String CHECK_ON_BUSINESS_APPLICATION = "check_on_business_application"; //查业务请示
     String BUDGET_ADJUSTMENT = "budget_adjustment"; //申请借款
     String CHECK_ON_BUDGET_ADJUSTMENT = "check_on_budget_adjustment"; //查借款
     //照护计划
     String ZHAOHU_PLAN = "zhaohu_plan"; //照护计划
     String VITAL_SIGNS_ENTER = "vital_signs_enter";//生命体征录入

}

package com.yuecheng.workportal.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xyz on 2017/1/9.
 */

public class DateTime {
    private int hour;
    private int day;
    private Date date;
    private DateFormat df;
    private String pointText;
    private Long time;
    private Long now;
//    private

    public DateTime() {
        hour = 60 * 60 * 1000;
        day = (60 * 60 * 24) * 1000;
        time = 28800L;
        now = new Date().getTime();
        pointText = "1970-01-01";
    }

    //获得当天0点时间
    public static Long getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (Long) cal.getTimeInMillis();
    }

    //获取星期几
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    //获取时间点
    public  String getTimePoint(Long time) {
        //现在时间
        now = new Date().getTime();

        //时间点比当天零点早
        if (time <= now && time != null) {
            date = new Date(time);
            if (time < getTimesmorning()) {
                if (time >= getTimesmorning() - day) {//比昨天零点晚
                    pointText = "昨天";
                    return pointText;
                } else {//比昨天零点早
                    if (time >= getTimesmorning() - 6 * day) {//比七天前的零点晚，显示星期几

                        return getWeekOfDate(date);
                    } else {//显示具体日期
                        df = new SimpleDateFormat("yyyy-MM-dd");
                        pointText = df.format(date);
                        return pointText;
                    }
                }

            } else {//无日期时间,当天内具体时间
                df = new SimpleDateFormat("HH:mm");
                pointText = df.format(date);
                return pointText;

            }

        }
        return pointText;
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        String dayBefore = new SimpleDateFormat("yyyy年MM月dd日  EEEE").format(c
                .getTime());
        return dayBefore;
    }
    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        String dayBefore = new SimpleDateFormat("yyyy年MM月dd日  EEEE").format(c
                .getTime());
        return dayBefore;
    }
}

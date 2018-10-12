package com.yuecheng.workportal.module.mycenter.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ldf.calendar.Utils;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.module.mycenter.adapter.ExampleAdapter;
import com.yuecheng.workportal.module.mycenter.bean.CalendarBean;
import com.yuecheng.workportal.utils.DateUtil;
import com.yuecheng.workportal.utils.LoadViewUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkAttendanceActivity extends BaseActivity {
    @BindView(R.id.show_year_view)
    TextView tvYear;

    @BindView(R.id.show_month_view)
    TextView tvMonth;

//    @BindView(R.id.content)
//    CoordinatorLayout content;

    @BindView(R.id.calendar_view)
    MonthPager monthPager;

    @BindView(R.id.list)
    RecyclerView rvToDoList;
    @BindView(R.id.late_tv)
    TextView lateTv; //迟到
    @BindView(R.id.leave_early_tv)
    TextView leaveEarlyTv; //早退
    @BindView(R.id.absenteeism_tv)
    TextView absenteeismTv; //旷工
    @BindView(R.id.leave_tv)
    TextView leaveTv; //请假

    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private Context context;
    private CalendarDate currentDate;
    private boolean initiated = false;
    private ExampleAdapter exampleAdapter;
    private long stringToDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_attendance);
        ButterKnife.bind(this);
        context = this;
        //此处强行setViewHeight，毕竟你知道你的日历牌的高度
        monthPager.setViewHeight(Utils.dpi2px(context, 270));
        rvToDoList.setHasFixedSize(true);
        //这里用线性显示 类似于listview
        rvToDoList.setLayoutManager(new LinearLayoutManager(this));
        exampleAdapter = new ExampleAdapter(this,true);
        rvToDoList.setAdapter(exampleAdapter);
        stringToDate = DateUtil.getStringToDate(DateUtil.geturrentTime("yyyy-MM-dd"), "yyyy-MM-dd");
        initCurrentDate();
        initCalendarView();
        initMarkData();

    }

    private List<CalendarBean> getSchedule(Long timelong) {
        List<CalendarBean> calendarBeans = new ArrayList<>();

        String CALANDER_EVENT_URL = "content://com.android.calendar/events";
        Uri uri = Uri.parse(CALANDER_EVENT_URL);
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        while (cursor.moveToNext()) {

            int columnCount = cursor.getColumnCount();
            Log.e("日历", "columnCount :" + columnCount);//多少个属性
            //for (int i = 0; i < columnCount; i++) {
            //获取到属性的名称
            //  String columnName = cursor.getColumnName(i);
            //获取到属性对应的值
            //   String message = cursor.getString(cursor.getColumnIndex(columnName));
            //打印属性和对应的值
//                Log.i("日历", columnName + " : " + message);


            //事件的标题
            String title = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
            //事件的起始时间
            String dtstart = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DTSTART));
            //事件的结束时间 ，如果事件是每天/周,那么就没有结束时间
            String dtend = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DTEND));
            //事件的描述
            String description = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION));
            //事件的重复规律
            String rrule = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.RRULE));
            //事件的复发日期。通常RDATE要联合RRULE一起使用来定义一个重复发生的事件的合集。
            String rdate = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.RDATE));
            //事件是否是全天的
            String allDay = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.ALL_DAY));
            //事件的地点
            String location = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION));
            //事件持续时间，例如“PT1H”表示事件持续1小时的状态， “P2W”指明2周的持续时间。P3600S表示3600秒
            String duration = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DURATION));
            if(Long.valueOf(dtstart) >= timelong && Long.valueOf(dtstart) < timelong+86400000){
                calendarBeans.add(new CalendarBean(title,dtstart,dtend,description,location));
            }
            Log.i("日历", "开始时间" + " : " + dtstart+
                    "\n结束时间 ："+dtend+
                    "\n标题 ："+title+
                    "\n事件描述 ："+description+
                    "\n重复规律 ："+rrule+
                    "\n事件的地点 ："+location);
        }
       // viewUtil.stopLoading();

        return calendarBeans;
    }

    /**
     * onWindowFocusChanged回调时，将当前月的种子日期修改为今天
     *
     * @return void
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !initiated) {
            refreshMonthPager();
            initiated = true;
        }
    }

    /*
     * 如果你想以周模式启动你的日历，请在onResume是调用
     * Utils.scrollTo(content, rvToDoList, monthPager.getCellHeight(), 200);
     * calendarAdapter.switchToWeek(monthPager.getRowIndex());
     * */
    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * 加载数据
     */
    protected void loadData() {
        exampleAdapter.showLoadingView();
        if (!androidUtil.hasInternetConnected()) {
            exampleAdapter.showNoNetView(v -> loadData());
        }else {
            List<CalendarBean> schedule = getSchedule(stringToDate);
            if(schedule.size()==0){
                exampleAdapter.showEmptyView(v -> loadData());
            }else{
                exampleAdapter.onRefresh(schedule);
            }
        }
    }
    @OnClick({R.id.back_iv, R.id.back_today_button, R.id.next_month_img, R.id.last_month_img})
    protected void click(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.back_today_button: //今天
                onClickBackToDayBtn();
                break;
            case R.id.next_month_img: //下一月
                monthPager.setCurrentItem(monthPager.getCurrentPosition() + 1);
                break;
            case R.id.last_month_img: //上一月
                monthPager.setCurrentItem(monthPager.getCurrentPosition() - 1);
                break;
//            case R.id.scroll_switch: //切换周月
//                if (calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.WEEK) {
//                    Utils.scrollTo(content, rvToDoList, monthPager.getViewHeight(), 200);
//                    calendarAdapter.switchToMonth();
//                } else {
//                    Utils.scrollTo(content, rvToDoList, monthPager.getCellHeight(), 200);
//                    calendarAdapter.switchToWeek(monthPager.getRowIndex());
//                }
//                break;
        }
    }

    /**
     * 初始化currentDate
     *
     * @return void
     */
    private void initCurrentDate() {
        currentDate = new CalendarDate();
        tvYear.setText(currentDate.getYear() + "年");
        tvMonth.setText(currentDate.getMonth() + "");
    }

    /**
     * 初始化CustomDayView，并作为CalendarViewAdapter的参数传入
     */
    private void initCalendarView() {
        initListener();
        CustomDayView customDayView = new CustomDayView(context, R.layout.custom_day);
        calendarAdapter = new CalendarViewAdapter(
                context,
                onSelectDateListener,
                CalendarAttr.WeekArrayType.Monday,
                customDayView);
        calendarAdapter.setOnCalendarTypeChangedListener(type -> rvToDoList.scrollToPosition(0));
        initMarkData();
        initMonthPager();
    }

    /**
     * 初始化标记数据，HashMap的形式，可自定义
     * 如果存在异步的话，在使用setMarkData之后调用 calendarAdapter.notifyDataChanged();
     */
    @SuppressLint("StaticFieldLeak")
    private void initMarkData() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                HashMap<String, String> markData = new HashMap<>();
                markData.put("2018-10-12", "1");
                markData.put("2018-10-13", "0");
                markData.put("2018-10-14", "1");
                markData.put("2018-10-15", "0");
                markData.put("2018-10-16", "0");
                markData.put("2018-10-17", "0");
                markData.put("2018-10-18", "0");
                markData.put("2018-10-19", "0");
                calendarAdapter.setMarkData(markData);
            }
        }.execute();

    }

    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                refreshClickDate(date);
                stringToDate = DateUtil.getStringToDate( date.toString(), "yyyy-MM-dd");
                List<CalendarBean> schedule = getSchedule(stringToDate);
                if(schedule.size()==0){
                    exampleAdapter.showEmptyView(v -> loadData());
                }else{
                    exampleAdapter.onRefresh(schedule);
                }
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                monthPager.selectOtherMonth(offset);
            }
        };
    }


    private void refreshClickDate(CalendarDate date) {
        currentDate = date;
        tvYear.setText(date.getYear() + "年");
        tvMonth.setText(date.getMonth() + "");
    }

    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     *
     * @return void
     */
    private void initMonthPager() {
        monthPager.setAdapter(calendarAdapter);
        monthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        monthPager.setPageTransformer(false, (page, position) -> {
            position = (float) Math.sqrt(1 - Math.abs(position));
            page.setAlpha(position);
        });
        monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    tvYear.setText(date.getYear() + "年");
                    tvMonth.setText(date.getMonth() + "");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void onClickBackToDayBtn() {
        refreshMonthPager();
    }

    private void refreshMonthPager() {
        CalendarDate today = new CalendarDate();
        calendarAdapter.notifyDataChanged(today);
        tvYear.setText(today.getYear() + "年");
        tvMonth.setText(today.getMonth() + "");
    }

    //更换效果(暂时无此功能)
    private void refreshSelectBackground() {
        ThemeDayView themeDayView = new ThemeDayView(context, R.layout.custom_day_focus);
        calendarAdapter.setCustomDayRenderer(themeDayView);
        calendarAdapter.notifyDataSetChanged();
        calendarAdapter.notifyDataChanged(new CalendarDate());
    }
}

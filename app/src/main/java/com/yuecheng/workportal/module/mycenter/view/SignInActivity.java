package com.yuecheng.workportal.module.mycenter.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.DPoint;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.common.Constants;
import com.yuecheng.workportal.module.mycenter.adapter.ClockInAdapter;
import com.yuecheng.workportal.module.mycenter.bean.ClockInBean;
import com.yuecheng.workportal.module.mycenter.presenter.UserPresenter;
import com.yuecheng.workportal.utils.DateTime;
import com.yuecheng.workportal.utils.DateUtil;
import com.yuecheng.workportal.widget.ConfirmDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.amap.api.fence.GeoFenceClient.GEOFENCE_IN;
import static com.amap.api.fence.GeoFenceClient.GEOFENCE_OUT;
import static com.amap.api.fence.GeoFenceClient.GEOFENCE_STAYED;

/**
 * 圆形地理围栏
 *
 * @author hongming.wang
 * @since 3.2.0
 */
public class SignInActivity extends BaseActivity implements GeoFenceListener, LocationSource, AMapLocationListener {
    @BindView(R.id.attendance_rl)
    RecyclerView attendanceRl;
    @BindView(R.id.positioning_tv)
    TextView positioningTv;
    @BindView(R.id.today)
    TextView today;
    @BindView(R.id.date)
    TextView date_tv;
    @BindView(R.id.clock_in_rl)
    RelativeLayout clockInRl;
    private ClockInAdapter clockInAdapter;
    private String[] datearr = new String[3];//保存当前显示的年月日
    private List<ClockInBean> clockInBeanListAll = new ArrayList<ClockInBean>();
    private List<ClockInBean> clockInBeanList = new ArrayList<ClockInBean>();
    /**
     * 用于显示当前的位置
     **/
    private AMapLocationClient mlocationClient;
    private OnLocationChangedListener mListener;
    private AMapLocationClientOption mLocationOption;
    private MapView mMapView;
    private AMap mAMap;
    // 当前的坐标点集合，主要用于进行地图的可视区域的缩放
    private LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
    // 地理围栏客户端
    private GeoFenceClient fenceClient = null;
    // 地理围栏的广播action
    private static final String GEOFENCE_BROADCAST_ACTION = "com.example.geofence.round";
    // 记录已经添加成功的围栏
    private HashMap<String, GeoFence> fenceMap = new HashMap<>();
    /**
     * 规定到达距离范围距离
     **/
    private float fenceRadius = 100.0F;
    private double mDistance_international = 0;
    private double mDistance_group = 0;
    AMapLocation currentLocation;
    DPoint centerPoint, centerPoint1;
    private String aoiName;
    private UserPresenter userPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_center_sign_in);
        ButterKnife.bind(this);
        setTitle("打卡签到");
        testingAuthority();
        userPresenter = new UserPresenter(this);
        // 初始化地理围栏
        fenceClient = new GeoFenceClient(getApplicationContext());
        mMapView = findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        init();
        addRoundFence();

    }

    //权限的检测
    void testingAuthority() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            ConfirmDialog dialog = ConfirmDialog.createDialog(this);
            dialog.setDialogTitle("提示");
            dialog.setCancelable(false);
            dialog.setDialogMessage("请开启位置服务，获取精准定位");
            dialog.setOnButton1ClickListener("取消", null,
                    new ConfirmDialog.OnButton1ClickListener() {
                        @Override
                        public void onClick(View view, DialogInterface dialog) {
                            dialog.cancel();
                        }
                    });
            dialog.setOnButton2ClickListener("去设置", null,
                    new ConfirmDialog.OnButton2ClickListener() {
                        @Override
                        public void onClick(View view, DialogInterface dialog) {
                            dialog.cancel();
                            // 转到手机设置界面，用户设置GPS
                            Intent intentl = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intentl, 0); //
                        }
                    });
            dialog.show();
        }
    }
    void init() {
        findViewById(R.id.back_iv).setOnClickListener(view -> finish());
        if (mAMap == null) {
            mAMap = mMapView.getMap();
            mAMap.getUiSettings().setRotateGesturesEnabled(false);
            mAMap.moveCamera(CameraUpdateFactory.zoomBy(6));
            setUpMap();
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(GEOFENCE_BROADCAST_ACTION);
        registerReceiver(mGeoFenceReceiver, filter);

        /** 创建pendingIntent */
        fenceClient.createPendingIntent(GEOFENCE_BROADCAST_ACTION);
        fenceClient.setGeoFenceListener(this);
        /** 设置地理围栏的触发行为,GEOFENCE_IN 进入地理围栏 GEOFENCE_OUT 退出地理围栏 GEOFENCE_STAYED 停留在地理围栏内10分钟*/
        fenceClient.setActivateAction(GEOFENCE_IN | GEOFENCE_OUT | GEOFENCE_STAYED);

        //设置当前系统日期
        Date date = new Date();
        String today = DateUtil.geturrentTime("MM月dd日 EEEE");
        date_tv.setText(today);
        //设置RecyclerView管理器
        attendanceRl.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        clockInAdapter = new ClockInAdapter(this);
        //设置添加或删除item时的动画，这里使用默认动画
        attendanceRl.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        attendanceRl.setAdapter(clockInAdapter);

        datearr[0] = String.valueOf(date.getYear() + 1900);
        datearr[1] = String.valueOf(date.getMonth() + 1);
        datearr[2] = String.valueOf(date.getDate());
        addData();//添加假数据
        initData();
    }

    private void initData() {
        clockInBeanList.clear();
        List<ClockInBean> screening = screening(clockInBeanListAll, datearr[0] + "-" + datearr[1] + "-" + datearr[2]);//筛选数据
        if (screening != null && screening.size() > 0) {
            clockInAdapter.onRefresh(screening);
        } else {
            clockInAdapter.showEmptyView(v -> {
                initData();
            });
        }
    }


    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        mAMap.setLocationSource(this);// 设置定位监听
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(
                BitmapDescriptorFactory.fromResource(R.mipmap.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        // 自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        // 将自定义的 myLocationStyle 对象添加到地图上
        mAMap.setMyLocationStyle(myLocationStyle);
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        try {
            unregisterReceiver(mGeoFenceReceiver);
        } catch (Throwable e) {
        }
        if (null != fenceClient) {
            fenceClient.removeGeoFence();
        }
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    private void drawFence(GeoFence fence) {
        switch (fence.getType()) {
            case GeoFence.TYPE_ROUND:
            case GeoFence.TYPE_AMAPPOI:
                LatLng center = new LatLng(fence.getCenter().getLatitude(),
                        fence.getCenter().getLongitude());
                // 绘制一个圆形
                mAMap.addCircle(new CircleOptions().center(center)
                        .radius(fence.getRadius()).strokeColor(Constants.STROKE_COLOR)
                        .fillColor(Constants.FILL_COLOR).strokeWidth(Constants.STROKE_WIDTH));
                boundsBuilder.include(center);
                break;
            case GeoFence.TYPE_POLYGON:
            case GeoFence.TYPE_DISTRICT:
                break;
            default:
                break;
        }
    }

    void drawFence2Map() {
        new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (new Object()) {
                        if (null == fenceList || fenceList.isEmpty()) {
                            return;
                        }
                        for (GeoFence fence : fenceList) {
                            if (fenceMap.containsKey(fence.getFenceId())) {
                                continue;
                            }
                            drawFence(fence);
                            fenceMap.put(fence.getFenceId(), fence);
                        }
                    }
                } catch (Throwable e) {

                }
            }
        }.start();
    }

    List<GeoFence> fenceList = new ArrayList<>();

    @Override
    public void onGeoFenceCreateFinished(final List<GeoFence> geoFenceList,
                                         int errorCode, String customId) {
        if (errorCode == GeoFence.ADDGEOFENCE_SUCCESS) {
            fenceList.addAll(geoFenceList);
            drawFence2Map();
        } else {
            Toast.makeText(SignInActivity.this, "地图加载错误：" + errorCode, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 接收触发围栏后的广播,当添加围栏成功之后，会立即对所有围栏状态进行一次侦测，如果当前状态与用户设置的触发行为相符将会立即触发一次围栏广播；
     * 只有当触发围栏之后才会收到广播,对于同一触发行为只会发送一次广播不会重复发送，除非位置和围栏的关系再次发生了改变。
     */
    private BroadcastReceiver mGeoFenceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 接收广播
            if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {
                Bundle bundle = intent.getExtras();
                String customId = bundle
                        .getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                GeoFence fence = bundle.getParcelable(GeoFence.BUNDLE_KEY_FENCE);

                //status标识的是当前的围栏状态，不是围栏行为
                int status = bundle.getInt(GeoFence.BUNDLE_KEY_FENCESTATUS);
                StringBuffer sb = new StringBuffer();
                switch (status) {
                    case GeoFence.STATUS_LOCFAIL:
                        sb.append("定位失败");
                        break;
                    case GeoFence.STATUS_IN:
                        sb.append("您已在公司范围内");
                        break;
                    case GeoFence.STATUS_OUT:
                        sb.append("您不在公司范围之内");
                        break;
                    case GeoFence.STATUS_STAYED:
                        sb.append("停留在公司范围内");
                        break;
                    default:
                        break;
                }
                if (status != GeoFence.STATUS_LOCFAIL) {
                    if (!TextUtils.isEmpty(customId)) {
                        sb.append(" customId: " + customId);
                    }
                    sb.append(" fenceId: " + fence.getFenceId());
                }
               // Toast.makeText(SignInActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
            }
        }
    };


    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                currentLocation = amapLocation;
                //Toast.makeText(SignInActivity.this,"定位成功",Toast.LENGTH_LONG).show();
                LatLng groupend = new LatLng(centerPoint1.getLatitude(), centerPoint1.getLongitude());//乐成集团
                LatLng internationalend = new LatLng(centerPoint.getLatitude(), centerPoint.getLongitude());//国际学校
                LatLng start = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                //位置信息（简要信息）
                aoiName = currentLocation.getAoiName();
                positioningTv.setText(" " + aoiName);
                mDistance_group = AMapUtils.calculateLineDistance(start, groupend);//乐成集团
                mDistance_international = AMapUtils.calculateLineDistance(start, internationalend); //国际学校
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } else {
                positioningTv.setText(" 定位失败");
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
//				Toast.makeText(SignInActivity.this,errText, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            // 设置定位监听
            mlocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
            // 只是为了获取当前位置，所以设置为单次定位
            mLocationOption.setOnceLocation(true);
            // 设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 添加圆形围栏
     *
     * @since 3.2.0
     */
    private void addRoundFence() {
        centerPoint1 = new DPoint(39.896387976936154, 116.4766156118845);
        centerPoint = new DPoint(39.89791485176392, 116.47434780410022);

        LatLng latLng1 = new LatLng(centerPoint1.getLatitude(), centerPoint1.getLongitude());
        Marker marker1 = mAMap.addMarker(new MarkerOptions().position(latLng1).title("乐成集团"));
        marker1.showInfoWindow();

        LatLng latLng = new LatLng(centerPoint.getLatitude(), centerPoint.getLongitude());
        Marker marker = mAMap.addMarker(new MarkerOptions().position(latLng).title("乐成国际学校").snippet("Beijing City International School"));
        marker.showInfoWindow();
        fenceClient.addGeoFence(centerPoint1, fenceRadius, "乐成集团");
        fenceClient.addGeoFence(centerPoint, fenceRadius, "乐成国际学校");
    }

    @OnClick({R.id.last_month_img, R.id.next_month_img, R.id.clock_in_button, R.id.my_work_attendance, R.id.reposition_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.last_month_img: //前一天
                String specifiedDayBefore = DateTime.getSpecifiedDayBefore(datearr[0] + "-" + datearr[1] + "-" + datearr[2]);
                ChangeData(specifiedDayBefore);
                break;
            case R.id.next_month_img: //后一天
                String specifiedDayAfter = DateTime.getSpecifiedDayAfter(datearr[0] + "-" + datearr[1] + "-" + datearr[2]);
                ChangeData(specifiedDayAfter);
                break;
            case R.id.clock_in_button: //打卡
                String time = DateUtil.geturrentTime("yyyy-MM-dd HH:mm");
             //   ToastUtil.info(SignInActivity.this, time + "==" + aoiName);
                if (mDistance_group <= fenceRadius || mDistance_international <= fenceRadius) { //在集团 或者 学校范围
                    AccessClockIn(time, aoiName, true, "");

                    clockInBeanListAll.add(new ClockInBean(time, aoiName, true, ""));
                    initData();
                } else {
                    notScope(); //不在范围弹窗
                }
                break;
            case R.id.my_work_attendance: //考勤查询
                //数据带过去为临时代码
                Intent intent = new Intent(this, WorkAttendanceActivity.class);
                intent.putExtra("clockInBeanListAll", (Serializable) clockInBeanListAll);
                startActivity(intent);
                break;
            case R.id.reposition_tv: //重新定位
                positioningTv.setText("");
                mlocationClient = new AMapLocationClient(this);
                mLocationOption = new AMapLocationClientOption();
                // 设置定位监听
                mlocationClient.setLocationListener(this);
                // 设置为高精度定位模式
                mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
                // 只是为了获取当前位置，所以设置为单次定位
                mLocationOption.setOnceLocation(true);
                // 设置定位参数
                mlocationClient.setLocationOption(mLocationOption);
                mlocationClient.startLocation();
                break;
        }
    }

    //日期发生改变
    private void ChangeData(String specifiedDay) {
        String[] year = specifiedDay.split("年");
        String[] month = year[1].split("月");
        String[] date = month[1].split("日");
        date_tv.setText(year[1]);
        long stringToDate1 = DateUtil.getStringToDate(specifiedDay, "yyyy年MM月dd日");
        long newdate1 = DateUtil.getStringToDate(new Date().toString(), "yyyy-MM-dd");
        if (stringToDate1 <= newdate1 && stringToDate1+86400000 > newdate1) {
            clockInRl.setVisibility(View.VISIBLE);
            today.setVisibility(View.VISIBLE);
        }else{
            clockInRl.setVisibility(View.GONE);
            today.setVisibility(View.GONE);
        }
        datearr[0] = year[0];
        datearr[1] = month[0];
        datearr[2] = date[0];
        initData();
    }

    //打卡请求
    private void AccessClockIn(String time, String aoiName, boolean intraArea, String memo) {
        userPresenter.clockIn(time, aoiName, intraArea, memo, new CommonPostView<Boolean>() {
            @Override
            public void postSuccess(ResultInfo<Boolean> resultInfo) {
                Boolean result = resultInfo.getResult();
                if (result) {
                    Toast.makeText(SignInActivity.this, "打卡成功", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void postError(String errorMsg) {
            //    Toast.makeText(SignInActivity.this, "打卡失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //不在打卡范围内弹窗
    private void notScope() {
        ValidationDialog validationDialog = new ValidationDialog(this);
        validationDialog.setTitleText(getString(R.string.not_scope)); //主标题
        validationDialog.setSubtitleTitleText(getString(R.string.not_scope), View.GONE); //副标题，是否显示
        validationDialog.et_dialog_one.setHint(getString(R.string.input_attendance_note)); //Hint语句
        //设置输入框高度
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) validationDialog.et_dialog_one.getLayoutParams();
        lp.height = 200;
        validationDialog.et_dialog_one.setLayoutParams(lp);

        validationDialog.showDialog();
        validationDialog.setClicklistener(new ValidationDialog.ClickListenerInterface() {
            @Override
            public void onCancleClick() {
                validationDialog.dismissDialog();
            }

            @Override
            public void onConfirmClick() {
                String s = validationDialog.et_dialog_one.getText().toString();

                validationDialog.dismissDialog();
            }
        });
    }

    private List<ClockInBean> screening(List<ClockInBean> clockInBeanListAll, String time) {
        for (int i = 0; i < clockInBeanListAll.size(); i++) {
            String[] split1 = clockInBeanListAll.get(i).getTime().split("\\s+");
            long stringToDate = DateUtil.getStringToDate(time, "yyyy-MM-dd");
            long stringToDate1 = DateUtil.getStringToDate(split1[0], "yyyy-MM-dd");
            if (stringToDate == stringToDate1) {
                this.clockInBeanList.add(clockInBeanListAll.get(i));
            }
        }
        return clockInBeanList;
    }

    private void addData() {
        ClockInBean clockInBean1 = new ClockInBean("2018-10-15 08:30", "乐成豪丽", true, "备注");
        ClockInBean clockInBean2 = new ClockInBean("2018-10-15 18:30", "乐成豪丽", true, "备注");
        ClockInBean clockInBean3 = new ClockInBean("2018-10-14 08:30", "乐成豪丽", true, "备注");
        ClockInBean clockInBean4 = new ClockInBean("2018-10-14 18:30", "乐成豪丽", true, "备注");
        ClockInBean clockInBean5 = new ClockInBean("2018-10-16 08:30", "乐成豪丽", true, "备注");
        ClockInBean clockInBean6 = new ClockInBean("2018-10-16 18:30", "乐成豪丽", true, "备注");

        clockInBeanListAll.add(clockInBean1);
        clockInBeanListAll.add(clockInBean2);
        clockInBeanListAll.add(clockInBean3);
        clockInBeanListAll.add(clockInBean4);
        clockInBeanListAll.add(clockInBean5);
        clockInBeanListAll.add(clockInBean6);
    }
}

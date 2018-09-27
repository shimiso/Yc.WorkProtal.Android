
package com.yuecheng.workportal.module.mycenter.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
import com.yuecheng.workportal.common.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 圆形地理围栏
 * 
 * @author hongming.wang
 * @since 3.2.0
 */
public class SignInActivity extends BaseActivity
		implements
        OnClickListener,
			GeoFenceListener,
        LocationSource,
			AMapLocationListener,
        OnCheckedChangeListener {
	/** 用于显示当前的位置 **/
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
	TextView mDistance_tv,mTime_tv;
	RelativeLayout commit_bt;
	/** 规定到达距离范围距离 **/
	private float fenceRadius = 100.0F;
	private double mDistance = 0;
	AMapLocation currentLocation;
	DPoint centerPoint,centerPoint1;
	private Handler mHandler = new Handler();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_center_sign_in);
		setTitle("打卡签到");
		// 初始化地理围栏
		fenceClient = new GeoFenceClient(getApplicationContext());
		mMapView = findViewById(R.id.map);
		mMapView.onCreate(savedInstanceState);
		init();
		addRoundFence();
	}

	void init() {
		mDistance_tv = findViewById(R.id.distance_tv);
		mTime_tv = findViewById(R.id.arriver_timetv);
		commit_bt = findViewById(R.id.arriver_bt);
		commit_bt.setOnClickListener(this);
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

		/**
		 * 创建pendingIntent
		 */
		fenceClient.createPendingIntent(GEOFENCE_BROADCAST_ACTION);
		fenceClient.setGeoFenceListener(this);
		/**
		 * 设置地理围栏的触发行为,默认为进入
		 */
		fenceClient.setActivateAction(GeoFenceClient.GEOFENCE_IN);

		mHandler.post(run);//设置系统时间
	}




	/**
	 * 设置系统时间
	 */
	private Runnable run = new Runnable() {
		@Override
		public void run() {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss
			Date date = new Date(System.currentTimeMillis());//获取当前时间
			mTime_tv.setText(simpleDateFormat.format(date)); //更新时间
			mHandler.postDelayed(run, 1000);
		}
	};
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
			case GeoFence.TYPE_ROUND :
			case GeoFence.TYPE_AMAPPOI :
				LatLng center = new LatLng(fence.getCenter().getLatitude(),
						fence.getCenter().getLongitude());
				// 绘制一个圆形
				mAMap.addCircle(new CircleOptions().center(center)
						.radius(fence.getRadius()).strokeColor(Constants.STROKE_COLOR)
						.fillColor(Constants.FILL_COLOR).strokeWidth(Constants.STROKE_WIDTH));
				boundsBuilder.include(center);
				break;
			case GeoFence.TYPE_POLYGON :
			case GeoFence.TYPE_DISTRICT :
				break;
			default :
				break;
		}
		// // 设置所有maker显示在当前可视区域地图中
		// LatLngBounds bounds = boundsBuilder.build();
		// mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
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
			Toast.makeText(SignInActivity.this,"地图加载错误："+errorCode, Toast.LENGTH_LONG).show();
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
					case GeoFence.STATUS_LOCFAIL :
						sb.append("定位失败");
						break;
					case GeoFence.STATUS_IN :
						sb.append("您已在公司范围内");
						break;
					case GeoFence.STATUS_OUT :
						sb.append("您不在公司范围之内");
						break;
					case GeoFence.STATUS_STAYED :
						sb.append("停留在公司范围内");
						break;
					default :
						break;
				}
				if(status != GeoFence.STATUS_LOCFAIL){
					if(!TextUtils.isEmpty(customId)){
						sb.append(" customId: " + customId);
					}
					sb.append(" fenceId: " + fence.getFenceId());
				}
				Toast.makeText(SignInActivity.this,sb.toString(), Toast.LENGTH_LONG).show();
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

				LatLng end= new LatLng(centerPoint1.getLatitude(), centerPoint1.getLongitude());
				LatLng start = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

				mDistance = AMapUtils.calculateLineDistance(start, end);
				mDistance_tv.setText("距离乐成集团：" + mDistance + "米");

				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
			} else {
				String errText = "定位失败," + amapLocation.getErrorCode() + ": "
						+ amapLocation.getErrorInfo();
				Log.e("AmapErr", errText);
				Toast.makeText(SignInActivity.this,errText, Toast.LENGTH_LONG).show();
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



	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		/*switch (buttonView.getId()) {
			case R.id.cb_alertIn :
				if (isChecked) {
					activatesAction |= GeoFenceClient.GEOFENCE_IN;
				} else {
					activatesAction = activatesAction
							& (GeoFenceClient.GEOFENCE_OUT
									| GeoFenceClient.GEOFENCE_STAYED);
				}
				break;
			case R.id.cb_alertOut :
				if (isChecked) {
					activatesAction |= GeoFenceClient.GEOFENCE_OUT;
				} else {
					activatesAction = activatesAction
							& (GeoFenceClient.GEOFENCE_IN
									| GeoFenceClient.GEOFENCE_STAYED);
				}
				break;
			case R.id.cb_alertStated :
				if (isChecked) {
					activatesAction |= GeoFenceClient.GEOFENCE_STAYED;
				} else {
					activatesAction = activatesAction
							& (GeoFenceClient.GEOFENCE_IN
									| GeoFenceClient.GEOFENCE_OUT);
				}
				break;
			default :
				break;
		}
		if (null != fenceClient) {
			fenceClient.setActivateAction(activatesAction);
		}*/
	}
	/**
	 * 添加圆形围栏
	 * 
	 * @since 3.2.0
	 * @author hongming.wang
	 *
	 */
	private void addRoundFence() {
		centerPoint1 = new DPoint(39.896387976936154,116.4766156118845);
		centerPoint = new DPoint(39.89791485176392,116.47434780410022);

		LatLng latLng1 = new LatLng(centerPoint1.getLatitude(),centerPoint1.getLongitude());
		Marker marker1 =mAMap.addMarker(new MarkerOptions().position(latLng1).title("乐成集团"));
		marker1.showInfoWindow();

		LatLng latLng = new LatLng(centerPoint.getLatitude(),centerPoint.getLongitude());
		Marker marker =mAMap.addMarker(new MarkerOptions().position(latLng).title("乐成国际学校").snippet("Beijing City International School"));
		marker.showInfoWindow();
		fenceClient.addGeoFence(centerPoint1, fenceRadius, "乐成集团");
		fenceClient.addGeoFence(centerPoint, fenceRadius, "乐成国际学校");
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.arriver_bt) {
			if (mDistance <= fenceRadius) {
				Toast.makeText(this, "打卡成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "外勤打卡", Toast.LENGTH_SHORT).show();
			}

		}
	}
}

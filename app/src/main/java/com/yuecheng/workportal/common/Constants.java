package com.yuecheng.workportal.common;

import android.graphics.Color;

/**
 * 
 * 系统全局配置.
 * @author 史明松
 */
public interface Constants {
	//当前登录用户ID
	String CURRENT_USER_NAME = "current_user_name";
	int LOAD_FIRST_MODEL = 0;
	//下拉刷新
	int LOAD_REFRESH_MODEL = 1;
	//上拉加载更多
	int LOAD_MORE_MODEL = 2;
	/**
	 * 地图中绘制多边形、圆形的边界颜色
	 * @since 3.3.0
	 */
	public static final int STROKE_COLOR = Color.argb(180, 63, 145, 252);
	/**
	 * 地图中绘制多边形、圆形的填充颜色
	 * @since 3.3.0
	 */
	public static final int FILL_COLOR = Color.argb(163, 118, 212, 243);

	/**
	 * 地图中绘制多边形、圆形的边框宽度
	 * @since 3.3.0
	 */
	public static final float STROKE_WIDTH = 5F;
}
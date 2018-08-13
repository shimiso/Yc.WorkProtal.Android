package com.yuecheng.workprotal.utils;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ToastUtil {

	public static void success(Context context, String text) {
		Toasty.success(context, text, Toast.LENGTH_SHORT, true).show();
	}

	public static void success(Context context, String text, int duration) {
		Toasty.success(context, text, duration, true).show();
	}
	public static void normal(Context context, String text) {
		Toasty.normal(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void normal(Context context, String text, int duration) {
		Toasty.normal(context, text, duration).show();
	}
	public static void info(Context context, String text) {
		Toasty.info(context, text, Toast.LENGTH_SHORT, true).show();
	}

	public static void info(Context context, String text, int duration) {
		Toasty.info(context, text, duration, true).show();
	}
	public static void error(Context context, String text) {
		Toasty.error(context, text, Toast.LENGTH_SHORT, true).show();
	}

	public static void error(Context context, String text, int duration) {
		Toasty.error(context, text, duration, true).show();
	}
	public static void warning(Context context, String text) {
		Toasty.warning(context, text, Toast.LENGTH_SHORT, true).show();
    }

	public static void warning(Context context, String text, int duration) {
		Toasty.warning(context, text, duration, true).show();
	}
}

package com.yuecheng.workprotal.module.myandroid.robot.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;


import com.yuecheng.workprotal.module.myandroid.robot.app.VRApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 手机设备、app信息相关的工具类
 */

public class DeviceUtils {
    public static String getVersionName() {
        Context context = VRApplication.getApplication();
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context
                    .getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionName;
    }

    /**
     * 根据应用名称查询对应包名
     *
     * @param appName
     * @return
     */
    public static List<String> getPackNameByAppName(String appName) {
        List<String> list = new ArrayList<>();
        String packName = "";
        String realAppName = appName;
        try {
            PackageManager packageManager = VRApplication.getApplication().getPackageManager();
            List<PackageInfo> allInstalledApps = packageManager.getInstalledPackages(0);
            String tempPackName = "";
            String tempAppName = "";
            for (PackageInfo packageInfo : allInstalledApps) {
                String appLabel = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                String packageName = packageInfo.packageName;
                if (appName.equalsIgnoreCase(appLabel)) {
                    packName = packageName;
                    break;
                }
                if (appLabel.contains(appName) || appName.contains(appLabel)) {
                    tempPackName = packageInfo.packageName;
                    tempAppName = appLabel;
                }
            }

            if (TextUtils.isEmpty(packName)) {
                packName = tempPackName;
                realAppName = tempAppName;
            }
            list.add(packName);
            list.add(realAppName);
        } catch (Exception e) {
        }
        return list;
    }
}

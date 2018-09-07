package com.yuecheng.workprotal.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.common.Constants;
import java.io.File;

/**
 * SharePreference工具类.
 *
 * @author 史明松
 */
public class SharePreferenceUtil {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String name = "GLOBAL_SET";
    private Context context;
    public static final String STORAGE_PATH = "storagepath";// 存储路径

    /**
     * 类的构造方法.
     *
     * @param context
     */
    public SharePreferenceUtil(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 类的构造方法.
     *
     * @param context
     * @param name
     */
    public SharePreferenceUtil(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 获取应用的Base路径.
     *
     * @return
     * @author 史明松
     * @update 2014年6月4日 下午3:16:17
     */
    public String getBasePath() {
        String basePath = getStoragePath() + context.getString(R.string.dir);
        File file = new File(basePath);
        if(!file.exists()){
            file.mkdirs();
        }
        return basePath;
    }

    /**
     * 获取应用的BitmapCache路径.
     *
     * @return
     * @author 史明松
     * @update 2014年6月4日 下午3:16:17
     */
    public String getBitmapCachePath() {
        String bitmapCachePath = getBasePath() + context.getString(R.string.bitmap_cache);
        File dirFile = new File(bitmapCachePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return bitmapCachePath;
    }

    /**
     * 获取应用的拍照临时保存的路径.
     *
     * @return
     * @author 史明松
     * @update 2014年6月23日 下午4:35:07
     */
    public String getCameraTempPath() {
        String cameraTempPath = getBasePath() + context.getString(R.string.camera_temp);
        File dirFile = new File(cameraTempPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return cameraTempPath;
    }

    /**
     * 存储卡路径 .
     *
     * @param value
     * @author 史明松
     * @update 2014年5月24日 下午5:04:20
     */
    public void setStoragePath(String value) {
        editor.putString(STORAGE_PATH, value);
        editor.commit();
    }

    public String getStoragePath() {
        return sp.getString(STORAGE_PATH, null);
    }

    /**
     * 功能:获取apk文件更新目录
     *
     * @return
     * @author yinxuejian
     */
    public String getUpdatePath() {
        String apkDownloadPath = getBasePath() + context.getString(R.string.apk_download);
        File dirFile = new File(apkDownloadPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return apkDownloadPath;
    }

    /**
     * 获取一个boolean值
     *
     * @param key      存储的key值
     * @param defValue 默认值
     * @return boolean
     */
    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    /**
     * 存储一个boolean值
     *
     * @param key   存储的key值
     * @param value 存储的boolean值
     */
    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    /**
     * 获取当前登录用户名
     *
     * @return
     */
    public String getCurrentUserName() {
        String currentUserId = sp.getString(Constants.CURRENT_USER_NAME, null);
        return currentUserId;
    }

    /**
     * 设置当前登录用户名
     *
     * @param username
     */
    public void setCurrentUserName(String username) {
        editor.putString(Constants.CURRENT_USER_NAME, username);
        editor.commit();
    }
}

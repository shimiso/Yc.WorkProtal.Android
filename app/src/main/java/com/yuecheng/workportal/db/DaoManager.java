package com.yuecheng.workportal.db;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yuecheng.workportal.greendao.DaoMaster;
import com.yuecheng.workportal.greendao.DaoSession;
import com.yuecheng.workportal.greendao.LoginUserDao;
import com.yuecheng.workportal.greendao.DaoMaster;
import com.yuecheng.workportal.greendao.DaoSession;
import com.yuecheng.workportal.greendao.LoginUserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.io.IOException;

/**
 * 描述: 数据库管理类
 * 作者: Shims
 */
public class DaoManager {
    public static final String DB_NAME = "workportal-db";
    private MySQLiteOpenHelper mSQLiteOpenHelper;
    private DaoSession mDaoSession;
    private DaoMaster mDaoMaster;
    private static DaoManager instance;

    public static DaoManager getInstance(Context context){
        if (null==instance){
            synchronized (DaoManager.class){
                if (instance==null){
                    instance=new DaoManager(context);
                }
            }
        }
        return instance;
    }

    public DaoManager(Context context){
        setDebugMode(true);//默认开启Log打印
        mSQLiteOpenHelper = new MySQLiteOpenHelper(context, DB_NAME,null);
        mDaoMaster = new DaoMaster(mSQLiteOpenHelper.getWritableDatabase());
        // getDaoMasterBySDCard(context);
        // new DaoMaster(mSQLiteOpenHelper.getEncryptedWritableDb("123456"));
        mDaoSession = mDaoMaster.newSession();
        mDaoSession.clear();//清空所有数据表的缓存
    }

    /**
     * 根据SD卡路径获取DaoMaster
     *
     * @param context
     * @return
     */
    public DaoMaster getDaoMasterBySDCard(Context context) {

        if (mDaoMaster == null) {

            try{
                ContextWrapper wrapper = new ContextWrapper(context) {
                    /**
                     * 获得数据库路径，如果不存在，则创建对象对象
                     *
                     * @param name
                     */
                    @Override
                    public File getDatabasePath(String name) {
                        // 判断是否存在sd卡
                        boolean sdExist = android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState());
                        if (!sdExist) {// 如果不存在,
                            Log.e("SD卡管理：", "SD卡不存在，请加载SD卡");
                            return null;
                        } else {// 如果存在
                            // 获取sd卡路径
                            String dbDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
                            dbDir += "/Android";// 数据库所在目录
                            String dbPath = dbDir + "/" + name;// 数据库路径
                            // 判断目录是否存在，不存在则创建该目录
                            File dirFile = new File(dbDir);
                            if (!dirFile.exists())
                                dirFile.mkdirs();

                            // 数据库文件是否创建成功
                            boolean isFileCreateSuccess = false;
                            // 判断文件是否存在，不存在则创建该文件
                            File dbFile = new File(dbPath);
                            if (!dbFile.exists()) {
                                try {
                                    isFileCreateSuccess = dbFile.createNewFile();// 创建文件
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else
                                isFileCreateSuccess = true;
                            // 返回数据库文件对象
                            if (isFileCreateSuccess)
                                return dbFile;
                            else
                                return super.getDatabasePath(name);
                        }
                    }

                    /**
                     * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
                     *
                     * @param name
                     * @param mode
                     * @param factory
                     */
                    @Override
                    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
                        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
                    }

                    /**
                     * Android 4.0会调用此方法获取数据库。
                     *
                     * @see android.content.ContextWrapper#openOrCreateDatabase(java.lang.String,
                     *      int,
                     *      android.database.sqlite.SQLiteDatabase.CursorFactory,
                     *      android.database.DatabaseErrorHandler)
                     * @param name
                     * @param mode
                     * @param factory
                     * @param errorHandler
                     */
                    @Override
                    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
                        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
                    }
                };
                DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(wrapper,DB_NAME,null);
                mDaoMaster = new DaoMaster(helper.getWritableDatabase()); //获取未加密的数据库
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return mDaoMaster;
    }


    public MySQLiteOpenHelper getSQLiteOpenHelper() {
        return mSQLiteOpenHelper;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    //对外定义方法
    public LoginUserDao getUserDao(){
        return mDaoSession.getLoginUserDao();
    }

    //是否开启Log
    public void setDebugMode(boolean flag) {
        MigrationHelper.DEBUG = true;//如果查看数据库更新的Log，请设置为true
        QueryBuilder.LOG_SQL = flag;
        QueryBuilder.LOG_VALUES = flag;

    }
}
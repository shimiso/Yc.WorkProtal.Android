package com.yuecheng.workprotal.db;

import android.content.Context;
import com.yuecheng.workprotal.greendao.DaoMaster;
import com.yuecheng.workprotal.greendao.DaoSession;
import com.yuecheng.workprotal.greendao.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * 描述: 数据库管理类
 * 作者: Shims
 */
public class DaoManager {
    public static final String DB_NAME = "user-db";
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
        mDaoSession = mDaoMaster.newSession();
        mDaoSession.clear();//清空所有数据表的缓存
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
    public UserDao getUserDao(){
        return mDaoSession.getUserDao();
    }

    //是否开启Log
    public void setDebugMode(boolean flag) {
        MigrationHelper.DEBUG = true;//如果查看数据库更新的Log，请设置为true
        QueryBuilder.LOG_SQL = flag;
        QueryBuilder.LOG_VALUES = flag;

    }
}
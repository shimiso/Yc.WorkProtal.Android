package com.yuecheng.workprotal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yuecheng.workprotal.bean.LoginUser;
import com.yuecheng.workprotal.greendao.DaoMaster;
import com.yuecheng.workprotal.greendao.LoginUserDao;

import org.greenrobot.greendao.database.Database;

/**
 * 数据库升级入口
 */
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {

        //把需要管理的数据库表DAO作为最后一个参数传入到方法中
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, LoginUserDao.class);
    }


}

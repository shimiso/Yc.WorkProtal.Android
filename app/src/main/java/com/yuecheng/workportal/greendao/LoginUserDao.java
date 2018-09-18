package com.yuecheng.workportal.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yuecheng.workportal.bean.LoginUser;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "login_user".
*/
public class LoginUserDao extends AbstractDao<LoginUser, Long> {

    public static final String TABLENAME = "login_user";

    /**
     * Properties of entity LoginUser.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Username = new Property(1, String.class, "username", false, "USERNAME");
        public final static Property Password = new Property(2, String.class, "password", false, "PASSWORD");
        public final static Property Access_token = new Property(3, String.class, "access_token", false, "ACCESS_TOKEN");
        public final static Property Expires_in = new Property(4, int.class, "expires_in", false, "EXPIRES_IN");
        public final static Property Token_type = new Property(5, String.class, "token_type", false, "TOKEN_TYPE");
    }


    public LoginUserDao(DaoConfig config) {
        super(config);
    }
    
    public LoginUserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"login_user\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USERNAME\" TEXT UNIQUE ," + // 1: username
                "\"PASSWORD\" TEXT," + // 2: password
                "\"ACCESS_TOKEN\" TEXT UNIQUE ," + // 3: access_token
                "\"EXPIRES_IN\" INTEGER NOT NULL ," + // 4: expires_in
                "\"TOKEN_TYPE\" TEXT);"); // 5: token_type
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"login_user\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LoginUser entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(2, username);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
 
        String access_token = entity.getAccess_token();
        if (access_token != null) {
            stmt.bindString(4, access_token);
        }
        stmt.bindLong(5, entity.getExpires_in());
 
        String token_type = entity.getToken_type();
        if (token_type != null) {
            stmt.bindString(6, token_type);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LoginUser entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(2, username);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
 
        String access_token = entity.getAccess_token();
        if (access_token != null) {
            stmt.bindString(4, access_token);
        }
        stmt.bindLong(5, entity.getExpires_in());
 
        String token_type = entity.getToken_type();
        if (token_type != null) {
            stmt.bindString(6, token_type);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LoginUser readEntity(Cursor cursor, int offset) {
        LoginUser entity = new LoginUser( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // username
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // password
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // access_token
            cursor.getInt(offset + 4), // expires_in
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // token_type
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LoginUser entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUsername(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPassword(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAccess_token(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setExpires_in(cursor.getInt(offset + 4));
        entity.setToken_type(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LoginUser entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LoginUser entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LoginUser entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
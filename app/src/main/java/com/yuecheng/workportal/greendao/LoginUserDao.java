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
        public final static Property UserId = new Property(6, String.class, "userId", false, "USER_ID");
        public final static Property UserIcon = new Property(7, String.class, "userIcon", false, "USER_ICON");
        public final static Property Code = new Property(8, String.class, "code", false, "CODE");
        public final static Property Guid = new Property(9, String.class, "guid", false, "GUID");
        public final static Property StaffGrade = new Property(10, String.class, "staffGrade", false, "STAFF_GRADE");
        public final static Property Name = new Property(11, String.class, "name", false, "NAME");
        public final static Property Email = new Property(12, String.class, "email", false, "EMAIL");
        public final static Property Gender = new Property(13, int.class, "gender", false, "GENDER");
        public final static Property MobilePhone = new Property(14, String.class, "mobilePhone", false, "MOBILE_PHONE");
        public final static Property Telephone = new Property(15, String.class, "telephone", false, "TELEPHONE");
        public final static Property OrganizationName = new Property(16, String.class, "organizationName", false, "ORGANIZATION_NAME");
        public final static Property PositionName = new Property(17, String.class, "positionName", false, "POSITION_NAME");
        public final static Property PartTimeJobs = new Property(18, String.class, "partTimeJobs", false, "PART_TIME_JOBS");
        public final static Property RongCloudToken = new Property(19, String.class, "rongCloudToken", false, "RONG_CLOUD_TOKEN");
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
                "\"TOKEN_TYPE\" TEXT," + // 5: token_type
                "\"USER_ID\" TEXT," + // 6: userId
                "\"USER_ICON\" TEXT," + // 7: userIcon
                "\"CODE\" TEXT," + // 8: code
                "\"GUID\" TEXT," + // 9: guid
                "\"STAFF_GRADE\" TEXT," + // 10: staffGrade
                "\"NAME\" TEXT," + // 11: name
                "\"EMAIL\" TEXT," + // 12: email
                "\"GENDER\" INTEGER NOT NULL ," + // 13: gender
                "\"MOBILE_PHONE\" TEXT," + // 14: mobilePhone
                "\"TELEPHONE\" TEXT," + // 15: telephone
                "\"ORGANIZATION_NAME\" TEXT," + // 16: organizationName
                "\"POSITION_NAME\" TEXT," + // 17: positionName
                "\"PART_TIME_JOBS\" TEXT," + // 18: partTimeJobs
                "\"RONG_CLOUD_TOKEN\" TEXT);"); // 19: rongCloudToken
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
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(7, userId);
        }
 
        String userIcon = entity.getUserIcon();
        if (userIcon != null) {
            stmt.bindString(8, userIcon);
        }
 
        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(9, code);
        }
 
        String guid = entity.getGuid();
        if (guid != null) {
            stmt.bindString(10, guid);
        }
 
        String staffGrade = entity.getStaffGrade();
        if (staffGrade != null) {
            stmt.bindString(11, staffGrade);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(12, name);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(13, email);
        }
        stmt.bindLong(14, entity.getGender());
 
        String mobilePhone = entity.getMobilePhone();
        if (mobilePhone != null) {
            stmt.bindString(15, mobilePhone);
        }
 
        String telephone = entity.getTelephone();
        if (telephone != null) {
            stmt.bindString(16, telephone);
        }
 
        String organizationName = entity.getOrganizationName();
        if (organizationName != null) {
            stmt.bindString(17, organizationName);
        }
 
        String positionName = entity.getPositionName();
        if (positionName != null) {
            stmt.bindString(18, positionName);
        }
 
        String partTimeJobs = entity.getPartTimeJobs();
        if (partTimeJobs != null) {
            stmt.bindString(19, partTimeJobs);
        }
 
        String rongCloudToken = entity.getRongCloudToken();
        if (rongCloudToken != null) {
            stmt.bindString(20, rongCloudToken);
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
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(7, userId);
        }
 
        String userIcon = entity.getUserIcon();
        if (userIcon != null) {
            stmt.bindString(8, userIcon);
        }
 
        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(9, code);
        }
 
        String guid = entity.getGuid();
        if (guid != null) {
            stmt.bindString(10, guid);
        }
 
        String staffGrade = entity.getStaffGrade();
        if (staffGrade != null) {
            stmt.bindString(11, staffGrade);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(12, name);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(13, email);
        }
        stmt.bindLong(14, entity.getGender());
 
        String mobilePhone = entity.getMobilePhone();
        if (mobilePhone != null) {
            stmt.bindString(15, mobilePhone);
        }
 
        String telephone = entity.getTelephone();
        if (telephone != null) {
            stmt.bindString(16, telephone);
        }
 
        String organizationName = entity.getOrganizationName();
        if (organizationName != null) {
            stmt.bindString(17, organizationName);
        }
 
        String positionName = entity.getPositionName();
        if (positionName != null) {
            stmt.bindString(18, positionName);
        }
 
        String partTimeJobs = entity.getPartTimeJobs();
        if (partTimeJobs != null) {
            stmt.bindString(19, partTimeJobs);
        }
 
        String rongCloudToken = entity.getRongCloudToken();
        if (rongCloudToken != null) {
            stmt.bindString(20, rongCloudToken);
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
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // token_type
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // userId
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // userIcon
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // code
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // guid
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // staffGrade
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // name
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // email
            cursor.getInt(offset + 13), // gender
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // mobilePhone
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // telephone
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // organizationName
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // positionName
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // partTimeJobs
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19) // rongCloudToken
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
        entity.setUserId(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setUserIcon(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCode(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setGuid(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setStaffGrade(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setName(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setEmail(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setGender(cursor.getInt(offset + 13));
        entity.setMobilePhone(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setTelephone(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setOrganizationName(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setPositionName(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setPartTimeJobs(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setRongCloudToken(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
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

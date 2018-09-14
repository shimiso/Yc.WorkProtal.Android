package com.yuecheng.workportal.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Bean 对象注释的解释
 *
 * Entity：告诉GreenDao该对象为实体，只有被@Entity注释的Bean类才能被dao类操作
 * nameInDb 在数据库中的名字，如不写则为实体中类名
 * indexes 索引
 * createInDb 是否创建表，默认为true,false时不创建
 * schema 指定架构名称为实体
 * active 无论是更新生成都刷新
 *
 * Id：对象的Id，使用Long类型作为EntityId，否则会报错。(autoincrement = true)表示主键会自增，如果false就会使用旧值
 * Property：可以自定义字段名，注意外键不能使用该属性  @Property(nameInDb = "price")
 * NotNull：属性不能为空
 * Transient：使用该注释的属性不会被存入数据库的字段中
 * Unique：该属性值必须在数据库中是唯一值
 * Generated：编译后自动生成的构造函数、方法等的注释，提示构造函数、方法等不能被修改
 * ToMany 一对多
 * OrderBy 排序
 * ToOne 一对一
 */
@Entity(nameInDb = "login_user")
public class LoginUser {

    //@Id：通过这个注解标记的字段必须是Long类型的，这个字段在数据库中表示它就是主键，并且它默认就是自增的
    @Id(autoincrement = true)
    private Long id;  

    @Unique
    private String username;  //用户名

    private String password;

    @Unique
    private String access_token;

    private int expires_in;

    private String token_type;

    @Generated(hash = 1364567019)
    public LoginUser(Long id, String username, String password, String access_token,
            int expires_in, String token_type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.token_type = token_type;
    }

    @Generated(hash = 1159929338)
    public LoginUser() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccess_token() {
        return this.access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return this.expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getToken_type() {
        return this.token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    //@Transient：表明这个字段不会被写入数据库，只是作为一个普通的java类字段，用来临时存储数据的，不会被持久化

}
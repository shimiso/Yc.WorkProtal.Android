package com.yuecheng.workportal.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

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
 * Transient：使用该注释的属性不会被存入数据库的字段中 只是作为一个普通的java类字段，用来临时存储数据的，不会被持久化
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

    private String code;

    private String staffGrade;

    private String name;

    private String email;

    private int gender;

    private String mobilePhone;

    private String telephone;

    private String organizationName;

    private String positionName;

    private String partTimeJobs;

    private String rongCloudToken;//融云token

    @Generated(hash = 224987295)
    public LoginUser(Long id, String username, String password, String access_token,
            int expires_in, String token_type, String code, String staffGrade,
            String name, String email, int gender, String mobilePhone,
            String telephone, String organizationName, String positionName,
            String partTimeJobs, String rongCloudToken) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.token_type = token_type;
        this.code = code;
        this.staffGrade = staffGrade;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.mobilePhone = mobilePhone;
        this.telephone = telephone;
        this.organizationName = organizationName;
        this.positionName = positionName;
        this.partTimeJobs = partTimeJobs;
        this.rongCloudToken = rongCloudToken;
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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStaffGrade() {
        return this.staffGrade;
    }

    public void setStaffGrade(String staffGrade) {
        this.staffGrade = staffGrade;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getPositionName() {
        return this.positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPartTimeJobs() {
        return this.partTimeJobs;
    }

    public void setPartTimeJobs(String partTimeJobs) {
        this.partTimeJobs = partTimeJobs;
    }

    public String getRongCloudToken() {
        return this.rongCloudToken;
    }

    public void setRongCloudToken(String rongCloudToken) {
        this.rongCloudToken = rongCloudToken;
    }

}
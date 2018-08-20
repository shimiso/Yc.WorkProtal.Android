package com.yuecheng.workprotal.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
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
 * Transient：使用该注释的属性不会被存入数据库的字段中
 * Unique：该属性值必须在数据库中是唯一值
 * Generated：编译后自动生成的构造函数、方法等的注释，提示构造函数、方法等不能被修改
 * ToMany 一对多
 * OrderBy 排序
 * ToOne 一对一
 */
@Entity(nameInDb = "user_info")
public class User {  

    //@Id：通过这个注解标记的字段必须是Long类型的，这个字段在数据库中表示它就是主键，并且它默认就是自增的
    @Id(autoincrement = true)
    private Long id;  
    
    private String name;  //普通字段

    private int age;  //普通字段

    //@Transient：表明这个字段不会被写入数据库，只是作为一个普通的java类字段，用来临时存储数据的，不会被持久化
    @Transient
    private int tempUsageCount; // not persisted  

    @Generated(hash = 1309193360)
    public User(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}  
package com.defineview.bill.mysqlitedemo.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

//以下所有参数重新设置后需MakeProject,重装APP或升级数据库
@Entity(
        //设置在数据库中的表名,默认为对应的Entity类名
        nameInDb = "USERS_TABLE",

        //设置表为动态表，具有更新、查询、删除方法
//        active = true,

        //定义索引跨越的列
        indexes = {
                //为name列设置索引，降序
                @Index(value = "name DESC")
        }

        /*设置GREENDAO是否在数据库中创建数据表，默认创建。
         如果多个实体类映射为同一张表，或已手动创建完数据表，将此设置为false*/
//        createInDb = false,
)

public class UserTwo {
    //设置Id,为Long类型,并将其设置为自增
    @Id(autoincrement = true)
    private Long id;

    //设置为唯一不重复索引
    @Index(name = "KEY_INDEX", unique = true)
    private String key;

    //设置在数据库中的列名，如不定义则为默认(例如myUser,则数据库中列名MY_USER)
    @Property(nameInDb = "userName")

    @NotNull//设置此列非空
    private String name;
    private String addTest;
    private String assTest2;

    //临时存储数据，不会被持久化
    @Transient
    private int tempUsageCount;
    //以下数据为MakeProject自动创建

public String getAssTest2() {
        return this.assTest2;
}

public void setAssTest2(String assTest2) {
        this.assTest2 = assTest2;
}

public String getAddTest() {
        return this.addTest;
}

public void setAddTest(String addTest) {
        this.addTest = addTest;
}

public String getName() {
        return this.name;
}

public void setName(String name) {
        this.name = name;
}

public String getKey() {
        return this.key;
}

public void setKey(String key) {
        this.key = key;
}

public Long getId() {
        return this.id;
}

public void setId(Long id) {
        this.id = id;
}

@Generated(hash = 1234771469)
public UserTwo(Long id, String key, @NotNull String name, String addTest,
                String assTest2) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.addTest = addTest;
        this.assTest2 = assTest2;
}

@Generated(hash = 1156005031)
public UserTwo() {
}
    
}

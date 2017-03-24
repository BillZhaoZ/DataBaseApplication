package com.defineview.bill.mysqlitedemo.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 实体类
 * Created by Bill on 2017/3/24.
 */

@Entity
public class UserOne {
    @Id
    private Long id;
    private String name;
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1230407037)
    public UserOne(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 292128769)
    public UserOne() {
    }

}

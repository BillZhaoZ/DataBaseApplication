package com.defineview.bill.mysqlitedemo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 自定义数据库帮助类
 * Created by Bill on 2017/3/13.
 */

public class UserInfoOpenHelper extends SQLiteOpenHelper {

    public UserInfoOpenHelper(Context context) {
        super(context, "userInfo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user (_id integer primary key autoincrement  ,name varchar(20),phone varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //执行sql语句做表结构的修改   数据库版本也需要同时变更  才会起作用
        // db.execSQL("alter table user add phone varchar(20)");

        //System.out.println("旧版本:" + oldVersion + "新版本+" + newVersion);
    }
}

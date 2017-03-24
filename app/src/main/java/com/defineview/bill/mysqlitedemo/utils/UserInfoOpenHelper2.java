package com.defineview.bill.mysqlitedemo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserInfoOpenHelper2 extends SQLiteOpenHelper {

    public UserInfoOpenHelper2(Context context) {
        super(context, "info2.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user2 (_id integer primary key autoincrement  ,name varchar(20),phone varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//		db.execSQL("alter table user2  add phone varchar(20)");
//		System.out.println("oldVersion:"+oldVersion +"  newVersion  :"+newVersion);
    }

}

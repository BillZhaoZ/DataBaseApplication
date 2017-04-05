package com.defineview.bill.mysqlitedemo.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.defineview.bill.mysqlitedemo.greendao.gen.DaoMaster;

/**
 * 数据库升级
 * <p>
 * 等待完善--暂时弃用
 */
public class DBHelper extends DaoMaster.OpenHelper {

    public static final String DBNAME = "assistant.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);

    }
}  
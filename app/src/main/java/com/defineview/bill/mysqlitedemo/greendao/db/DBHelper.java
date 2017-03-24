package com.defineview.bill.mysqlitedemo.greendao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.defineview.bill.mysqlitedemo.greendao.gen.DaoMaster;

/**
 * 数据库升级
 * 等待完善
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
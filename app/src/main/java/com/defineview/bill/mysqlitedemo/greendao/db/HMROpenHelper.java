package com.defineview.bill.mysqlitedemo.greendao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.defineview.bill.mysqlitedemo.greendao.gen.DaoMaster;
import com.defineview.bill.mysqlitedemo.greendao.gen.UserTwoDao;

import org.greenrobot.greendao.database.Database;

/**
 * 类名：HMROpenHelper
 * 类描述：用于数据库升级的工具类
 */
public class HMROpenHelper extends DaoMaster.OpenHelper {

    public HMROpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * 数据库升级
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //操作数据库的更新
        MigrationHelper.migrate(db, UserTwoDao.class);
    }

}

package com.defineview.bill.mysqlitedemo.greendao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.defineview.bill.mysqlitedemo.greendao.gen.DaoMaster;
import com.defineview.bill.mysqlitedemo.greendao.gen.UserTwoDao;

import org.greenrobot.greendao.database.Database;

/**
 * 类名：MedOpenHelper
 * 类描述：用于数据库升级的工具类
 * <p>
 * 1 新建临时表   generateTempTables(db, daoClasses);
 * 2 创建新表     createAllTables(db, false, daoClasses);
 * 3 临时表数据写入新表，删除临时表   restoreData(db, daoClasses);
 * <p>
 * 步骤：
 * 比如升级UserTwo表，需要添加字段SEX； 首先，需要在build.gradle里面修改数据库版本号为2，
 * 然后在UserTwo里面添加字段SEX，接着Make-Project，最后运行项目，就会走升级的OnUpgrade方法；
 */
public class MedOpenHelper extends DaoMaster.OpenHelper {

    public MedOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
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

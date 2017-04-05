package com.defineview.bill.mysqlitedemo.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.defineview.bill.mysqlitedemo.model.UserBean;

import java.util.ArrayList;

/**
 * sql语句查询
 * Created by Bill on 2017/3/18.
 */

public class UserInfoDbUtils {

    private SQLiteDatabase db;
    private UserInfoOpenHelper mySqliteOpenHelper;

    public UserInfoDbUtils(Context context) {
        //1.创建一个数据库帮助类对象
        mySqliteOpenHelper = new UserInfoOpenHelper(context);
    }

    public void add(UserBean bean) {

        //2.调用帮助类对象的getReadableDatabas方法用来创建数据库
        db = mySqliteOpenHelper.getReadableDatabase();

        //3.执行insert 对应的sql语句 的方法  sql:sql语句    bindArgs：占位符的参数值
        db.execSQL("insert into user (name,phone) values(?,?)", new Object[]{bean.name, bean.phone});
        //4.关闭数据库
        db.close();
    }

    public void delete(String name) {

        //2.调用帮助类对象的getReadableDatabas方法用来创建数据库
        db = mySqliteOpenHelper.getReadableDatabase();

        //3.执行delete 对应的sql语句 的方法  sql:sql语句    bindArgs：占位符的参数值
        db.execSQL("delete from user where name=?;", new Object[]{name});
        //4.关闭数据库
        db.close();
    }

    public void update(UserBean bean) {

        //2.调用帮助类对象的getReadableDatabas方法用来创建数据库
        db = mySqliteOpenHelper.getReadableDatabase();
        //3.执行update 对应的sql语句 的方法  sql:sql语句    bindArgs：占位符的参数值
        db.execSQL("update user set phone=? where name=?;", new Object[]{bean.phone, bean.name});

        //4.关闭数据库
        db.close();
    }

    /**
     * 查询
     */
    public void query() {

        //2.调用帮助类对象的getReadableDatabas方法用来创建数据库
        db = mySqliteOpenHelper.getReadableDatabase();


        ArrayList<UserBean> list = new ArrayList<UserBean>();

        //3.执行select 对应的sql语句 的方法  sql:sql语句    selectionArgs：占位符的参数值
        Cursor cursor = db.rawQuery("select * from user", null);

        //4.解析结果集
        //判断结果集是否有效
        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {//游标是否继续向下移动
                UserBean bean = new UserBean();
                //通过列的索引获取对应的数据
                bean.name = cursor.getString(cursor.getColumnIndex("name"));
                bean.phone = cursor.getString(cursor.getColumnIndex("phone"));

                System.out.println("name:" + bean.name + " phone:" + bean.phone);

                list.add(bean);
            }
            //关闭cursor
            cursor.close();
        }
        //5.关闭数据库
        db.close();
    }

}

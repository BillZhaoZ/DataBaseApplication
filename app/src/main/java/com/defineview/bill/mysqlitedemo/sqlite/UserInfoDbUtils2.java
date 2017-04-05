package com.defineview.bill.mysqlitedemo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.defineview.bill.mysqlitedemo.model.UserBean2;
import com.defineview.bill.mysqlitedemo.utils.UserInfoOpenHelper2;

import java.util.ArrayList;

/**
 * 调用方法查询
 * 数据库操作类
 */
public class UserInfoDbUtils2 {

    private SQLiteDatabase db;
    private UserInfoOpenHelper2 mySqliteOpenHelper;

    public UserInfoDbUtils2(Context context) {
        //1.创建一个数据库帮助类对象
        mySqliteOpenHelper = new UserInfoOpenHelper2(context);
    }

    /**
     * 增加
     *
     * @param bean
     * @return
     */
    public boolean add(UserBean2 bean) {
        //2.调用帮助类对象的getReadableDatabas方法用来创建数据库
        db = mySqliteOpenHelper.getReadableDatabase();
        //3.执行insert 对应的sql语句 的方法  sql:sql语句    bindArgs：占位符的参数值

        ContentValues values = new ContentValues();
        //key:表中字段的名称  
        values.put("name", bean.name);
        values.put("phone", bean.phone);
        //table:表名   nullColumnHack:可以为null   values:添加哪些列的数据  return :新添加这一条数据行的id，如果返回-1代表添加失败
        long result = db.insert("user2", null, values);//底层是在拼装sql语句
        //4.关闭数据库
        db.close();

        if (result == -1) {
            return false;
        }

        return true;
    }

    /**
     * 删除
     *
     * @param name
     * @return
     */
    public int delete(String name) {
        //2.调用帮助类对象的getReadableDatabas方法用来创建数据库
        db = mySqliteOpenHelper.getReadableDatabase();

        //3.执行delete 对应的sql语句 的方法  sql:sql语句    bindArgs：占位符的参数值
        //whereClause：删除条件  whereArgs：条件中占位符的值  返回值：成功删除多少行
        int delete = db.delete("user2", "name=?", new String[]{name});

        //4.关闭数据库
        db.close();
        return delete;
    }

    /**
     * 更新
     *
     * @param bean
     * @return
     */
    public int update(UserBean2 bean) {
        //2.调用帮助类对象的getReadableDatabas方法用来创建数据库
        db = mySqliteOpenHelper.getReadableDatabase();
        //3.执行update 对应的sql语句 的方法  sql:sql语句    bindArgs：占位符的参数值

        ContentValues values = new ContentValues();
        values.put("phone", bean.phone);

        //values:更新的列的数据   whereClause:更新条件 whereArgs:条件中占位符的值     返回值：成功修改多少行
        int update = db.update("user2", values, "name=?", new String[]{bean.name});
        //4.关闭数据库
        db.close();
        return update;
    }

    /**
     * 查询
     */
    public void query() {
        ArrayList<UserBean2> list = new ArrayList<UserBean2>();

        //2.调用帮助类对象的getReadableDatabas方法用来创建数据库
        db = mySqliteOpenHelper.getReadableDatabase();

        //3.执行select 对应的sql语句 的方法  sql:sql语句    selectionArgs：占位符的参数值
//		Cursor cursor = db.rawQuery("select * from user", null);

        //columns:查询的列，如果查询所有传null  selection:查询条件  selectionArgs：条件占位符的参数值  groupBy:分组 having：分组条件   orderBy:按什么排序
        Cursor cursor = db.query("user2", new String[]{"name", "phone"}, "name = ?", new String[]{"王五"}, null, null, "_id desc");

        //4.解析结果集
        //判断结果集是否有效
        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {//游标是否继续向下移动
                UserBean2 bean = new UserBean2();
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

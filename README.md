# sqLite & GreenDAO 使用教程

sqLite数据库

一、sqLite数据库的创建和升级；

     // 可在Application里面进行此操作  或者自定义一个管理类
        MedOpenHelper mHelper = new MedOpenHelper(MyApplication.getInstances(), "notes-db", null);//为数据库升级封装过的使用方式
        db = mHelper.getWritableDatabase();

     // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();

    /**
     * 类名：MedOpenHelper
     * 类描述：用于数据库升级的工具类
     *
     * 1 新建临时表   generateTempTables(db, daoClasses);
     * 2 创建新表     createAllTables(db, false, daoClasses);
     * 3 临时表数据写入新表，删除临时表   restoreData(db, daoClasses);
     *
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


二、包含sqLite数据库的两种增删改查的方式；

  1、SQL语句

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

    // 例子
     switch (id) {
                case R.id.tv_add:

                    UserBean bean = new UserBean();
                    bean.name = "王五";
                    bean.phone = "110";
                    dbUtils.add(bean);

                    UserBean bean2 = new UserBean();
                    bean2.name = "张三";
                    bean2.phone = "119";
                    dbUtils.add(bean2);

                    System.out.println("增加两人，" + "name:" + bean.name + " phone:" + bean.phone + "；  \n      name:" + bean2.name + " phone:" + bean2.phone);

                    break;

                case R.id.tv_delete:
                    dbUtils.delete("王五");
                    dbUtils.delete("张三");

                    System.out.println("删除了，王五、张三");

                    break;

                case R.id.tv_uodate:

                    UserBean bean1 = new UserBean();
                    bean1.name = "王五";
                    bean1.phone = "120";
                    dbUtils.update(bean1);

                    System.out.println("王五的电话换成了120");

                    break;

                case R.id.tv_query:
                    dbUtils.query();

                    break;
            }

  2、调用方法

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

    // 例子
    switch (v.getId()) {

                case R.id.bt_add:
                    UserBean2 bean = new UserBean2();
                    bean.name = "王五";
                    bean.phone = "110";
                    boolean result = dbUtils.add(bean);

                    if (result) {
                        Toast.makeText(this, "添加王五成功", 0).show();
                    } else {
                        Toast.makeText(this, "添加失败", 0).show();
                    }
                    break;

                case R.id.bt_delete:
                    int delete = dbUtils.delete("王五");
                    Toast.makeText(this, "删除了" + delete + "行，名字是：王五", 0).show();
                    break;

                case R.id.bt_update:
                    UserBean2 bean1 = new UserBean2();
                    bean1.name = "王五";
                    bean1.phone = "120";
                    int update = dbUtils.update(bean1);
                    Toast.makeText(this, "更新了" + update + "行，王五电话变成，120", 0).show();
                    break;

                case R.id.bt_query:
                    dbUtils.query();
                    break;

                default:
                    break;
            }

GreenDAO数据库

GreenDAO的集成、简单的增删改查和数据库升级；

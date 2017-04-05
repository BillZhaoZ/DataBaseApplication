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

一、GreenDAO的集成、

   1.build.gradle配置

    apply plugin: 'com.android.application'

    android {
        compileSdkVersion 23
        buildToolsVersion "23.0.3"

        defaultConfig {
            applicationId "com.defineview.bill.mysqlitedemo"
            minSdkVersion 19
            targetSdkVersion 23
            versionCode 1
            versionName "1.0"
        }

        buildTypes {
            release {
                minifyEnabled false
                proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            }
        }
    }

    // 配置greendao
    apply plugin: 'org.greenrobot.greendao'

    buildscript {
        repositories {
            mavenCentral()
        }

        dependencies {
            classpath 'org.greenrobot:greendao-gradle-plugin:3.0.0'
        }
    }

    greendao {
        schemaVersion 2                                               // 版本号，升级时可配置
        daoPackage 'com.defineview.bill.mysqlitedemo.greendao.gen'    // 包名
        targetGenDir 'src/main/java'                                  // 文件生成目录
    }

    dependencies {

        compile fileTree(dir: 'libs', include: ['*.jar'])
        androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
            exclude group: 'com.android.support', module: 'support-annotations'
        })

        compile 'com.android.support:appcompat-v7:23.4.0'
        testCompile 'junit:junit:4.12'

        // GreenDAO集成
        compile 'org.greenrobot:greendao:3.0.1'
        compile 'org.greenrobot:greendao-generator:3.0.0'
    }

   2.初始化数据库

     //初始化数据库
       userDaoTwo = EntityManager.getInstance().getUserTwoDao();

     /**
      * 实体类管理（表格管理）
      */
     public class EntityManager {

         private static EntityManager entityManager;

         /**
          * 创建User1表实例
          *
          * @return
          */
         public UserOneDao getUserOneDao() {
             return DaoManager.getInstance().getSession().getUserOneDao();
         }

         /**
          * 创建User2表实例
          *
          * @return
          */
         public UserTwoDao getUserTwoDao() {
             return DaoManager.getInstance().getSession().getUserTwoDao();
         }

         /**
          * 创建单例
          *
          * @return
          */
         public static EntityManager getInstance() {
             if (entityManager == null) {
                 entityManager = new EntityManager();
             }
             return entityManager;
         }
     }


二、简单的增删改查

       /**
         * 方法名：addData()
         * 方法描述：增加数据
         */
        private void addData() {
            try {
                String userName = et_userName.getText().toString().trim();

                if (userName.equals("")) {
                    ToastUtils.showShort(getInstances(), "添加为空！");
                    return;
                }

                UserTwo mUser1 = new UserTwo();
                mUser1.setName(userName);
                mUser1.setAddTest("hello");
                mUser1.setAssTest2("hi");
                userDaoTwo.insert(mUser1);
                initData();

                ToastUtils.showShort(getInstances(), "添加完成！");
            } catch (Exception e) {
                ToastUtils.showShort(getInstances(), "添加了相同数据！");
            }
        }

        /**
         * 方法名：deleteData()
         * 方法描述：根据姓名删除数据
         */
        private void deleteData() {
            String userName = et_deleteUserName.getText().toString().trim();

            if (userName.equals("")) {
                ToastUtils.showShort(getInstances(), "请输入要删除的姓名！");
                return;
            }

            List<UserTwo> listUser = queryDataByName(userName);

            if (listUser == null || listUser.size() == 0) {
                ToastUtils.showShort(getInstances(), "你输入的姓名不存在，请重新输入！");
                return;
            } else {

                for (int i = 0; i < listUser.size(); i++) {
                    UserTwo user = listUser.get(i);
                    //根据Entity删除数据
                    userDaoTwo.delete(user);

                    //根据Id删除数据
                    // userDaowowo.deleteByKey(user.getId());
                }
            }

            initData();
            ToastUtils.showShort(getInstances(), "删除完成！");
        }

        /**
         * 方法名：deleteAllData()
         * 方法描述：删除所有数据
         */
        private void deleteAllData() {
            userDaoTwo.deleteAll();
            initData();
            ToastUtils.showShort(getInstances(), "删除完成！");
        }

        /**
         * 方法名：queryData()
         * 方法描述：查询全部数据
         */
        private void queryData() {
            userList = userDaoTwo.loadAll();
        }

        /**
         * 方法名：queryDataByName
         * 方法描述：根据姓名查询数据
         *
         * @param name 姓名
         */
        List<UserTwo> queryDataByName(String name) {
            QueryBuilder qb = userDaoTwo.queryBuilder();
            List<UserTwo> users = qb.where(UserTwoDao.Properties.Name.eq(name)).list();
            //查询姓名为小红且id为1的数据，并以id为升序排列
    //        List<UserTwo> users = qb.where(qb.and(userDaowowo.Properties.Name.eq("小红"),userDaowowo.Properties.Id.eq((long)1)))
    //                .orderAsc(userDaowowo.Properties.Id).list();
            return users;
        }

        /**
         * 方法名：updateData()
         * 方法描述：根据id更改数据
         */
        private void updateData() {
            String preUserId = et_preUserId.getText().toString().trim();
            String afterUserName = et_afterUserName.getText().toString().trim();

            if (preUserId.equals("") || afterUserName.equals("")) {
                ToastUtils.showShort(getInstances(), "输入为空！");
                return;
            }

            Long userId = Long.parseLong(preUserId);
            UserTwo findUser = userDaoTwo.queryBuilder().where(UserTwoDao.Properties.Id.eq(userId)).build().unique();

            if (findUser != null) {
                findUser.setName(afterUserName);
                userDaoTwo.update(findUser);
            }

            ToastUtils.showShort(getInstances(), "更改完成！");
            initData();
        }

三、数据库升级；

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

    /**
     * 类名：MigrationHelper
     * 类描述：用于数据库升级的工具类
     */
    public class MigrationHelper {

        /**
         * 调用升级方法
         *
         * @param db
         * @param daoClasses 一系列dao.class
         */
        public static void migrate(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
            //1 新建临时表
            generateTempTables(db, daoClasses);
            //2 创建新表
            createAllTables(db, false, daoClasses);
            //3 临时表数据写入新表，删除临时表
            restoreData(db, daoClasses);
        }

        /**
         * 生成临时表，存储旧的表数据
         *
         * @param db
         * @param daoClasses
         */
        private static void generateTempTables(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
            for (int i = 0; i < daoClasses.length; i++) {
                DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
                String tableName = daoConfig.tablename;
                if (!checkTable(db, tableName))
                    continue;
                String tempTableName = daoConfig.tablename.concat("_TEMP");
                StringBuilder insertTableStringBuilder = new StringBuilder();
                insertTableStringBuilder.append("alter table ")
                        .append(tableName)
                        .append(" rename to ")
                        .append(tempTableName)
                        .append(";");
                db.execSQL(insertTableStringBuilder.toString());
            }
        }

        /**
         * 检测table是否存在
         *
         * @param db
         * @param tableName
         */
        private static Boolean checkTable(Database db, String tableName) {
            StringBuilder query = new StringBuilder();
            query.append("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='").append(tableName).append("'");
            Cursor c = db.rawQuery(query.toString(), null);

            if (c.moveToNext()) {
                int count = c.getInt(0);
                if (count > 0) {
                    return true;
                }
                return false;
            }

            return false;
        }

        /**
         * 删除所有旧表
         *
         * @param db
         * @param ifExists
         * @param daoClasses
         */
        private static void dropAllTables(Database db, boolean ifExists, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
            reflectMethod(db, "dropTable", ifExists, daoClasses);
        }

        /**
         * 创建新的表结构
         *
         * @param db
         * @param ifNotExists
         * @param daoClasses
         */
        private static void createAllTables(Database db, boolean ifNotExists, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
            reflectMethod(db, "createTable", ifNotExists, daoClasses);
        }

        /**
         * 创建根删除都在NoteDao声明了，可以直接拿过来用
         * dao class already define the sql exec method, so just invoke it
         */
        private static void reflectMethod(Database db, String methodName, boolean isExists, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {

            if (daoClasses.length < 1) {
                return;
            }

            try {
                for (Class cls : daoClasses) {
                    //根据方法名，找到声明的方法
                    Method method = cls.getDeclaredMethod(methodName, Database.class, boolean.class);
                    method.invoke(null, db, isExists);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        /**
         * 临时表的数据写入新表
         *
         * @param db
         * @param daoClasses
         */
        private static void restoreData(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {

            for (int i = 0; i < daoClasses.length; i++) {
                DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
                String tableName = daoConfig.tablename;
                String tempTableName = daoConfig.tablename.concat("_TEMP");

                if (!checkTable(db, tempTableName))
                    continue;
                // get all columns from tempTable, take careful to use the columns list
                List<String> columns = getColumns(db, tempTableName);

                //新表，临时表都包含的字段
                ArrayList<String> properties = new ArrayList<>(columns.size());
                for (int j = 0; j < daoConfig.properties.length; j++) {
                    String columnName = daoConfig.properties[j].columnName;
                    if (columns.contains(columnName)) {
                        properties.add(columnName);
                    }
                }

                if (properties.size() > 0) {
                    final String columnSQL = TextUtils.join(",", properties);

                    StringBuilder insertTableStringBuilder = new StringBuilder();
                    insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (");
                    insertTableStringBuilder.append(columnSQL);
                    insertTableStringBuilder.append(") SELECT ");
                    insertTableStringBuilder.append(columnSQL);
                    insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");
                    db.execSQL(insertTableStringBuilder.toString());
                }

                StringBuilder dropTableStringBuilder = new StringBuilder();
                dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);
                db.execSQL(dropTableStringBuilder.toString());
            }
        }

        /**
         * 获取所有条目
         *
         * @param db
         * @param tableName
         * @return
         */
        private static List<String> getColumns(Database db, String tableName) {
            List<String> columns = null;
            Cursor cursor = null;

            try {
                cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 0", null);
                if (null != cursor && cursor.getColumnCount() > 0) {
                    columns = Arrays.asList(cursor.getColumnNames());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null)
                    cursor.close();
                if (null == columns)
                    columns = new ArrayList<>();
            }

            return columns;
        }

    }

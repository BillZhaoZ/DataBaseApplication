package com.defineview.bill.mysqlitedemo.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.defineview.bill.mysqlitedemo.R;
import com.defineview.bill.mysqlitedemo.greendao.entity.UserTwo;
import com.defineview.bill.mysqlitedemo.greendao.gen.UserTwoDao;
import com.defineview.bill.mysqlitedemo.greendao.manger.DaoManager;
import com.defineview.bill.mysqlitedemo.utils.ToastUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.defineview.bill.mysqlitedemo.UI.MyApplication.getInstances;

/**
 * GreenDAO ---第二种
 */
public class GreenDAO2 extends AppCompatActivity implements View.OnClickListener {

    private ListView myListView;
    private DataAdapter myAdapter;
    private List<UserTwo> userList = new ArrayList<UserTwo>();
    private UserTwoDao userDaowowo;

    EditText et_userName;
    EditText et_deleteUserName;
    EditText et_preUserId;
    EditText et_afterUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao2);

        Button btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
        Button btnDelete = (Button) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);
        Button btnUpdate = (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        Button btnDeleteByName = (Button) findViewById(R.id.btn_deteleByName);
        btnDeleteByName.setOnClickListener(this);

        et_preUserId = (EditText) findViewById(R.id.et_preUserId);
        et_afterUserName = (EditText) findViewById(R.id.et_afterName);
        et_userName = (EditText) findViewById(R.id.et_userName);
        myListView = (ListView) findViewById(R.id.my_listView);
        et_deleteUserName = (EditText) findViewById(R.id.et_deteleUserName);

        //初始化数据库

        userDaowowo = DaoManager.getInstance().getDaoSession().getUserTwoDao();
        initData();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        queryData();
        myAdapter = new DataAdapter(userList, GreenDAO2.this);
        myListView.setAdapter(myAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                addData();
                break;
            case R.id.btn_delete:
                deleteAllData();
                break;
            case R.id.btn_update:
                updateData();
                break;
            case R.id.btn_deteleByName:
                deleteData();
                break;
        }
    }

    /**
     * 方法名：addData()
     * 方法描述：增加数据
     */
    void addData() {
        try {
            String userName = et_userName.getText().toString().trim();

            if (userName == null || userName.equals("")) {
                ToastUtils.showShort(getInstances(), "添加为空！");
                return;
            }

            UserTwo mUser1 = new UserTwo();
            mUser1.setName(userName);
            mUser1.setAddTest("hello");
            mUser1.setAssTest2("hi");
            userDaowowo.insert(mUser1);
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
    void deleteData() {

        String userName = et_deleteUserName.getText().toString().trim();
        if (userName == null || userName.equals("")) {

            ToastUtils.showShort(getInstances(), "输入为空！");
            return;
        }

        List<UserTwo> listUser = queryDataByName(userName);
        for (int i = 0; i < listUser.size(); i++) {
            UserTwo user = listUser.get(i);
            //根据Entity删除数据
            userDaowowo.delete(user);

            //根据Id删除数据
//        userDaowowo.deleteByKey(user.getId());
        }

        initData();
        ToastUtils.showShort(getInstances(), "删除完成！");
    }

    /**
     * 方法名：deleteAllData()
     * 方法描述：删除所有数据
     */
    void deleteAllData() {
        userDaowowo.deleteAll();
        initData();
        ToastUtils.showShort(getInstances(), "删除完成！");
    }

    /**
     * 方法名：queryData()
     * 方法描述：查询全部数据
     */
    void queryData() {
        userList = userDaowowo.loadAll();
    }


    /**
     * 方法名：queryDataByName
     * 方法描述：根据姓名查询数据
     *
     * @param name 姓名
     */
    List<UserTwo> queryDataByName(String name) {
        QueryBuilder qb = userDaowowo.queryBuilder();
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
    void updateData() {
        String preUserId = et_preUserId.getText().toString().trim();
        String afterUserName = et_afterUserName.getText().toString().trim();

        if (preUserId == null || preUserId.equals("") || afterUserName == null || afterUserName.equals("")) {
            ToastUtils.showShort(getInstances(), "输入为空！");
            return;
        }

        Long userId = Long.parseLong(preUserId);
        UserTwo findUser = userDaowowo.queryBuilder().where(UserTwoDao.Properties.Id.eq(userId)).build().unique();

        if (findUser != null) {
            findUser.setName(afterUserName);
            userDaowowo.update(findUser);
        }

        ToastUtils.showShort(getInstances(), "更改完成！");
        initData();
    }

    /**
     * 方法名：executeSql()
     * 方法描述：执行sql语句
     */
    private void executeSql() {
        try {
            String sql = "select ADD_TEST from USERS_TABLE where userName = '小明'";
            DaoManager.getInstance().getDaoSession().getDatabase().execSQL(sql);
        } catch (Exception e) {
            return;
        }


    }
}

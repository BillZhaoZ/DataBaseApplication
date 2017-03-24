package com.defineview.bill.mysqlitedemo.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.defineview.bill.mysqlitedemo.R;
import com.defineview.bill.mysqlitedemo.greendao.entity.User;
import com.defineview.bill.mysqlitedemo.greendao.gen.UserDao;
import com.defineview.bill.mysqlitedemo.greendao.manger.EntityManager;

import java.util.ArrayList;
import java.util.List;

/**
 * GreendAO 的增删改查
 */
public class QueryActiviyGreenDAO extends AppCompatActivity implements View.OnClickListener {

    private Button mAdd, mDelete, mUpdate, mFind;
    private TextView mContext;
    private User mUser;
    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_activiy_green_dao);

        initView();
        initEvent();

        mUserDao = EntityManager.getInstance().getUserDao();
    }

    /**
     * 注册点击事件
     */
    private void initEvent() {
        mAdd.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mFind.setOnClickListener(this);
    }

    /**
     * 初始化布局
     */
    private void initView() {

        mContext = (TextView) findViewById(R.id.textView);
        mAdd = (Button) findViewById(R.id.button);
        mDelete = (Button) findViewById(R.id.button2);
        mUpdate = (Button) findViewById(R.id.button3);
        mFind = (Button) findViewById(R.id.button4);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                addDate();
                break;

            case R.id.button2:
                deleteDate();
                break;

            case R.id.button3:
                updateDate();
                break;

            case R.id.button4:
                findDate();
                break;
        }
    }

    /**
     * 增加数据
     */
    private void addDate() {

        String userName = "";
        ArrayList<User> userArrayList = new ArrayList<>();

        userArrayList.add(0, new User((long) 0, "aa"));
        userArrayList.add(1, new User((long) 1, "bb"));
        userArrayList.add(2, new User((long) 2, "cc"));
        userArrayList.add(3, new User((long) 3, "dd"));
        userArrayList.add(4, new User((long) 4, "ee"));

        for (int i = 0; i < userArrayList.size(); i++) {
            User user = userArrayList.get(i);

            mUserDao.insert(user);//添加一个
            userName += user.getName();
        }

        mContext.setText(userName);
    }

    /**
     * 删除数据
     */
    private void deleteDate() {
        deleteUserById(2);
    }

    /**
     * 根据主键删除User
     *
     * @param id User的主键Id
     */
    public void deleteUserById(long id) {
        mUserDao.deleteByKey(id);

        findDate();
    }

    /**
     * 更改数据
     */
    private void updateDate() {
        mUser = new User((long) 2, "C+++");
        mUserDao.update(mUser);

        mContext.setText("bb变成了：" + mUser.getName());
    }

    /**
     * 查找数据
     */
    private void findDate() {
        List<User> users = mUserDao.loadAll();
        String userName = "";

        for (int i = 0; i < users.size(); i++) {
            userName += users.get(i).getName() + ",";
        }

        mContext.setText("查询全部数据为: " + userName);
    }
}

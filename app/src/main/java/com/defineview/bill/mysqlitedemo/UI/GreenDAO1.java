package com.defineview.bill.mysqlitedemo.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.defineview.bill.mysqlitedemo.R;
import com.defineview.bill.mysqlitedemo.greendao.entity.UserOne;
import com.defineview.bill.mysqlitedemo.greendao.gen.UserOneDao;
import com.defineview.bill.mysqlitedemo.greendao.manger.EntityManager;

import java.util.ArrayList;
import java.util.List;

/***
 * 第一种(简易)
 */
public class GreenDAO1 extends AppCompatActivity implements View.OnClickListener {

    private Button mAdd, mDelete, mUpdate, mFind;
    private TextView mContext;
    private UserOne mUser;
    private UserOneDao mUserOneDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao1);

        initView();
        initEvent();

        mUserOneDao = EntityManager.getInstance().getUserOneDao();
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
        ArrayList<UserOne> userArrayList = new ArrayList<>();

        userArrayList.add(0, new UserOne((long) 0, "aa"));
        userArrayList.add(1, new UserOne((long) 1, "bb"));
        userArrayList.add(2, new UserOne((long) 2, "cc"));
        userArrayList.add(3, new UserOne((long) 3, "dd"));
        userArrayList.add(4, new UserOne((long) 4, "ee"));

        for (int i = 0; i < userArrayList.size(); i++) {
            UserOne user = userArrayList.get(i);

            mUserOneDao.insert(user);//添加一个
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
        mUserOneDao.deleteByKey(id);

        findDate();
    }

    /**
     * 更改数据
     */
    private void updateDate() {
        mUser = new UserOne((long) 2, "C+++");
        mUserOneDao.update(mUser);

        mContext.setText("bb变成了：" + mUser.getName());
    }

    /**
     * 查找数据
     */
    private void findDate() {
        List<UserOne> users = mUserOneDao.loadAll();
        String userName = "";

        for (int i = 0; i < users.size(); i++) {
            userName += users.get(i).getName() + ",";
        }

        mContext.setText("查询全部数据为: " + userName);
    }
}

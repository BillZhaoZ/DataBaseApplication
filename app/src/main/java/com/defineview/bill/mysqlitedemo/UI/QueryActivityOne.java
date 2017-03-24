package com.defineview.bill.mysqlitedemo.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.defineview.bill.mysqlitedemo.R;
import com.defineview.bill.mysqlitedemo.model.UserBean;
import com.defineview.bill.mysqlitedemo.utils.UserInfoDbUtils;

public class QueryActivityOne extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_one);

        findViewById(R.id.tv_add).setOnClickListener(this);
        findViewById(R.id.tv_delete).setOnClickListener(this);
        findViewById(R.id.tv_uodate).setOnClickListener(this);
        findViewById(R.id.tv_query).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        UserInfoDbUtils dbUtils = new UserInfoDbUtils(getApplicationContext());

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
    }
}

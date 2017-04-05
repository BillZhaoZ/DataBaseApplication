package com.defineview.bill.mysqlitedemo.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.defineview.bill.mysqlitedemo.R;
import com.defineview.bill.mysqlitedemo.model.UserBean2;
import com.defineview.bill.mysqlitedemo.sqlite.UserInfoDbUtils2;

public class QueryActiviyTwo extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_activiy_two);

        findViewById(R.id.bt_add).setOnClickListener(this);
        findViewById(R.id.bt_delete).setOnClickListener(this);
        findViewById(R.id.bt_update).setOnClickListener(this);
        findViewById(R.id.bt_query).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        UserInfoDbUtils2 dbUtils = new UserInfoDbUtils2(this);

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

    }
}

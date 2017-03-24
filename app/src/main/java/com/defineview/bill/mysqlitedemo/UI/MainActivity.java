package com.defineview.bill.mysqlitedemo.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.defineview.bill.mysqlitedemo.R;

/**
 * 主页
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        findViewById(R.id.tv_add_1).setOnClickListener(this);
        findViewById(R.id.tv_add_2).setOnClickListener(this);
        findViewById(R.id.tv_add_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            // sqlite 增删改查 一
            case R.id.tv_add_1:
                startActivity(new Intent(getApplicationContext(), QueryActivityOne.class));
                break;

            // sqlite 增删改查 二
            case R.id.tv_add_2:
                startActivity(new Intent(getApplicationContext(), QueryActiviyTwo.class));
                break;

            // greenDAO 增删改查
            case R.id.tv_add_3:
                startActivity(new Intent(getApplicationContext(), QueryActiviyGreenDAO.class));
                break;
        }
    }
}

package com.defineview.bill.mysqlitedemo.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.defineview.bill.mysqlitedemo.R;

/**
 * GreendAO 的增删改查
 */
public class QueryActiviyGreenDAO extends AppCompatActivity implements View.OnClickListener {

    private Button mOne, mTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_activiy_green_dao);

        initView();
        initEvent();
    }

    /**
     * 注册点击事件
     */
    private void initEvent() {
        mOne.setOnClickListener(this);
        mTwo.setOnClickListener(this);
    }

    /**
     * 初始化布局
     */
    private void initView() {

        mOne = (Button) findViewById(R.id.button);
        mTwo = (Button) findViewById(R.id.button2);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                startActivity(new Intent(getApplicationContext(), GreenDAO1.class));
                break;

            case R.id.button2:
                startActivity(new Intent(getApplicationContext(), GreenDAO2.class));
                break;
        }
    }
}

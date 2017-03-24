package com.defineview.bill.mysqlitedemo.UI;

import android.app.Application;

/**
 * 通用的全局Application
 * Created by anye0 on 2016/7/24.
 */
public class MyApplication extends Application {

    public static MyApplication instances;

    @Override
    public void onCreate() {
        super.onCreate();

        instances = this;
    }

    public static MyApplication getInstances() {
        return instances;
    }

}

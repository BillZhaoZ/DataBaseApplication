package com.defineview.bill.mysqlitedemo.app;

import android.app.Application;

/**
 * 通用的全局Application
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

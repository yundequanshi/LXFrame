package com.base.frame.basic;

import android.annotation.SuppressLint;
import android.app.Application;

import com.blankj.utilcode.util.CrashUtils;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @SuppressLint("MissingPermission")
    public void init() {
        CrashUtils.init();//捕获应用异常信息
    }
}

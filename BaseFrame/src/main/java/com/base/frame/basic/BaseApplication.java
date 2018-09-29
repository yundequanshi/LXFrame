package com.base.frame.basic;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.PackageManager;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;

import com.base.frame.utils.PermissionCheckUtils;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mmkv.MMKV;

import java.util.List;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public void init() {
        if (ActivityCompat.checkSelfPermission(getBaseContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermission();
            return;
        }
        CrashUtils.init();//捕获应用异常信息
        MMKV.initialize(getBaseContext());//缓存信息
    }

    /**
     * 请求权限
     */
    private void requestPermission() {
        PermissionCheckUtils.requestStorage(new PermissionCheckUtils.OnPermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {
                init();
            }
        });
    }
}

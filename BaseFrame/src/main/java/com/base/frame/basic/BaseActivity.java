package com.base.frame.basic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.base.frame.R;
import com.blankj.utilcode.util.ActivityUtils;
import com.qmuiteam.qmui.util.QMUIColorHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.io.Serializable;

public class BaseActivity extends FragmentActivity {

    private final String BUNDLE_KEY = "BUNDLE_KEY";
    public int color = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (color != -1) {
            initSystemBar(color);
        }
    }

    private void initSystemBar(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            window.setBackgroundDrawable(null);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//是否全透明，5.0以上默认半透明
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int defColor = getResources().getColor(color);
            window.setStatusBarColor(defColor);//app主色调
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            // 状态栏透明 需要在创建SystemBarTintManager 之前调用。
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            // 使StatusBarTintView 和 actionbar的颜色保持一致，风格统一。
            tintManager.setStatusBarTintResource(color);//app主色调
            // 设置状态栏的文字颜色
            tintManager.setStatusBarDarkMode(false, this);
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 返回上一层，结束当前
     */
    public void back() {
        onBackPressed();
    }

    /**
     * 跳转activity
     */
    public void StartToActivity(Class<? extends Activity> clz) {
        ActivityUtils.startActivity(clz);
    }

    /**
     * 跳转activity 通过intent
     */
    public void StartToActivity(Intent intent) {
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转activity 传值
     */
    public void StartToActivity(Class<? extends Activity> clz, Object obj) {
        Bundle bundle = new Bundle();
        if (obj != null) {
            bundle.putSerializable(BUNDLE_KEY, (Serializable) obj);
        }
        ActivityUtils.startActivity(clz, bundle);
    }

    /**
     * 获取传值
     */
    public Object GetPassValue() {
        Object object = null;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            object = bundle.getSerializable(BUNDLE_KEY);
        }
        return object;
    }
}

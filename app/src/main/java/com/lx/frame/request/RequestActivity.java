package com.lx.frame.request;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.base.frame.basic.BaseActivity;
import com.base.frame.http.HttpDisposable;
import com.base.frame.http.HttpFactory;
import com.base.frame.http.UploadUtils;
import com.lx.frame.R;
import com.lx.frame.common.APIConstants;
import com.lx.frame.common.HttpRequest;
import com.lx.frame.entity.LoginData;
import com.qmuiteam.qmui.util.QMUIColorHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

public class RequestActivity extends BaseActivity {

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        color = R.color.colorAccent;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        ButterKnife.bind(this);
        request();
    }

    /**
     * 请求
     */
    private void request() {
        Map<String, String> map = new HashMap<>();
        HttpRequest.getInstance()
                .login(map)
                .compose(HttpFactory.<LoginData>schedulers())
                .subscribe(new HttpDisposable<LoginData>() {
                    @Override
                    public void success(LoginData data) {

                    }
                });
    }
}

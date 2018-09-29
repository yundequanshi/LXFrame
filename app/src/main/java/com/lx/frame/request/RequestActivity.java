package com.lx.frame.request;

import android.os.Bundle;

import com.base.frame.basic.BaseActivity;
import com.base.frame.http.HttpDisposable;
import com.base.frame.http.HttpFactory;
import com.base.frame.http.UploadUtils;
import com.lx.frame.R;
import com.lx.frame.common.APIConstants;
import com.lx.frame.common.HttpRequest;
import com.lx.frame.entity.LoginData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

public class RequestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        ButterKnife.bind(this);
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

    /**
     * 上传一张图片
     */
    private void upload(String path) {
        HttpRequest.getInstance(APIConstants.API_UPLOAD_IMAGE)
                .upload(UploadUtils.getMultipartBody(path))
                .compose(HttpFactory.<List<String>>schedulers())
                .subscribe(new HttpDisposable<List<String>>() {
                    @Override
                    public void success(List<String> data) {

                    }
                });
    }

    /**
     * 上传多张图片
     */
    private void uploads(List<String> path) {
        HttpRequest.getInstance(APIConstants.API_UPLOAD_IMAGE)
                .uploads(UploadUtils.getMultipartBodysForPath(path))
                .compose(HttpFactory.<List<String>>schedulers())
                .subscribe(new HttpDisposable<List<String>>() {
                    @Override
                    public void success(List<String> data) {

                    }
                });
    }
}

package com.lx.frame.image;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.base.frame.basic.BaseActivity;
import com.base.frame.http.HttpDisposable;
import com.base.frame.http.HttpFactory;
import com.base.frame.http.UploadUtils;
import com.lx.frame.R;
import com.lx.frame.common.APIConstants;
import com.lx.frame.common.HttpRequest;

import java.util.List;

import butterknife.ButterKnife;

public class ImageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        color = R.color.colorAccent;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
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

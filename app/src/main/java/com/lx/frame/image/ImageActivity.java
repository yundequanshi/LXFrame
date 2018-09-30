package com.lx.frame.image;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.base.frame.basic.BaseActivity;
import com.base.frame.http.HttpDisposable;
import com.base.frame.http.HttpFactory;
import com.base.frame.http.UploadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.lx.frame.R;
import com.lx.frame.common.APIConstants;
import com.lx.frame.common.App;
import com.lx.frame.common.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        color = R.color.colorAccent;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        ((App)getApplicationContext()).setHttpConfig();
    }


    @OnClick({R.id.btn1, R.id.btn2})
    void allOnClick(View view) {
        switch (view.getId()) {
            //选择一张图片
            case R.id.btn1:
                ImageSelectUtils.selectSingleImage(this);
                break;
            //选择多张图片
            case R.id.btn2:
                ImageSelectUtils.selectMultipleImage(this, 3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // 图片、视频、音频选择结果回调
            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
            // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
            switch (requestCode) {
                //单选
                case ImageSelectUtils.CHOOSE_SINGLE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (!selectList.isEmpty()) {
                        upload(selectList.get(0).getCompressPath());
                    }
                    break;
                //多选
                case ImageSelectUtils.CHOOSE_MULTIPLE_REQUEST:
                    List<LocalMedia> selectList1 = PictureSelector.obtainMultipleResult(data);
                    if (!selectList1.isEmpty()) {
                        List<String> paths = new ArrayList<>();
                        for (LocalMedia localMedia : selectList1) {
                            paths.add(localMedia.getCompressPath());
                        }
                        uploads(paths);
                    }
                    break;
            }
        }
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
                        if (!data.isEmpty()) {
                            ToastUtils.showShort("上传成功，地址是" + data.get(0));
                        }
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
                        String sAll = "";
                        for (String s : data) {
                            sAll += s + ",";
                        }
                        ToastUtils.showShort("上传成功，地址是" + sAll);
                    }
                });
    }
}

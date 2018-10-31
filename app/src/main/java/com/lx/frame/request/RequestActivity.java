package com.lx.frame.request;

import android.os.Bundle;
import android.view.View;

import com.base.frame.basic.BaseActivity;
import com.base.frame.http.HttpDisposable;
import com.base.frame.http.HttpException;
import com.base.frame.http.HttpFactory;
import com.base.frame.http.HttpResponseInterface;
import com.base.frame.httpError.HttpErrorDisposable;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.lx.frame.R;
import com.lx.frame.common.APIConstants;
import com.lx.frame.common.App;
import com.lx.frame.common.HttpRequest;
import com.lx.frame.common.HttpRequestError;
import com.lx.frame.entity.LoginData;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        color = R.color.colorAccent;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    void allOnClick(View view) {
        switch (view.getId()) {
            //请求1
            case R.id.btn1:
                setHttpConfig();
                request1();
                break;
            //请求2
            case R.id.btn2:
                ((App) getApplicationContext()).setHttpConfig();
                request2();
                break;
            //请求3
            case R.id.btn3:
                request3();
                break;
        }
    }

    /**
     * 请求1
     */
    private void request1() {
        Map<String, String> map = new HashMap<>();
        map.put("companyId", "1");
        map.put("createTime", "2");
        map.put("imagesUrl", "3");
        map.put("star", "4");
        map.put("remark", "5");
        map.put("userId", "6");
        map.put("nickName", "7");
        map.put("headPicture", "8");
        HttpRequest.getInstance()
                .addComment(map)
                .compose(HttpFactory.<Boolean>schedulers())
                .subscribe(new HttpDisposable<Boolean>() {
                    @Override
                    public void success(Boolean data) {
                        if (data) {
                            ToastUtils.showShort("添加成功");
                        }
                    }
                });
    }

    /**
     * 请求2
     */
    private void request2() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", "15806692503");
        map.put("password", "123456");
        map.put("phoneInfo", "fp[dlfpss");
        map.put("version", "1.0");
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
     * 请求3
     */
    private void request3() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", "15806692503");
        map.put("password", "123456");
        map.put("phoneInfo", "fp[dlfpss");
        map.put("version", "1.0");
        HttpRequestError.getInstance()
                .login(map)
                .compose(HttpFactory.<LoginData>schedulers())
                .subscribe(new HttpErrorDisposable<LoginData>() {
                    @Override
                    public void success(LoginData data) {

                    }
                });
    }

    /**
     * 初始化(最好放在Application中)
     */
    private void setHttpConfig() {
        HttpFactory.HTTP_HOST_URL = APIConstants.API_DEFAULT_HOST;
        HttpFactory.httpResponseInterface = new HttpResponseInterface() {
            @Override
            public String getResponseData(Gson gson, String response) {
                EmojiResState emojiResState =
                        gson.fromJson(response, EmojiResState.class);
                if (emojiResState.state != 1) {
                    throw new HttpException(emojiResState.msg);
                }
                if (emojiResState.response.code != 40000) {
                    if (emojiResState.response.code == 30000) {
                        throw new HttpException("token失效了");
                    }
                    throw new HttpException(emojiResState.response.msg);
                }
                return gson.toJson(emojiResState.response.data);
            }
        };
    }
}

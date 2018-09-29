package com.lx.frame.common;

import com.base.frame.basic.BaseApplication;
import com.base.frame.http.HttpExceptionInterface;
import com.base.frame.http.HttpFactory;
import com.base.frame.http.HttpResponse;

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initInThis();
    }

    /**
     * 初始化
     */
    private void initInThis() {
        HttpFactory.HTTP_HOST_URL = APIConstants.API_DEFAULT_HOST;
        HttpResponse httpResponse = new HttpResponse();
        HttpFactory.httpResponse = httpResponse;
        HttpFactory.httpExceptionInterface = new HttpExceptionInterface() {
            @Override
            public void getHttpExceptionState(int state) {

            }
        };
    }
}

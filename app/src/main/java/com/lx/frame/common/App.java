package com.lx.frame.common;

import com.base.frame.basic.BaseApplication;
import com.base.frame.http.HttpException;
import com.base.frame.http.HttpFactory;
import com.base.frame.http.HttpResponseInterface;
import com.google.gson.Gson;
import com.lx.frame.request.HttpResponse;

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initInThis();
    }

    /**
     * 初始化
     */
    public void initInThis() {
        setHttpConfig();
    }

    /**
     * 请求配置
     */
    public void setHttpConfig() {
        HttpFactory.HTTP_HOST_URL = APIConstants.API_DEFAULT_HOST2;
        HttpFactory.httpResponseInterface = new HttpResponseInterface() {
            @Override
            public String getResponseData(Gson gson, String response) {
                HttpResponse httpResponse =
                        gson.fromJson(response, HttpResponse.class);
                if (httpResponse.state != 1) {
                    throw new HttpException(httpResponse.msg);
                }

                if (httpResponse.res.code != 40000) {
                    if (httpResponse.res.code == 30000) {
                        throw new HttpException("token失效了");
                    }

                    throw new HttpException(httpResponse.res.msg);
                }
                return gson.toJson(httpResponse.res.data);
            }
        };
    }
}

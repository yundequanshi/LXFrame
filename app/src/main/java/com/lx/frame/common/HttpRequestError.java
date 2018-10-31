package com.lx.frame.common;

import com.base.frame.http.HttpFactory;
import com.base.frame.httpError.HttpFactoryError;
import com.lx.frame.request.API;


public class HttpRequestError {

    private static API Instance;

    public static API getInstance() {
        if (Instance == null) {
            synchronized (HttpRequestError.class) {
                if (Instance == null) {
                    Instance = HttpFactoryError.getInstance(API.class);
                }
            }
        }
        return Instance;
    }

    public static API getInstance(String url) {
        return HttpFactoryError.getChangeUrlInstance(url, API.class);
    }
}

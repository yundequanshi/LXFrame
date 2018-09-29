package com.base.frame.http;

import android.text.TextUtils;

public class HttpException extends RuntimeException {

    private String msg;

    public HttpException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return TextUtils.isEmpty(msg) ? "" : msg;
    }
}

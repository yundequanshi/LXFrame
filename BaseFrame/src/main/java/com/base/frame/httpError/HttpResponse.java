package com.base.frame.httpError;

public class HttpResponse<T> {
    public String msg;
    public HttpResponse.BaseRes<T> res;
    public int state;

    public static class BaseRes<T> {
        public int code;
        public String msg;
        public T data;
    }
}

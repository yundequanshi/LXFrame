package com.base.frame.httpError;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;

import io.reactivex.observers.DisposableObserver;


public abstract class HttpErrorDisposable<T> extends DisposableObserver<T> {

    public HttpErrorDisposable() {
    }

    @Override
    protected void onStart() {
    }

    @Override
    public void onNext(T value) {
        success(value);
    }

    @Override
    public void onError(Throwable e) {
        String errorMsg = e.getMessage();
        try {
            HttpResponse httpResponse =
                    new Gson().fromJson(errorMsg, HttpResponse.class);
            if (httpResponse.state == 1000) {
                ToastUtils.showShort(httpResponse.msg);
            }
        } catch (Exception exception) {
            ToastUtils.showShort(exception.toString());
        }

    }

    @Override
    public void onComplete() {
    }

    public abstract void success(T t);
}

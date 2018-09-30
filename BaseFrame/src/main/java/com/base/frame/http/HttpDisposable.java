package com.base.frame.http;

import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;

import io.reactivex.observers.DisposableObserver;


public abstract class HttpDisposable<T> extends DisposableObserver<T> {

    public HttpDisposable() {
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
        ToastUtils.showShort(e.getMessage());
    }

    @Override
    public void onComplete() {
    }

    public abstract void success(T t);
}

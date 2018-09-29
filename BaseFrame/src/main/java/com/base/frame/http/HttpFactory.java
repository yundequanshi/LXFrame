package com.base.frame.http;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Logger;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class HttpFactory {

    public static String HTTP_HOST_URL = "";
    public static HttpResponse httpResponse = new HttpResponse();
    public static HttpExceptionInterface httpExceptionInterface = null;

    private HttpFactory() {
    }

    private static OkHttpClient HTTP_CLIENT =
            new Builder().
                    addInterceptor(new HttpInterceptor())
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

    private static Retrofit retrofit = null;

    public static <T> T getChangeUrlInstance(String url, Class<T> service) {
        return new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(ResponseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(HTTP_CLIENT)
                .build()
                .create(service);
    }

    public static <T> T getInstance(Class<T> service) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(HTTP_HOST_URL)
                    .addConverterFactory(ResponseConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(HTTP_CLIENT)
                    .build();
        }
        return retrofit.create(service);
    }

    @SuppressWarnings("unchecked")
    public static <T> ObservableTransformer<T, T> schedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}

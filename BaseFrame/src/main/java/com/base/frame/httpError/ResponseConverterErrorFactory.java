package com.base.frame.httpError;

import com.base.frame.http.HttpException;
import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResponseConverterErrorFactory extends Converter.Factory {

    private final Gson mGson;

    public ResponseConverterErrorFactory(Gson gson) {
        this.mGson = gson;
    }

    public static ResponseConverterErrorFactory create() {
        return create(new Gson());
    }

    public static ResponseConverterErrorFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new ResponseConverterErrorFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new BaseResponseBodyConverter(type);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return GsonConverterFactory.create().requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    private class BaseResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private Type mType;

        private BaseResponseBodyConverter(Type mType) {
            this.mType = mType;
        }

        public T convert(ResponseBody response) {
            try {
                String strResponse = response.string();
                HttpResponse httpResponse =
                        mGson.fromJson(strResponse, HttpResponse.class);
                if (httpResponse.state != 1) {
                    throw new HttpException(httpResponse.msg);
                }

                if (httpResponse.res.code == 40000) {
                    return (T) mGson.toJson(httpResponse.res.data);
                } else {
                    throw new HttpException(strResponse);
                }
            } catch (Exception e) {
                throw new HttpException(e.getMessage());
            } finally {
                response.close();
            }
        }
    }
}

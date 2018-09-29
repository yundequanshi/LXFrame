package com.base.frame.http;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ResponseConverterFactory extends Converter.Factory {

    private final Gson mGson;

    public ResponseConverterFactory(Gson gson) {
        this.mGson = gson;
    }

    public static ResponseConverterFactory create() {
        return create(new Gson());
    }

    public static ResponseConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new ResponseConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new BaseResponseBodyConverter(type);
    }

    private class BaseResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private Type mType;

        private BaseResponseBodyConverter(Type mType) {
            this.mType = mType;
        }

        public T convert(ResponseBody response) {
            Object object;
            try {
                String strResponse = response.string();
                if (TextUtils.isEmpty(strResponse)) {
                    throw new HttpException("返回值是空的—-—");
                }
                JSONObject jsonObject = new JSONObject(strResponse);
                int state = jsonObject.getInt(HttpFactory.httpResponse.stateName);
                if (state == HttpFactory.httpResponse.successState) {
                    String strData = jsonObject.getString(HttpFactory.httpResponse.dataName);
                    object = mGson.fromJson(strData, mType);
                } else {
                    if (HttpFactory.httpExceptionInterface != null) {
                        HttpFactory.httpExceptionInterface.getHttpExceptionState(state);
                    }
                    String msg = jsonObject.getString(HttpFactory.httpResponse.msgName);
                    throw new HttpException(msg);
                }
            } catch (JSONException e) {
                throw new HttpException("解析异常—-—");
            } catch (IOException e) {
                throw new HttpException(e.getMessage());
            } finally {
                response.close();
            }
            return (T) object;
        }
    }
}

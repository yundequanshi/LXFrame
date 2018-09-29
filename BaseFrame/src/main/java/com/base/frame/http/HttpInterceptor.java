package com.base.frame.http;

import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 自定义请求拦截器
 */
public class HttpInterceptor implements Interceptor {

  private static final Charset UTF8 = Charset.forName("UTF-8");
  private static String REQUEST_TAG = "请求";

  @Override
  public Response intercept(Chain chain) throws IOException {
    if (!NetworkUtils.isConnected()) {
      throw new HttpException("网络连接异常，请检查网络后重试");
    }
    Request request = chain.request();
    Response response = chain.proceed(request);
    logRequest(request);
    logResponse(response);
    return response;
  }

  private void logResponse(Response response) {
    try {
      ResponseBody responseBody = response.body();
      String rBody = null;
      BufferedSource source = responseBody.source();
      source.request(Long.MAX_VALUE); // Buffer the entire body.
      Buffer buffer = source.buffer();
      Charset charset = UTF8;
      MediaType contentType = responseBody.contentType();
      if (contentType != null) {
        try {
          charset = contentType.charset(UTF8);
        } catch (UnsupportedCharsetException e) {
          e.printStackTrace();
        }
      }
      rBody = buffer.clone().readString(charset);
      Log.d(REQUEST_TAG + "返回值", rBody);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void logRequest(Request request) {
    Log.d(REQUEST_TAG + "url", request.url().toString());
    if (request.method().equals("GET")) {
      return;
    }
    try {
      RequestBody requestBody = request.body();
      String parameter = null;
      Buffer buffer = new Buffer();
      requestBody.writeTo(buffer);
      parameter = buffer.readString(UTF8);
      buffer.flush();
      buffer.close();
      Log.d(REQUEST_TAG + "参数", parameter);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}


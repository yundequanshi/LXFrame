package com.lx.frame.request;

import com.lx.frame.entity.LoginData;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

public interface API {

    @Multipart
    @POST("file/uploadImages")
    Observable<List<String>> upload(@Part MultipartBody.Part file);

    @Multipart
    @POST("file/uploadImages")
    Observable<List<String>> uploads(@Part List<MultipartBody.Part> file);

    //登陆
    Observable<LoginData> login(@Body Map<String, String> map);

    //添加表情
    @POST("user/addComment")
    Observable<Boolean> addComment(@Body Map<String, String> map);
}

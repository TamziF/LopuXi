package com.example.lopuxi.network;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiService {

    @POST("registration")
    Call<RegAnswer> registration(@Body PostAuthReg postAuthReg);

    @POST("auth")
    Call<AuthAnswer> authorization(@Body PostAuthReg postAuthReg);

    @Multipart
    @Headers({
            "Connect-TimeOut: 240", // Таймаут соединения в секундах
            "Read-TimeOut: 240"     // Таймаут чтения в секундах
    })
    @POST("upload")
    Call<UploadAnswer> uploadImage(@Header("Authorization") String token, @Part MultipartBody.Part file);

    @GET
    Call<ArrayList<Post>> getFeed(@Header("Authorization") String token, @Url String url);

    @POST("post")
    Call<Post> createPost(@Header("Authorization") String token, @Body CreatePostForm post);
}
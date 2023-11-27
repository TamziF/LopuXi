package com.example.lopuxi.network;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;
public interface MarsApiService {
    @GET("photos")
    Call<List<MarsPhoto>> getPhotos();
}
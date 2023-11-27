package com.example.lopuxi.network;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class App extends Application {

    private static MarsApiService marsApiService;

    private static ApiService Api;

    private Retrofit retrofit;

    public static String BASE_URL = "https://mouse-enormous-probably.ngrok-free.app/";
    //public static String BASE_URL = "https://7d98-193-41-140-122.ngrok.io/";

    public static String username;
    public static String password;

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        Api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService.class);
    }

    public static void reNewDate(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LopuXi", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        password = sharedPreferences.getString("password", "");
    }

    public static MarsApiService getMarsApiService() {
        return marsApiService;
    }

    public static ApiService getApi() {
        return Api;
    }
}

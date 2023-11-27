package com.example.lopuxi.mainpart;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.lopuxi.ApiStatus;
import com.example.lopuxi.network.App;
import com.example.lopuxi.network.CreatePostForm;
import com.example.lopuxi.network.MarsApiService;
import com.example.lopuxi.network.MarsPhoto;
import com.example.lopuxi.network.Post;
import com.example.lopuxi.network.UploadAnswer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends ViewModel {

    private MutableLiveData<ApiStatus> _feedStatus = new MutableLiveData<ApiStatus>();
    public LiveData<ApiStatus> feedStatus = _feedStatus;

    private MutableLiveData<ArrayList<String>> _tokens = new MutableLiveData<ArrayList<String>>();
    public LiveData<ArrayList<String>> tokens = _tokens;

    public ArrayList<Post> posts = new ArrayList<>();

    public String token;

    public Integer requestNumber = 0;

    public void setEndGetFeed(boolean endGetFeed) {
        this.endGetFeed = endGetFeed;
    }

    private boolean endGetFeed = false;

    private MutableLiveData<ApiStatus> _postStatus = new MutableLiveData<ApiStatus>();
    public LiveData<ApiStatus> postStatus = _postStatus;

    public void getFeed() {
        if(!endGetFeed){
            _feedStatus.setValue(ApiStatus.LOADING);
            App.getApi().getFeed("Bearer " + token, App.BASE_URL + "feed/" + requestNumber.toString()).enqueue(new Callback<ArrayList<Post>>() {
                @Override
                public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                    if(response.body() != null){
                        posts.addAll(response.body());
                        requestNumber++;
                        _feedStatus.setValue(ApiStatus.DONE);
                    }
                    if(response.body().size() == 0){
                        endGetFeed = true;
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                    _feedStatus.setValue(ApiStatus.ERROR);
                }
            });
        }
    }

    public void uploadImage(String uri) {

        File file = new File(uri);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        App.getApi().uploadImage("Bearer " + token, body).enqueue(new Callback<UploadAnswer>() {
            @Override
            public void onResponse(Call<UploadAnswer> call, Response<UploadAnswer> response) {
                if(_tokens.getValue() != null){
                    ArrayList<String> list = _tokens.getValue();
                    list.add(response.body().uri);
                    _tokens.setValue(list);
                }
                else {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(response.body().uri);
                    _tokens.setValue(list);
                }
                //createPost("pipipopa", "какой же он Рыжий");
                Log.v("TESTOSTERON", "что-то есть");
            }

            @Override
            public void onFailure(Call<UploadAnswer> call, Throwable t) {
                //_tokens.setValue(t.toString());
                Log.v("TESTOSTERON", "ERROR: " + t.toString());
            }
        });

    }

    public void createPost(String author, String text) {

        _postStatus.setValue(ApiStatus.LOADING);

        App.getApi().createPost("Bearer " + token, new CreatePostForm(author, text, tokens.getValue())).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.v("TESTOSTERON", "Это окей");
                _postStatus.setValue(ApiStatus.DONE);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.v("TESTOSTERON", t.toString());
                _postStatus.setValue(ApiStatus.ERROR);
            }
        });

    }

}
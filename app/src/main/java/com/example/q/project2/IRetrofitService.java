package com.example.q.project2;

import android.graphics.Bitmap;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IRetrofitService {

    @GET("getphotos")
    Call<List<String>> getPhotosId();

    @GET("uploads/{id}")
    Call<Bitmap> test(String id);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://52.231.153.77:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}

package com.example.petrescue.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {


    public static Retrofit generateRetrofit() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        return new Retrofit.Builder()
                .baseUrl("http://192.168.0.5:8888/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}

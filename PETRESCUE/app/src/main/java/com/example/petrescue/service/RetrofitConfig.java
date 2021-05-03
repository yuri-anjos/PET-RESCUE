package com.example.petrescue.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    public static Retrofit generateRetrofit(){
        return new Retrofit.Builder()
                .baseUrl("http://192.168.0.4:8888/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

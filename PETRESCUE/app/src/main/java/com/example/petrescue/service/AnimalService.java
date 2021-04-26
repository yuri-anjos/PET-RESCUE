package com.example.petrescue.service;

import com.example.petrescue.domain.Animal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AnimalService {

    @GET("animal")
    Call<List<Animal>> buscarAnimaisAdocao(@Query("pg") Integer pg);

}

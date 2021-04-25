package com.example.petrescue.service;

import com.example.petrescue.domain.VaquinhaDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VaquinhaService {

    @GET("vaquinha")
    Call<List<VaquinhaDTO>> buscarVaquinhas(@Query("pg") Integer pg);
}

package com.example.petrescue.service;

import com.example.petrescue.domain.Vaquinha;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VaquinhaService {

    @GET("vaquinha")
    Call<List<Vaquinha>> buscarVaquinhas(@Query("pg") Integer pg);

    @GET("vaquinha/{idvaquinha}")
    Call<Vaquinha> buscarVaquinhaId(@Path("idvaquinha") Integer idvaquinha);

    @POST("vaquinha")
    Call<Vaquinha> cadastrarVaquinha(@Body Vaquinha vaquinha);

    @POST("vaquinha/editar")
    Call<Vaquinha> editarVaquinha(@Body Vaquinha vaquinha);
}

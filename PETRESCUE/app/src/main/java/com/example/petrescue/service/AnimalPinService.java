package com.example.petrescue.service;

import com.example.petrescue.domain.AnimalPIN;
import com.example.petrescue.domain.subClasses.Localizacao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AnimalPinService {

    @POST("animalpin/buscar")
    Call<List<AnimalPIN>> buscarAnimaisPin(@Body Localizacao localizacao);

    @GET("animalpin/usuario/{idusuario}")
    Call<List<AnimalPIN>> buscarAnimaisPinUsuarioId(@Path("idusuario") Integer idusuario);

    @GET("animalpin/{idanimalpin}")
    Call<AnimalPIN> buscarAnimalPinId(@Path("idanimalpin") Integer idanimalpin);

    @POST("animalpin")
    Call<AnimalPIN> cadastrarAnimalPIN(@Body AnimalPIN AnimalPIN);

    @PUT("animalpin/editar")
    Call<AnimalPIN> editarAnimalPIN(@Body AnimalPIN AnimalPIN);
}

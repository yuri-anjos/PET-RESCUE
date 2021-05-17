package com.example.petrescue.service;

import com.example.petrescue.domain.Animal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AnimalService {

    @GET("animal")
    Call<List<Animal>> buscarAnimaisAdocao(@Query("pg") Integer pg);

    @GET("animal/{idanimal}")
    Call<Animal> buscarAnimalAdocaoId(@Path("idanimal") Integer idanimal);

    @GET("animal/usuario/{idusuario}")
    Call<List<Animal>> buscarAnimaisAdocaoUsuarioId(@Path("idusuario") Integer idusuario);

    @GET("animal/adotar/{idanimal}")
    Call<Animal> adotarAnimal(@Path("idanimal") Integer idanimal);

    @POST("animal")
    Call<Animal> cadastrarAnimal(@Body Animal animal);

    @PUT("animal/editar")
    Call<Animal> editarAnimal(@Body Animal animal);
}

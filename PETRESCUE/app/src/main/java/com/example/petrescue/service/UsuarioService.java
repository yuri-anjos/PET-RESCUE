package com.example.petrescue.service;

import com.example.petrescue.domain.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UsuarioService {

    @POST("usuario/cadastro")
    Call<Usuario> cadastrar(@Body Usuario usuario);

    @POST("usuario/login")
    Call<Usuario> logar(@Body Usuario usuario);

    @GET("usuario/{idusuario}")
    Call<Usuario> buscarUsuarioId(@Path("idusuario") Integer idusuario);

//    @GET("product")
//    Call<List<Product>> get();
//
//    @PUT("product/{id}")
//    Call<Product> edit(@Path("id") Integer id, @Body Product product);
//
//    @DELETE("product/{id}")
//    Call<Void> delete(@Path("id") Integer id);
}

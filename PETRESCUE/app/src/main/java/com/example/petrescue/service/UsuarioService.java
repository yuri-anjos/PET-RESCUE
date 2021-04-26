package com.example.petrescue.service;

import com.example.petrescue.domain.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioService {

    @POST("usuario/cadastro")
    Call<Usuario> cadastrar(@Body Usuario usuario);

    @POST("usuario/login")
    Call<Usuario> logar(@Body Usuario usuario);

//    @GET("product")
//    Call<List<Product>> get();
//
//    @PUT("product/{id}")
//    Call<Product> edit(@Path("id") Integer id, @Body Product product);
//
//    @DELETE("product/{id}")
//    Call<Void> delete(@Path("id") Integer id);
}

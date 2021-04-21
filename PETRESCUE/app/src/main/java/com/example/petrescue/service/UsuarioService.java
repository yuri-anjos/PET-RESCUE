package com.example.petrescue.service;

import com.example.petrescue.domain.UsuarioDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioService {

    @POST("usuario/cadastrar")
    Call<UsuarioDTO> create(@Body UsuarioDTO usuarioDTO);

//    @GET("product")
//    Call<List<Product>> get();
//
//    @PUT("product/{id}")
//    Call<Product> edit(@Path("id") Integer id, @Body Product product);
//
//    @DELETE("product/{id}")
//    Call<Void> delete(@Path("id") Integer id);
}

package com.example.petrescue.service;

import com.example.petrescue.domain.Conversa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ConversaService {

    @GET("conversa/{idusuario1}/{idusuario2}")
    Call<Integer> buscarConversaAmbosUsuarios(@Path("idusuario1") Integer idusuario1, @Path("idusuario2") Integer idusuario2);

    @GET("conversa/id/{idconversa}/{idusuario}")
    Call<Conversa> buscarConversaId(@Path("idconversa") Integer idconversa, @Path("idusuario") Integer idusuario);

    @GET("conversa/usuario/{idusuario}")
    Call<List<Conversa>> buscarConversasUsuarioId(@Path("idusuario") Integer idusuario);
}

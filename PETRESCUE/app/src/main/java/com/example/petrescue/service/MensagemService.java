package com.example.petrescue.service;

import com.example.petrescue.domain.Mensagem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MensagemService {

    @GET("mensagem/{idconversa}")
    Call<List<Mensagem>> buscarMensagensConversaId(@Path("idconversa") Integer idconversa);

    @POST("mensagem")
    Call<Mensagem> cadastrarMensagem(@Body Mensagem mensagem);
}

package com.example.petrescue.service;


import com.example.petrescue.domain.Doacao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DoacaoService {

    @POST("doacao")
    Call<Void> doarParaVaquinha(@Body Doacao doacao);

    @GET("doacao/vaquinha/{idvaquinha}")
    Call<List<Doacao>> buscarDoacoesVaquinhaId(@Path("idvaquinha") Integer idvaquinha);
}

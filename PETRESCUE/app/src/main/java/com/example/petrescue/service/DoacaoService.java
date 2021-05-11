package com.example.petrescue.service;


import com.example.petrescue.domain.Doacao;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DoacaoService {

    @POST("doacao")
    Call<Void> doarParaVaquinha(@Body Doacao doacao);
}

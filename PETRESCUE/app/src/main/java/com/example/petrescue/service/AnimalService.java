package com.example.petrescue.service;

import com.example.petrescue.domain.AnimalDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AnimalService {

    @GET("animal")
    Call<List<AnimalDTO>> buscarAnimaisAdocao();
}

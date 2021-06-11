package com.example.petrescue.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petrescue.ControleActivity;
import com.example.petrescue.R;
import com.example.petrescue.domain.AnimalPIN;
import com.example.petrescue.domain.adapter.AdapterPin;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.domain.subClasses.Localizacao;
import com.example.petrescue.service.AnimalPinService;
import com.example.petrescue.service.RetrofitConfig;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListaAnimalPin extends Fragment implements AdapterPin.OnPinListener {

    private RecyclerView recyclerView;
    private AdapterPin adapterPin;
    private List<AnimalPIN> animalPINList;
    private Retrofit retrofit;
    private AnimalPinService animalPinService;
    private View view;
    private Localizacao localizacao;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_lista_pin, container, false);

        this.inicializaComponentes(this.view);
        localizacao = (Localizacao) getArguments().getSerializable("location");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        buscarPins();
    }

    private void inicializaComponentes(View v) {
        this.recyclerView = v.findViewById(R.id.rv_listapin);

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.animalPinService = retrofit.create(AnimalPinService.class);

        this.animalPINList = new ArrayList<>();
        this.adapterPin = new AdapterPin(this.animalPINList, this);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(this.adapterPin);
    }

    private void buscarPins() {
        this.animalPinService.buscarAnimaisPin(localizacao).enqueue(new Callback<List<AnimalPIN>>() {
            @Override
            public void onResponse(Call<List<AnimalPIN>> call, Response<List<AnimalPIN>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        Toast.makeText(getActivity(), "A lista não foi atualizada pois não foi retornado NENHUMA vaquinha do servidor!", Toast.LENGTH_LONG).show();
                    } else {
                        animalPINList.clear();
                        animalPINList.addAll(response.body());
                        adapterPin.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<AnimalPIN>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidor, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
        });

    }


    @Override
    public void onPinClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("idpin", this.animalPINList.get(position).getId());
        Navigation.findNavController(view).navigate(R.id.action_nav_lista_pin_to_nav_pin, bundle);
    }
}

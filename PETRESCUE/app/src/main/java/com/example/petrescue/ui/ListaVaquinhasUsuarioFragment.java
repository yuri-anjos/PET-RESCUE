package com.example.petrescue.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petrescue.R;
import com.example.petrescue.domain.Vaquinha;
import com.example.petrescue.domain.adapter.AdapterVaquinha;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.RetrofitConfig;
import com.example.petrescue.service.VaquinhaService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListaVaquinhasUsuarioFragment extends Fragment implements AdapterVaquinha.OnVaquinhaListener {

    private RecyclerView recyclerView;
    private AdapterVaquinha vaquinhaAdapter;
    private List<Vaquinha> listaVaquinha;
    private Retrofit retrofit;
    private VaquinhaService vaquinhaService;
    private View view;
    private Integer idusuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_lista_vaquinhas_usuario, container, false);
        this.idusuario = getArguments().getInt("idusuario");
        this.inicializaComponentes(this.view);
        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.buscarAnimaisAdocao();
    }

    private void inicializaComponentes(View v) {
        this.recyclerView = v.findViewById(R.id.rv_listavaquinhasusuario);
        this.retrofit = RetrofitConfig.generateRetrofit();
        this.vaquinhaService = retrofit.create(VaquinhaService.class);

        this.listaVaquinha = new ArrayList<>();
        this.vaquinhaAdapter = new AdapterVaquinha(this.listaVaquinha, this);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(this.vaquinhaAdapter);
    }

    private void buscarAnimaisAdocao() {
        this.vaquinhaService.buscarVaquinhasUsuarioId(this.idusuario).enqueue(new Callback<List<Vaquinha>>() {
            @Override
            public void onResponse(Call<List<Vaquinha>> call, Response<List<Vaquinha>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        Toast.makeText(getActivity(), "A lista não foi atualizada pois não foi retornado NENHUMA vaquinha do servidor!", Toast.LENGTH_LONG).show();
                    } else {
                        listaVaquinha.clear();
                        listaVaquinha.addAll(response.body());
                        vaquinhaAdapter.notifyDataSetChanged();                    }
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Vaquinha>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidor, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
        });
    }

    @Override
    public void onVaquinhaClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("idvaquinha", this.listaVaquinha.get(position).getId());
        Navigation.findNavController(this.view).navigate(R.id.action_nav_lista_vaquinhas_usuario_to_nav_vaquinha, bundle);
    }
}
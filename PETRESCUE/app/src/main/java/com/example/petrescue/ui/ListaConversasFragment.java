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

import com.example.petrescue.ControleActivity;
import com.example.petrescue.R;
import com.example.petrescue.domain.Conversa;
import com.example.petrescue.domain.Vaquinha;
import com.example.petrescue.domain.adapter.AdapterConversa;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.ConversaService;
import com.example.petrescue.service.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListaConversasFragment extends Fragment implements AdapterConversa.OnConversaListener {

    private RecyclerView recyclerView;
    private AdapterConversa adapterConversa;
    private List<Conversa> listaConversa;
    private Retrofit retrofit;
    private ConversaService conversaService;
    private View v;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.v = inflater.inflate(R.layout.fragment_lista_conversas, container, false);

        this.inicializaComponentes(this.v);

        return this.v;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.buscarConversas();
    }

    private void buscarConversas(){
        this.conversaService.buscarConversasUsuarioId(ControleActivity.USUARIO.getId()).enqueue(new Callback<List<Conversa>>() {
            @Override
            public void onResponse(Call<List<Conversa>> call, Response<List<Conversa>> response) {
                if (response.isSuccessful()) {
                    listaConversa.clear();
                    listaConversa.addAll(response.body());
                    if (listaConversa.isEmpty()) {
                        Toast.makeText(getActivity(), "Você não possui contatos!", Toast.LENGTH_LONG).show();
                    }
                    adapterConversa.notifyDataSetChanged();
                } else {
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Conversa>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidor, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
        });
    }

    private void inicializaComponentes(View v) {
        this.recyclerView = v.findViewById(R.id.rv_conversas);

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.conversaService = retrofit.create(ConversaService.class);

        this.listaConversa = new ArrayList<>();
        this.adapterConversa = new AdapterConversa(this.listaConversa, this);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(this.adapterConversa);
    }

    @Override
    public void onConversaClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("idconversa", this.listaConversa.get(position).getId());
        Navigation.findNavController(v).navigate(R.id.action_nav_conversas_to_nav_chat, bundle);
    }
}

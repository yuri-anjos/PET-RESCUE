package com.example.petrescue.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class ListaVaquinhasFragment extends Fragment implements AdapterVaquinha.OnVaquinhaListener {

    private RecyclerView recyclerView;
    private AdapterVaquinha vaquinhaAdapter;
    private List<Vaquinha> listaVaquinha;
    private Integer pagina;
    private Retrofit retrofit;
    private VaquinhaService vaquinhaService;

    private Button btMinusPage;
    private Button btPlusPage;
    private TextView tvActualPage;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_lista_vaquinhas, container, false);
        this.inicializaComponentes(this.view);

        this.btMinusPage.setOnClickListener(v -> this.buscarAnimaisAdocao(pagina - 1));
        this.btPlusPage.setOnClickListener(v -> this.buscarAnimaisAdocao(pagina + 1));

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.buscarAnimaisAdocao(this.pagina);
    }

    private void inicializaComponentes(View v) {
        this.recyclerView = v.findViewById(R.id.rv_listavaquinhas);
        this.btMinusPage = v.findViewById(R.id.bt_minuspage_listavaquinhas);
        this.btPlusPage = v.findViewById(R.id.bt_pluspage_listavaquinhas);
        this.tvActualPage = v.findViewById(R.id.tv_actualpage_listavaquinhas);
        this.pagina = 0;

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.vaquinhaService = retrofit.create(VaquinhaService.class);

        this.listaVaquinha = new ArrayList<>();
        this.vaquinhaAdapter = new AdapterVaquinha(this.listaVaquinha, this);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(this.vaquinhaAdapter);
    }

    private void buscarAnimaisAdocao(Integer pg) {
        this.vaquinhaService.buscarVaquinhas(pagina).enqueue(new Callback<List<Vaquinha>>() {
            @Override
            public void onResponse(Call<List<Vaquinha>> call, Response<List<Vaquinha>> response) {
                if (response.isSuccessful()) {
                    listaVaquinha.clear();
                    listaVaquinha.addAll(response.body());
                    if (listaVaquinha.isEmpty()) {
                        Toast.makeText(getActivity(), "A lista não foi atualizada pois não foi retornado NENHUMA vaquinha do servidor!", Toast.LENGTH_LONG).show();
                    }
                    vaquinhaAdapter.notifyDataSetChanged();
                    pagina = pg;

                    if (listaVaquinha.size() == 10) {
                        btPlusPage.setEnabled(true);
                    } else {
                        btPlusPage.setEnabled(false);
                    }

                    if (pg == 0) {
                        btMinusPage.setEnabled(false);
                    } else {
                        btMinusPage.setEnabled(true);
                    }

                    btPlusPage.setText(Integer.toString(pg + 2));
                    btMinusPage.setText(Integer.toString(pg));
                    tvActualPage.setText(Integer.toString(pg + 1));
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
        Navigation.findNavController(this.view).navigate(R.id.action_nav_lista_vaquinhas_to_nav_vaquinha, bundle);
    }
}
package com.example.petrescue.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.petrescue.R;
import com.example.petrescue.domain.Animal;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.AnimalService;
import com.example.petrescue.service.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListaAdocoesFragment extends Fragment {

    private List<Animal> listaAnimalAdocao;
    private ArrayAdapter<Animal> animalArrayAdapter;
    private Integer pagina;
    private Retrofit retrofit;
    private AnimalService animalService;

    private ListView lvAnimaisAdocao;
    private Button btMinusPage;
    private Button btPlusPage;
    private TextView tvActualPage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lista_adocoes, container, false);
        this.inicializaComponentes(root);

        this.lvAnimaisAdocao.setOnItemClickListener((parent, view, position, id) -> {

        });

        this.btMinusPage.setOnClickListener(v -> this.buscarAnimaisAdocao(pagina - 1));
        this.btPlusPage.setOnClickListener(v -> this.buscarAnimaisAdocao(pagina + 1));

        return root;
    }

    private void inicializaComponentes(View v) {
        this.lvAnimaisAdocao = v.findViewById(R.id.lv_animais_listaadocoes);
        this.btMinusPage = v.findViewById(R.id.bt_minuspage_listaadocoes);
        this.btPlusPage = v.findViewById(R.id.bt_pluspage_listaadocoes);
        this.tvActualPage = v.findViewById(R.id.tv_actualpage_listaadocoes);
        this.pagina = 0;

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.animalService = retrofit.create(AnimalService.class);

        this.listaAnimalAdocao = new ArrayList<>();
        this.animalArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, this.listaAnimalAdocao);
        this.lvAnimaisAdocao.setAdapter(this.animalArrayAdapter);

        this.buscarAnimaisAdocao(this.pagina);
    }

    private void buscarAnimaisAdocao(Integer pg) {
        this.animalService.buscarAnimaisAdocao(pagina).enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful()) {
                    listaAnimalAdocao.clear();
                    listaAnimalAdocao.addAll(response.body());
                    if (listaAnimalAdocao.isEmpty()) {
                        Toast.makeText(getActivity(), "A lista não foi atualizada pois não foi retornado NENHUM animal do servidor!", Toast.LENGTH_LONG).show();
                    }
                    animalArrayAdapter.notifyDataSetChanged();
                    pagina = pg;

                    if (listaAnimalAdocao.size() == 10) {
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
                    tvActualPage.setText(Integer.toString(pg+1));

                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                Log.i("DEBUG", t.getMessage());
            }
        });
    }
}
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
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petrescue.R;
import com.example.petrescue.domain.Animal;
import com.example.petrescue.domain.adapter.AdapterAnimal;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.AnimalService;
import com.example.petrescue.service.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListaAdocoesFragment extends Fragment implements AdapterAnimal.OnAnimalListener {

    private RecyclerView recyclerView;
    private AdapterAnimal animalAdapter;
    private List<Animal> listaAnimalAdocao;
    private int pagina;
    private Retrofit retrofit;
    private AnimalService animalService;

    private Button btCadastrar;
    private Button btMinusPage;
    private Button btPlusPage;
    private TextView tvActualPage;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_lista_adocoes, container, false);
        this.inicializaComponentes(this.view);

        this.btMinusPage.setOnClickListener(v -> this.buscarAnimaisAdocao(pagina - 1));
        this.btPlusPage.setOnClickListener(v -> this.buscarAnimaisAdocao(pagina + 1));

        this.btCadastrar.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("animal", new Animal());
            Navigation.findNavController(v).navigate(R.id.action_nav_lista_adocao_to_nav_form_animal_adocao, bundle);
        });

        return this.view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.buscarAnimaisAdocao(this.pagina);
    }

    private void inicializaComponentes(View v) {
        this.recyclerView = v.findViewById(R.id.rv_listaadocoes);
        this.btMinusPage = v.findViewById(R.id.bt_minuspage_listaadocoes);
        this.btPlusPage = v.findViewById(R.id.bt_pluspage_listaadocoes);
        this.tvActualPage = v.findViewById(R.id.tv_actualpage_listaadocoes);
        this.btCadastrar = v.findViewById(R.id.bt_cadastrar_listaadocoes);
        this.pagina = 0;

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.animalService = retrofit.create(AnimalService.class);

        this.listaAnimalAdocao = new ArrayList<>();
        this.animalAdapter = new AdapterAnimal(this.listaAnimalAdocao, this);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        this.recyclerView.setAdapter(this.animalAdapter);
    }

    private void buscarAnimaisAdocao(int pg) {
        this.animalService.buscarAnimaisAdocao(pg).enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        Toast.makeText(getActivity(), "A lista n??o foi atualizada pois n??o foi retornado NENHUM animal do servidor!", Toast.LENGTH_LONG).show();
                    } else {
                        listaAnimalAdocao.clear();
                        listaAnimalAdocao.addAll(response.body());
                        animalAdapter.notifyDataSetChanged();
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
                        tvActualPage.setText(Integer.toString(pg + 1));
                    }
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidor, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", t.getMessage());
            }
        });
    }

    @Override
    public void onAnimalClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("idanimal", this.listaAnimalAdocao.get(position).getId());
        Navigation.findNavController(this.view).navigate(R.id.action_nav_lista_adocao_to_nav_animal_adocao, bundle);
    }
}
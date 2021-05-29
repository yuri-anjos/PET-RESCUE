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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petrescue.R;
import com.example.petrescue.domain.Usuario;
import com.example.petrescue.domain.adapter.AdapterInstituicoes;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.RetrofitConfig;
import com.example.petrescue.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListaInstituicoesFragment extends Fragment implements AdapterInstituicoes.OnInstituicaoListener {

    private RecyclerView recyclerView;
    private AdapterInstituicoes adapterInstituicoes;
    private List<Usuario> listaInstituicoes;
    private Retrofit retrofit;
    private UsuarioService usuarioService;
    private View view;

    private int pagina;
    private Button btMinusPage;
    private Button btPlusPage;
    private TextView tvActualPage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.fragment_lista_instituicoes, container, false);

        this.inicializaComponentes(this.view);

        this.btMinusPage.setOnClickListener(v -> this.buscarConversas(pagina - 1));
        this.btPlusPage.setOnClickListener(v -> this.buscarConversas(pagina + 1));

        return this.view;
    }

    private void inicializaComponentes(View v) {
        this.recyclerView = v.findViewById(R.id.rv_listainstituicoes);
        this.btMinusPage = v.findViewById(R.id.bt_minuspage_listainstituicoes);
        this.btPlusPage = v.findViewById(R.id.bt_pluspage_listainstituicoes);
        this.tvActualPage = v.findViewById(R.id.tv_actualpage_listainstituicoes);
        this.pagina = 0;

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.usuarioService = retrofit.create(UsuarioService.class);

        this.listaInstituicoes = new ArrayList<>();
        this.adapterInstituicoes = new AdapterInstituicoes(this.listaInstituicoes, this);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(this.adapterInstituicoes);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.buscarConversas(this.pagina);
    }

    private void buscarConversas(int pg) {
        this.usuarioService.buscarInstituicoes(pg).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        Toast.makeText(getActivity(), "A lista não foi atualizada pois não foi retornado NENHUM abrigo do servidor!", Toast.LENGTH_LONG).show();
                    } else {
                        listaInstituicoes.clear();
                        listaInstituicoes.addAll(response.body());
                        adapterInstituicoes.notifyDataSetChanged();
                        pagina = pg;

                        if (listaInstituicoes.size() == 10) {
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
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidor, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
        });
    }

    @Override
    public void onInstituicaoListener(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("idusuario", this.listaInstituicoes.get(position).getId());
        Navigation.findNavController(this.view).navigate(R.id.action_nav_lista_instituicoes_to_nav_usuario, bundle);
    }
}
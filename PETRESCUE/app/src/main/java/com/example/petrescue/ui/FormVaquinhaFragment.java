package com.example.petrescue.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.petrescue.R;
import com.example.petrescue.domain.Vaquinha;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.VaquinhaService;
import com.example.petrescue.service.RetrofitConfig;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FormVaquinhaFragment extends Fragment {

    private TextInputEditText titulo;
    private TextInputEditText descricao;
    private TextInputEditText meta;
    private TextInputEditText foto;
    private Button salvar;

    private Vaquinha vaquinha;
    private Retrofit retrofit;
    private VaquinhaService vaquinhaService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_form_vaquinha, container, false);

        this.vaquinha = (Vaquinha) getArguments().getSerializable("vaquinha");

        this.inicializaComponentes(root);

        this.salvar.setOnClickListener(v -> {
            this.vaquinha.setTitulo(this.titulo.getText().toString());
            this.vaquinha.setDescricao(this.descricao.getText().toString());
            this.vaquinha.setMeta(this.meta.getText().toString() == null ? null : Double.parseDouble(this.meta.getText().toString()) );
            this.vaquinha.setFoto(this.foto.getText().toString());
            if (this.vaquinha.getId() != null) {
                this.editarVaquinha();
            } else {
                this.cadastrarVaquinha(root);
            }
        });

        return root;
    }

    private void inicializaComponentes(View v) {
        this.titulo = v.findViewById(R.id.et_titulo_formvaquinha);
        this.descricao = v.findViewById(R.id.et_descricao_formvaquinha);
        this.meta = v.findViewById(R.id.et_meta_formvaquinha);
        this.foto = v.findViewById(R.id.et_foto_formvaquinha);
        this.salvar = v.findViewById(R.id.bt_salvar_formvaquinha);

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.vaquinhaService= this.retrofit.create(VaquinhaService.class);

        if (this.vaquinha.getId() != null) {
            this.carregarInputs();
        }
    }

    private void carregarInputs() {
        this.titulo.setText(this.vaquinha.getTitulo());
        this.descricao.setText(this.vaquinha.getDescricao());
        this.meta.setText(this.vaquinha.getMeta() == null ? Double.toString(0.0)  : Double.toString(this.vaquinha.getMeta()));
        this.foto.setText(this.vaquinha.getFoto());
    }

    private void cadastrarVaquinha(View v) {
        this.vaquinhaService.cadastrarVaquinha(this.vaquinha).enqueue(new Callback<Vaquinha>() {
            @Override
            public void onResponse(Call<Vaquinha> call, Response<Vaquinha> response) {
                if (response.isSuccessful()) {
                    getActivity().onBackPressed();
                    Bundle bundle = new Bundle();
                    bundle.putInt("idvaquinha", response.body().getId());
                    Navigation.findNavController(v).navigate(R.id.action_nav_form_vaquinha_to_nav_vaquinha, bundle);
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<Vaquinha> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
        });
    }

    private void editarVaquinha() {
        this.vaquinhaService.editarVaquinha(this.vaquinha).enqueue(new Callback<Vaquinha>() {
            @Override
            public void onResponse(Call<Vaquinha> call, Response<Vaquinha> response) {
                if (response.isSuccessful()) {
                    getActivity().onBackPressed();
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<Vaquinha> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
        });
    }
}
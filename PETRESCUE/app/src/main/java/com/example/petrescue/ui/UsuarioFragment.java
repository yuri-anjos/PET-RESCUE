package com.example.petrescue.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.petrescue.ControleActivity;
import com.example.petrescue.R;
import com.example.petrescue.domain.CarteiraDTO;
import com.example.petrescue.domain.Usuario;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.RetrofitConfig;
import com.example.petrescue.service.UsuarioService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UsuarioFragment extends Fragment {

    private LinearLayout linearLayoutSaldo;
    private ImageView foto;
    private TextView nome;
    private TextView email;
    private TextView saldo;
    private TextInputEditText saldoAdicional;
    private Button adicionarSaldo;
    private Button editar;

    private Retrofit retrofit;
    private UsuarioService usuarioService;
    private Usuario usuario;
    private Integer idusuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_usuario, container, false);

        if (getArguments() != null) {
            this.idusuario = getArguments().getInt("idusuario");
        }

        this.inicializaComponentes(root);

        this.adicionarSaldo.setOnClickListener(v -> {
            this.usuarioService.depositarSaldo(new CarteiraDTO(this.usuario.getId(), Double.parseDouble(this.saldoAdicional.getText().toString()))).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()) {
                        ControleActivity.USUARIO = response.body();
                        Toast.makeText(getActivity(), "Depósito Concluído!", Toast.LENGTH_LONG).show();
                        saldoAdicional.setText(Double.toString(0.0));
                        onResume();
                    } else {
                        Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                        Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "THROW ERROR: " + t.toString());
                }
            });
        });

        this.editar.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_nav_usuario_to_nav_editar_usuario);
        });

        return root;
    }

    private void inicializaComponentes(View v) {
        this.linearLayoutSaldo = v.findViewById(R.id.ll_saldo_usuario);
        this.foto = v.findViewById(R.id.iv_foto_usuario);
        this.nome = v.findViewById(R.id.tv_nome_usuario);
        this.email = v.findViewById(R.id.tv_email_usuario);
        this.saldo = v.findViewById(R.id.tv_saldo_usuario);
        this.saldoAdicional = v.findViewById(R.id.et_saldo_adicional_usuario);
        this.adicionarSaldo = v.findViewById(R.id.bt_adicionar_saldo_usuario);
        this.editar = v.findViewById(R.id.bt_editar_usuario);

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.usuarioService = this.retrofit.create(UsuarioService.class);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (this.idusuario != null && this.idusuario != ControleActivity.USUARIO.getId()) {
            this.usuarioService.buscarUsuarioId(this.idusuario).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()) {
                        usuario = response.body();
                    } else {
                        Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                        Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "THROW ERROR: " + t.toString());
                }
            });
        } else {
            this.usuario = ControleActivity.USUARIO;
        }
        this.atualizaCampos();
    }

    private void atualizaCampos() {
        if (this.usuario.getId().equals(ControleActivity.USUARIO.getId())) {
            this.linearLayoutSaldo.setVisibility(View.VISIBLE);
            this.editar.setVisibility(View.VISIBLE);
        } else {
            this.linearLayoutSaldo.setVisibility(View.GONE);
            this.editar.setVisibility(View.GONE);
        }
    }
}
package com.example.petrescue.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.petrescue.R;
import com.example.petrescue.domain.Usuario;
import com.example.petrescue.service.RetrofitConfig;
import com.example.petrescue.service.UsuarioService;

import retrofit2.Retrofit;

public class EditarUsuarioFragment extends Fragment {

    private Usuario usuario;
    private Retrofit retrofit;
    private UsuarioService usuarioService;

    private Button salvar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_editar_usuario, container, false);

        this.inicializaComponentes(v);

        this.salvar.setOnClickListener(v1 -> {
        });

        return v;
    }

    private void inicializaComponentes(View v) {
        this.salvar = v.findViewById(R.id.bt_salvar_editarusuario);

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.usuarioService = this.retrofit.create(UsuarioService.class);
    }
}

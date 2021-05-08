package com.example.petrescue.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.petrescue.ControleActivity;
import com.example.petrescue.R;
import com.example.petrescue.domain.Usuario;
import com.example.petrescue.service.UsuarioService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Retrofit;

public class UsuarioFragment extends Fragment {

    private ImageView foto;
    private TextView nome;
    private TextView email;
    private TextView saldo;
    private TextInputEditText saldoAdicional;
    private Button btAdicionarSaldo;
    private Button btEditar;

    private Retrofit retrofit;
    private UsuarioService usuarioService;
    private Usuario usuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_usuario, container, false);

        this.inicializaComponentes(root);

        this.btAdicionarSaldo.setOnClickListener(v -> {
            //chama api passando valor
        });

        this.btEditar.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_nav_usuario_to_nav_editar_usuario);
        });

        return root;
    }

    private void inicializaComponentes(View v) {
        this.foto = v.findViewById(R.id.iv_foto_usuario);
        this.nome = v.findViewById(R.id.tv_nome_usuario);
        this.email = v.findViewById(R.id.tv_email_usuario);
        this.saldo = v.findViewById(R.id.tv_saldo_usuario);
        this.saldoAdicional = v.findViewById(R.id.et_saldo_adicional_usuario);
        this.btAdicionarSaldo = v.findViewById(R.id.bt_adicionar_saldo_usuario);
        this.btEditar = v.findViewById(R.id.bt_editar_usuario);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.usuario = ControleActivity.USUARIO;
    }
}
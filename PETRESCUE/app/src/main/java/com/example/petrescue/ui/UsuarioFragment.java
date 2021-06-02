package com.example.petrescue.ui;

import android.graphics.drawable.Drawable;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petrescue.ControleActivity;
import com.example.petrescue.R;
import com.example.petrescue.domain.Animal;
import com.example.petrescue.domain.CarteiraDTO;
import com.example.petrescue.domain.Usuario;
import com.example.petrescue.domain.Vaquinha;
import com.example.petrescue.domain.adapter.AdapterAnimal;
import com.example.petrescue.domain.adapter.AdapterVaquinha;
import com.example.petrescue.domain.enums.TipoUsuario;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.AnimalService;
import com.example.petrescue.service.CircleImageTransform;
import com.example.petrescue.service.ConversaService;
import com.example.petrescue.service.RetrofitConfig;
import com.example.petrescue.service.UsuarioService;
import com.example.petrescue.service.VaquinhaService;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UsuarioFragment extends Fragment {

    private LinearLayout containerSaldo;
    private ImageView foto;
    private TextView nome;
    private TextView email;
    private TextView saldo;
    private TextInputEditText saldoAdicional;
    private Button adicionarSaldo;
    private Button editar;
    private Button conversar;

    private View view;
    private Retrofit retrofit;

    private UsuarioService usuarioService;
    private Usuario usuario;
    private Integer idusuario;

    private ConversaService conversaService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_usuario, container, false);

        if (getArguments() != null) {
            this.idusuario = getArguments().getInt("idusuario");
        }

        this.inicializaComponentes(this.view);

        this.adicionarSaldo.setOnClickListener(v -> {
            this.usuarioService.depositarSaldo(new CarteiraDTO(this.usuario.getId(), this.saldoAdicional.getText().toString().length() > 0 ? Double.parseDouble(this.saldoAdicional.getText().toString()) : 0)).enqueue(new Callback<Usuario>() {
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

        this.conversar.setOnClickListener(v -> {
            this.conversaService = this.retrofit.create(ConversaService.class);
            this.conversaService.buscarConversaAmbosUsuarios(ControleActivity.USUARIO.getId(), this.idusuario).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("idconversa", response.body());
                        Navigation.findNavController(v).navigate(R.id.action_nav_usuario_to_nav_chat, bundle);
                    } else {
                        Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                        Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "THROW ERROR: " + t.toString());
                }
            });
        });

        return this.view;
    }

    private void inicializaComponentes(View v) {
        this.containerSaldo = v.findViewById(R.id.container_saldo_usuario);
        this.foto = v.findViewById(R.id.iv_foto_usuario);
        this.nome = v.findViewById(R.id.tv_nome_usuario);
        this.email = v.findViewById(R.id.tv_email_usuario);
        this.saldo = v.findViewById(R.id.tv_saldo_usuario);
        this.saldoAdicional = v.findViewById(R.id.et_saldo_adicional_usuario);
        this.adicionarSaldo = v.findViewById(R.id.bt_adicionar_saldo_usuario);
        this.editar = v.findViewById(R.id.bt_editar_usuario);
        this.conversar = v.findViewById(R.id.bt_conversar_usuario);
        this.saldoAdicional.setText(Double.toString(0.0));

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
                        atualizaCampos();
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
            this.atualizaCampos();
        }
    }

    private void atualizaCampos() {
        this.nome.setText(this.usuario.getNome());
        this.email.setText(this.usuario.getEmail());
        if (ControleActivity.USUARIO.getId().equals(this.usuario.getId())) {
            this.editar.setVisibility(View.VISIBLE);
            this.conversar.setVisibility(View.GONE);
            this.containerSaldo.setVisibility(View.VISIBLE);
            this.saldo.setVisibility(View.VISIBLE);
            this.saldo.setText("R$: " + this.usuario.getSaldo());
        } else {
            this.containerSaldo.setVisibility(View.GONE);
            this.saldo.setVisibility(View.GONE);
            this.editar.setVisibility(View.GONE);
            this.conversar.setVisibility(View.VISIBLE);
        }
        int img = TipoUsuario.INSTITUCIONAL.equals(this.usuario.getTipoUsuario()) ? R.drawable.instituicoes_icon : R.drawable.perfil_icon ;
        this.foto.setImageResource(img);
        if (this.usuario.getFoto() != null && this.usuario.getFoto().length() > 0) {
            Picasso.get()
                    .load(this.usuario.getFoto()).transform(new CircleImageTransform())
                    .placeholder(img)
                    .error(img)
                    .resize(130, 130)
                    .centerCrop()
                    .into(this.foto);
        }
    }
}
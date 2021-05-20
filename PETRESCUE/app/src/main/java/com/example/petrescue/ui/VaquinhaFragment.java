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
import com.example.petrescue.domain.Doacao;
import com.example.petrescue.domain.Vaquinha;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.DoacaoService;
import com.example.petrescue.service.RetrofitConfig;
import com.example.petrescue.service.VaquinhaService;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VaquinhaFragment extends Fragment {

    private Retrofit retrofit;
    private VaquinhaService vaquinhaService;
    private DoacaoService doacaoService;

    private Integer idvaquinha;
    private Vaquinha vaquinha;
    private Doacao doacao;

    private Button editar;
    private Button doar;
    private Button btAccessarUsuario;
    private TextInputEditText valor;
    private ImageView foto;
    private TextView inicio;
    private TextView titulo;
    private TextView descricao;
    private TextView arrecadado;
    private TextView meta;
    private LinearLayout doador;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_vaquinha, container, false);
        this.idvaquinha = getArguments().getInt("idvaquinha");
        this.inicializaComponentes(v);

        this.editar.setOnClickListener(v1 -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("vaquinha", this.vaquinha);
            Navigation.findNavController(v).navigate(R.id.action_nav_vaquinha_to_nav_form_vaquinha, bundle);
        });

        this.btAccessarUsuario.setOnClickListener(v12 -> {
            Bundle bundle = new Bundle();
            bundle.putInt("idusuario", this.vaquinha.getIdUsuario());
            Navigation.findNavController(v).navigate(R.id.action_nav_vaquinha_to_nav_usuario, bundle);
        });

        this.doar.setOnClickListener(v1 -> {
            this.doacao = new Doacao();
            if (ControleActivity.USUARIO.getSaldo() >= Double.parseDouble(this.valor.getText().toString())) {
                this.doacao.setIdDoador(ControleActivity.USUARIO.getId());
                this.doacao.setIdVaquinha(this.vaquinha.getId());
                this.doacao.setQuantia(Double.parseDouble(this.valor.getText().toString()));
                this.doacaoService = this.retrofit.create(DoacaoService.class);
                this.doacaoService.doarParaVaquinha(this.doacao).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            valor.setText(Double.toString(0.0));
                            Toast.makeText(getActivity(), "Doação Concluída!", Toast.LENGTH_LONG).show();
                            onResume();
                        } else {
                            Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                            Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                        Log.i("DEBUG", "THROW ERROR: " + t.toString());
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Saldo Insuficiente: " + ControleActivity.USUARIO.getSaldo(), Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    private void inicializaComponentes(View v) {
        this.editar = v.findViewById(R.id.bt_editar_vaquinha);
        this.doar = v.findViewById(R.id.bt_doar_vaquinha);
        this.valor = v.findViewById(R.id.et_valor_vaquinha);
        this.foto = v.findViewById(R.id.iv_foto_vaquinha);
        this.inicio = v.findViewById(R.id.tv_inicio_vaquinha);
        this.titulo = v.findViewById(R.id.tv_titulo_vaquinha);
        this.descricao = v.findViewById(R.id.tv_descricao_vaquinha);
        this.arrecadado = v.findViewById(R.id.tv_arrecadado_vaquinha);
        this.meta = v.findViewById(R.id.tv_meta_vaquinha);
        this.doador = v.findViewById(R.id.ll_doador);
        this.btAccessarUsuario = v.findViewById(R.id.bt_acessar_usuario_vaquinha);
        this.valor.setText(Double.toString(0.0));

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.vaquinhaService = this.retrofit.create(VaquinhaService.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.vaquinhaService.buscarVaquinhaId(this.idvaquinha).enqueue(new Callback<Vaquinha>() {
            @Override
            public void onResponse(Call<Vaquinha> call, Response<Vaquinha> response) {
                if (response.isSuccessful()) {
                    vaquinha = response.body();
                    atualizaCampos();
                } else {
                    Toast.makeText(getActivity(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<Vaquinha> call, Throwable t) {
                Toast.makeText(getActivity(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.toString());
            }
        });
    }

    private void atualizaCampos() {
//        this.foto.setImageBitmap();
        this.inicio.setText(new SimpleDateFormat("dd/MM/yyyy").format(this.vaquinha.getInicio()));
        this.titulo.setText(this.vaquinha.getTitulo());
        this.descricao.setText(this.vaquinha.getDescricao());
        this.arrecadado.setText(Double.toString(this.vaquinha.getValorArrecadado()));
        this.meta.setText(Double.toString(this.vaquinha.getMeta()));
        this.btAccessarUsuario.setText("Acesse o perfil de " + this.vaquinha.getNomeUsuario());

        if (ControleActivity.USUARIO.getId().equals(this.vaquinha.getIdUsuario())) {
            this.editar.setVisibility(View.VISIBLE);
            this.doador.setVisibility(View.GONE);
            this.btAccessarUsuario.setVisibility(View.GONE);
        } else {
            this.editar.setVisibility(View.GONE);
            this.doador.setVisibility(View.VISIBLE);
            this.btAccessarUsuario.setVisibility(View.VISIBLE);
        }

        if (this.vaquinha.getAtivo().equals(false)) {
            this.editar.setVisibility(View.GONE);
            this.doador.setVisibility(View.GONE);
        }
    }
}
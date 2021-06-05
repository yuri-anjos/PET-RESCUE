package com.example.petrescue;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.petrescue.domain.Usuario;
import com.example.petrescue.domain.enums.TipoUsuario;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.RetrofitConfig;
import com.example.petrescue.service.UsuarioService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroActivity extends AppCompatActivity {

    private TextInputEditText etNome;
    private TextInputEditText etEmail;
    private TextInputEditText etSenha;
    private TextInputEditText etSenha2;
    private TextInputEditText etFoto;

    private TextInputEditText etCpfCnpj;
    private TextInputEditText etDescricaoOng;

    private LinearLayout llInstituiucao;
    private RadioGroup rgTipoUsuario;
    private Button btCadastroUsuario;

    private Usuario usuario;
    private Retrofit retrofit;
    private UsuarioService usuarioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        this.iniciarComponentes();

        this.rgTipoUsuario.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_individuo_cadastrousuario:
                    this.usuario.setTipoUsuario(TipoUsuario.INDIVIDUO);
                    this.llInstituiucao.setVisibility(View.GONE);
                    break;
                case R.id.rb_instituiucao_cadastrousuario:
                    this.usuario.setTipoUsuario(TipoUsuario.INSTITUCIONAL);
                    this.llInstituiucao.setVisibility(View.VISIBLE);
                    break;
            }
        });

        this.btCadastroUsuario.setOnClickListener(v -> {
            if (this.etSenha.getText().toString().length() > 0 && this.etSenha2.getText().length() > 0 && this.etSenha.getText().toString().equals(this.etSenha2.getText().toString())) {
                this.usuario.setNome(this.etNome.getText().toString());
                this.usuario.setEmail(this.etEmail.getText().toString());
                this.usuario.setSenha(this.etSenha.getText().toString());
                this.usuario.setFoto(this.etFoto.getText().toString());
                if (this.usuario.getTipoUsuario().equals(TipoUsuario.INDIVIDUO)) {
                    this.usuario.setCpfCnpj(null);
                    this.usuario.setDescricao(null);
                } else {
                    this.usuario.setCpfCnpj(this.etCpfCnpj.getText().toString());
                    this.usuario.setDescricao(this.etDescricaoOng.getText().toString());
                }
                this.cadastrarUsuario(this.usuario);
            } else {
                if (this.etSenha.getText().toString().length() > 0 && this.etSenha2.getText().toString().length() > 0) {
                    Toast.makeText(getApplicationContext(), "Senha incorreta!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Senha não pode ser nulo!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void iniciarComponentes() {
        this.etNome = this.findViewById(R.id.et_nome_cadastrousuario);
        this.etEmail = this.findViewById(R.id.et_email_cadastrousuario);
        this.etSenha = this.findViewById(R.id.et_senha_cadastrousuario);
        this.etSenha2 = this.findViewById(R.id.et_senha2_cadastrousuario);
        this.etFoto = this.findViewById(R.id.et_foto_cadastrousuario);

        this.etCpfCnpj = this.findViewById(R.id.et_cpf_cnpj_cadastrousuario);
        this.etDescricaoOng = this.findViewById(R.id.et_descricao_cadastrousuario);
        this.llInstituiucao = this.findViewById(R.id.ll_instituiucao_cadastrousuario);

        this.rgTipoUsuario = this.findViewById(R.id.rg_tipo_cadastrousuario);
        this.btCadastroUsuario = this.findViewById(R.id.bt_cadastrar_cadastrousuario);
        this.usuario = new Usuario();
        this.usuario.setTipoUsuario(TipoUsuario.INDIVIDUO);

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.usuarioService = retrofit.create(UsuarioService.class);
    }

    private void cadastrarUsuario(Usuario usuario) {
        this.usuarioService.cadastrar(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), ControleActivity.class);
                    intent.putExtra("idusuario", response.body().getId());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Falha ao conectar com o servidor, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
        });
    }
}
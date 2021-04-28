package com.example.petrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petrescue.domain.Usuario;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.RetrofitConfig;
import com.example.petrescue.service.UsuarioService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail;
    private TextInputEditText etSenha;
    private Button btLogar;
    private Retrofit retrofit;
    private UsuarioService usuarioService;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.iniciarComponentes();

        this.btLogar.setOnClickListener(v -> {
            this.realizarLogin();
        });
    }

    private void iniciarComponentes() {
        this.etEmail = this.findViewById(R.id.et_email_loginusuario);
        this.etSenha = this.findViewById(R.id.et_senha_loginusuario);
        this.btLogar = this.findViewById(R.id.bt_logar_loginusuario);

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.usuarioService = retrofit.create(UsuarioService.class);
    }

    private void realizarLogin() {
        this.usuario = new Usuario();
        this.usuario.setEmail(this.etEmail.getText().toString());
        this.usuario.setSenha(this.etSenha.getText().toString());

        this.usuarioService.logar(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                    intent.putExtra("usuario", response.body());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), ErrorResponse.formatErrorResponse(response), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Falha ao conectar com o servidos, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
        });

    }
}
package com.example.petrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petrescue.domain.UsuarioDTO;
import com.example.petrescue.domain.subClasses.ErrorResponse;
import com.example.petrescue.service.RetrofitConfig;
import com.example.petrescue.service.UsuarioService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail;
    private TextInputEditText etSenha;
    private TextView tvErroLogin;
    private Button btLogar;
    private Retrofit retrofit;
    private UsuarioService usuarioService;
    private UsuarioDTO usuarioDTO;

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
        this.tvErroLogin = this.findViewById(R.id.tv_erro_loginusuario);

        this.retrofit = RetrofitConfig.generateRetrofit();
        this.usuarioService = retrofit.create(UsuarioService.class);
    }

    private void realizarLogin() {
        this.usuarioDTO = new UsuarioDTO();
        this.usuarioDTO.setEmail(this.etEmail.getText().toString());
        this.usuarioDTO.setSenha(this.etSenha.getText().toString());

        this.usuarioService.logar(usuarioDTO).enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                    intent.putExtra("usuario", response.body());
                    startActivity(intent);
                    finish();
                } else {
                    tvErroLogin.setVisibility(View.VISIBLE);
                    tvErroLogin.setText("Login inválido");
                    ErrorResponse message=new Gson().fromJson(response.errorBody().charStream(),ErrorResponse.class);
                    Toast.makeText(getApplicationContext(), message.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i("DEBUG", "RESPONSE ERROR: " + message);
                }
            }

            @Override
            public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                tvErroLogin.setVisibility(View.VISIBLE);
                tvErroLogin.setText("O servidor está fora, tente novamente mais tarde!");
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
        });

    }
}
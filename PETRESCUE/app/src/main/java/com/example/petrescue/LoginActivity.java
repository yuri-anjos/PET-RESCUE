package com.example.petrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.petrescue.domain.UsuarioDTO;
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

    private void iniciarComponentes(){
        this.etEmail = this.findViewById(R.id.et_email);
        this.etSenha = this.findViewById(R.id.et_senha);
        this.btLogar = this.findViewById(R.id.bt_logar);
    }

    private void realizarLogin(){
        this.usuarioDTO = new UsuarioDTO();
        this.usuarioDTO.setEmail(this.etEmail.getText().toString());
        this.usuarioDTO.setSenha(this.etSenha.getText().toString());

        this.retrofit = RetrofitConfig.generateRetrofit();

        UsuarioService usuarioService = retrofit.create(UsuarioService.class);
        usuarioService.logar(usuarioDTO).enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if (response.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                    intent.putExtra("usuario", (Parcelable) response.body());
                    startActivity(intent);
                    finish();
                }else{
                    Log.i("DEBUG", "RESPONSE ERROR: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                Log.i("DEBUG", "THROW ERROR: " + t.getMessage());
            }
        });

    }
}
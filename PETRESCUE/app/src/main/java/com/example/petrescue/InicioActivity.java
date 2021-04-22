package com.example.petrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class InicioActivity extends AppCompatActivity {

    private Button btLogin;
    private Button btCadastro;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        this.iniciarComponentes();

        this.btLogin.setOnClickListener(v -> {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        this.btCadastro.setOnClickListener(v -> {
            intent = new Intent(this, CadastroActivity.class);
            startActivity(intent);
        });
    }

    private void iniciarComponentes(){
        this.btLogin = this.findViewById(R.id.bt_login_inicio);
        this.btCadastro = this.findViewById(R.id.bt_cadastro_inicio);
    }
}
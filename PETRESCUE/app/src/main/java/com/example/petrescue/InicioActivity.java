package com.example.petrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class InicioActivity extends AppCompatActivity {

    private Button btLogin;
    private Button btInstituicao;
    private Button btIndividuuo;
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

        this.btInstituicao.setOnClickListener(v -> {

        });

        this.btIndividuuo.setOnClickListener(v -> {

        });
    }

    private void iniciarComponentes(){
        this.btLogin = this.findViewById(R.id.bt_login);
        this.btInstituicao = this.findViewById(R.id.bt_instituicao);
        this.btIndividuuo = this.findViewById(R.id.bt_individuo);
    }
}
package com.example.petrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail;
    private TextInputEditText etSenha;
    private Button btLogar;

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
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
    }
}
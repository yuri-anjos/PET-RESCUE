package com.example.petrescue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    private Button btLogin;
    private Button btCadastro;

    private String permissoes[] = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        this.iniciarComponentes();

        this.btLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            boolean continuar = this.verificarPermissoesAtivas();
            if (continuar) {
                startActivity(intent);
            }
        });

        this.btCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(this, CadastroActivity.class);
            boolean continuar = this.verificarPermissoesAtivas();
            if (continuar) {
                startActivity(intent);
            }
        });
    }

    private boolean verificarPermissoesAtivas() {
        List<String> permissoesRequeridas = new ArrayList<>();
        for (String permissao : this.permissoes) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissao) != PackageManager.PERMISSION_GRANTED) {
                permissoesRequeridas.add(permissao);
            }
        }

        if (!permissoesRequeridas.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissoesRequeridas.toArray(new String[permissoesRequeridas.size()]), 1);
            return false;
        }
        return true;
    }

    private void iniciarComponentes() {
        this.btLogin = this.findViewById(R.id.bt_login_inicio);
        this.btCadastro = this.findViewById(R.id.bt_cadastro_inicio);
    }
}
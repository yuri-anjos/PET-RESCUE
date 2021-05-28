package com.example.petrescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
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
import com.example.petrescue.domain.subClasses.Localizacao;
import com.example.petrescue.service.RetrofitConfig;
import com.example.petrescue.service.UsuarioService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroActivity extends AppCompatActivity {

    private TextInputEditText etNome;
    private TextInputEditText etEmail;
    private TextInputEditText etSenha;
    private TextInputEditText etFoto;

    private TextInputEditText etCpfCnpj;
    private TextInputEditText etDescricaoOng;
    private LinearLayout llInstituiucao;

    private RadioGroup rgTipoUsuario;
    private Button btCadastroUsuario;

    private Usuario usuario;
    private Retrofit retrofit;
    private UsuarioService usuarioService;

    private FusedLocationProviderClient cliente;

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
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int errorCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        switch (errorCode) {
            case ConnectionResult.SERVICE_MISSING:
            case ConnectionResult.SERVICE_DISABLED:
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                GoogleApiAvailability.getInstance().getErrorDialog(this, errorCode, 0, dialog -> finish()).show();
                break;
            case ConnectionResult.SUCCESS:
                Log.i("DEBUG", "Conectou na google service.");
                break;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        this.cliente.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location == null) {
                        Log.i("DEBUG", "Erro ao buscar posição atual. sucess");
                    } else {
                        Log.i("DEBUG", "Buscou ultima posicao.");
                        this.usuario.setLocalizacao(new Localizacao(location.getLatitude(), location.getLongitude()));
                    }
                })
                .addOnFailureListener(e -> {
                    Log.i("DEBUG", "Erro ao buscar posição atual. error");
                });

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(20);
        locationRequest.setFastestInterval(10);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(builder.build()).addOnSuccessListener(
                locationSettingsResponse -> {
                    Log.i("DEBUG", "Buscou configuracoes do cliente.");
                })
                .addOnFailureListener(e -> {
                    if (e instanceof ResolvableApiException) {
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(this, 10);
                        } catch (IntentSender.SendIntentException sendIntentException) {
                            sendIntentException.printStackTrace();
                        }
                    }
                });

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult == null) {
                    Log.i("DEBUG", "Localizacao vazia.");
                    return;
                } else {
                    for (Location location : locationResult.getLocations()) {
                        Log.i("DEBUG", "Localizacao atual: " + location.getLatitude() + " " + location.getLongitude());
                    }
                }
            }

            @Override
            public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                Log.i("DEBUG", "Localizacao disponivel.");
            }
        };
        cliente.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void iniciarComponentes() {
        this.etNome = this.findViewById(R.id.et_nome_cadastrousuario);
        this.etEmail = this.findViewById(R.id.et_email_cadastrousuario);
        this.etSenha = this.findViewById(R.id.et_senha_cadastrousuario);
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

        this.cliente = LocationServices.getFusedLocationProviderClient(this);
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